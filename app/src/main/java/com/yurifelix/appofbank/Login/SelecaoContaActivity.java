package com.yurifelix.appofbank.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.yurifelix.appofbank.Conexao.Conexao;
import com.yurifelix.appofbank.MainActivity;
import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.ServerOffActivity;

import java.util.ArrayList;

public class SelecaoContaActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;


    private ProgressBar progressBar;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_conta);

        sharedPreferences = getSharedPreferences("DADOS", MODE_PRIVATE);

        if(sharedPreferences.getString("CLIENTE","")==null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        if(sharedPreferences.getString("CONTA","")==null){
            startActivity(new Intent(getApplicationContext(), CriarContaActivity.class));
            finish();
        }


        progressBar = findViewById(R.id.progressBar);


        //definicao listview 6b
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        ReceberContas receberContas = new ReceberContas();
        receberContas.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String conta = arrayList.get(position);

                sharedPreferences.edit().putString("CONTA", conta).apply();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();


            }
        });

    }


    public void novaConta(View v){
        startActivity(new Intent(this, CriarContaActivity.class));

    }

    public void sair(View v){
        sharedPreferences.edit().putString("CLIENTE", "").apply();
        startActivity(new Intent(this, LoginActivity.class));
    }




    class ReceberContas extends AsyncTask<Void, Void,Void> {

        private String resultado;

        @Override
        protected Void doInBackground(Void... voids) {

            String cliente = sharedPreferences.getString("CLIENTE","");


            try{
                Conexao conexao = new Conexao(2525);
                conexao.conectaServidor();
                conexao.enviaString("recebeContas");
                conexao.enviaString(cliente);

                resultado = conexao.recebeString();

                System.out.println("r"+resultado);

                conexao.fechaConexao();

                if(!resultado.equals("")){
                    String[] contas = resultado.split("&&");
                    arrayList.clear();
                    for(String c : contas){
                        arrayList.add(c);
                    }
                    Handler handler = new Handler(getApplicationContext().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });

                }else{
                    startActivity(new Intent(getApplicationContext(), CriarContaActivity.class));
                    finish();
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

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.INVISIBLE);


        }

    }


}
