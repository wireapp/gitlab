package com.wire.bots.gitlab.test.helpers;

import com.wire.bots.sdk.ClientRepo;
import com.wire.bots.sdk.WireClient;
import com.wire.bots.sdk.assets.IAsset;
import com.wire.bots.sdk.assets.IGeneric;
import com.wire.bots.sdk.models.AssetKey;
import com.wire.bots.sdk.models.otr.PreKey;
import com.wire.bots.sdk.server.model.Conversation;
import com.wire.bots.sdk.server.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class DummyRepo extends ClientRepo {
    public DummyRepo() {
        super(null, null, null);
    }

    @Override
    public WireClient getClient(String botId) {
        return new WireClient() {
            @Override
            public void close() {

            }

            @Override
            public void sendText(String txt) {

            }

            @Override
            public void sendDirectText(String txt, String userId) {

            }

            @Override
            public void sendText(String txt, long expires) {

            }

            @Override
            public void sendText(String txt, long expires, String messageId) {

            }

            @Override
            public void sendLinkPreview(String url, String title, IGeneric image) {

            }

            @Override
            public void sendDirectLinkPreview(String url, String title, IGeneric image, String userId) {

            }

            @Override
            public void sendPicture(byte[] bytes, String mimeType) {

            }

            @Override
            public void sendDirectPicture(byte[] bytes, String mimeType, String userId) {

            }

            @Override
            public void sendPicture(IGeneric image) {

            }

            @Override
            public void sendDirectPicture(IGeneric image, String userId) {

            }

            @Override
            public void sendAudio(byte[] bytes, String name, String mimeType, long duration) {

            }

            @Override
            public void sendVideo(byte[] bytes, String name, String mimeType, long duration, int h, int w) {

            }

            @Override
            public void sendFile(File file, String mime) {

            }

            @Override
            public void sendDirectFile(File file, String mime, String userId) {

            }

            @Override
            public void sendDirectFile(IGeneric preview, IGeneric asset, String userId) {

            }

            @Override
            public void ping() {

            }

            @Override
            public void sendReaction(String msgId, String emoji) {

            }

            @Override
            public void deleteMessage(String msgId) {

            }

            @Override
            public byte[] downloadAsset(String assetKey, String assetToken, byte[] sha256Challenge, byte[] otrKey) {
                return new byte[0];
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public UUID getConversationId() {
                return null;
            }

            @Override
            public String getDeviceId() {
                return null;
            }

            @Override
            public Collection<User> getUsers(Collection<String> userIds) {
                return null;
            }

            @Override
            public User getUser(String userId) {
                return null;
            }

            @Override
            public Conversation getConversation() {
                return null;
            }

            @Override
            public void acceptConnection(UUID user) {

            }

            @Override
            public String decrypt(String userId, String clientId, String cypher) {
                return null;
            }

            @Override
            public PreKey newLastPreKey() {
                return null;
            }

            @Override
            public ArrayList<PreKey> newPreKeys(int from, int count) {
                return null;
            }

            @Override
            public void uploadPreKeys(ArrayList<PreKey> preKeys) {

            }

            @Override
            public ArrayList<Integer> getAvailablePrekeys() {
                return null;
            }

            @Override
            public boolean isClosed() {
                return false;
            }

            @Override
            public byte[] downloadProfilePicture(String assetKey) {
                return new byte[0];
            }

            @Override
            public AssetKey uploadAsset(IAsset asset) {
                return null;
            }

            @Override
            public void call(String content) {

            }
        };
    }
}
