package es.uca.iw.ebz.usuario;

import java.util.Collection;
import java.util.stream.DoubleStream;

public enum TipoUsuario {
    Cliente, Empleado;

    public int ToInt(){
        switch (this){
            case Cliente: return 0;
            case Empleado: return 1;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }

    public static TipoUsuario intToEnum(int i){
        switch (i){
            case 0: return TipoUsuario.Cliente;
            case 1: return TipoUsuario.Empleado;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }

}
