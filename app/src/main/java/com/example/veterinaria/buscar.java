package com.example.veterinaria;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class buscar extends AppCompatActivity {

    EditText edtIdBuscado, edtNombre, edtTipo, edtRaza, edtColor, edtPeso, edtGenero;
    Button btnBuscarMascota, btnActualizarMascota, btnEliminarMascota;

    private final String URL = "http://192.168.1.45:3000/mascotas";
    RequestQueue requestQueue;
    String idReal = ""; // ID real del backend

    private void loadUI() {
        edtIdBuscado = findViewById(R.id.edtIdBuscado);
        edtNombre = findViewById(R.id.edtNombreEdit);
        edtTipo = findViewById(R.id.edtTipoEdit);
        edtRaza = findViewById(R.id.edtRazaEdit);
        edtColor = findViewById(R.id.edtColorEdit);
        edtPeso = findViewById(R.id.edtPesoEdit);
        edtGenero = findViewById(R.id.edtGeneroEdit);

        btnBuscarMascota = findViewById(R.id.btnBuscarMascota);
        btnActualizarMascota = findViewById(R.id.btnActualizarMascota);
        btnEliminarMascota = findViewById(R.id.btnEliminarMascota);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> insets);

        loadUI();

        btnBuscarMascota.setOnClickListener(view -> searchById());

        btnActualizarMascota.setOnClickListener(view -> {
            if (idReal.isEmpty()) {
                Toast.makeText(this, "Primero busca la mascota", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Actualizar Mascota")
                        .setMessage("¿Quieres actualizar la Mascota?")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Aceptar", (dialog, which) -> updateMascota())
                        .show();
            }
        });

        btnEliminarMascota.setOnClickListener(view -> {
            if (idReal.isEmpty()) {
                Toast.makeText(this, "Primero busca la mascota", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar Mascota")
                        .setMessage("¿Quieres eliminar la Mascota?")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Aceptar", (dialog, which) -> deleteMascota())
                        .show();
            }
        });
    }

    private void formClear() {
        edtNombre.setText("");
        edtTipo.setText("");
        edtRaza.setText("");
        edtColor.setText("");
        edtPeso.setText("");
        edtGenero.setText("");
        idReal = "";
    }

    private void searchById() {
        String idMascota = edtIdBuscado.getText().toString().trim();
        if (idMascota.isEmpty()) {
            edtIdBuscado.setError("Escriba el ID");
            edtIdBuscado.requestFocus();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    boolean found = false;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            if (obj.getString("id").equals(idMascota)) {
                                edtNombre.setText(obj.getString("nombre"));
                                edtTipo.setText(obj.getString("tipo"));
                                edtRaza.setText(obj.getString("raza"));
                                edtColor.setText(obj.getString("color"));
                                edtPeso.setText(obj.getString("peso"));
                                edtGenero.setText(obj.getString("genero"));
                                idReal = obj.getString("id");
                                found = true;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!found) {
                        formClear();
                        Toast.makeText(this, "No existe la mascota", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    formClear();
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void updateMascota() {
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombre.getText().toString().trim());
            jsonObject.put("tipo", edtTipo.getText().toString().trim());
            jsonObject.put("raza", edtRaza.getText().toString().trim());
            jsonObject.put("color", edtColor.getText().toString().trim());
            jsonObject.put("peso", Double.parseDouble(edtPeso.getText().toString().trim()));
            jsonObject.put("genero", edtGenero.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String endPoint = URL + "/" + idReal;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                response -> Toast.makeText(this, "Mascota actualizada correctamente", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void deleteMascota() {
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + idReal;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                response -> {
                    Toast.makeText(this, "Mascota eliminada correctamente", Toast.LENGTH_LONG).show();
                    formClear();
                    edtIdBuscado.setText("");
                    edtIdBuscado.requestFocus();
                },
                error -> Toast.makeText(this, "No se pudo eliminar la mascota", Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonObjectRequest);
    }
}
