package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class CompraTarjeta {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Tarjeta tarjeta;

    @Column(name = "Destino")
    @NotNull
    private String sDestino;

    @Column(name = "Importe")
    @NotNull
    private float fImporte;

    @OneToOne
    private Movimiento movimiento;

    public CompraTarjeta() {}

    public CompraTarjeta(Tarjeta tarjeta, String sDestino, float fImporte, Movimiento movimiento) {
        this.tarjeta = tarjeta;
        this.sDestino = sDestino;
        this.fImporte = fImporte;
        this.movimiento = movimiento;
    }

    //getters
    public UUID getId() {return id;}
    public Tarjeta getTarjeta() {return tarjeta;}
    public String getDestino() {return sDestino;}
    public float getImporte() {return fImporte;}
    public Movimiento getMovimiento() {return movimiento;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setTarjeta(Tarjeta tarjeta) {this.tarjeta = tarjeta;}
    public void setDestino(String sDestino) {this.sDestino = sDestino;}
    public void setImporte(float fImporte) {this.fImporte = fImporte;}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}

}
