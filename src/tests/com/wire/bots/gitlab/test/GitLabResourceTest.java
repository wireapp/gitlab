package com.wire.bots.gitlab.test;

import com.wire.bots.gitlab.Config;
import com.wire.bots.gitlab.Service;
import com.wire.bots.gitlab.resource.GitLabResource;
import com.wire.bots.gitlab.test.helpers.DummyRepo;
import io.dropwizard.testing.FixtureHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class GitLabResourceTest {
    @ClassRule
    public static final DropwizardAppRule<Config> app
            = new DropwizardAppRule<>(Service.class, "gitlab.yaml");
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GitLabResource(new DummyRepo()))
            .build();
    private String botId = UUID.randomUUID().toString();

    @Test
    public void testOnPushEvent() {
        String event = "push";
        String payload = FixtureHelpers.fixture("fixtures/events/" + event + ".json");
        gitHubWebhookPost(botId, event, payload);
    }

    private void gitHubWebhookPost(String botId, String event, String payload) {
        Response response = resources.target("/")
                .path(botId)
                .request()
                .header("X-Gitlab-Event", event)
                .header("X-Gitlab-Token", "token")
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON));
        assertEquals("gitHubWebhookPost Response status", 200, response.getStatus());
    }
}

