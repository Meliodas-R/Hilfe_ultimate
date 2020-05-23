package com.example.hilfe_ultimate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ContactoBD {

    private SQLiteDatabase bd;
    private AdminSQLiteOpenHelper admin;

    public ContactoBD(Context context) {
        admin = new AdminSQLiteOpenHelper(context, "bd", null, 1);
    }

    public void abrirBD(){
        bd = admin.getWritableDatabase();
    }

    public void cerrarBD(){
        bd.close();
    }

    public long agregarContacto(Contacto contacto){
        abrirBD();
        long registro=0;

        ContentValues content=new ContentValues();
        content.put("NOMBRE_CONTACTO",contacto.getNombreContacto());
        content.put("TELEFONO_CONTACTO",contacto.getTelefonoContacto());

        registro= bd.insert("CONTACTO",null, content);
        cerrarBD();
        return registro;
    }

    public ArrayList<Contacto> listarContactos(){
        abrirBD();
        ArrayList<Contacto> listaContactos=new ArrayList<Contacto>();
        Cursor cursor=bd.rawQuery("SELECT ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO from CONTACTO",null);
        if (cursor.getCount()==0){
            cursor.close();
            cerrarBD();
            return null;
        }

        while(cursor.moveToNext()){
            Contacto contacto=new Contacto();
            contacto.setIdContacto(cursor.getInt(0));
            contacto.setNombreContacto(cursor.getString(1));
            contacto.setTelefonoContacto(cursor.getString(2));

            listaContactos.add(contacto);
        }
        cerrarBD();
        return listaContactos;
    }

    /**
     * Método que elimina un contacto.
     *
     * @param id Identificador del contacto.
     * @return Número de registros eliminados.
     */
    public long eliminarContacto(Integer id){
        abrirBD();
        long registro=0;
        registro = bd.delete("CONTACTO", "ID_CONTACTO= " + id, null);
        cerrarBD();
        return registro;
    }

//    /**
//     * Método que modifica un contacto.
//     *
//     * @param id Identificador del elemento.
//     * @return Número de registros modificados.
//     */
//    public long modificarContacto(Integer id){
//        abrirBD();
//        long registro=0;
//        registro = bd.update("CONTACTO","ID_CONTACTO=" + id, null);
//        cerrarBD();
//        return registro;
//
//    }


}
