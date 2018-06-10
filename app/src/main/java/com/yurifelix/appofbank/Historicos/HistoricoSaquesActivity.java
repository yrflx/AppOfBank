package com.yurifelix.appofbank.Historicos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yurifelix.appofbank.Adapters.HistoricoSaqueAdapter;
import com.yurifelix.appofbank.Conexao.Conexao;

import com.yurifelix.appofbank.Model.Saque;
import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.ServerOffActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoricoSaquesActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private HistoricoSaqueAdapter adapter;
    private ArrayList<Saque> saques;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_saques);

        listView = findViewById(R.id.listview);
        saques = new ArrayList<>();
        adapter = new HistoricoSaqueAdapter(this, saques);
        listView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        ReceberHistoricoSaques receberHistoricoSaques = new ReceberHistoricoSaques();
        receberHistoricoSaques.execute();




    }

    class ReceberHistoricoSaques extends AsyncTask<Void, Void,Void> {

        private String resultado;
        private SharedPreferences sharedPreferences;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Handler handler = new Handler(getApplicationContext().getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);

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
                    progressBar.setIndeterminate(false);

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
                conexao.enviaString("receberHistoricoSaques");
                conexao.enviaString(conta);
                resultado = "";
                resultado = conexao.recebeString();
                System.out.println("---->"+resultado);
                conexao.fechaConexao();
                Handler handler = new Handler(getApplicationContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!resultado.equals("")){
                            System.out.println("NAO DEU MERDA");
                            String[] result = resultado.split("&&");
                            System.out.println("NAO DEU MERDA  resultsize:" + result.length);
                            saques.clear();

                            for(String r : result){
                                String[] valorData = r.split("##");
                                System.out.println("---->VALOR:"+valorData[0]);
                                System.out.println("---->data:"+valorData[1]);

                                String[] d = valorData[1].split("/");
                                int ano = Integer.parseInt(d[0]);
                                int mes = Integer.parseInt(d[1]);
                                int dia = Integer.parseInt(d[2]);

                                Calendar data = Calendar.getInstance();

                                System.out.println("data:" + data.toString());

                                Saque saque = new Saque(Double.parseDouble(valorData[0]));
                                saque.setData(data);

                                saques.add(saque);

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
