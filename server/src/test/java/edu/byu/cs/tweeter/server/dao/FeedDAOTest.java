package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;

class FeedDAOTest {

    private final User user0 = new User("Guy", "Fieri", "");

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");
    private final User user6 = new User("Mother", "Teresa", "");
    private final User user7 = new User("Harriett", "Hansen", "");

    private final Status status10 = new Status(user2,  "Anyone know if @HelenHopwell is on tweeter yet?",
            LocalDateTime.of(2020, 10, 1 ,19, 13));
    private final Status status11 = new Status(user7, "Covfefe",
            LocalDateTime.of(2020, 9, 28, 18,26));
    private final Status status12 = new Status(user6, "Check this out: www.crouton.net",
            LocalDateTime.of(2020, 9, 20, 3, 41));
    private final Status status13 = new Status(user1, "Has anyone ever heard of twitter.com?  I think it's a tweeter knock off.",
            LocalDateTime.of(2020, 9, 15, 19, 30));
    private final Status status14 = new Status(user4, "I can't seem to find @realDonaldDuck on tweeter!",
            LocalDateTime.of(2020, 9, 13, 1, 12));
    private final Status status15 = new Status(user5, "Got lost in the grocery store again today.",
            LocalDateTime.of(2020, 8, 28, 14, 58));
    private final Status status16 = new Status(user1, "I should have joined tweeter a long time ago",
            LocalDateTime.of(2020, 8, 27, 18, 37));

    private FeedDAO feedDAOSpy;

    @BeforeEach
    void setup() {
        feedDAOSpy = Mockito.spy(new FeedDAO());
    }

    @Test
    void testGetFeed_noStatusesForFeed() {
        List<Status> feed = Collections.emptyList();
        Mockito.when(feedDAOSpy.getDummyFeed()).thenReturn(feed);

        FeedRequest request = new FeedRequest(user0, 10, null);
        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(0, response.getStatuses().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFeed_oneStatusForFeed_limitGreaterThanStatuses() {
        List<Status> feed = Collections.singletonList(status10);
        Mockito.when(feedDAOSpy.getDummyFeed()).thenReturn(feed);

        FeedRequest request = new FeedRequest(user0, 10, null);
        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(1, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status10));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFeed_twoStatusesForFeed_limitEqualsStatuses() {
        List<Status> feed = Arrays.asList(status10, status11);
        Mockito.when(feedDAOSpy.getDummyFeed()).thenReturn(feed);

        FeedRequest request = new FeedRequest(user0, 2, null);
        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status10));
        Assertions.assertTrue(response.getStatuses().contains(status11));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFeed_limitLessThanStatuses_endsOnPageBoundary() {
        List<Status> feed = Arrays.asList(status10, status11, status12, status13, status14, status15);
        Mockito.when(feedDAOSpy.getDummyFeed()).thenReturn(feed);

        FeedRequest request = new FeedRequest(user0, 2, null);
        FeedResponse response = feedDAOSpy.getFeed(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status10));
        Assertions.assertTrue(response.getStatuses().contains(status11));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FeedRequest(user0, 2, response.getStatuses().get(1));
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status12));
        Assertions.assertTrue(response.getStatuses().contains(status13));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FeedRequest(user0, 2, response.getStatuses().get(1));
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status14));
        Assertions.assertTrue(response.getStatuses().contains(status15));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFeed_limitLessThanStatuses_notEndsOnPageBoundary() {
        List<Status> feed = Arrays.asList(status10, status11, status12, status13, status14, status15, status16);
        Mockito.when(feedDAOSpy.getDummyFeed()).thenReturn(feed);

        FeedRequest request = new FeedRequest(user0, 2, null);
        FeedResponse response = feedDAOSpy.getFeed(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status10));
        Assertions.assertTrue(response.getStatuses().contains(status11));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FeedRequest(user0, 2, response.getStatuses().get(1));
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status12));
        Assertions.assertTrue(response.getStatuses().contains(status13));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FeedRequest(user0, 2, response.getStatuses().get(1));
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status14));
        Assertions.assertTrue(response.getStatuses().contains(status15));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new FeedRequest(user0, 2, response.getStatuses().get(1));
        response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(1, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status16));
        Assertions.assertFalse(response.getHasMorePages());
    }

}
