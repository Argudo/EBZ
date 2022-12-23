package es.uca.iw.ebz.Movimiento.Recibo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Recibo {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column(name = "Importe")
    @NotNull
    private float fImporte;

    @ManyToOne
    private Cuenta cuenta;

    @OneToOne
    private Movimiento movimiento;

    public Recibo() {}

    //getters
    public UUID getId() {return id;}
    public float getImporte() {return fImporte;}
    public Cuenta getCuenta() {return cuenta;}
    public Movimiento getMovimiento() {return movimiento;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setImporte(float fImporte) {this.fImporte = fImporte;}
    public void setCuenta(Cuenta cuenta) {this.cuenta = cuenta;}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}
}
