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

/**
 * Adaptador de listas.
 *
 */
public class ContactosListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contacto> listaContacto;

    /**
     * Adapta la lista de contactos.
     *
     * @param context Contexto del método.
     * @param listaContacto Lista de contactos.
     */
    public ContactosListAdapter(Context context, ArrayList<Contacto> listaContacto) {
        this.context = context;
        this.listaContacto = listaContacto;
    }

    /**
     * Obtiene el tamaño de la lista de contactos.
     *
     * @return 0.
     */
    @Override
    public int getCount() {
        if (listaContacto!=null) return listaContacto.size();
        return 0;
    }

    /**
     * Obtiene el item de la lista de contactos.
     *
     * @param i Entero.
     * @return Lista de contactos con su item.
     */
    @Override
    public Object getItem(int i) {
        return listaContacto.get(i);
    }

    /**
     * Obtiene el identificador del item.
     *
     * @param position Posicion del item.
     * @return 0.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Obtiene la vista de la lista adaptada.
     *
     * @param position Posicion del item.
     * @param convertView Vista convertida.
     * @param parent Parent.
     * @return Lista convertida.
     */
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
