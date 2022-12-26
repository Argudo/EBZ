package es.uca.iw.ebz.Movimiento.RecargaTarjeta;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class RecargaTarjeta {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Cuenta cuenta;

    @ManyToOne
    private Tarjeta tarjeta;

    @Column(name = "Importe")
    @NotNull
    private BigDecimal fImporte;

    @OneToOne
    private Movimiento movimiento;

    public RecargaTarjeta() {}

    public RecargaTarjeta(Cuenta cuenta, Tarjeta tarjeta, float fImporte, Movimiento movimiento) {
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;
        this.fImporte = BigDecimal.valueOf(fImporte);
        this.movimiento = movimiento;
    }

    //getters
    public UUID getId() {return id;}
    public Cuenta getCuenta() {return cuenta;}
    public Tarjeta getTarjeta() {return tarjeta;}
    public float getImporte() {return fImporte.floatValue();}
    public Movimiento getMovimiento() {return movimiento;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setCuenta(Cuenta cuenta) {this.cuenta = cuenta;}
    public void setTarjeta(Tarjeta tarjeta) {this.tarjeta = tarjeta;}
    public void setImporte(float fImporte) {this.fImporte = BigDecimal.valueOf(fImporte);}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}

}
