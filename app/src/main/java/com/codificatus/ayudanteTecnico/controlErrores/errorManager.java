package com.codificatus.ayudanteTecnico.controlErrores;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class errorManager {
    public static void handleException(Context context, Exception e) {
        Log.e("MiApp", "Ocurrió un error: " + e.getMessage());
        Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_SHORT).show();
    }

    public static void logException(Exception e) {
        FirebaseCrashlytics.getInstance().recordException(e);
    }

    public static void logCustomEvent(String eventName, String parameterName, String parameterValue) {
        FirebaseCrashlytics.getInstance().log(eventName + ": " + parameterName + " = " + parameterValue);
    }

    public static void logUserEvent(String userId, String eventName, String eventDetails) {
        FirebaseCrashlytics.getInstance().setUserId(userId);
        FirebaseCrashlytics.getInstance().log(eventName + ": " + eventDetails);
    }
/*
    public static void sendAutoReport(Context context, Exception e) {
        logException(e);

        String userId = getUserId(); // Implement this method to get the user's ID
        if (userId != null) {
            logUserEvent(userId, "AutoReport", "Error occurred");
        }

        Toast.makeText(context, "Se ha enviado un informe automático de error", Toast.LENGTH_SHORT).show();
    }

 */
}
