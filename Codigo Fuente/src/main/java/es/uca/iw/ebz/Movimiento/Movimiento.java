package es.uca.iw.ebz.Movimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Movimiento {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    private Date Fecha;

    @Column
    @NotNull
    private TipoMoviento tipo;

    public Movimiento() {}

    public Movimiento(Date fecha, TipoMoviento tipo) {
        Fecha = fecha;
        this.tipo = tipo;
    }

    public static List<Movimiento> sortByFechaASC(List<Movimiento> movimientos) {
        movimientos.sort((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));
        return movimientos;
    }

    //getters
    public UUID getId() {return id;}
    public Date getFecha() {return Fecha;}
    public TipoMoviento getTipo() {return tipo;}

    //setters
    public void setId(UUID id) {this.id = id;}
    public void setFecha(Date fecha) {Fecha = fecha;}
    public void setTipo(TipoMoviento tipo) {this.tipo = tipo;}
}