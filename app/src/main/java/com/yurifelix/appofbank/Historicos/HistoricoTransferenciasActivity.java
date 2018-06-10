package com.yurifelix.appofbank.Historicos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.yurifelix.appofbank.Adapters.HistoricoTransferenciaAdapter;
import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.Model.Conta;
import com.yurifelix.appofbank.Model.Tranferencia;
import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.ServerOffActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoricoTransferenciasActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private HistoricoTransferenciaAdapter adapter;
    private ArrayList<Tranferencia> tranferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_transferencias);

        listView = findViewById(R.id.listview);
        tranferencias = new ArrayList<>();
        adapter = new HistoricoTransferenciaAdapter(this, tranferencias);
        listView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);




    }

    @Override
    protected void onStart() {
        super.onStart();

        ReceberHistoricoTransferencia receberHistoricoTransferencia = new ReceberHistoricoTransferencia();
        receberHistoricoTransferencia.execute();
    }

    class ReceberHistoricoTransferencia extends AsyncTask<Void, Void,Void> {

        private String resultado;
        private SharedPreferences sharedPreferences;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Handler handler = new Handler(getApplicationContext().getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                //    progressBar.setVisibility(View.VISIBLE);
                  //  progressBar.setIndeterminate(true);

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
                  //  progressBar.setVisibility(View.INVISIBLE);
                  //  progressBar.setIndeterminate(false);

                }
            });

        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);
            String conta = sharedPreferences.getString("CONTA", "");


            try{
                Conexao conexao = new Conexao(2525);


                conexao.conectaServidor();

                conexao.enviaString("receberHistoricoTransferencia");

                conexao.enviaString(conta);

                resultado = "";
                resultado = conexao.recebeString();
                System.out.println("RESULTADO:" + resultado);
                conexao.fechaConexao();
                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!resultado.equals("")){

                            String[] result = resultado.split("&&");

                            tranferencias.clear();

                            for(String r : result){
                                String[] valorData = r.split("##");

                                String valor = valorData[0];

                                Conta receptor = new Conta();
                                receptor.setNumero(valorData[2]);

                                String[] d = valorData[3].split("/");

                                int ano = Integer.parseInt(d[0]);
                                int mes = Integer.parseInt(d[1]);
                                int dia = Integer.parseInt(d[2]);

                                Calendar data = Calendar.getInstance();

                                data.set(ano,mes, dia);


                                Tranferencia tranferencia = new Tranferencia();

                                tranferencia.setValor(Double.parseDouble(valor));
                                tranferencia.setReceptor(receptor);
                                tranferencia.setDataTransferecia(data);

                                tranferencias.add(tranferencia);

                            }
                            adapter.notifyDataSetChanged();
                        }else {
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
