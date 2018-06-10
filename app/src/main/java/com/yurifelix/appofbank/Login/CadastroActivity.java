package com.yurifelix.appofbank.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.ServerOffActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class CadastroActivity extends AppCompatActivity {

    private EditText ed_nome;
    private EditText ed_telefone;
    private EditText ed_dataNascimento;

    private EditText ed_email;
    private EditText ed_senha;
    private EditText ed_senhaConfirma;

    private Button bt_cadastrar;
    private ProgressBar progressBar;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ed_nome = findViewById(R.id.ed_nome);
        ed_telefone = findViewById(R.id.ed_telefone);
        ed_dataNascimento = findViewById(R.id.ed_data_nascimento);

        ed_email = findViewById(R.id.ed_email);
        ed_senha = findViewById(R.id.ed_senha1);
        ed_senhaConfirma = findViewById(R.id.ed_senha2);

        progressBar = findViewById(R.id.progress);

        bt_cadastrar = findViewById(R.id.bt_cadastrar);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nome = ed_nome.getText().toString();
                String email = ed_email.getText().toString();
                String telefone = ed_telefone.getText().toString();
                String dNasc = ed_dataNascimento.getText().toString();
                String senha = ed_senha.getText().toString();
                String senhaconfirma = ed_senhaConfirma.getText().toString();

                if(nome.equals("") || telefone.equals("") || dNasc.equals("")|| email.equals("") ||
                        senha.equals("") || senhaconfirma.equals("")){

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else if(!(senha.equals(senhaconfirma))){
                    Toast.makeText(getApplicationContext(), "As senhas não conferem", Toast.LENGTH_SHORT).show();
                }else{
                    Cadastro cadastro = new Cadastro();

                    cadastro.execute();

                }


            }
        });

    }


    class Cadastro extends AsyncTask<Void, Void,Void> {


        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private BufferedReader bufferedReader;
        private PrintStream printStream;

        private String resultado;

        @Override
        protected Void doInBackground(Void... voids) {

            bt_cadastrar.setClickable(false);

            try{
                Conexao conexao = new Conexao(2525);

                conexao.conectaServidor();

                conexao.enviaString("cadastro");
                conexao.enviaString(ed_nome.getText()+"&&"+ed_telefone.getText()+"&&"+
                        ed_dataNascimento.getText()+"&&"+ed_email.getText()+"&&"+ed_senha.getText());

                resultado = conexao.recebeString();

                conexao.fechaConexao();

                if(!resultado.equals("")){
                    Handler handler = new Handler(getApplicationContext().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            if(resultado.equals("true")){
                                Toast.makeText(getApplicationContext(),"Cadastro Realizado", Toast.LENGTH_SHORT).show();

                                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                                sharedPreferences.edit().putString("CLIENTE", ed_email.getText().toString()).apply();
                                sharedPreferences.edit().putBoolean("LOGADO", true).apply();

                                startActivity(new Intent(CadastroActivity.this, CriarContaActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



            }catch (Exception e) {
                e.printStackTrace();
                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(CadastroActivity.this, ServerOffActivity.class));

                    }
                });
            }

            bt_cadastrar.setClickable(true);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bt_cadastrar.setClickable(false);
            bt_cadastrar.setVisibility(View.INVISIBLE);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("post", "post");
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.INVISIBLE);

            bt_cadastrar.setClickable(true);
            bt_cadastrar.setVisibility(View.VISIBLE);


        }

    }



}



