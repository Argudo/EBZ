package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class CompraTarjeta {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @ManyToOne
    private Tarjeta tarjeta;

    @Column(name = "Destino")
    @NotNull
    private String sDestino;

    @Column(name = "Importe")
    @NotNull
    private BigDecimal fImporte;

    @OneToOne
    private Movimiento movimiento;

    @Version
    private Integer version;

    public CompraTarjeta() {}

    public CompraTarjeta(Tarjeta tarjeta, String sDestino, float fImporte, Movimiento movimiento) {
        this.tarjeta = tarjeta;
        this.sDestino = sDestino;
        this.fImporte = BigDecimal.valueOf(fImporte);
        this.movimiento = movimiento;
    }

    //getters
    public UUID getId() {return id;}
    public Tarjeta getTarjeta() {return tarjeta;}
    public String getDestino() {return sDestino;}
    public float getImporte() {return fImporte.floatValue();}
    public Movimiento getMovimiento() {return movimiento;}
    public Integer getVersion() {return version;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setTarjeta(Tarjeta tarjeta) {this.tarjeta = tarjeta;}
    public void setDestino(String sDestino) {this.sDestino = sDestino;}
    public void setImporte(float fImporte) {this.fImporte = BigDecimal.valueOf(fImporte);}
    public void setMovimiento(Movimiento movimiento) {this.movimiento = movimiento;}
    public void setVersion(Integer version) {this.version = version;}

}
