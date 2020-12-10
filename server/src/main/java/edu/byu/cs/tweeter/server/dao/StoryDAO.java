package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;

/**
 * A DAO for accessing 'story' data from the database.
 */
public class StoryDAO {
    // This is the hard coded status data returned by the 'getStory()' method
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user0 = new User("Test", "User", MALE_IMAGE_URL);

    private final Status status1 = new Status(user0, "Just got my new tweeter account set up!",
            LocalDateTime.of(2020,10,7,20,24));
    private final Status status2 = new Status(user0, "What's up homies?",
            LocalDateTime.of(2020,10,7,20,23));
    private final Status status3 = new Status(user0, "5",
            LocalDateTime.of(2020,10,7,20,22));
    private final Status status4 = new Status(user0, "4",
            LocalDateTime.of(2020,10,7,19,22));
    private final Status status5 = new Status(user0, "3",
            LocalDateTime.of(2020,10,7,19,22));
    private final Status status6 = new Status(user0, "2",
            LocalDateTime.of(2020,10,7,19,22));
    private final Status status7 = new Status(user0, "1",
            LocalDateTime.of(2020,10,7,19,21));
    private final Status status8 = new Status(user0, "I can count to 5!",
            LocalDateTime.of(2020,10,7,19,21));
    private final Status status9 = new Status(user0, "I'm not sure how this whole thing works",
            LocalDateTime.of(2020,10,7,19,20));

    private final Table table;

    public StoryDAO() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        table = dynamoDB.getTable("story");
    }

    /**
     * Returns the statuses of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current
     *      * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    public StoryResponse getStory(StoryRequest request) {
        assert request.getLimit() > 0;
        assert request.getUser() != null;

        List<Status> responseStatus = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            String lastKeyAlias = null;
            long lastKeyEpoch = 0;
            if (request.getLastStatus() != null) {
                lastKeyAlias = request.getLastStatus().getUser().getAlias();
                lastKeyEpoch = request.getLastStatus().timestampToEpoch();
            }
            Map<String, Object> valueMap = new HashMap<String, Object>();
            valueMap.put(":a", request.getUser().getAlias());

            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("alias = :a")
                    .withValueMap(valueMap)
                    .withScanIndexForward(true)
                    .withMaxResultSize(10);

            if (lastKeyAlias != null) {
                querySpec = querySpec.withExclusiveStartKey("alias", lastKeyAlias, "epoch_time_posted", lastKeyEpoch);
            }

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            Item item = null;

            try {
                System.out.println("Getting story of " + request.getUser().getAlias());
                items = table.query(querySpec);

                iterator = items.firstPage().iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    Status status = new Status(new User(null, null, item.getString("alias"), null), item.getString("body"), item.getString("timestamp"));
                    responseStatus.add(status);
                }

                Map<String, AttributeValue> lastKey = items.firstPage().getLowLevelResult().getQueryResult().getLastEvaluatedKey();
                if (lastKey != null) {
                    hasMorePages = true;
                }
            } catch (Exception e) {
                System.err.println("Unable to query story");
                System.err.println(e.getMessage());
            }
        }

        return new StoryResponse(responseStatus, hasMorePages);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of statuses from which we are returning paged results.
     * @return the index of the first status to be returned.
     */
    private int getStatusesStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }

        return statusesIndex;
    }

    /**
     * Returns the list of dummy story data. This is written as a separate method to allow
     * mocking of the story.
     *
     * @return the story.
     */
    List<Status> getDummyStory() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9);
    }

    /**
     * Adds a status to a user's story.
     *
     * @return PostResponse indicating success or failure.
     */
    public PostResponse addToStory(PostRequest request) {
        Status status = request.getStatus();
        long epoch = status.timestampToEpoch();
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("alias", status.getUser().getAlias(), "epoch_time_posted", epoch)
                            .withString("body", status.getBody())
                            .withString("timestamp", status.getTimestamp()));
            return new PostResponse();
        } catch (Exception e) {
            System.err.println("Unable to add status to story for: " + status.getUser().getAlias());
            System.err.println(e.getMessage());
            return new PostResponse("Post to story failed for: " + status.getUser().getAlias());
        }

    }
}
