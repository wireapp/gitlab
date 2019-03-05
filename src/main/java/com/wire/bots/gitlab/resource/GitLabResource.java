package com.wire.bots.gitlab.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wire.bots.gitlab.Database;
import com.wire.bots.gitlab.WebHookHandler;
import com.wire.bots.gitlab.model.GitResponse;
import com.wire.bots.sdk.ClientRepo;
import com.wire.bots.sdk.WireClient;
import com.wire.bots.sdk.exceptions.MissingStateException;
import com.wire.bots.sdk.tools.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/{botId}")
public class GitLabResource {

    private final ClientRepo repo;
    private final WebHookHandler webHookHandler;

    public GitLabResource(ClientRepo repo) {
        this.repo = repo;
        webHookHandler = new WebHookHandler();
    }

    @POST
    public Response webHook(
            @HeaderParam("X-Gitlab-Event") String event,
            @HeaderParam("X-Gitlab-Token") String token,
            @PathParam("botId") String botId,
            String payload) {

        String secret = new Database(botId).getSecret();
        if (!secret.equals(token)) {
            Logger.warning("Invalid Gitlab-Token. Bot: %s", botId);
            return Response.
                    status(403).
                    build();
        }

        try (WireClient client = repo.getClient(botId)) {
            ObjectMapper mapper = new ObjectMapper();
            GitResponse response = mapper.readValue(payload, GitResponse.class);

            Logger.info("%s.%s Bot: %s", event, response.action, botId);

            String message = webHookHandler.handle(response.action, response);
            if (message != null && !message.isEmpty())
                client.sendText(message);

        } catch (MissingStateException e) {
            Logger.info("GitLabResource.webHook: Bot previously deleted. Bot: %s", botId);
            webHookHandler.unsubscribe(botId);
            return Response.
                    status(404).
                    build();
        } catch (Exception e) {
            Logger.error("GitLabResource.webHook: %s", e);
            return Response.
                    serverError().
                    build();
        }

        return Response.
                ok().
                build();
    }
}
