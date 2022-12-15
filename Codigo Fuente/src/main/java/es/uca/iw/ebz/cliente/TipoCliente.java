package es.uca.iw.ebz.cliente;

public enum TipoCliente {
    empresa, persona;

    public int ToInt(){
        switch (this){
            case empresa: return 0;
            case persona: return 1;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }

    public static TipoCliente intToEnum(int i){
        switch (i){
            case 0: return TipoCliente.empresa;
            case 1: return TipoCliente.persona;
            default: throw new IllegalArgumentException("Error, dato fura del dominio");
        }
    }
}
