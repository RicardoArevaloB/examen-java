package com.skeletonhexa.domain.entities;

public class Especialidadmedic {
    private Integer id;
    private String nombre;

    
    public Especialidadmedic() {}

   
    public Especialidadmedic(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Especialidadmedic [id=" + id + ", nombre=" + nombre + "]";
    }

    
}