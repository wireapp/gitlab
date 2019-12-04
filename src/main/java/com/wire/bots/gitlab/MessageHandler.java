//
// Wire
// Copyright (C) 2016 Wire Swiss GmbH
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see http://www.gnu.org/licenses/.
//

package com.wire.bots.gitlab;

import com.wire.bots.gitlab.utils.SessionIdentifierGenerator;
import com.wire.bots.sdk.MessageHandlerBase;
import com.wire.bots.sdk.WireClient;
import com.wire.bots.sdk.factories.StorageFactory;
import com.wire.bots.sdk.models.TextMessage;
import com.wire.bots.sdk.server.model.NewBot;
import com.wire.bots.sdk.server.model.User;
import com.wire.bots.sdk.tools.Logger;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;

public class MessageHandler extends MessageHandlerBase {
    private final SessionIdentifierGenerator sesGen = new SessionIdentifierGenerator();
    private final StorageFactory storageFactory;

    MessageHandler(StorageFactory storageFactory) {
        this.storageFactory = storageFactory;
    }

    @Override
    public boolean onNewBot(NewBot newBot) {
        Logger.info("onNewBot: bot: %s, user: %s",
                newBot.id,
                newBot.origin.id);
        try {
            String secret = sesGen.next(12);
            getDatabase(newBot.id).insertSecret(secret);
        } catch (Exception e) {
            Logger.error("onNewBot: %s", e);
        }
        return true;
    }

    @Override
    public void onNewConversation(WireClient client) {
        try {
            String help = formatHelp(client);
            String origin = getOwner(client).id;
            client.sendDirectText(help, origin);
        } catch (Exception e) {
            Logger.error("onNewConversation: %s", e);
        }
    }

    @Override
    public void onText(WireClient client, TextMessage msg) {
        try {
            if (msg.getText().equalsIgnoreCase("/help")) {
                String help = formatHelp(client);

                client.sendDirectText(help, msg.getUserId());
            }
        } catch (Exception e) {
            Logger.error("onText: %s", e);
        }
    }

    @Override
    public void onBotRemoved(String botId) {
        try {
            getDatabase(botId).unsubscribe();
        } catch (Exception e) {
            Logger.error("onBotRemoved: %s %s", botId, e);
        }
    }

    private String formatHelp(WireClient client) throws Exception {
        String botId = client.getId();
        String host = getHost();
        String secret = getDatabase(botId).getSecret();
        String name = client.getConversation().name;
        String convName = name != null ? URLEncoder.encode(name, "UTF-8") : "";
        User owner = getOwner(client);
        String handle = owner.handle;

        String url = String.format("%s/%s#conv=%s,owner=@%s", host, botId, convName, handle);
        return formatHelp(url, secret);
    }

    private Database getDatabase(String botId) {
        return new Database(botId);
    }

    private String formatHelp(String url, String secret) {
        return String.format("Hello I'm the GitLab bot. Here's how to configure me:\n\n"
                        + "1. Go to the Gitlab project page which you'd like to connect me to\n"
                        + "2. Go to 'Parameters' > 'Integrations'\n"
                        + "3. In the 'URL' field, enter : %s\n"
                        + "4. In the 'Secret Token' field, enter : %s\n"
                        + "5. Choose the events you want to receive from 'Push events', 'Issue events', 'Comments' and/or 'Pipeline events'\n"
                        + "6. Leave 'Enable SSL verification' checked\n"
                        + "7. Click on 'Add webhook'",
                url,
                secret);
    }

    private String getHost() {
        return Service.config.baseUrl;
    }

    private User getOwner(WireClient client) throws Exception {
        String botId = client.getId();
        NewBot state = storageFactory.create(botId).getState();
        Collection<User> users = client.getUsers(Collections.singletonList(state.origin.id));
        return users.stream().findFirst().get();
    }
}
