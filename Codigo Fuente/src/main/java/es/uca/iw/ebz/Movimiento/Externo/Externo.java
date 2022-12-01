package es.uca.iw.ebz.Movimiento.Externo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Externo {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Cuenta cuentaPropia;

    @Column(name = "NumeroCuentaAjena")
    private String sNumCuentaAjena;

    @Column(name = "Importe")
    @NotNull
    private float fImporte;

    @OneToOne
    private Movimiento movimiento;

    public Externo() {}

    public Externo(float fImporte, Cuenta cuentaPropia, String sNumCuentaAjena) {
        this.cuentaPropia = cuentaPropia;
        this.sNumCuentaAjena = sNumCuentaAjena;
        this.fImporte = fImporte;
    }

    public Externo(float fImporte, Cuenta cuentaPropia, String sNumCuentaAjena, Movimiento movimiento) {
        this.cuentaPropia = cuentaPropia;
        this.sNumCuentaAjena = sNumCuentaAjena;
        this.fImporte = fImporte;
        this.movimiento = movimiento;
    }

    //getters
    public UUID getId() {return id;}
    public Cuenta getCuentaPropia() {return cuentaPropia;}
    public String getNumCuentaAjena() {return sNumCuentaAjena;}
    public float getImporte() {return fImporte;}
    public Movimiento getMovimiento() {return movimiento;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setCuentaPropia(Cuenta cuentaPropia) {this.cuentaPropia = cuentaPropia;}
    public void setNumCuentaAjena(String sNumCuentaAjena) {this.sNumCuentaAjena = sNumCuentaAjena;}
    public void setImporte(float fImporte) {this.fImporte = fImporte;}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}
}
