package com.yurifelix.appofbank;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.Model.Conta;

/**
 * Created by yurif on 22/05/2018.
 */


public class ServiceAtualiza extends Service implements Runnable{

    private String resultadoConta;
    private Conta conta;

    private Conexao conexao;
    private double saldo = 0;

    private String clienteAtual = "";
    private String contaAtual = "";

    private SharedPreferences sharedPreferences;

    private Intent intent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ONS", "onStartCommand");
        this.intent = intent;
        sharedPreferences = getSharedPreferences("DADOS", Context.MODE_PRIVATE);


        if(sharedPreferences.getBoolean("LOGADO",false)){
            contaAtual = sharedPreferences.getString("CONTA","");
            clienteAtual = sharedPreferences.getString("CLIENTE","");

            if(!(clienteAtual.equals("")) || !(contaAtual.equals(""))){

                System.out.println("==== ABRINDO THREAD====");
                Thread thread = new Thread(this);
                thread.start();


            }
        }



        return START_STICKY;


    }



    public void AtualizaDados() throws InterruptedException {

        try{
                    System.out.println("NOVA ATUALIZACAO");
                    conexao.enviaString("service");
                    conexao.enviaString(contaAtual);

                    String resultado = conexao.recebeString();

                    if(Double.parseDouble(resultado)!=saldo){

                        saldo = Double.parseDouble(resultado);


                        if(MainActivity.isActivityAtiva()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }


        }catch (Exception e){
            Log.e("ATUALIZADADOS", "erro:" + e);
        }finally {
            if(conexao.isConected()){
                //conexao.fechaConexao();
            }
        }




    }


    @Override
    public void run() {
        try {

            sharedPreferences = getSharedPreferences("DADOS", Context.MODE_PRIVATE);


            conexao = new Conexao(2626);
            conexao.conectaServidor();

            while (true){


                Thread.sleep( 1000);
                AtualizaDados();
                Log.i("SERVICE", "rodando");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
