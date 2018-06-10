package com.yurifelix.appofbank;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;

import java.util.Random;

public class RandomCodeActivity extends AppCompatActivity {

    private Button bt_gerarCode;

    private TextView textViewCode;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_code);

        textViewCode = findViewById(R.id.textCodigo);
        progressBar = findViewById(R.id.progress);
        bt_gerarCode = findViewById(R.id.bt_gerarCodigo);

        progressBar = findViewById(R.id.progressBar3);

        bt_gerarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GerarCode gerarCode = new GerarCode();
                gerarCode.execute();


            }
        });


    }

    public class GerarCode extends AsyncTask<Void, Void, Void>{

        private int code = 0;
        private  String resultado;
        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Conexao conexao = new Conexao(2525);
                conexao.conectaServidor();
                conexao.enviaString("randomCode");

                Random random = new Random();
                int numberRandom = random.nextInt((9999 - 1000) + 1) + 1000;

                SharedPreferences sharedPreferences = getSharedPreferences("DADOS",MODE_PRIVATE );
                String conta = sharedPreferences.getString("CONTA", "");

                conexao.enviaString(String.valueOf(numberRandom) +"&&"+ conta );

                resultado = conexao.recebeString();
                conexao.fechaConexao();
                code = numberRandom;

                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(resultado.equals("true")){
                            textViewCode.setText(String.valueOf(code));
                        }else{
                            Toast.makeText(getApplicationContext(),"erro. tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Handler handler = new Handler(getApplicationContext().getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);
                    textViewCode.setText("");
                    textViewCode.setVisibility(View.INVISIBLE);
                }
            });

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Handler handler = new Handler(getApplicationContext().getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    textViewCode.setVisibility(View.VISIBLE);

                }
            });

        }
    }


}
