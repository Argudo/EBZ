package es.uca.iw.ebz.usuario.cliente;

import javax.persistence.Entity;


public enum TipoCliente {
    Empresa, Persona;

    public int ToInt(){
        switch (this){
            case Empresa: return 0;
            case Persona: return 1;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }

    public static TipoCliente intToEnum(int i){
        switch (i){
            case 0: return TipoCliente.Empresa;
            case 1: return TipoCliente.Persona;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }
}
