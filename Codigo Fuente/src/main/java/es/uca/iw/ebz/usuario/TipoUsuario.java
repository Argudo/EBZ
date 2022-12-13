package es.uca.iw.ebz.usuario;

public enum TipoUsuario {
    cliente, empleado;

    public int ToInt(){
        switch (this){
            case cliente: return 0;
            case empleado: return 1;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }

    public static TipoUsuario intToEnum(int i){
        switch (i){
            case 0: return TipoUsuario.cliente;
            case 1: return TipoUsuario.empleado;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }
}
