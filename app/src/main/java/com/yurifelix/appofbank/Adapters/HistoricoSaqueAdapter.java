package com.yurifelix.appofbank.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yurifelix.appofbank.Model.Saque;
import com.yurifelix.appofbank.Model.Tranferencia;
import com.yurifelix.appofbank.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yurif on 25/04/2018.
 */

public class HistoricoSaqueAdapter extends ArrayAdapter<Saque> {

    private ArrayList<Saque> saques;
    private Context context;



    public HistoricoSaqueAdapter(Context context, ArrayList<Saque> saques) {
        super(context, 0, saques);
        this.saques = saques;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(!(saques==null)){

            //montagem da view;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_hist_saque_list, parent, false);

            Saque saque = saques.get(i);

            TextView valor = view.findViewById(R.id.tv_valor);

            TextView data = view.findViewById(R.id.tv_data);

            Calendar calendar = saque.getData();

            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);


            data.setText(dia+"/"+(mes+1)+"/"+ano);
            valor.setText("R$ " + saque.getValor());



        }
        return view;
    }
}
