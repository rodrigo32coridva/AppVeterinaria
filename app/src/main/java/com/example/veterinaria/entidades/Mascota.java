package com.example.veterinaria.entidades;

public class Mascota {
    private int id; // ← se usará solo al leer desde la BD
    private String nombre;
    private String tipo;
    private String raza;
    private String genero;
    private double peso;
    private String color;

    public Mascota() { }

    // Constructor sin id (para registrar nuevas mascotas)
    public Mascota(String nombre, String tipo, String raza, String genero, double peso, String color) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.color = color;
    }

    // Constructor con id (cuando recuperas de la BD)
    public Mascota(int id, String nombre, String tipo, String raza, String genero, double peso, String color) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.color = color;
    }
    //  Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPeso() {   // debe devolver double
        return peso;
    }
    public void setPeso(double peso) {   // debe recibir double
        this.peso = peso;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
