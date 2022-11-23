package es.uca.iw.ebz.Cuenta;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    @NotEmpty
    private String sNumeroCuenta;

    @Column
    @NotEmpty
    private float fSaldo = 0;

    @Column
    @NotEmpty
    private Date fechaCreacion;

    @Column
    private Date fechaEliminacion;

    //@ManyToOne
    //private user;
    public Cuenta() {}

    public Cuenta(String sNumeroCuenta, float fSaldo, Date fechaCreacion, Date fechaEliminacion) {
        this.sNumeroCuenta = sNumeroCuenta;
        this.fSaldo = fSaldo;
        this.fechaCreacion = fechaCreacion;
        this.fechaEliminacion = fechaEliminacion;
    }

    public Cuenta(Date fechaCreacion, Date fechaEliminacion) {
        this.fechaCreacion = fechaCreacion;
        this.fechaEliminacion = fechaEliminacion;
    }

    //getters
    public int getId() {return id;}
    public String getNumeroCuenta() {return sNumeroCuenta;}
    public float getSaldo() {return fSaldo;}
    public Date getFechaCreacion() {return fechaCreacion;}
    public Date getFechaEliminacion() {return fechaEliminacion;}

    //setters
    public void setNumeroCuenta(String sNumeroCuenta) {this.sNumeroCuenta = sNumeroCuenta;}
    public void setSaldo(float fSaldo) {this.fSaldo = fSaldo;}
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public void setFechaEliminacion(Date fechaEliminacion) {this.fechaEliminacion = fechaEliminacion;}


}
