package at.aau.serg.websocketdemoapp.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import at.aau.serg.websocketdemoapp.msg.MessageType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {

    // TODO use correct hostname:port
    /**
     * localhost from the Android emulator is reachable as 10.0.2.2
     * https://developer.android.com/studio/run/emulator-networking
     */
    //private final String WEBSOCKET_URI = "ws://10.0.2.2:8080/websocket-example-handler";
    private final String WEBSOCKET_URI = "ws://192.168.0.178:8080/websocket-example-handler";

    private WebSocket webSocket;
    private Map<String, WebSocketMessageHandler<String>> messageHandlers;


    public WebSocketClient() {
        messageHandlers = new HashMap<>();
    }

    public void connectToServer() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(WEBSOCKET_URI)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d("Network", "connected");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(text, JsonElement.class);
                String type = jsonElement.getAsJsonObject().get("messageType").getAsString();

                WebSocketMessageHandler<String> handler = messageHandlers.get(type);
                if (handler!= null) {
                    handler.onMessageReceived(text);
                } else {
                    Log.d("Network", "Unknown message type: " + type);
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                // WebSocket connection closed
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                // Permission needed to transmit cleartext in the manifest
                Log.d("Network", "connection failure");
            }
        });
    }

    public void addMessageHandler(String messageType, WebSocketMessageHandler<String> handler) {
        messageHandlers.put(messageType, handler);
    }

    public void sendMessageToServer(String msg) {
        if (webSocket!= null) {
            webSocket.send(msg);
        } else {
            Log.d("Network", "WebSocket is not open, can't send a message");
        }
    }
}
