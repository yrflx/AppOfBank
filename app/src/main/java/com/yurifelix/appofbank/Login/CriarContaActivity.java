package com.yurifelix.appofbank.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.MainActivity;

import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.ServerOffActivity;




public class CriarContaActivity extends AppCompatActivity {

    private EditText limite_et;
    private EditText saldo_et;
    private EditText banco_et;
    private EditText agencia_et;
    private EditText numero_et;

    private Button bt_criar;

    private ProgressBar progressBar;

    private String limite;
    private String saldo;
    private String banco;
    private String agencia;
    private String numero;

    private String resultado;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        limite_et = findViewById(R.id.et_limite_conta);
        saldo_et = findViewById(R.id.et_saldo);
        banco_et = findViewById(R.id.et_banco);
        agencia_et = findViewById(R.id.et_agencia);
        numero_et = findViewById(R.id.et_numero);

        progressBar = findViewById(R.id.progress);

        bt_criar = findViewById(R.id.bt_criarConta);

        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CriarConta criarConta = new CriarConta();
                criarConta.execute();


            }
        });

    }


    class CriarConta extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bt_criar.setClickable(false);
            bt_criar.setVisibility(View.INVISIBLE);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... voids) {

            limite = limite_et.getText().toString();
            saldo = saldo_et.getText().toString();
            banco = banco_et.getText().toString();
            agencia = agencia_et.getText().toString();
            numero = numero_et.getText().toString();

            try {
                Conexao conexao = new Conexao(2525);
                conexao.conectaServidor();

                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                String cliente = sharedPreferences.getString("CLIENTE", "");


                conexao.enviaString("cadastrarConta");
                conexao.enviaString(numero +"&&"+ limite +"&&"+ saldo +"&&"+ banco +"&&"+ agencia +"&&"+ cliente);
                resultado = conexao.recebeString();
                conexao.fechaConexao();


                if (resultado.equals("true")) {

                    sharedPreferences.edit().putString("CONTA",numero).apply();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } else if (resultado.equals("Banco Nao Encontrado") || resultado.equals("Agencia Nao Encontrada") ||
                        resultado.equals("Conta ja existe")) {

                    Handler handler = new Handler(getApplicationContext().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),resultado, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Handler handler = new Handler(getApplicationContext().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Erro", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }catch (Exception e) {
                e.printStackTrace();

                startActivity(new Intent(getApplicationContext(), ServerOffActivity.class));

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.INVISIBLE);

            bt_criar.setClickable(true);
            bt_criar.setVisibility(View.VISIBLE);


        }


    }


    public void sair(View v){
        sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
        sharedPreferences.edit().putString("CLIENTE", "").apply();
        startActivity(new Intent(this, LoginActivity.class));
    }




}
