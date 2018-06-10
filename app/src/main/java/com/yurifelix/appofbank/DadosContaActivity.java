package com.yurifelix.appofbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.Login.LoginActivity;
import com.yurifelix.appofbank.Login.SelecaoContaActivity;

public class DadosContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_conta);
    }


    SharedPreferences sharedPreferences;
    boolean logado;

    public synchronized void gerenciarContas(View v){
        sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);


        logado = sharedPreferences.getBoolean("LOGADO", false);

        if(logado){
            sharedPreferences.edit().putString("CONTA","").apply();
            startActivity(new Intent(this, SelecaoContaActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }


    public synchronized void sair(View v){

        sharedPreferences.edit().putBoolean("LOGADO", false).apply();
        startActivity(new Intent(this, LoginActivity.class));

        finish();

    }

    public synchronized void excluirConta(View v){
        ExcluirConta excluirConta = new ExcluirConta();
        excluirConta.execute();
    }
    public synchronized void finalizarCadastro(View v){
        ExcluirCadastro excluirCadastro = new ExcluirCadastro();
        excluirCadastro.execute();

    }



    ///////////////////////////////////// TASKs
    public class ExcluirConta extends AsyncTask<Void, Void, Void> {

        private SharedPreferences sharedPreferences;
        private String resultado;



        @Override
        protected Void doInBackground(Void... voids) {
            sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
            String conta = sharedPreferences.getString("CONTA","");


            Conexao conexao = new Conexao(2525);

            conexao.conectaServidor();
            conexao.enviaString("removerConta");
            conexao.enviaString(conta);

            resultado = "";
            resultado = conexao.recebeString();

            conexao.fechaConexao();

            Handler handler = new Handler(getApplicationContext().getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(!resultado.equals("")){

                        if(resultado.equals("true")){
                            Toast.makeText(getApplicationContext(), "Conta excluida com sucesso.", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(), SelecaoContaActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT);
                        }


                    }else{
                        startActivity(new Intent(getApplicationContext(), ServerOffActivity.class));
                    }

                }
            });



            return null;
        }


    }


    public class ExcluirCadastro extends AsyncTask<Void, Void, Void> {

        private SharedPreferences sharedPreferences;
        private String resultado;


        @Override
        protected Void doInBackground(Void... voids) {

            try{

                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                String cliente = sharedPreferences.getString("CLIENTE","");


                Conexao conexao = new Conexao(2525);

                conexao.conectaServidor();
                conexao.enviaString("removerCliente");
                conexao.enviaString(cliente);

                resultado = "";
                resultado = conexao.recebeString();

                conexao.fechaConexao();

                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!resultado.equals("")){

                            if(resultado.equals("true")){
                                Toast.makeText(getApplicationContext(), "Cadastro excluido com sucesso.", Toast.LENGTH_SHORT);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT);
                            }


                        }else{
                            startActivity(new Intent(getApplicationContext(), ServerOffActivity.class));
                        }

                    }
                });

            }catch (Exception e){

            }


            return null;
        }


    }




}
