package es.uca.iw.ebz.Cuenta;




import es.uca.iw.ebz.usuario.cliente.Cliente;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue
    @Column(length=16)
    private UUID id;

    @Column(unique = true, name = "numero_cuenta")
    @NotEmpty
    private String sNumeroCuenta;

    @Column(name = "saldo")
    @NotNull
    private BigDecimal fSaldo = BigDecimal.valueOf(0);

    @Column
    @NotNull
    private Date fechaCreacion;

    @Column
    private Date fechaEliminacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull
    private Cliente cliente;
    public Cuenta() {}

    public Cuenta(String sNumeroCuenta, float fSaldo, Date fechaCreacion) {
        this.sNumeroCuenta = sNumeroCuenta;
        this.fSaldo = BigDecimal.valueOf(fSaldo);
        this.fechaCreacion = fechaCreacion;
        this.fechaEliminacion = fechaEliminacion;
    }

    public Cuenta(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    //getters
    public UUID getId() {return id;}
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroCuenta() {return sNumeroCuenta;}
    public @NotNull BigDecimal getSaldo() {return fSaldo;}
    public Date getFechaCreacion() {return fechaCreacion;}
    public Date getFechaEliminacion() {return fechaEliminacion;}
    public Cliente getCliente() {return cliente;}
    public String getDNICliente() {return cliente.getUsuario().getDNI();}

    //setters
    public void setNumeroCuenta(String sNumeroCuenta) {this.sNumeroCuenta = sNumeroCuenta;}
    public void setSaldo(BigDecimal fSaldo) {this.fSaldo = fSaldo;}
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public void setFechaEliminacion(Date fechaEliminacion) {this.fechaEliminacion = fechaEliminacion;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

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
}
