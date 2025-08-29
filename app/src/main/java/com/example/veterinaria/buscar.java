package com.example.veterinaria;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaria.entidades.Mascota;
import com.example.veterinaria.entidades.MascotaDBHelper;

import java.util.ArrayList;

public class buscar extends AppCompatActivity {

    EditText editTextBusqueda, editTextId, editTextNombre, editTextRaza,
            editTextTipo, editTextGenero, editTextPeso, editTextColor;
    ListView listaResultados;
    Button btnEditar, btnEliminar;

    MascotaDBHelper dbHelper;
    ArrayList<Mascota> listaMascotas;
    ArrayAdapter<String> adaptador;

    int mascotaSeleccionadaId = -1; // id de la mascota seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);

        // Inicializar vistas
        editTextBusqueda = findViewById(R.id.editTextBusqueda);
        editTextId = findViewById(R.id.editTextId);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextRaza = findViewById(R.id.editTextRaza);
        editTextTipo = findViewById(R.id.editTextTipo);
        editTextGenero = findViewById(R.id.editTextGenero);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextColor = findViewById(R.id.editTextColor);
        listaResultados = findViewById(R.id.listaResultados);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new MascotaDBHelper(this);

        // Bloquear campos y botones al inicio
        setCamposEditable(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        // Buscar al presionar Enter
        editTextBusqueda.setOnEditorActionListener((v, actionId, event) -> {
            buscarMascotas();
            return true;
        });

        // Selección de ítem en la lista
        listaResultados.setOnItemClickListener((adapterView, view, position, id) -> {
            Mascota mascota = listaMascotas.get(position);
            mascotaSeleccionadaId = mascota.getId();

            editTextId.setText(String.valueOf(mascota.getId()));
            editTextNombre.setText(mascota.getNombre());
            editTextRaza.setText(mascota.getRaza());
            editTextTipo.setText(mascota.getTipo());
            editTextGenero.setText(mascota.getGenero());
            editTextPeso.setText(String.valueOf(mascota.getPeso()));
            editTextColor.setText(mascota.getColor());

            setCamposEditable(true);
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(true);
        });

        // Confirmar antes de editar
        btnEditar.setOnClickListener(v -> {
            if (mascotaSeleccionadaId == -1) {
                Toast.makeText(this, "Seleccione una mascota", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Desea guardar los cambios de esta mascota?")
                    .setPositiveButton("Sí", (dialog, which) -> editarMascota())
                    .setNegativeButton("No", null)
                    .show();
        });

        // Confirmar antes de eliminar
        btnEliminar.setOnClickListener(v -> {
            if (mascotaSeleccionadaId == -1) {
                Toast.makeText(this, "Seleccione una mascota", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Está seguro de eliminar esta mascota?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarMascota())
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    // Buscar mascotas (por cualquier campo)
    private void buscarMascotas() {
        String textoBusqueda = editTextBusqueda.getText().toString().trim();
        listaMascotas = dbHelper.buscarMascotas(textoBusqueda); // 🔹 Mejorar query en DBHelper

        ArrayList<String> nombres = new ArrayList<>();
        for (Mascota m : listaMascotas) {
            nombres.add(m.getId() + " - " + m.getNombre() + " (" + m.getTipo() + ")");
        }

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaResultados.setAdapter(adaptador);
    }

    private void editarMascota() {
        Mascota mascota = new Mascota(
                mascotaSeleccionadaId,
                editTextNombre.getText().toString(),
                editTextTipo.getText().toString(),
                editTextRaza.getText().toString(),
                editTextGenero.getText().toString(),
                Double.parseDouble(editTextPeso.getText().toString()),
                editTextColor.getText().toString()
        );

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE mascotas SET nombre=?, raza=?, tipo=?, genero=?, peso=?, color=? WHERE id=?",
                new Object[]{
                        mascota.getNombre(),
                        mascota.getRaza(),
                        mascota.getTipo(),
                        mascota.getGenero(),
                        mascota.getPeso(),
                        mascota.getColor(),
                        mascota.getId()
                });

        Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
        buscarMascotas();
    }

    // Eliminar mascota seleccionada
    private void eliminarMascota() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM mascotas WHERE id=?", new Object[]{mascotaSeleccionadaId});

        Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
        mascotaSeleccionadaId = -1;
        limpiarCampos();

        setCamposEditable(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        buscarMascotas();
    }

    private void limpiarCampos() {
        editTextId.setText("");
        editTextNombre.setText("");
        editTextRaza.setText("");
        editTextTipo.setText("");
        editTextGenero.setText("");
        editTextPeso.setText("");
        editTextColor.setText("");
    }

    private void setCamposEditable(boolean editable) {
        editTextNombre.setEnabled(editable);
        editTextRaza.setEnabled(editable);
        editTextTipo.setEnabled(editable);
        editTextGenero.setEnabled(editable);
        editTextPeso.setEnabled(editable);
        editTextColor.setEnabled(editable);


        editTextId.setEnabled(false);

    }
}
