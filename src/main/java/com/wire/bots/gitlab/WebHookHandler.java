package com.wire.bots.gitlab;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.MustacheNotFoundException;
import com.wire.bots.gitlab.model.GitResponse;
import com.wire.bots.sdk.tools.Logger;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;

public class WebHookHandler {
    private final static MustacheFactory mf = new DefaultMustacheFactory();

    @Nullable
    public String handle(String event, GitResponse response) {
        try {
            if (!response.deleted) {
                Mustache mustache = compileTemplate("en", event, response.action);
                return execute(mustache, response);
            }
        } catch (MustacheNotFoundException ex) {
            return null;
        }
        return null;
    }

    private Mustache compileTemplate(String language, String event, @Nullable String action) {
        if (action == null) {
            String path = String.format("templates/%s/%s.template", language, event);
            return mf.compile(path);
        }

        String path = String.format("templates/%s/%s.%s.template", language, event, action);
        return mf.compile(path);
    }

    private String execute(Mustache mustache, Object model) {
        try (StringWriter sw = new StringWriter()) {
            mustache.execute(new PrintWriter(sw), model).flush();
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public void unsubscribe(String botId) {
        try {
            boolean unsubscribe = new Database(botId).unsubscribe();
            if (unsubscribe)
                Logger.info("Unsubscribed bot: %s", botId);
        } catch (Exception e) {
            Logger.error("WebHookHandler: %s %s", botId, e);
        }
    }
}
