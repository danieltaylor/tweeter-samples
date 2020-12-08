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
import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;

class StoryDAOTest {

    private final User user0 = new User("Daffy", "Duck", "");

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

    private StoryDAO storyDAOSpy;

    @BeforeEach
    void setup() {
        storyDAOSpy = Mockito.spy(new StoryDAO());
    }

    @Test
    void testGetStory_noStatusesForStory() {
        List<Status> story = Collections.emptyList();
        Mockito.when(storyDAOSpy.getDummyStory()).thenReturn(story);

        StoryRequest request = new StoryRequest(user0, 10, null);
        StoryResponse response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(0, response.getStatuses().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_oneStatusForStory_limitGreaterThanStatuses() {
        List<Status> story = Collections.singletonList(status1);
        Mockito.when(storyDAOSpy.getDummyStory()).thenReturn(story);

        StoryRequest request = new StoryRequest(user0, 10, null);
        StoryResponse response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(1, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status1));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_twoStatusesForStory_limitEqualsStatuses() {
        List<Status> story = Arrays.asList(status1, status2);
        Mockito.when(storyDAOSpy.getDummyStory()).thenReturn(story);

        StoryRequest request = new StoryRequest(user0, 2, null);
        StoryResponse response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status1));
        Assertions.assertTrue(response.getStatuses().contains(status2));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetStory_limitLessThanStatuses_endsOnPageBoundary() {
        List<Status> story = Arrays.asList(status1, status2, status3, status4, status5, status6);
        Mockito.when(storyDAOSpy.getDummyStory()).thenReturn(story);

        StoryRequest request = new StoryRequest(user0, 2, null);
        StoryResponse response = storyDAOSpy.getStory(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status1));
        Assertions.assertTrue(response.getStatuses().contains(status2));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new StoryRequest(user0, 2, response.getStatuses().get(1));
        response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status3));
        Assertions.assertTrue(response.getStatuses().contains(status4));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new StoryRequest(user0, 2, response.getStatuses().get(1));
        response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status5));
        Assertions.assertTrue(response.getStatuses().contains(status6));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetStory_limitLessThanStatuses_notEndsOnPageBoundary() {
        List<Status> story = Arrays.asList(status1, status2, status3, status4, status5, status6, status7);
        Mockito.when(storyDAOSpy.getDummyStory()).thenReturn(story);

        StoryRequest request = new StoryRequest(user0, 2, null);
        StoryResponse response = storyDAOSpy.getStory(request);

        // Verify first page
        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status1));
        Assertions.assertTrue(response.getStatuses().contains(status2));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new StoryRequest(user0, 2, response.getStatuses().get(1));
        response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status3));
        Assertions.assertTrue(response.getStatuses().contains(status4));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new StoryRequest(user0, 2, response.getStatuses().get(1));
        response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(2, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status5));
        Assertions.assertTrue(response.getStatuses().contains(status6));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new StoryRequest(user0, 2, response.getStatuses().get(1));
        response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(1, response.getStatuses().size());
        Assertions.assertTrue(response.getStatuses().contains(status7));
        Assertions.assertFalse(response.getHasMorePages());
    }
}
