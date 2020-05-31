package com.example.hilfe_ultimate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Componente de acceso a datos.
 *
 */
public class ContactoBD {

    private SQLiteDatabase bd;
    private AdminSQLiteOpenHelper admin;

    /**
     *
     *
     * @param context Contexto de la actividad.
     */
    public ContactoBD(Context context) {
        admin = new AdminSQLiteOpenHelper(context, "bd", null, 1);
    }

    /**
     * Abre la base de datos para poder interactuar con ella.
     *
     */
    public void abrirBD(){
        bd = admin.getWritableDatabase();
    }

    /**
     * Cierra la base de datos y termina la interaccion con ella.
     *
     */
    public void cerrarBD(){
        bd.close();
    }

    /**
     * Agrega un contacto a la base de datos.
     *
     * @param contacto Objeto contacto que contiene todos sus atributos.
     * @return Registros insertados.
     */
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

    /**
     * Lista todos los contactos que hay en la base de datos.
     *
     * @return Listado de contactos.
     */
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
     * Elimina un contacto de la base de datos.
     *
     * @param id Identificador del contacto.
     * @return Registros eliminados.
     */
    public long eliminarContacto(Integer id){
        abrirBD();
        long registro=0;
        registro = bd.delete("CONTACTO", "ID_CONTACTO = " + id, null);
        cerrarBD();
        return registro;
    }

    /**
     * Modifica un contacto de la base de datos.
     *
     * @param id Identificador del elemento.
     * @return Registros modificados.
     */
    public long modificarContacto(Contacto contacto, Integer id){
        abrirBD();
        long registro=0;
        ContentValues content = new ContentValues();
        content.put("NOMBRE_CONTACTO", contacto.getNombreContacto());
        content.put("TELEFONO_CONTACTO", contacto.getTelefonoContacto());
        registro = bd.update("CONTACTO", content, "ID_CONTACTO = " + id, null);
        cerrarBD();
        return registro;

    }


}
