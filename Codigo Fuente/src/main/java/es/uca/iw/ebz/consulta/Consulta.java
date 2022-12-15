package es.uca.iw.ebz.consulta;


import es.uca.iw.ebz.cliente.Cliente;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Consulta {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private String titulo;

    @Column
    @NotNull
    private String descripcion;

    @Column
    @NotNull
    private Date fechaCreacion;

    @Column
    private Date fechaEliminacion;

    //@ManyToOne()
    //private Usuario cliente;

    //@ManyToOne()
    //private Usuario admin;


    public Consulta() {}

    //Falta añadir el cliente y el admin
    public Consulta(String titulo, String descripcion, Date fechaCreacion){

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;

    }

    public static List<Consulta> sortByFechaASC(List<Consulta> consultas){
        consultas.sort((c1, c2) -> c1.getFechaCreacion().compareTo(c2.getFechaCreacion()));
        return consultas;
    }

    //Getters
    public UUID getId() {return id;}

    public Date getFechaCreacion() {return fechaCreacion;}

    public Date getFechaEliminacion() {return fechaEliminacion;}

    public String getTitulo() {return titulo;}

    public String getDescripcion() {return descripcion;}

    //Setters

    public void setId(UUID id) {this.id = id;}

    public void setFechaEliminacion(Date fecha) {this.fechaEliminacion = fecha;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}





}
