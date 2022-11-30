package es.uca.iw.ebz.tarjeta;

public enum EnumTarjeta {
	Debito, Credito, Prepago;
	
	public static EnumTarjeta toTipo(Integer iTipo){
		switch(iTipo) {
			case 1:
				return EnumTarjeta.Debito;
			case 2:
				return EnumTarjeta.Credito;
			case 3:
				return EnumTarjeta.Prepago;
			default:
				throw new IllegalArgumentException("El tipo de tarjeta no está entre los registrados");
		}
	}
	
	public static int toInt(EnumTarjeta tipo){
		switch(tipo) {
			case Debito:
				return 1;
			case Credito:
				return 2;
			case Prepago:
				return 3;
			default:
				throw new IllegalArgumentException("El tipo de tarjeta no está entre los registrados");
		}
	}
}
