package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.util.SaltedSHAHashing;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class UserDAO {
    private final Table table;

    public UserDAO() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        table = dynamoDB.getTable("user");
    }
    /**
     * Gets the user from the database that is specified in the login request, if the password is correct.
     * Returns null if the user is not found or password is incorrect.
     *
     * @param request contains the username and password of the user to be logged in.
     * @return the user.
     */
    public User login(LoginRequest request) {
        assert request.getAlias() != null;
        assert request.getPassword() != null;

        try {
            Item outcome = table.getItem("alias", request.getAlias());
            String securePassword = outcome.getString("secure_password");
            String salt = outcome.getString("salt");

            if (securePassword.equals(SaltedSHAHashing.getSecurePassword(request.getPassword(), salt))) {
                return new User(outcome.getString("first_name"), outcome.getString("last_name"), outcome.getString("alias"), outcome.getString("image_url"));
            } else {
                return null;
            }
        }
        catch (Exception e) {
            System.err.println("Unable to read item: trying to login " + request.getAlias());
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Registers a new user in the database from the register request, and returns the new user.
     * Returns null if the user cannot be created.
     *
     * @param request contains the registration information for the new user.
     * @return the newly registered user.
     */
    public User register(RegisterRequest request) {
        assert request.getAlias() != null;
        assert request.getPassword() != null;

        //make sure user with requested alias doesn't already exist
        try {
            Item outcome = table.getItem("alias", request.getAlias());
            if (outcome != null) {
                return null;
            }
        }
        catch (Exception e) {
            System.err.println("Unable to read item: checking for alias=" + request.getAlias());
            System.err.println(e.getMessage());
        }

        String imageUrl;
        if (request.getImageBytes() != null) {
            imageUrl = uploadImageBytes(request.getAlias(), request.getImageBytes());
        } else {
            imageUrl = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        }

        //add user to db
        String salt = SaltedSHAHashing.getSalt();
        String securePassword = SaltedSHAHashing.getSecurePassword(request.getPassword(), salt);

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("alias", request.getAlias())
                            .withString("first_name", request.getFirstName())
                            .withString("last_name", request.getLastName())
                            .withString("image_url", imageUrl)
                            .withString("secure_password", securePassword)
                            .withString("salt", salt)
                            .withInt("num_followers", 0)
                            .withInt("num_followees", 0));

            return  new User(request.getFirstName(), request.getLastName(), request.getAlias(), imageUrl);
        } catch (Exception e) {
            System.err.println("Unable to add item: register new user for " + request.getAlias());
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets the user from the database that is specified by the alias in the user request.
     *
     * @param alias the alias of the user to be retrieved.
     * @return the user.
     */
    public User user(String alias) {
        assert alias != null;

        try {
            System.out.println("Getting user for alias " + alias);
            Item outcome = table.getItem("alias", alias);
            return new User(outcome.getString("first_name"), outcome.getString("last_name"), outcome.getString("alias"), outcome.getString("image_url"));
        }
        catch (Exception e) {
            System.err.println("Unable to read user: " + alias);
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets the count of users from the database that the user specified is followed by. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param alias the alias of the User whose count of how many followers is desired.
     * @return said count.
     */
    public Integer getFollowerCount(String alias) {
        assert alias != null;

        try {
            System.out.println("Getting follower count for " + alias);
            Item outcome = table.getItem("alias", alias);
            return outcome.getInt("num_followers");
        }
        catch (Exception e) {
            System.err.println("Unable to read user: " + alias);
            System.err.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param alias the alias of the User whose count of how many following is desired.
     * @return said count.
     */
    public Integer getFolloweeCount(String alias) {
        assert alias != null;

        try {
            System.out.println("Getting followee count for " + alias);
            Item outcome = table.getItem("alias", alias);
            return outcome.getInt("num_followees");
        }
        catch (Exception e) {
            System.err.println("Unable to read user: " + alias);
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public void incrementFollowerCount(String alias) {
        int followerCount = getFollowerCount(alias);
        followerCount++;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
				.withUpdateExpression("SET num_followers = :i")
				.withValueMap(new ValueMap().withInt(":i", followerCount))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
		}
		catch (Exception e) {
			System.err.println("Unable to increment follower count for " + alias);
			System.err.println(e.getMessage());
		}
    }

    public void incrementFolloweeCount(String alias) {
        int followerCount = getFolloweeCount(alias);
        followerCount++;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("SET num_followees = :i")
                .withValueMap(new ValueMap().withInt(":i", followerCount))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        }
        catch (Exception e) {
            System.err.println("Unable to increment followee count for " + alias);
            System.err.println(e.getMessage());
        }
    }

    public void decrementFollowerCount(String alias) {
        int followerCount = getFollowerCount(alias);
        followerCount--;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("SET num_followers = :i")
                .withValueMap(new ValueMap().withInt(":i", followerCount))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        }
        catch (Exception e) {
            System.err.println("Unable to increment follower count for " + alias);
            System.err.println(e.getMessage());
        }
    }

    public void decrementFolloweeCount(String alias) {
        int followerCount = getFolloweeCount(alias);
        followerCount--;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("SET num_followees = :i")
                .withValueMap(new ValueMap().withInt(":i", followerCount))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        }
        catch (Exception e) {
            System.err.println("Unable to increment followee count for " + alias);
            System.err.println(e.getMessage());
        }
    }

    private String uploadImageBytes(String alias, byte[] imageBytes) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        String bucketName = "tweeteruserimages";

        // upload file to the specified S3 bucket using the alias as the S3 key
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(imageBytes.length);
            PutObjectResult result = s3.putObject(bucketName, alias.substring(1) + ".png", new ByteArrayInputStream(imageBytes), metadata);
            return "https://tweeteruserimages.s3-us-west-2.amazonaws.com/" + alias.substring(1) + ".png";
        } catch (Exception e) {
            System.err.println("Image upload failed: " + e.getMessage());
            return null;
        }
    }
}
