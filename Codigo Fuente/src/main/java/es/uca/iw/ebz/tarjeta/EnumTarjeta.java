package es.uca.iw.ebz.tarjeta;

import org.apache.commons.lang3.StringUtils;

public enum EnumTarjeta {
	Debito, Credito, Prepago;
	
	public static EnumTarjeta toTipo(Integer iTipo){
		switch(iTipo) {
			case 1: return EnumTarjeta.Debito;
			case 2: return EnumTarjeta.Credito;
			case 3: return EnumTarjeta.Prepago;
			default: throw new IllegalArgumentException("El tipo de tarjeta no está entre los registrados");
		}
	}
	
	public static EnumTarjeta toTipo(String sTipo) {
		sTipo = sTipo.toLowerCase();
		sTipo = StringUtils.stripAccents(sTipo);
		switch(sTipo) {
		case "debito" : return EnumTarjeta.Debito;
		case "credito" : return EnumTarjeta.Credito;
		case "prepago" : return EnumTarjeta.Prepago;
		default : throw new IllegalArgumentException("El tipo de tarjeta no está entre los registrados");
		}
	}
	
	public static int toInt(EnumTarjeta tipo){
		switch(tipo) {
			case Debito: return 1;
			case Credito: return 2;
			case Prepago: return 3;
			default: throw new IllegalArgumentException("El tipo de tarjeta no está entre los registrados");
		}
	}
}
