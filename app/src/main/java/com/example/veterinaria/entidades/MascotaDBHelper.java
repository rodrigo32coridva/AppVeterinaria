package com.example.veterinaria.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MascotaDBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "veterinaria.db";
    private static final int VERSION_BD = 6; // Subimos la versión porque corregimos columnas
    private static final String TABLA_MASCOTAS = "mascotas";

    public MascotaDBHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_MASCOTAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "raza TEXT, " +
                "tipo TEXT, " +
                "genero TEXT, " +
                "peso REAL, " +
                "color TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_MASCOTAS);
        onCreate(db);
    }

    //  Registrar mascota
    public long registrarMascota(Mascota mascota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", mascota.getNombre());
        valores.put("raza", mascota.getRaza());
        valores.put("tipo", mascota.getTipo());
        valores.put("genero", mascota.getGenero());
        valores.put("peso", mascota.getPeso());
        valores.put("color", mascota.getColor());
        return db.insert(TABLA_MASCOTAS, null, valores);
    }

    //  Listar todas las mascotas
    public ArrayList<Mascota> listarMascotas() {
        ArrayList<Mascota> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_MASCOTAS, null);

        if (cursor.moveToFirst()) {
            do {
                Mascota mascota = new Mascota(
                        cursor.getInt(0),   // id
                        cursor.getString(1),// nombre
                        cursor.getString(3),// tipo
                        cursor.getString(2),// raza
                        cursor.getString(4),// genero
                        cursor.getDouble(5),// peso
                        cursor.getString(6) // color
                );
                lista.add(mascota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    // Buscar por nombre
    public ArrayList<Mascota> buscarMascotas(String texto) {
        ArrayList<Mascota> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM mascotas WHERE " +
                "id LIKE ? OR nombre LIKE ? OR tipo LIKE ? OR raza LIKE ? OR genero LIKE ? OR color LIKE ? OR peso LIKE ?";

        String like = "%" + texto + "%";
        Cursor cursor = db.rawQuery(query, new String[]{like, like, like, like, like, like, like});

        if (cursor.moveToFirst()) {
            do {
                Mascota mascota = new Mascota(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getString(6)
                );
                lista.add(mascota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }


    // Eliminar mascota por ID
    public int eliminarMascota(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLA_MASCOTAS, "id = ?", new String[]{String.valueOf(id)});
    }
}
