package es.uca.iw.ebz.consulta;

public enum EnumEstado {

    Abierto, Pendiente, Cerrado;

    public static EnumEstado toTipo(Integer iTipo){
        switch(iTipo) {
            case 1:
                return EnumEstado.Pendiente;
            case 2:
                return EnumEstado.Abierto;
            case 3:
                return EnumEstado.Cerrado;
            default:
                throw new IllegalArgumentException("El tipo de estado no está entre los registrados");
        }
    }

    public static int toInt(EnumEstado tipo){
        switch(tipo) {
            case Pendiente:
                return 1;
            case Abierto:
                return 2;
            case Cerrado:
                return 3;
            default:
                throw new IllegalArgumentException("El tipo de estado no está entre los registrados");
        }
    }
}
