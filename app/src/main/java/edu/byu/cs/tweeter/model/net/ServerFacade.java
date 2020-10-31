package edu.byu.cs.tweeter.model.net;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    public static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    public static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private static final User user0 = new User("Test", "User", MALE_IMAGE_URL);

    private static final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private static final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private static final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private static final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private static final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private static final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private static final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private static final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private static final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private static final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private static final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private static final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private static final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private static final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private static final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private static final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private static final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private static final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private static final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private static final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    private static final Status status1 = new Status(user0, "Just got my new tweeter account set up!",
            LocalDateTime.of(2020,10,7,20,24));
    private static final Status status2 = new Status(user0, "What's up homies?",
            LocalDateTime.of(2020,10,7,20,23));
    private static final Status status3 = new Status(user0, "5",
            LocalDateTime.of(2020,10,7,20,22));
    private static final Status status4 = new Status(user0, "4",
            LocalDateTime.of(2020,10,7,19,22));
    private static final Status status5 = new Status(user0, "3",
            LocalDateTime.of(2020,10,7,19,22));
    private static final Status status6 = new Status(user0, "2",
            LocalDateTime.of(2020,10,7,19,22));
    private static final Status status7 = new Status(user0, "1",
            LocalDateTime.of(2020,10,7,19,21));
    private static final Status status8 = new Status(user0, "I can count to 5!",
            LocalDateTime.of(2020,10,7,19,21));
    private static final Status status9 = new Status(user0, "I'm not sure how this whole thing works",
            LocalDateTime.of(2020,10,7,19,20));

    private static final Status status10 = new Status(user2,  "Anyone know if @HelenHopwell is on tweeter yet?",
            LocalDateTime.of(2020, 10, 1 ,19, 13));
    private static final Status status11 = new Status(user7, "Covfefe",
            LocalDateTime.of(2020, 9, 28, 18,26));
    private static final Status status12 = new Status(user19, "Check this out: www.crouton.net",
            LocalDateTime.of(2020, 9, 20, 3, 41));
    private static final Status status13 = new Status(user1, "Has anyone ever heard of twitter.com?  I think it's a tweeter knock off.",
            LocalDateTime.of(2020, 9, 15, 19, 30));
    private static final Status status14 = new Status(user20, "I can't seem to find @realDonaldDuck on tweeter!",
            LocalDateTime.of(2020, 9, 13, 1, 12));
    private static final Status status15 = new Status(user5, "Got lost in the grocery store again today.",
            LocalDateTime.of(2020, 8, 28, 14, 58));
    private static final Status status16 = new Status(user1, "I should have joined tweeter a long time ago",
            LocalDateTime.of(2020, 8, 27, 18, 37));
    private static final Status status17 = new Status(user3, "Are @IgorIsaacson and @IsabelIsaacson related?",
            LocalDateTime.of(2020, 8, 20, 10, 40));
    private static final Status status18 = new Status(user8, "I think my account was hacked yesterday",
            LocalDateTime.of(2020, 8, 18, 14, 22));
    private static final Status status19 = new Status(user8, "DM ME FOR FREE RAYBANS",
            LocalDateTime.of(2020, 8, 17, 6, 3));
    private static final Status status20 = new Status(user9, "new tweeter who dis lol",
            LocalDateTime.of(2020, 8, 15, 12, 32));
    private static final Status status21 = new Status(user19, "test post please ignore",
            LocalDateTime.of(2020, 8, 13, 15, 29));
    private static final Status status22 = new Status(user8, "I'm just posting so my followers feeds aren't empty.",
            LocalDateTime.of(2020, 8, 10, 17, 39));
    private static final Status status23 = new Status(user16, "Loving the new tweeter update 😘",
            LocalDateTime.of(2020, 8, 10, 17, 39));
    private static final Status status24 = new Status(user4, "first",
            LocalDateTime.of(2020, 6, 5, 6, 32));

    private static List<User> allUsers = Arrays.asList(user0, user1, user2, user3, user4, user5, user6, user7,
            user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
            user19, user20);
    private static List<Status> allStatuses = Arrays.asList(status1, status2, status3, status4, status5,
            status6, status7, status8, status9, status10, status11, status12, status13, status14, status15,
            status16, status17, status18, status19, status20, status21, status22, status23, status24);
    private static List<User> followees = new ArrayList<>();
    private static List<User> followers = new ArrayList<>();
    private static List<Status> story0 = new ArrayList<>();
    private static List<Status> feed = new ArrayList<>();
    private static List<Status> currStory;
    private static List<User> currFollowees;
    private static List<User> currFollowers;
    private static User currUser;

    public ServerFacade(){
        if (story0.isEmpty()) {
            story0.add(status1);
            story0.add(status2);
            story0.add(status3);
            story0.add(status4);
            story0.add(status5);
            story0.add(status6);
            story0.add(status7);
            story0.add(status8);
            story0.add(status9);
        }
        if (followees.isEmpty()) {
            followees.add(user1);
            followees.add(user2);
            followees.add(user3);
            followees.add(user4);
            followees.add(user6);
            followees.add(user7);
            followees.add(user8);
            followees.add(user9);
            followees.add(user10);
            followees.add(user11);
            followees.add(user12);
            followees.add(user13);
            followees.add(user14);
            followees.add(user15);
            followees.add(user16);
            followees.add(user17);
            followees.add(user18);
            followees.add(user19);
            followees.add(user20);
        }
        if (followers.isEmpty()){
            followers.add(user3);
            followers.add(user4);
            followers.add(user5);
            followers.add(user7);
            followers.add(user8);
            followers.add(user9);
            followers.add(user10);
            followers.add(user11);
            followers.add(user12);
            followers.add(user13);
            followers.add(user14);
            followers.add(user15);
        }
//        if (feed.isEmpty()){
//            feed.add(status10);
//            feed.add(status11);
//            feed.add(status12);
//        }
    }
    
    /**
     * Performs a register and if successful, returns the registered user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a register.
     * @return the register response.
     */
    public RegisterResponse register(RegisterRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getAlias(),
                request.getImageUrl());
        if (request.getImageBytes() != null){
            user.setImageBytes(request.getImageBytes());
        }
        currStory = new ArrayList<>();
        currFollowees = new ArrayList<>();
        currFollowers = new ArrayList<>();
        currUser = user;

        return new RegisterResponse(user, new AuthToken());
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        User user;
        if (request.getAlias().equals("") || request.getAlias().equalsIgnoreCase("@testuser")) {
            user = new User("Test", "User",
                    ServerFacade.MALE_IMAGE_URL);
            currStory = story0;
        } else {
            user = new User("Test", "User", request.getAlias(),
                    ServerFacade.MALE_IMAGE_URL);
            currStory = new ArrayList<>();
        }
        currFollowees = new ArrayList<>(followees);
        currFollowers = new ArrayList<>(followers);
        currUser = user;

        return new LoginResponse(user, new AuthToken());
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees(request);
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the generator.
     */
    List<User> getDummyFollowees(FollowingRequest request) {
        if (currUser == null) { //for ServerFacadeTest to work
            return getDummyFollowees();
        }
        if (!request.getFollower().getAlias().equalsIgnoreCase(currUser.getAlias())) {
            ArrayList<User> otherFollowees = new ArrayList<>(followees);
            otherFollowees.remove(request.getFollower());
            return otherFollowees;
        }
        return currFollowees;
    }
    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the generator.
     */
    List<User> getDummyFollowees() {
        return allUsers;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowee() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers(request);
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the generator.
     */
    List<User> getDummyFollowers(FollowersRequest request) {
        if (!request.getFollowee().getAlias().equalsIgnoreCase(currUser.getAlias())) {
            ArrayList<User> otherFollowers = new ArrayList<>(followers);
            otherFollowers.remove(request.getFollowee());
            return otherFollowers;
        }
        return currFollowers;
    }
    
    /**
     * Returns the statuses that the user specified in the request has posted. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose statuses are to be returned and any
     *                other information required to satisfy the request.
     * @return the status response.
     */
    public StoryResponse getStory(StoryRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStoryStatuses(request.getUser());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusesIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    /**
     * Returns the list of dummy statuses data. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the generator.
     */
    List<Status> getDummyStoryStatuses(User user) {
        if (user.equals(currUser)) {
            return currStory;
        } else {
            ArrayList<Status> otherStory = new ArrayList<>();
            for (Status status : allStatuses) {
                if (user.equals(status.getUser())) {
                    otherStory.add(status);
                }
            }
            return otherStory;
        }
    }

    /**
     * Returns the statuses of the followees of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose statuses are to be returned and any
     *                other information required to satisfy the request.
     * @return the status response.
     */
    public FeedResponse getFeed(FeedRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyFeedStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusesIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of status from which we are returning paged results.
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
                }
            }
        }

        return statusesIndex;
    }

    /**
     * Returns the list of dummy statuses data. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the generator.
     */
    List<Status> getDummyFeedStatuses() {
        for (Status status : allStatuses) {
            if (currFollowees.contains(status.getUser()) && !feed.contains(status)){
                feed.add(status);
            }
        }
        ArrayList<Status> toRemove = new ArrayList<>();
        for (Status status : feed){
            if (!currFollowees.contains(status.getUser())){
                toRemove.add(status);
            }
        }
        for (Status status : toRemove) {
            feed.remove(status);
        }

        Collections.sort(feed, new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                return -1 * (o1.getDate().compareTo(o2.getDate()));
            }
        });

        return feed;
    }

    /**
     * Queries profile info for a user and if successful, returns the user's number of followers
     * and followees, and if they are followed by the requesting user. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to request profile info.
     * @return the profile response.
     */
    public ProfileInfoResponse getProfileInfo(ProfileInfoRequest request) {
        if (request.getRequestedUser().equals(request.getRequestingUser())){
            return new ProfileInfoResponse(currFollowers.size(), currFollowees.size(), false);
        } else {
            boolean isFollowed;
            if (currFollowees.contains(request.getRequestedUser())) {
                isFollowed = true;
                return new ProfileInfoResponse(followers.size(), followees.size() - 1, isFollowed);
            } else {
                isFollowed = false;
                return new ProfileInfoResponse(followers.size() - 1, followees.size() - 1, isFollowed);
            }
        }
    }

    public UserResponse getUser(UserRequest request) {
        if (request.getAlias().equalsIgnoreCase(currUser.getAlias())){
            return new UserResponse(currUser);
        }
        for (User user : allUsers) {
            if (user.getAlias().equalsIgnoreCase(request.getAlias())){
                return new UserResponse(user);
            }
        }
        return new UserResponse("User not found.");
    }

    /**
     * Posts a status and if successful, returns the PostResponse.
     *
     * @param request contains all information needed to post a status.
     * @return the post response.
     */
    public PostResponse post(PostRequest request) {
        currStory.add(0, request.getStatus());
        return new PostResponse();
    }

    /**
     * Follows a user and if successful, returns the FollowResponse.
     *
     * @param request contains all information needed to follow a user.
     * @return the follow response.
     */
    public FollowResponse follow(FollowRequest request) {
        if (currFollowees.contains(request.getToBeFollowed())){
            return new FollowResponse(request.getRequestingUser().getAlias() + " is already following " + request.getToBeFollowed().getAlias() + ".");
        } else {
            currFollowees.add(0, request.getToBeFollowed());
            return new FollowResponse();
        }
    }

    /**
     * Unfollows a user and if successful, returns the UnfollowResponse.
     *
     * @param request contains all information needed to unfollow a user.
     * @return the unfollow response.
     */
    public UnfollowResponse unfollow(UnfollowRequest request) {
        if (!currFollowees.contains(request.getToBeUnfollowed())){
            return new UnfollowResponse(request.getRequestingUser().getAlias() + " is not currently following " + request.getToBeUnfollowed().getAlias() + ".");
        } else {
            currFollowees.remove(request.getToBeUnfollowed());
            return new UnfollowResponse();
        }
    }

    /**
     * Invalidates an auth token and if successful, returns the LogoutResponse.
     *
     * @param request contains all information needed to logout a user.
     * @return the logout response.
     */
    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse();
    }
}
