package es.uca.iw.ebz.tarjeta;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.apache.commons.codec.binary.Hex;
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
	@Column(length=16, name = "id")
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
	
	@ManyToOne
	private Cliente _clienteTitular;
	
	//TODO: Conexion relacional
	@Column(name = "numCuenta")
	String _sNumCuenta;

	public Tarjeta() {
	}

	public Tarjeta(int iPin, TipoTarjeta tipoTarjeta) {
		super();
		_iPin = iPin;
		_tipoTarjeta = tipoTarjeta;
		_sNumCuenta = "81732HAASKJDHA1" + (int) (Math.random()*25+1) + "872";
		_sNumTarjeta = GenerarNumTarjeta();
		_fechaExpiracion = GenerarFechaExpiracion();
	}

	private String GenerarNumTarjeta() {
		String sNumTarjeta = "416180";
		//Tipo de tarjeta
		switch(_tipoTarjeta.getTipo()) {
			case Debito:
				sNumTarjeta += "00";
				String sNumCuenta_sha256 = StringToHexadecimal(Hashing.sha256().hashString(_sNumCuenta, StandardCharsets.UTF_8).toString());
				sNumTarjeta += sNumCuenta_sha256.substring(0,8).toUpperCase();
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
	
	private static Date GenerarFechaExpiracion() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, calendar.MONTH);
		calendar.set(Calendar.YEAR, calendar.YEAR);
		return calendar.getTime();
	}
	
	public static String StringToHexadecimal(String str) {
	      StringBuffer sb = new StringBuffer();
	      //Converting string to character array
	      char ch[] = str.toCharArray();
	      for(int i = 0; i < ch.length; i++) {
	         String hexString = Integer.toHexString(ch[i]);
	         sb.append(hexString);
	      }
	      return sb.toString();
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
	
	public static Component GenerarTarjeta(Tarjeta t) {
		UUID _id = t._iId;
		VerticalLayout vlTarjeta = new VerticalLayout();
        DateFormat dateFormat = new SimpleDateFormat("mm/yy");  
        String fechaExpiracion = dateFormat.format(t.getFechaExpiracion());  
        vlTarjeta.add(new H4("EBZ"),
        			   new H5(t.getNumTarjeta()),
        			   new H6(fechaExpiracion)
        			 );
        vlTarjeta.setClassName("tarjeta-mid");
        vlTarjeta.setWidth("300px");
        vlTarjeta.setHeight("200px");
        vlTarjeta.setPadding(false);
        vlTarjeta.getStyle().set("display", "-webkit-box");
        vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return vlTarjeta;
	}

	public UUID getId() { return _iId; }
	public String getNumTarjeta() { return _sNumTarjeta; }
	public void setNumTarjeta(String sNumTarjeta) { this._sNumTarjeta = sNumTarjeta; }
	public int getiPin() { return _iPin; }
	public void setiPin(int iPin) { this._iPin = iPin; }
	public Date getFechaExpiracion() { return _fechaExpiracion; }
	public void setFechaExpiracion(Date fechaExpiracion) { this._fechaExpiracion = fechaExpiracion; }
	public Date getFechaCreacion() { return _fechaCreacion; }
	public void setFechaCreacion(Date fechaCreacion) { this._fechaCreacion = fechaCreacion; }
	public Enum getTipoTarjeta() { return _tipoTarjeta.getTipo(); }
	public Date getFechaCancelacion() { return _fechaCancelacion; }
	public void setFechaCancelacion(Date fechaCancelacion) { _fechaCancelacion = fechaCancelacion; }
	public void setCliente(Cliente cliente) {this._clienteTitular = cliente; }
	public Cliente getCliente() { return this._clienteTitular; }
}
