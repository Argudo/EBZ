package es.uca.iw.ebz.tarjeta;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;

import com.google.common.hash.Hashing;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Entity
class TipoTarjeta{
	public static enum Tipo {Debito, Credito, Prepago};
	Tipo _tipoNombre;
	
	TipoTarjeta(int iTipo){
		_tipoNombre = IntToTipo(iTipo);
	}
	
	TipoTarjeta(Tipo enumTipo){
		_tipoNombre = enumTipo;
	}
	
	Tipo getTipo() { return _tipoNombre; }
	
	Tipo IntToTipo(int iTipo){
		switch(iTipo) {
			case 1:
				return Tipo.Debito;
			case 2:
				return Tipo.Credito;
			case 3:
				return Tipo.Prepago;
			default:
				return null;
		}
	}
}

@Entity
public class Tarjeta {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	UUID iId;
	
	String sNumTarjeta;
	int iPin;
	Date fechaExpiracion;
	Date fechaCreacion;
	Date fechaCancelacion;
	
	@ManyToOne
	TipoTarjeta tipoTarjeta;
	
	//TODO: Conexion relacional
	String sNumCuenta;
	
	public Tarjeta(int iPin, TipoTarjeta tipoTarjeta) {
		super();
		this.iPin = iPin;
		this.tipoTarjeta = tipoTarjeta;
		sNumTarjeta = GenerarNumTarjeta();
	}

	private String GenerarNumTarjeta() {
		String sNumTarjeta = "416180";
		//Tipo de tarjeta
		switch(tipoTarjeta.getTipo()) {
			case Debito:
				sNumTarjeta += "00";
				String sNumCuenta_sha256 = Hashing.sha256().hashString(sNumCuenta, StandardCharsets.UTF_8).toString();
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
	
	public Component GenerarTarjetaComponent() {
		VerticalLayout vlTarjeta = new VerticalLayout();
        vlTarjeta.add(new H4("EBZ"),
        			   new H5(sNumTarjeta),
        			   new H6(fechaExpiracion.toString()));
        vlTarjeta.setClassName("tarjeta-mid");
        vlTarjeta.setWidth("300px");
        vlTarjeta.setHeight("200px");
        vlTarjeta.setPadding(false);
        vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return vlTarjeta;
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

	public String getsNumTarjeta() { return sNumTarjeta; }
	public void setsNumTarjeta(String sNumTarjeta) { this.sNumTarjeta = sNumTarjeta; }
	public int getiPin() { return iPin; }
	public void setiPin(int iPin) { this.iPin = iPin; }
	public Date getFechaExpiracion() { return fechaExpiracion; }
	public void setFechaExpiracion(Date fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
	public Date getFechaCreacion() { return fechaCreacion; }
	public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
