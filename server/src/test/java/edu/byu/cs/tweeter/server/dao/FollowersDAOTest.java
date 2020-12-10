package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowersResponse;

class FollowersDAOTest {

//    private final User user1 = new User("Daffy", "Duck", "");
//    private final User user2 = new User("Fred", "Flintstone", "");
//    private final User user3 = new User("Barney", "Rubble", "");
//    private final User user4 = new User("Wilma", "Rubble", "");
//    private final User user5 = new User("Clint", "Eastwood", "");
//    private final User user6 = new User("Mother", "Teresa", "");
//    private final User user7 = new User("Harriett", "Hansen", "");
//    private final User user8 = new User("Zoe", "Zabriski", "");
//
//    private FollowersDAO followersDAOSpy;
//
//    @BeforeEach
//    void setup() {
//        followersDAOSpy = Mockito.spy(new FollowersDAO());
//    }
//
//    @Test
//    void testGetFollowers_noFollowersForUser() {
//        List<User> followers = Collections.emptyList();
//        Mockito.when(followersDAOSpy.getDummyFollowers()).thenReturn(followers);
//
//        FollowersRequest request = new FollowersRequest(user1, 10, null);
//        LiteFollowersResponse response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(0, response.getFollowers().size());
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowers_oneFollowerForUser_limitGreaterThanUsers() {
//        List<User> followees = Collections.singletonList(user2);
//        Mockito.when(followersDAOSpy.getDummyFollowers()).thenReturn(followees);
//
//        FollowersRequest request = new FollowersRequest(user1, 10, null);
//        LiteFollowersResponse response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(1, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user2.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowers_twoFollowersForUser_limitEqualsUsers() {
//        List<User> followees = Arrays.asList(user2, user3);
//        Mockito.when(followersDAOSpy.getDummyFollowers()).thenReturn(followees);
//
//        FollowersRequest request = new FollowersRequest(user3, 2, null);
//        LiteFollowersResponse response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user3.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowers_limitLessThanUsers_endsOnPageBoundary() {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
//        Mockito.when(followersDAOSpy.getDummyFollowers()).thenReturn(followees);
//
//        FollowersRequest request = new FollowersRequest(user5, 2, null);
//        LiteFollowersResponse response = followersDAOSpy.getFollowers(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user3.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowersRequest(user5, 2, user3);
//        response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user4.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user5.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowersRequest(user5, 2, user5);
//        response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user6.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user7.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//
//    @Test
//    void testGetFollowers_limitLessThanUsers_notEndsOnPageBoundary() {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
//        Mockito.when(followersDAOSpy.getDummyFollowers()).thenReturn(followees);
//
//        FollowersRequest request = new FollowersRequest(user6, 2, null);
//        LiteFollowersResponse response = followersDAOSpy.getFollowers(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user3.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowersRequest(user6, 2, user3);
//        response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user4.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user5.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowersRequest(user6, 2, user5);
//        response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(2, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user6.getAlias()));
//        Assertions.assertTrue(response.getFollowers().contains(user7.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify fourth page
//        request = new FollowersRequest(user6, 2, user7);
//        response = followersDAOSpy.getFollowers(request);
//
//        Assertions.assertEquals(1, response.getFollowers().size());
//        Assertions.assertTrue(response.getFollowers().contains(user8.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
}
