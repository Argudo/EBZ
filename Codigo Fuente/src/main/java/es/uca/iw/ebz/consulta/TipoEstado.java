package es.uca.iw.ebz.consulta;

import javax.persistence.*;

@Entity
public class TipoEstado {

    @Id
    @Column(name = "id")
    Integer _iId;

    @Column(name = "Nombre")
    String _sNombre;

    public TipoEstado() {}

    public TipoEstado(EnumEstado enumTipo){

        _iId = EnumEstado.toInt(enumTipo);
        _sNombre = enumTipo.name();

    }

    public TipoEstado(int id, String nombre) {
        _iId = id;
        _sNombre = nombre;
    }

    public EnumEstado getTipo() {return EnumEstado.toTipo(_iId); }
}
