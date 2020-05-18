package com.example.hilfe_ultimate;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //final String crearTablaContactos="create table contactos(idContacto int primary key autoincrement, nombreContacto text not null, apellidoContacto text, telefonoContacto text not null)";

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table CONTACTO(ID_CONTACTO INTEGER primary key autoincrement, NOMBRE_CONTACTO text not null, TELEFONO_CONTACTO text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
