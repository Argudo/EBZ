package es.uca.iw.ebz.tarjeta;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import javax.persistence.Id;

import com.google.common.hash.Hashing;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Entity
public class Tarjeta {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	@Column(name = "id")
	private UUID _iId;
	
	@Column(name = "numTarjeta")
	private String _sNumTarjeta;
	@Column(name = "PIN")
	private int _iPin;
	@Column(name = "fechaExpiracion")
	private Date _fechaExpiracion;
	
	@Column(name = "fechaCreacion")
	private Date _fechaCreacion;
	
	@Column(name = "fechaCancelacion")
	private Date _fechaCancelacion;
	
	@ManyToOne
	private TipoTarjeta _tipoTarjeta;
	
	//TODO: Conexion relacional
	@Column(name = "numCuenta")
	String _sNumCuenta;
	
	public Tarjeta(int iPin, TipoTarjeta tipoTarjeta) {
		super();
		_iPin = iPin;
		_tipoTarjeta = tipoTarjeta;
		_sNumTarjeta = GenerarNumTarjeta();
	}

	private String GenerarNumTarjeta() {
		String sNumTarjeta = "416180";
		//Tipo de tarjeta
		switch(_tipoTarjeta.getTipo()) {
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

	public String getsNumTarjeta() { return _sNumTarjeta; }
	public void setsNumTarjeta(String sNumTarjeta) { this._sNumTarjeta = sNumTarjeta; }
	public int getiPin() { return _iPin; }
	public void setiPin(int iPin) { this._iPin = iPin; }
	public Date getFechaExpiracion() { return _fechaExpiracion; }
	public void setFechaExpiracion(Date fechaExpiracion) { this._fechaExpiracion = fechaExpiracion; }
	public Date getFechaCreacion() { return _fechaCreacion; }
	public void setFechaCreacion(Date fechaCreacion) { this._fechaCreacion = fechaCreacion; }
}
