package es.uca.iw.ebz.Cuenta;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    public Cuenta(String sNumeroCuenta, float fSaldo, Date fechaCreacion) {
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
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCuenta() {return sNumeroCuenta;}
    public float getSaldo() {return fSaldo;}
    public Date getFechaCreacion() {return fechaCreacion;}
    public Date getFechaEliminacion() {return fechaEliminacion;}

    //setters
    public void setNumeroCuenta(String sNumeroCuenta) {this.sNumeroCuenta = sNumeroCuenta;}
    public void setSaldo(float fSaldo) {this.fSaldo = fSaldo;}
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public void setFechaEliminacion(Date fechaEliminacion) {this.fechaEliminacion = fechaEliminacion;}

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", sNumeroCuenta='" + sNumeroCuenta + '\'' +
                ", fSaldo=" + fSaldo +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEliminacion=" + fechaEliminacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cuenta cuenta = (Cuenta) o;

        if (id != cuenta.id) return false;
        if (Float.compare(cuenta.fSaldo, fSaldo) != 0) return false;
        if (sNumeroCuenta != null ? !sNumeroCuenta.equals(cuenta.sNumeroCuenta) : cuenta.sNumeroCuenta != null)
            return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(cuenta.fechaCreacion) : cuenta.fechaCreacion != null)
            return false;
        return fechaEliminacion != null ? fechaEliminacion.equals(cuenta.fechaEliminacion) : cuenta.fechaEliminacion == null;
    }
}
