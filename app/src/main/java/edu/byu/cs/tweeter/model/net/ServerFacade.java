package edu.byu.cs.tweeter.model.net;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
            LocalDateTime.of(120,10,7,20,24));
    private static final Status status2 = new Status(user0, "What's up homies?",
            LocalDateTime.of(120,10,7,20,23));

    private static final Status status10 = new Status(user2,  "Anyone know if @HelenHopwell is on tweeter yet?",
            LocalDateTime.of(120, 10, 1 ,19, 13));
    private static final Status status11 = new Status(user7, "Covfefe",
            LocalDateTime.of(120, 9, 28, 18,26));
    private static final Status status12 = new Status(user19, "Check this out: www.crouton.net",
            LocalDateTime.of(120, 9, 20, 3, 41));

    private static List<User> allUsers = Arrays.asList(user0, user1, user2, user3, user4, user5, user6, user7,
            user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
            user19, user20);
    private static List<Status> allStatuses = Arrays.asList(status1, status2, status10, status11, status12);
    private static List<User> followees = new ArrayList<>();
    private static List<User> followers = Arrays.asList(user3, user4, user5, user7,
            user8, user9, user10, user11, user12, user13, user14, user15);
    private static List<Status> story0 = new ArrayList<>();
    private static List<Status> feed = new ArrayList<>();
    private static List<Status> currStory;

    public ServerFacade(){
        if (story0.isEmpty()) {
            story0.add(status1);
            story0.add(status2);
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
        if (feed.isEmpty()){
            feed.add(status10);
            feed.add(status11);
            feed.add(status12);
        }
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
        if (request.getAlias().equals("")) {
            user = new User("Test", "User",
                    ServerFacade.MALE_IMAGE_URL);
            currStory = story0;
        } else {
            user = new User("Test", "User", request.getAlias(),
                    ServerFacade.MALE_IMAGE_URL);
            currStory = new ArrayList<>();
        }

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

        List<User> allFollowees = getDummyFollowees();
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
    List<User> getDummyFollowees() {
        return followees;
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

        List<User> allFollowers = getDummyFollowers();
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
    List<User> getDummyFollowers() {
        return followers;
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
        if (user.equals(user2)){
            return Arrays.asList(status10);
        } else if (user.equals(user7)){
            return Arrays.asList(status11);
        } else if (user.equals(user19)){
            return Arrays.asList(status12);
        }else if (!user.equals(user0) && allUsers.contains(user)) {
            return new ArrayList<>();
        } else {
            return currStory;
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
            if (followees.contains(status.getUser()) && !feed.contains(status)){
                feed.add(status);
            }
        }
        for (Status status : feed){
            if (!followees.contains(status.getUser())){
                feed.remove(status);
                break;
            }
        }

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
            return new ProfileInfoResponse(followers.size(), followees.size(), false);
        } else {
            boolean isFollowed;
            if (followees.contains(request.getRequestedUser())) {
                isFollowed = true;
            } else {
                isFollowed = false;
            }
            return new ProfileInfoResponse(34, 48, isFollowed);
        }
    }

    public UserResponse getUser(UserRequest request) {
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
        if (followees.contains(request.getToBeFollowed())){
            return new FollowResponse(request.getRequestingUser().getAlias() + " is already following " + request.getToBeFollowed().getAlias() + ".");
        } else {
            followees.add(0, request.getToBeFollowed());
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
        if (!followees.contains(request.getToBeUnfollowed())){
            return new UnfollowResponse(request.getRequestingUser().getAlias() + " is not currently following " + request.getToBeUnfollowed().getAlias() + ".");
        } else {
            followees.remove(request.getToBeUnfollowed());
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
