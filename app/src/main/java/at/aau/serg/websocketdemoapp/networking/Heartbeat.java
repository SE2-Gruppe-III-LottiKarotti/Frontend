package at.aau.serg.websocketdemoapp.networking;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import at.aau.serg.websocketdemoapp.msg.HeartbeatMessage;

public class Heartbeat {
    private static final long HEARTBEAT_TIME = 5000;
    private Timer timer;
    private WebSocketClient networkHandler;

    private final Gson gson = new Gson();

    public Heartbeat(WebSocketClient networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendHeartbeat();
            }
        }, 0, HEARTBEAT_TIME);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void sendHeartbeat() {
        HeartbeatMessage heartbeatMessage = new HeartbeatMessage();
        heartbeatMessage.setText("ping");
        String jsonMessage = gson.toJson(heartbeatMessage);
        networkHandler.sendMessageToServer(jsonMessage);
        Log.d("heartbeat", "send heartbeatMsg " + jsonMessage);
    }
}
