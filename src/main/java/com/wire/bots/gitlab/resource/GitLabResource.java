package com.wire.bots.gitlab.resource;

import com.wire.bots.gitlab.Database;
import com.wire.bots.gitlab.WebHookHandler;
import com.wire.bots.gitlab.model.GitResponse;
import com.wire.bots.sdk.ClientRepo;
import com.wire.bots.sdk.WireClient;
import com.wire.bots.sdk.exceptions.MissingStateException;
import com.wire.bots.sdk.tools.Logger;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
            @Valid GitResponse payload) {

        String secret = new Database(botId).getSecret();
        if (secret != null && !secret.equals(token)) {
            Logger.warning("Invalid Gitlab-Token. Bot: %s", botId);
            return Response.
                    status(403).
                    build();
        }

        Logger.info("%s.%s Bot: %s", event, payload.action, botId);

        String message = webHookHandler.handle(payload.action, payload);

        if (message == null || message.isEmpty())
            return Response.
                    ok().
                    build();

        try (WireClient client = repo.getClient(botId.toString())) {
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
