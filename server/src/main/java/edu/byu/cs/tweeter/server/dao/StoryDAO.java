package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getUser() != null;

        List<Status> allStatuses = getDummyStory();
        List<Status> responseStatus = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int statusesIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                    responseStatus.add(allStatuses.get(statusesIndex));
                }

                hasMorePages = statusesIndex < allStatuses.size();
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
        //TODO: Add real implementation
        return new PostResponse();
    }
}
