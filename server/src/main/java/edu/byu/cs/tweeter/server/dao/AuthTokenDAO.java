package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.server.util.SaltedSHAHashing;

/**
 * A DAO for accessing 'auth_token' data from the database.
 */
public class AuthTokenDAO {
    final Table table;

    public AuthTokenDAO() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        table = dynamoDB.getTable("auth_token");
    }

    /**
     * Adds a new auth token to the database for the specified user.
     * Only to be called after validating a register or login request via UserDAO.
     * Returns null if the alias is invalid.
     *
     * @param alias the alias of the user to create an auth token for
     * @return the new auth token.
     */
    public AuthToken create(String alias) {
        assert alias != null;
        AuthToken authToken = new AuthToken(UUID.randomUUID().toString());
        long expireEpoch = Instant.now().plus(30, ChronoUnit.MINUTES).getEpochSecond();
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("auth_token", authToken.getValue())
                            .withString("alias", alias)
                            .withLong("expire_epoch", expireEpoch));
        } catch (Exception e) {
            System.err.println("Unable to add item: auth token for " + alias);
            System.err.println(e.getMessage());
        }

        return authToken;
    }

    /**
     * Checks the database to see if an auth token exists, is valid, and is associated with a given alias.
     *
     * @param authToken the auth token
     * @param alias the alias
     * @return true if valid, false if not.
     */
    public boolean isValid(AuthToken authToken, String alias) {
        assert authToken != null;
        assert alias != null;

        try {
            Item outcome = table.getItem("auth_token", authToken.getValue());

            return outcome != null && outcome.getString("alias").equals(alias);
        }
        catch (Exception e) {
            System.err.println("Unable to read item: auth token for " + alias);
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Invalidates a given auth token.
     *
     * @param authToken the auth token
     * @return true if the auth token was found and invalidated, false if not.
     */
    public boolean invalidate(AuthToken authToken) {
        assert authToken != null;
        try {
			table.deleteItem("auth_token", authToken.getValue());
			return true;
		}
		catch (Exception e) {
			System.err.println("Unable to delete item: auth token " + authToken.getValue());
			System.err.println(e.getMessage());
			return false;
		}
    }
}
