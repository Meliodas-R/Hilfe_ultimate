package com.example.hilfe_ultimate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactosListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contacto> listaContacto;

    public ContactosListAdapter(Context context, ArrayList<Contacto> listaContacto) {
        this.context = context;
        this.listaContacto = listaContacto;
    }

    @Override
    public int getCount() {
        if (listaContacto!=null) return listaContacto.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return listaContacto.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacto contacto= (Contacto) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.adapter_view_layout,null);
        TextView tvId= (TextView) convertView.findViewById(R.id.textView1);
        TextView tvNombre= (TextView) convertView.findViewById(R.id.textView2);
        TextView tvTfno= (TextView) convertView.findViewById(R.id.textView3);

        tvId.setText(contacto.getIdContacto().toString());
        tvNombre.setText(contacto.getNombreContacto());
        tvTfno.setText(contacto.getTelefonoContacto());

        return convertView;
    }
}
