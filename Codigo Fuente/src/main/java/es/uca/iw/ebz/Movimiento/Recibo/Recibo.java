package es.uca.iw.ebz.Movimiento.Recibo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Recibo {

    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column(name = "Importe")
    @NotNull
    private BigDecimal fImporte;

    @ManyToOne
    private Cuenta cuenta;

    @OneToOne
    private Movimiento movimiento;

    @Version
    private Integer version;

    public Recibo() {}

    //getters
    public UUID getId() {return id;}
    public float getImporte() {return fImporte.floatValue();}
    public Cuenta getCuenta() {return cuenta;}
    public Movimiento getMovimiento() {return movimiento;}

    public Integer getVersion() {return version;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setImporte(float fImporte) {this.fImporte = BigDecimal.valueOf(fImporte);}
    public void setCuenta(Cuenta cuenta) {this.cuenta = cuenta;}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}
    public void setVersion(Integer version) {this.version = version;}
}
