package es.uca.iw.ebz.Movimiento.Interno;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Interno {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "Importe")
    @NotNull
    private float fImporte;

    @ManyToOne
    private Cuenta cuentaDestino;

    @ManyToOne
    private Cuenta cuentaOrigen;

    @OneToOne
    private Movimiento movimiento;

    public Interno() {}

    public Interno(float fImporte, Cuenta cuentaDestino, Cuenta cuentaOrigen) {
        this.fImporte = fImporte;
        this.cuentaDestino = cuentaDestino;
        this.cuentaOrigen = cuentaOrigen;
    }

    public Interno(float fImporte, Cuenta cuentaDestino, Cuenta cuentaOrigen, Movimiento movimiento) {
        this.fImporte = fImporte;
        this.cuentaDestino = cuentaDestino;
        this.cuentaOrigen = cuentaOrigen;
        this.movimiento = movimiento;
    }

    //getters
    public UUID getId() {return id;}
    public float getImporte() {return fImporte;}
    public Cuenta getCuentaDestino() {return cuentaDestino;}
    public Cuenta getCuentaOrigen() {return cuentaOrigen;}
    public Movimiento getMovimiento() {return movimiento;}


    //setters
    public void setId(UUID id) {this.id = id;}
    public void setImporte(float fImporte) {this.fImporte = fImporte;}
    public void setCuentaDestino(Cuenta cuentaDestino) {this.cuentaDestino = cuentaDestino;}
    public void setCuentaOrigen(Cuenta cuentaOrigen) {this.cuentaOrigen = cuentaOrigen;}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}
}
