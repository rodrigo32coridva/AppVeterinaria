package com.example.veterinaria;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista extends AppCompatActivity {

    ListView lstMascotas; // Contenedor donde se mostrará la lista
    private final String URL = "http://192.168.1.45:3000/mascotas"; // Cambia IP a la de tu backend

    RequestQueue requestQueue;

    private void loadUI() {
        lstMascotas = findViewById(R.id.lstMascotas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.listar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();
        getData();
    }

    private void getData() {
        // 1. Inicializar el canal de comunicación
        requestQueue = Volley.newRequestQueue(this);

        // 2. Hacer solicitud GET al API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    Log.d("Datos recibidos:", response.toString());
                    renderData(response);
                },
                error -> Log.e("Error WS:", error.toString())
        );

        // 3. Agregar solicitud a la cola
        requestQueue.add(jsonArrayRequest);
    }

    private void renderData(JSONArray mascotas) {
        try {
            ArrayList<String> listaMascotas = new ArrayList<>();

            for (int i = 0; i < mascotas.length(); i++) {
                JSONObject jsonObject = mascotas.getJSONObject(i);

                // Ajusta los nombres según tu API
                String nombre = jsonObject.getString("nombre");
                String tipo = jsonObject.getString("tipo");
                String raza = jsonObject.getString("raza");

                listaMascotas.add(nombre + " - " + tipo + " (" + raza + ")");
            }

            // Adaptador para mostrar en el ListView
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listaMascotas
            );
            lstMascotas.setAdapter(arrayAdapter);

        } catch (Exception error) {
            Log.e("Error JSON recibido:", error.toString());
        }
    }
}
