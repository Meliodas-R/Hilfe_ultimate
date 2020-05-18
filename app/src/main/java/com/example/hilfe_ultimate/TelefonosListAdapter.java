package com.example.hilfe_ultimate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TelefonosListAdapter extends ArrayAdapter<Telefono> {

    private static final String TAG = "TelefonoListAdapter";

    private Context mContext;
    int mResource;

    public TelefonosListAdapter(Context context, int resource, ArrayList<Telefono> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position).getIdTelefono();
        String nombre = getItem(position).getNombreTelefono();
        String telefono = getItem(position).getTelefonoTelefono();

        Telefono tfn = new Telefono(id,nombre,telefono);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvId= (TextView) convertView.findViewById(R.id.textView1);
        TextView tvNombre= (TextView) convertView.findViewById(R.id.textView2);
        TextView tvTfno= (TextView) convertView.findViewById(R.id.textView3);

        tvId.setText(id.toString());
        tvNombre.setText(nombre);
        tvTfno.setText(telefono);

        return convertView;
    }
}
