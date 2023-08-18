package com.codificatus.ayudanteTecnico;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ErrorHandlingUtil {
    public static void handleException(Context context, Exception e) {
        Log.e("MiApp", "Ocurrió un errorManager: " + e.getMessage());
        Toast.makeText(context, "Ocurrió un errorManager", Toast.LENGTH_SHORT).show();
    }
}
