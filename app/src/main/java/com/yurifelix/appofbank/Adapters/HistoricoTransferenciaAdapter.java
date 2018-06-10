package com.yurifelix.appofbank.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yurifelix.appofbank.R;
import com.yurifelix.appofbank.Model.Tranferencia;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yurif on 25/04/2018.
 */

public class HistoricoTransferenciaAdapter extends ArrayAdapter<Tranferencia> {

    private ArrayList<Tranferencia> tranferencias;
    private Context context;



    public HistoricoTransferenciaAdapter(Context context, ArrayList<Tranferencia> tranferencias) {
        super(context, 0, tranferencias);
        this.tranferencias = tranferencias;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(!(tranferencias==null)){

            //montagem da view;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_hist_transf_list, parent, false);

            Tranferencia tranf = tranferencias.get(i);

            TextView destinatario = view.findViewById(R.id.tv_destinatario);

            TextView valor = view.findViewById(R.id.tv_valor);

            TextView data = view.findViewById(R.id.tv_data);
            TextView hora = view.findViewById(R.id.tv_hora);

            destinatario.setText(tranf.getEmissor().toString());

            valor.setText(String.format(String.valueOf(valor.getText()), "%.2f"));

            Calendar calendar = tranf.getDataTransferecia();

            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            int hr = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);


            data.setText(dia+"/"+mes+"/"+ano);
            data.setText(hr+"h"+min);




        }
        return view;
    }
}
