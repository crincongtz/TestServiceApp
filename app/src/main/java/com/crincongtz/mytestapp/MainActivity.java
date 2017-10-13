package com.crincongtz.mytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    /**
     * INSTUCCIONES:
     *
     * Activity manda cadena
     * Servicio reordena cadena (La voltea)
     * Servicio regresa a activity
     */
    private ReceptorRespuesta myReceptor;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myReceptor = new ReceptorRespuesta();
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constante.ACTION_RUN_SERVICE);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceptor, filter);
    }

    public void startMyService(View view){

         String myCadena = "Este es mi servicio";

        // Iniciar servicio
         Intent intent = new Intent(this, MyService.class);
         intent.putExtra("cadena", myCadena);
         startService(intent);
    }

    public void recibirCadena(String cadena){
        tvResult.setText(getString(R.string.text_result, cadena));
        doStopService();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Quitar registro de receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceptor);
    }

    private void doStopService() {
        Log.v(TAG, "*DETENER SERVICIO...");
        if(stopService(new Intent(this, MyService.class)))
            Log.i(TAG, "Servicio detenido con exito");
        else
            Log.e(TAG, "Servicio no fue detenido");
    }

    private class ReceptorRespuesta extends BroadcastReceiver{

        private ReceptorRespuesta(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {
             switch (intent.getAction()){
                 case Constante.ACTION_RUN_SERVICE:
                     String response = intent.getStringExtra(Constante.EXTRA_STRING);
                     recibirCadena(response);
                     break;
         }
        }
    }









}
