package com.skeletonhexa.domain.entities;
public class Medico {
    private Integer id;
    private String nombre;
    private Integer especialidadId;
    private String horarioInicio;
    private String horarioFin;

    public Medico() {}

    public Medico(Integer id, String nombre, Integer especialidadId, String horarioInicio, String horarioFin) {
        this.id = id;
        this.nombre = nombre;
        this.especialidadId = especialidadId;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }

    // Getters and Setters
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

    public Integer getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Integer especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    @Override
    public String toString() {
        return "Medico [id=" + id + ", nombre=" + nombre + ", especialidadId=" + especialidadId + ", horarioInicio="
                + horarioInicio + ", horarioFin=" + horarioFin + "]";
    }

    
}