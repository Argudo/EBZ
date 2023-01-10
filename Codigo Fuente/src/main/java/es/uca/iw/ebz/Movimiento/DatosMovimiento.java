package es.uca.iw.ebz.Movimiento;

import java.util.Date;

public class DatosMovimiento {
    private String id;
    private Date fecha;
    private String tipo;
    private String origen;
    private String destino;
    private String importe;
    private String concepto;

    public DatosMovimiento(String id, Date fecha, String tipo, String origen, String destino, String importe, String concepto) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        this.importe = importe;
        this.concepto = concepto;
    }

    public DatosMovimiento(){}

    //getters
    public String getId() {
        return id;
    }
    public Date getFecha() {
        return fecha;
    }
    public String getTipo() {
        return tipo;
    }
    public String getOrigen() {
        return origen;
    }
    public String getDestino() {
        return destino;
    }
    public String getImporte() {
        return importe;
    }
    public String getConcepto() {
        return concepto;
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public void setImporte(String importe) {
        this.importe = importe;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
