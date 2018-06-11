    package com.yurifelix.appofbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.Historicos.HistoricoSaquesActivity;
import com.yurifelix.appofbank.Historicos.HistoricoTransferenciasActivity;
import com.yurifelix.appofbank.Login.LoginActivity;
import com.yurifelix.appofbank.Login.SelecaoContaActivity;

    public class MainActivity extends AppCompatActivity {

    private TextView nome;
    private TextView saldo;

    private TextView conta;
    private TextView agencia;

    private TextView textoConta;
    private TextView textoAgencia;
    private TextView textoSaldoEmConta;

    private TextView textoRsConta;

    private ProgressBar progressDados;
    private ProgressBar progressConta;

    private SharedPreferences sharedPreferences;

    private Boolean logado = false;
    private String clienteAtual = "";
    private String contaAtual = "";

    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recebe objetos layout

        //Dados
        textoConta = findViewById(R.id.tv_conta);
        textoAgencia = findViewById(R.id.tv_agencia);
        nome = findViewById(R.id.tv_usuario);
        conta = findViewById(R.id.tv_conta_num);
        agencia = findViewById(R.id.tv_agencia_num);

        //conta
        textoSaldoEmConta = findViewById(R.id.tv_saldoEmConta);
        textoRsConta = findViewById(R.id.tv_Reais_saldo);
        saldo = findViewById(R.id.tv_saldo);

        //progresBar
        progressConta = findViewById(R.id.progressConta);
        progressDados = findViewById(R.id.progressDados);



    }
    @Override
    protected void onStart() {

        super.onStart();

        int verifica = verificaLogin();

        if(verifica==-1){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }else if(verifica==0){
            startActivity(new Intent(this, SelecaoContaActivity.class));
            finish();
            return;
        }else if(verifica==1) {
            //recebe dados do servidor

            ReceberDadosMain receberDadosMain = new ReceberDadosMain();
            receberDadosMain.execute();




        }


    }

    public int verificaLogin() {

        sharedPreferences = getSharedPreferences("DADOS", Context.MODE_PRIVATE);
        logado = sharedPreferences.getBoolean("LOGADO", false);
        clienteAtual = sharedPreferences.getString("CLIENTE", "");
        contaAtual = sharedPreferences.getString("CONTA", "");

        if (!logado || clienteAtual.equals("")) return -1;

        if(contaAtual.equals("")){

            startActivity(new Intent(this, SelecaoContaActivity.class));
            finish();
            return 0;
        }

        return 1;

    }

    //historicos

    public void historicoTransferencias(View v){

        startActivity(new Intent(this, HistoricoTransferenciasActivity.class));

    }

    public void historicoSaques(View v){

        startActivity(new Intent(this, HistoricoSaquesActivity.class));
    }

    //metodos botoes inferiores

    public void realizarTransferencia(View v){
        startActivity(new Intent(this, RealizarTranferenciaActivity.class));
    }

    public void realizarSaque(View v){
        startActivity(new Intent(this, RandomCodeActivity.class));
    }

    public void dadosConta(View v){
        startActivity(new Intent(this, DadosContaActivity.class));
    }



    class ReceberDadosMain extends AsyncTask<Void, Void,Void> {

            private String resultadoCliente;
            private String resultadoConta;

            private String resultado;

            @Override
            protected Void doInBackground(Void... voids) {

                try{
                    Conexao conexao = new Conexao(2525);
                    conexao.conectaServidor();

                    conexao.enviaString("dadosMain");
                    conexao.enviaString(clienteAtual +"&&"+ contaAtual); //passar email e conta

                    System.out.println(clienteAtual +"&&"+ contaAtual);

                    resultadoCliente = conexao.recebeString();
                    resultadoConta = conexao.recebeString();

                    System.out.println(resultadoCliente+"//"+resultadoConta);

                    conexao.fechaConexao();


                    //dados do cliente nao batem, entao vai a tela de login
                    if(resultadoCliente == null){

                        Handler h = new Handler(getApplicationContext().getMainLooper());
                        h.post(new Runnable() {
                            @Override
                            public void run() {

                                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                                sharedPreferences.edit().putBoolean("LOGADO", false).apply();
                                sharedPreferences.edit().putString("CLIENTE", "").apply();
                                sharedPreferences.edit().putString("CONTA", "").apply();

                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        });
                    }

                    //dados da conta nao batem, entao vai para selecao de conta
                    if(resultadoConta == null){

                        Handler h = new Handler(getApplicationContext().getMainLooper());
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
                                sharedPreferences.edit().putString("CONTA", "").apply();

                                startActivity(new Intent(getApplicationContext(), SelecaoContaActivity.class));
                                finish();
                            }
                        });
                    }


                    if((!resultadoConta.equals("")) || (!resultadoCliente.equals(""))){

                        Handler handler = new Handler(getApplicationContext().getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String[] dadosConta = resultadoConta.split("&&");
                                String[] dadosUsuario = resultadoCliente.split("&&");

                                nome.setText(dadosUsuario[0]);

                                conta.setText(dadosConta[0]);
                                agencia.setText(dadosConta[1]);
                                saldo.setText(dadosConta[2]);


                            }
                        });


                    }

                }catch (Exception e) {
                    e.printStackTrace();


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
                        //dados
                        textoConta.setVisibility(View.INVISIBLE);
                        textoAgencia.setVisibility(View.INVISIBLE);
                        nome.setVisibility(View.INVISIBLE);
                        conta.setVisibility(View.INVISIBLE);
                        agencia.setVisibility(View.INVISIBLE);

                        //conta
                        textoSaldoEmConta.setVisibility(View.INVISIBLE);
                        textoRsConta.setVisibility(View.INVISIBLE);
                        saldo.setVisibility(View.INVISIBLE);


                        //progressBar
                        progressConta.setVisibility(View.VISIBLE);
                        progressDados.setVisibility(View.VISIBLE);

                        progressDados.setIndeterminate(true);
                        progressConta.setIndeterminate(true);

                    }
                });

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                //dados
                textoConta.setVisibility(View.VISIBLE);
                textoAgencia.setVisibility(View.VISIBLE);
                nome.setVisibility(View.VISIBLE);
                conta.setVisibility(View.VISIBLE);
                agencia.setVisibility(View.VISIBLE);

                //conta
                textoSaldoEmConta.setVisibility(View.VISIBLE);
                textoRsConta.setVisibility(View.VISIBLE);
                saldo.setVisibility(View.VISIBLE);


                //progressBar
                progressConta.setVisibility(View.INVISIBLE);
                progressDados.setVisibility(View.INVISIBLE);

                progressDados.setIndeterminate(false);
                progressConta.setIndeterminate(false);


                intent = new Intent(getApplicationContext(), ServiceAtualiza.class);

                intent.putExtra("SALDO", saldo.getText().toString());

                Context context = getApplicationContext();
                context.startService(intent);
            }

        }


        @Override
        protected void onStop() {

            super.onStop();
            try{
                stopService(intent);
            }catch (Exception e) {}

        }



    }


