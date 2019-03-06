package com.wire.bots.gitlab.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.wire.bots.gitlab.model.GitResponse;
import io.dropwizard.testing.FixtureHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MessageTemplateTest {
    private static String[] locales = new String[]{"en"};

    // ------------------- Tests -------------------
    @Test
    public void pushTest() throws IOException {
        test("push");
    }

    // ------------------- Tests -------------------

    private void test(String action) throws IOException {
        String filename = getJsonPath(action);
        String payload = FixtureHelpers.fixture(filename);
        ObjectMapper mapper = new ObjectMapper();
        GitResponse gitResponse = mapper.readValue(payload, GitResponse.class);

        for (String locale : locales) {
            Mustache mustache = compileTemplate(locale, action);
            String message = execute(mustache, gitResponse);

            String f = getMessagePath(action);
            String expected = FixtureHelpers.fixture(f);

            Assert.assertEquals("", expected, message);
        }
    }

    private String getMessagePath(String action) {
        return String.format("fixtures/messages/%s.txt", action);
    }

    private String getJsonPath(String action) {
        return String.format("fixtures/events/%s.json", action);
    }

    private Mustache compileTemplate(String language, String action) {
        MustacheFactory mf = new DefaultMustacheFactory();
        String path = String.format("templates/%s/%s.template", language, action);
        Mustache mustache = mf.compile(path);
        Assert.assertNotNull(path, mustache);
        return mustache;
    }

    private String execute(Mustache mustache, Object model) {
        try (StringWriter sw = new StringWriter()) {
            mustache.execute(new PrintWriter(sw), model).flush();
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(mustache.getName(), false);
            return null;
        }
    }
}
