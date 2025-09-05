package com.example.veterinaria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar, btnListar, btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializar botones
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);

        // Ajuste de Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Al hacer clic en "Registrar"
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, registro.class);
            startActivity(intent);
        });

        // Al hacer clic en "Listar"
        btnListar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, lista.class);
            startActivity(intent);
        });

        // Al hacer clic en "Buscar"
        btnBuscar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, buscar.class);
            startActivity(intent);
        });
    }
}
