package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowingResponse;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;

/**
 * A DAO for accessing 'followers' data from the database.
 */
public class FollowsDAO {
    private final Table table;
    private final Index index;

    public FollowsDAO() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        table = dynamoDB.getTable("follows");
        index = table.getIndex("follows_index");
    }

    /**
     * Gets the users from the database that the user specified in the request is followed by. Uses
     * information in the request object to limit the number of followers returned and to return the
     * next set of followers after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */
    public LiteFollowersResponse getFollowers(FollowersRequest request) {
        assert request.getLimit() > 0;
        assert request.getFollowee() != null;

        List<String> followersList = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            String lastKeyValue = null;
            if (request.getLastFollower() != null) {
                lastKeyValue = request.getLastFollower().getAlias();
            }
            Map<String, Object> valueMap = new HashMap<String, Object>();
            valueMap.put(":fh", request.getFollowee().getAlias());

            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("followee_handle = :fh")
                    .withValueMap(valueMap)
                    .withScanIndexForward(true)
                    .withMaxResultSize(10);

            if (lastKeyValue != null) {
                querySpec = querySpec.withExclusiveStartKey("followee_handle", request.getFollowee().getAlias(), "follower_handle", lastKeyValue);
            }

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            Item item = null;

            try {
                System.out.println("Getting followers of " + request.getFollowee().getAlias());
                items = index.query(querySpec);

                iterator = items.firstPage().iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    followersList.add(item.getString("follower_handle"));
                }

                Map<String, AttributeValue> lastKey = items.firstPage().getLowLevelResult().getQueryResult().getLastEvaluatedKey();
                if (lastKey != null) {
                    hasMorePages = true;
                    //lastKeyValue = lastKey.get("follower_handle").getS();
                }
            } catch (Exception e) {
                System.err.println("Unable to query followers");
                System.err.println(e.getMessage());
            }
        }

        return new LiteFollowersResponse(followersList, hasMorePages);
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public LiteFollowingResponse getFollowees(FollowingRequest request) {
        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        List<String> followeesList = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            String lastKeyValue = null;
            if (request.getLastFollowee() != null) {
                lastKeyValue = request.getLastFollowee().getAlias();
            }
            Map<String, Object> valueMap = new HashMap<String, Object>();
            valueMap.put(":fh", request.getFollower().getAlias());

            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("follower_handle = :fh")
                    .withValueMap(valueMap)
                    .withScanIndexForward(true)
                    .withMaxResultSize(10);

            if (lastKeyValue != null) {
                querySpec = querySpec.withExclusiveStartKey("followee_handle", lastKeyValue, "follower_handle", request.getFollower().getAlias());
            }

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            Item item = null;

            try {
                System.out.println("Getting followees of " + request.getFollower().getAlias());
                items = table.query(querySpec);

                iterator = items.firstPage().iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    followeesList.add(item.getString("followee_handle"));
                }

                Map<String, AttributeValue> lastKey = lastKey = items.firstPage().getLowLevelResult().getQueryResult().getLastEvaluatedKey();
                if (lastKey != null) {
                    hasMorePages = true;
                    //lastKeyValue = lastKey.get("followee_handle").getS();
                }
            } catch (Exception e) {
                System.err.println("Unable to query followees");
                System.err.println(e.getMessage());
            }
        }

        return new LiteFollowingResponse(followeesList, hasMorePages);
    }

    /**
     * Adds a follows relationship to the follows list.
     *
     * @return response indicating success or failure.
     */
    public FollowResponse follow(FollowRequest request) {
        assert request.getRequestingUser().getAlias() != null;
        assert request.getToBeFollowed().getAlias() != null;
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("followee_handle", request.getToBeFollowed().getAlias(), "follower_handle", request.getRequestingUser().getAlias()));
            return new FollowResponse();
        } catch (Exception e) {
            System.err.println("Unable to add new follow relationship: " + request.getRequestingUser().getAlias() + " follows " + request.getToBeFollowed().getAlias());
            System.err.println(e.getMessage());
            return new FollowResponse("Unable to follow " + request.getToBeFollowed().getAlias());
        }
    }

    /**
     * Removes a follows relationship from the follows list.
     *
     * @return response indicating success or failure.
     */
    public UnfollowResponse unfollow(UnfollowRequest request) {
        assert request.getRequestingUser().getAlias() != null;
        assert request.getToBeUnfollowed().getAlias() != null;
		try {
			table.deleteItem("followee_handle", request.getToBeUnfollowed().getAlias(), "follower_handle", request.getRequestingUser().getAlias());
            return new UnfollowResponse();
		}
		catch (Exception e) {
			System.err.println("Unable to delete follows relationship: " + request.getRequestingUser().getAlias() + " follows " + request.getToBeUnfollowed().getAlias());
			System.err.println(e.getMessage());
			return new UnfollowResponse("Unable to delete follows relationship: " + request.getRequestingUser().getAlias() + " follows " + request.getToBeUnfollowed().getAlias());
		}
    }

    /**
     * Checks if one user is following another.
     *
     * @param followerAlias the alias of the user who is or is not doing the following.
     * @param followeeAlias the alias of the user who is or is not being followed.
     * @return true if follower is following followee, else false.
     */
    public boolean isFollowing(String followerAlias, String followeeAlias){
        assert followerAlias != null;
        assert followeeAlias != null;

        if (followerAlias.equals(followeeAlias)) {
            return false;
        }

        try {
            System.out.println("Checking if " + followerAlias + " follows " + followeeAlias);
            Item outcome = table.getItem("followee_handle", followeeAlias, "follower_handle", followerAlias);
            System.out.println(followerAlias + (outcome != null ? " follows " : " does not follow ") + followeeAlias);
            return outcome != null;
        }
        catch (Exception e) {
            System.err.println("Unable to check if " + followerAlias + " follows " + followeeAlias);
            System.err.println(e.getMessage());
            return false;
        }
    }
}
