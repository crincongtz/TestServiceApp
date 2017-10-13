package com.crincongtz.mytestapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceConfigurationError;

/**
 * Created by crincon on 13/10/17.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    @Override
    public void onCreate() {
        Log.v(TAG, "Iniciando servicio");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String cadena;
        if (intent.hasExtra("cadena")){
            cadena = intent.getExtras().getString("cadena");
            // Entonces reordenar cadena
            invertirCadena(cadena);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void invertirCadena(String cadena){

        String[] myStrings = cadena.split(" ");

        List<String> listString = new ArrayList<>();
        Collections.addAll(listString, myStrings);
        Collections.reverse(listString);

        StringBuilder sb = new StringBuilder();
        for(String string: listString){
            sb.append(string).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);

        // Reornar el resultado
        Intent localIntent = new Intent(Constante.ACTION_RUN_SERVICE)
                .putExtra(Constante.EXTRA_STRING, sb.toString());

        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
