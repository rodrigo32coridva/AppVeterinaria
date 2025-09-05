package com.example.veterinaria;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class registro extends AppCompatActivity {

    EditText edtNombre, edtTipo, edtRaza, edtColor, edtPeso, edtGenero;
    Button btnGuardar;

    // Cambia la IP si tu servidor está en otra dirección
    private final String URL = "http://192.168.1.45:3000/mascotas";
    RequestQueue requestQueue;

    private void loadUI() {
        edtNombre = findViewById(R.id.edtNombre);
        edtTipo = findViewById(R.id.edtTipo);
        edtRaza = findViewById(R.id.edtRaza);
        edtColor = findViewById(R.id.edtColor);
        edtPeso = findViewById(R.id.edtPeso);
        edtGenero = findViewById(R.id.edtGenero);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();

        btnGuardar.setOnClickListener(view -> sendDataWS());
    }

    private void sendDataWS() {
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("tipo", edtTipo.getText().toString());
            jsonObject.put("raza", edtRaza.getText().toString());
            jsonObject.put("color", edtColor.getText().toString());
            jsonObject.put("peso", edtPeso.getText().toString());
            jsonObject.put("genero", edtGenero.getText().toString());
        } catch (Exception error) {
            Log.e("Error JSON envio", error.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                response -> {
                    Toast.makeText(registro.this, "Mascota registrada con éxito", Toast.LENGTH_SHORT).show();
                    Log.i("Registro exitoso", response.toString());
                },
                volleyError -> {
                    Toast.makeText(registro.this, "Error al registrar mascota", Toast.LENGTH_SHORT).show();

                    if (volleyError.networkResponse != null) {
                        int statusCode = volleyError.networkResponse.statusCode;
                        String body = new String(volleyError.networkResponse.data);
                        Log.e("VolleyError", "Código HTTP: " + statusCode + ", Respuesta: " + body);
                    } else {
                        Log.e("VolleyError", "Sin respuesta del servidor", volleyError);
                    }

                    volleyError.printStackTrace();
                }

        );

        requestQueue.add(jsonObjectRequest);
    }
}
