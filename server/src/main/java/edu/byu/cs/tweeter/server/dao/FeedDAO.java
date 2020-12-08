package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;

/**
 * A DAO for accessing 'feed' data from the database.
 */
public class FeedDAO {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    private final Status status10 = new Status(user2,  "Anyone know if @HelenHopwell is on tweeter yet?",
            LocalDateTime.of(2020, 10, 1 ,19, 13));
    private final Status status11 = new Status(user7, "Covfefe",
            LocalDateTime.of(2020, 9, 28, 18,26));
    private final Status status12 = new Status(user19, "Check this out: www.crouton.net",
            LocalDateTime.of(2020, 9, 20, 3, 41));
    private final Status status13 = new Status(user1, "Has anyone ever heard of twitter.com?  I think it's a tweeter knock off.",
            LocalDateTime.of(2020, 9, 15, 19, 30));
    private final Status status14 = new Status(user20, "I can't seem to find @realDonaldDuck on tweeter!",
            LocalDateTime.of(2020, 9, 13, 1, 12));
    private final Status status15 = new Status(user5, "Got lost in the grocery store again today.",
            LocalDateTime.of(2020, 8, 28, 14, 58));
    private final Status status16 = new Status(user1, "I should have joined tweeter a long time ago",
            LocalDateTime.of(2020, 8, 27, 18, 37));
    private final Status status17 = new Status(user3, "Are @IgorIsaacson and @IsabelIsaacson related?",
            LocalDateTime.of(2020, 8, 20, 10, 40));
    private final Status status18 = new Status(user8, "I think my account was hacked yesterday",
            LocalDateTime.of(2020, 8, 18, 14, 22));
    private final Status status19 = new Status(user8, "DM ME FOR FREE RAYBANS",
            LocalDateTime.of(2020, 8, 17, 6, 3));
    private final Status status20 = new Status(user9, "new tweeter who dis lol",
            LocalDateTime.of(2020, 8, 15, 12, 32));
    private final Status status21 = new Status(user19, "test post please ignore",
            LocalDateTime.of(2020, 8, 13, 15, 29));
    private final Status status22 = new Status(user8, "I'm just posting so my followers feeds aren't empty.",
            LocalDateTime.of(2020, 8, 10, 17, 39));
    private final Status status23 = new Status(user16, "Loving the new tweeter update 😘",
            LocalDateTime.of(2020, 8, 10, 17, 39));
    private final Status status24 = new Status(user4, "first",
            LocalDateTime.of(2020, 6, 5, 6, 32));


    /**
     * Returns the statuses of the followers of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current
     *      * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    public FeedResponse getFeed(FeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getUser() != null;

        List<Status> allStatuses = getDummyFeed();
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

        return new FeedResponse(responseStatus, hasMorePages);
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
            System.out.println(lastStatus.getBody());
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                System.out.println("all Statuses[" + i + ": " + allStatuses.get(i).getBody());
                if(lastStatus.equals(allStatuses.get(i))) {
                    System.out.println("EQUAL");
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }
        System.out.println("statusesIndex = " + statusesIndex);
        return statusesIndex;
    }

    /**
     * Returns the list of dummy feed data. This is written as a separate method to allow
     * mocking of the feed.
     *
     * @return the feed.
     */
    List<Status> getDummyFeed() {
        return Arrays.asList(status10, status11, status12, status13, status14, status15, status16, status17, status18,
                status19, status20, status21, status22, status23, status24);
    }

    /**
     * Adds a status to a user's feed.
     *
     * @return PostResponse indicating success or failure.
     */
    public PostResponse addToFeed(PostRequest request) {
        //TODO: Add real implementation
        return new PostResponse();
    }
}
