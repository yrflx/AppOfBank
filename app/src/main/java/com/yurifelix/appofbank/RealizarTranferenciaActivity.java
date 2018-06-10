package com.yurifelix.appofbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.Model.Conta;

public class RealizarTranferenciaActivity extends AppCompatActivity {

    private EditText et_valor;
    private EditText et_destinatario;

    private Button button;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_tranferencia);

        et_valor = findViewById(R.id.et_valor);
        et_destinatario = findViewById(R.id.et_destino);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transferir transferir = new Transferir();
                transferir.execute();

            }
        });

    }

    public class Transferir extends AsyncTask<Void, Void, Void>{

            private SharedPreferences sharedPreferences;
            private String resultado;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);

                    }
                });

            }

            @Override
            protected Void doInBackground(Void... voids) {
                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                String conta = sharedPreferences.getString("CONTA","");


                Double valor = Double.parseDouble(et_valor.getText().toString());
                String destinatario = et_destinatario.getText().toString();

                Conexao conexao = new Conexao(2525);

                conexao.conectaServidor();
                conexao.enviaString("realizarTransferencia");
                conexao.enviaString(conta +"&&"+ valor +"&&"+ destinatario );

                resultado = "";
                resultado = conexao.recebeString();

                conexao.fechaConexao();

                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!resultado.equals("")){

                            if(resultado.equals("true")){
                                Toast.makeText(getApplicationContext(), "Transferencia realizada com sucesso.", Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            startActivity(new Intent(getApplicationContext(), ServerOffActivity.class));
                        }

                    }
                });



                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                        et_destinatario.setText("");
                        et_valor.setText("");

                    }
                });


            }
    }


}
