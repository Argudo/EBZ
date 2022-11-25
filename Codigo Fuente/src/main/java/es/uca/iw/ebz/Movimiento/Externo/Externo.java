package es.uca.iw.ebz.Movimiento.Externo;

import es.uca.iw.ebz.Cuenta.Cuenta;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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

    public Externo() {}

    public Externo(Cuenta cuentaPropia, String sNumCuentaAjena, float fImporte) {
        this.cuentaPropia = cuentaPropia;
        this.sNumCuentaAjena = sNumCuentaAjena;
        this.fImporte = fImporte;
    }

    //getters
    public UUID getId() {return id;}
    public Cuenta getCuentaPropia() {return cuentaPropia;}
    public String getNumCuentaAjena() {return sNumCuentaAjena;}
    public float getImporte() {return fImporte;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setCuentaPropia(Cuenta cuentaPropia) {this.cuentaPropia = cuentaPropia;}
    public void setNumCuentaAjena(String sNumCuentaAjena) {this.sNumCuentaAjena = sNumCuentaAjena;}
    public void setImporte(float fImporte) {this.fImporte = fImporte;}
}
