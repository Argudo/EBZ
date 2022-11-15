package es.uca.iw.ebz.tarjeta;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.google.common.hash.Hashing;

class TipoTarjeta{
	public enum Nombre {Debito, Credito, Prepago};
	Nombre _tipoNombre;
	
	TipoTarjeta(int iTipo){
		_tipoNombre = IntToTipo(iTipo);
	}
	
	Nombre IntToTipo(int iTipo){
		switch(iTipo) {
			case 1:
				return Nombre.Debito;
			case 2:
				return Nombre.Credito;
			case 3:
				return Nombre.Prepago;
			default:
				return null;
		}
	}
}

public class Tarjeta {
	String sNumTarjeta;
	int iId;
	int iPin;
	Date fechaExpiracion;
	Date fechaCreacion;
	Date fechaCancelacion;
	TipoTarjeta.Nombre _tipoTarjeta;
	
	//Atributos relacionales
	//TODO: Conexion
	String _sNumCuenta;
	
	Tarjeta(){
		
	}
	
	private String GenerarNumTarjeta() {
		String sNumTarjeta = "4161-80";
		//Tipo de tarjeta
		switch(_tipoTarjeta) {
			case Debito:
				sNumTarjeta += "00";
				String sNumCuenta_sha256 = Hashing.sha256().hashString(_sNumCuenta, StandardCharsets.UTF_8).toString();
				sNumTarjeta += sNumCuenta_sha256.substring(0,8);
				break;
			case Credito:
				sNumTarjeta += "1";
				//TODO: segun rango crediticio añadir digito
				sNumTarjeta += "1";
				break;
			case Prepago:
				sNumTarjeta += "20";
				break;
		}
		sNumTarjeta += CalculateCheckDigit(sNumTarjeta);
		return sNumTarjeta;
	}
	
	private static String CalculateCheckDigit(String card) {
		if (card == null)
			return null;
		String digit;
		/* convert to array of int for simplicity */
		int[] digits = new int[card.length()];
		for (int i = 0; i < card.length(); i++) {
			digits[i] = Character.getNumericValue(card.charAt(i));
		}
		
		/* double every other starting from right - jumping from 2 in 2 */
		for (int i = digits.length - 1; i >= 0; i -= 2)	{
			digits[i] += digits[i];
			
			/* taking the sum of digits grater than 10 - simple trick by substract 9 */
			if (digits[i] >= 10) {
				digits[i] = digits[i] - 9;
			}
		}
		int sum = 0;
		for (int i = 0; i < digits.length; i++) {
			sum += digits[i];
		}
		/* multiply by 9 step */
		sum = sum * 9;
		
		/* convert to string to be easier to take the last digit */
		digit = sum + "";
		return digit.substring(digit.length() - 1);
	}
	public String getsNumTarjeta() {
		return sNumTarjeta;
	}
	public void setsNumTarjeta(String sNumTarjeta) {
		this.sNumTarjeta = sNumTarjeta;
	}
	public int getiPin() {
		return iPin;
	}
	public void setiPin(int iPin) {
		this.iPin = iPin;
	}
	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}
	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
}
