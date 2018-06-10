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


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;

    private ProgressBar progressBar;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        senha = findViewById(R.id.et_senha);

        progressBar = findViewById(R.id.progress);

        login = findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emails = email.getText().toString();
                String senhas = senha.getText().toString();

                if( emails.equals("") || senhas.equals("") ){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                }else{
                    Login login = new Login();
                    login.execute();
                }

            }
        });

    }

    public void cadastraSe(View v){
        startActivity(new Intent(getApplicationContext(), CadastroActivity.class));

    }


    class Login extends AsyncTask<Void, Void,Void> {


        private String resultado;

        @Override
        protected Void doInBackground(Void... voids) {


            try{
                Conexao conexao = new Conexao(2525);
                conexao.conectaServidor();
                conexao.enviaString("login");
                conexao.enviaString(email.getText()+"&&"+senha.getText());
                //Conexao.enviaString("yurifelixyf@gmail.com&&yuri");
                resultado = conexao.recebeString();


                conexao.fechaConexao();



                if(resultado.equals("true")){
                    SharedPreferences sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("LOGADO", true).apply();
                    sharedPreferences.edit().putString("CLIENTE", email.getText().toString()).apply();

                    String salvo = "";
                    salvo = sharedPreferences.getString("CLIENTE","");


                    startActivity(new Intent(getApplicationContext(), SelecaoContaActivity.class));
                    //startActivity(new Intent(getApplicationContext(), CriarContaActivity.class));
                    finish();
                }else if(resultado.equals("false")){
                    Handler handler = new Handler(getApplicationContext().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Não encontrado", Toast.LENGTH_SHORT).show();
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
        protected void onPreExecute() {
            super.onPreExecute();

            login.setClickable(false);
            login.setVisibility(View.INVISIBLE);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.INVISIBLE);

            login.setClickable(true);
            login.setVisibility(View.VISIBLE);


        }

    }

}
