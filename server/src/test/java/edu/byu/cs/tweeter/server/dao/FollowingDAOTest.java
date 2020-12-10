package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.client.model.service.response.LiteFollowingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;

class FollowingDAOTest {

//    private final User user1 = new User("Daffy", "Duck", "");
//    private final User user2 = new User("Fred", "Flintstone", "");
//    private final User user3 = new User("Barney", "Rubble", "");
//    private final User user4 = new User("Wilma", "Rubble", "");
//    private final User user5 = new User("Clint", "Eastwood", "");
//    private final User user6 = new User("Mother", "Teresa", "");
//    private final User user7 = new User("Harriett", "Hansen", "");
//    private final User user8 = new User("Zoe", "Zabriski", "");
//
//    private FollowingDAO followingDAOSpy;
//
//    @BeforeEach
//    void setup() {
//        followingDAOSpy = Mockito.spy(new FollowingDAO());
//    }
//
//    @Test
//    void testGetFollowees_noFolloweesForUser() {
//        List<User> followees = Collections.emptyList();
//        Mockito.when(followingDAOSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user1, 10, null);
//        LiteFollowingResponse response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(0, response.getFollowees().size());
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {
//        List<User> followees = Collections.singletonList(user2);
//        Mockito.when(followingDAOSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user1, 10, null);
//        LiteFollowingResponse response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(1, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user2.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {
//        List<User> followees = Arrays.asList(user2, user3);
//        Mockito.when(followingDAOSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user3, 2, null);
//        LiteFollowingResponse response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user3.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
//        Mockito.when(followingDAOSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user5, 2, null);
//        LiteFollowingResponse response = followingDAOSpy.getFollowees(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user3.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user5, 2, user3);
//        response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user4.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user5.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user5, 2, user5);
//        response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user6.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user7.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
//
//
//    @Test
//    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {
//        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
//        Mockito.when(followingDAOSpy.getDummyFollowees()).thenReturn(followees);
//
//        FollowingRequest request = new FollowingRequest(user6, 2, null);
//        LiteFollowingResponse response = followingDAOSpy.getFollowees(request);
//
//        // Verify first page
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user2.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user3.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify second page
//        request = new FollowingRequest(user6, 2, user3);
//        response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user4.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user5.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify third page
//        request = new FollowingRequest(user6, 2, user5);
//        response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(2, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user6.getAlias()));
//        Assertions.assertTrue(response.getFollowees().contains(user7.getAlias()));
//        Assertions.assertTrue(response.getHasMorePages());
//
//        // Get and verify fourth page
//        request = new FollowingRequest(user6, 2, user7);
//        response = followingDAOSpy.getFollowees(request);
//
//        Assertions.assertEquals(1, response.getFollowees().size());
//        Assertions.assertTrue(response.getFollowees().contains(user8.getAlias()));
//        Assertions.assertFalse(response.getHasMorePages());
//    }
}
