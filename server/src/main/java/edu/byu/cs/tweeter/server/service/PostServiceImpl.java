package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.service.PostService;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.dao.*;

import java.util.HashMap;
import java.util.Map;

public class PostServiceImpl implements PostService {

    @Override
    public PostResponse post(PostRequest request) {
        if (!getAuthTokenDAO().isValid(request.getAuthToken(), request.getStatus().getUser().getAlias())){
            return new PostResponse("Invalid auth token.");
        }

        addToPostsQueue(request.getStatus());

        return getStoryDAO().addToStory(request);
    }

    private void addToPostsQueue(Status status) {
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/557088822867/tweeter_posts_queue";

        final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("status_user_alias", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(status.getUser().getAlias()));
        messageAttributes.put("status_timestamp", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(status.getTimestamp()));

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageAttributes(messageAttributes)
                .withMessageBody(status.getBody())
                .withDelaySeconds(5);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
        System.out.println("Adding to posts queue: message ID = " + msgId);
    }


    /**
     * Returns an instance of {@link FeedDAO}. Allows mocking of the FeedDAO class
     * for testing purposes. All usages of FeedDAO should get their FeedDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the StoryDAO class
     * for testing purposes. All usages of StoryDAO should get their StoryDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

    /**
     * Returns an instance of {@link AuthTokenDAO}. Allows mocking of the AuthTokenDAO class
     * for testing purposes. All usages of AuthTokenDAO should get their AuthTokenDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
