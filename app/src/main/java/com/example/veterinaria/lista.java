package com.example.veterinaria;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaria.entidades.Mascota;
import com.example.veterinaria.entidades.MascotaDBHelper;

import java.util.ArrayList;

public class lista extends AppCompatActivity {

    private ListView listaResultados;
    private MascotaDBHelper dbHelper;
    private ArrayList<Mascota> listaMascotas;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        // Referencia al ListView
        listaResultados = findViewById(R.id.listaResultados);

        dbHelper = new MascotaDBHelper(this);

        cargarMascotas();
    }

    private void cargarMascotas() {
        listaMascotas = dbHelper.buscarMascotas("");

        ArrayList<String> nombres = new ArrayList<>();
        int contador = 1; // numeración bonita
        for (Mascota m : listaMascotas) {
            String raza = (m.getRaza() == null || m.getRaza().isEmpty())
                    ? "No tiene raza"
                    : m.getRaza();

            // usamos contador en lugar del id real
            nombres.add(contador + " - " + m.getNombre() + " (" + raza + ")");
            contador++;
        }

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaResultados.setAdapter(adaptador);
    }
}
