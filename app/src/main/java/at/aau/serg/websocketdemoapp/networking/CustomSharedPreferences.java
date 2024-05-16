package at.aau.serg.websocketdemoapp.networking;

import android.content.Context;

public class CustomSharedPreferences {
    private static final String PREFS_NAME = "MyPrefs";

    /*public static void saveToSharedPreferences (Context context, String playerID, String roomID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("playerID", playerID);
        editor.putString("roomID", roomID);
        editor.apply();
    }*/

    public static void savePlayerIDToSharedPreferences(Context context, String playerID) {

        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("playerID", playerID);
        editor.apply();
    }

    public static void saveRoomIDToSharedPreferences(Context context, String roomID) {

        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("roomID", roomID);
        editor.apply();
    }

    public static String getPlayerIDFromSharedPreferences(Context context) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("playerID", null);
    }

    public static String getRoomIDFromSharedPreferences(Context context) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("roomID", null);
    }


}
