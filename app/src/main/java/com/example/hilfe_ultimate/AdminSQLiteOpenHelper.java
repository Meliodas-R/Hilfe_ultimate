package com.example.hilfe_ultimate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Permite crear la base de datos y actualizar la estructura de tablas y datos
 * iniciales.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * Constructor de la base de datos.
     *
     * @param context Contexto.
     * @param name    Nombre de la base de datos.
     * @param factory Factory.
     * @param version Versión de la base de datos.
     */
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Define la estructura de las tablas y crea la base de datos.
     *
     * @param db Nombre de la base de datos.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table CONTACTO(ID_CONTACTO INTEGER primary key autoincrement, NOMBRE_CONTACTO text not null, TELEFONO_CONTACTO text not null)");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (1, 'SOS','112')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (2, 'Policia','091')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (3, 'Guardia civil','062')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (4, 'Bomberos','085')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (5, 'Emergencia medica','061')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (6, 'Cruz roja','901222222')");
        db.execSQL("insert into CONTACTO (ID_CONTACTO, NOMBRE_CONTACTO, TELEFONO_CONTACTO) values (7, 'Proteccion civil','1006')");
    }

    /**
     * Actualiza la base de datos.
     *
     * @param db         Nombre de la base de datos.
     * @param oldVersion Versión antigua de la base de datos.
     * @param newVersion Versión nueva de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
