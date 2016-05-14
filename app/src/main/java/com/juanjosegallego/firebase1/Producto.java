package com.juanjosegallego.firebase1;

public class Producto {

    private String nombre,id,valor;
    public Producto(){

    }
    public Producto(String id,String nombre,String valor){
        this.id=id;
        this.nombre=nombre;
        this.valor=valor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getValor() {
        return valor;
    }
}
