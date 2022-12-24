package es.uca.iw.ebz.tarjeta;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.google.common.hash.Hashing;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.usuario.cliente.Cliente;


@Entity
public class Tarjeta {
	@Id
	@GeneratedValue
	@Column(length=16, name = "id")
	private UUID _iId;
	
	@Column(name = "numTarjeta")
	@NotNull
	private String _sNumTarjeta;
	
	@Column(name = "PIN")
	@NotNull
	private int _iPin;
	
	@Column(name = "fechaExpiracion")
	@NotNull
	private Date _fechaExpiracion;
	
	@Column(name = "fechaCreacion")
	@NotNull
	private Date _fechaCreacion;
	
	@Column(name = "fechaCancelacion")
	private Date _fechaCancelacion;
	
	@Column(name = "gastoDiario")
	private BigDecimal _gastoDiario;
	
	@Column(name = "gastoMax")
	private BigDecimal _gastoMax;
	
	@Column(name = "costeMantenimiento")
	private BigDecimal _costeMantenimiento;
	
	@Column(name = "CVC")
	private String _cvc;
	
	@ManyToOne
	@NotNull
	private TipoTarjeta _tipoTarjeta;
	
	@ManyToOne
	@NotNull
	private Cliente _clienteTitular;
	
	@OneToOne
	Cuenta _cuenta;
	
	public Tarjeta() {}
	
	//Constructor prepago
	public Tarjeta(int iPin, Cliente cliente) {
		this(iPin, new TipoTarjeta(EnumTarjeta.Prepago), null,  cliente);
	}


	//Constructor Débito y Credito
	public Tarjeta(int iPin, TipoTarjeta tipoTarjeta, Cuenta cuenta, Cliente cliente) {
		if(tipoTarjeta.getTipo() != EnumTarjeta.Prepago && cuenta == null) throw new IllegalArgumentException("La cuenta no puede ser nula para los tipos débito y crédito");
		_iPin = iPin;
		_tipoTarjeta = tipoTarjeta;
		_cuenta = cuenta;
		_clienteTitular = cliente;
		_sNumTarjeta = GenerarNumTarjeta();
		_cvc = GenerarCVC();
		_fechaCreacion = new Date();
		_fechaExpiracion = GenerarFechaExpiracion();
	}

	private String GenerarNumTarjeta() {
		String sNumTarjeta = "416180";
		//Tipo de tarjeta
		switch(_tipoTarjeta.getTipo()) {
			case Debito:
				sNumTarjeta += "00";
				sNumTarjeta += StringToHexadecimal(Hashing.sha256().hashString(_cuenta.getNumeroCuenta(), StandardCharsets.UTF_8).toString()).substring(0,8).toUpperCase();
				break;
			case Credito:
				sNumTarjeta += "10";
				sNumTarjeta += StringToHexadecimal(Hashing.sha256().hashString(_cuenta.getNumeroCuenta(), StandardCharsets.UTF_8).toString()).substring(0,8).toUpperCase();
				break;
			case Prepago:
				sNumTarjeta += "20";
				String cliente_sha256 = StringToHexadecimal(Hashing.sha256().hashString(_clienteTitular.getUsuario().getDNI(), StandardCharsets.UTF_8).toString());
				sNumTarjeta += cliente_sha256.substring(0,8).toUpperCase();
				break;
		}
		sNumTarjeta += CalculateCheckDigit(sNumTarjeta);
		return sNumTarjeta;
	}
	
	private String GenerarCVC() { return GenerarCVC(null); }
	private String GenerarCVC(Integer valor) {
		int iValor = valor == null? new Random().nextInt(1, 999) : valor;
		String sCVC = String.valueOf(iValor);
		while(sCVC.length() < 3) {
			sCVC = "0".concat(sCVC);
		}
		return sCVC;
	}
	
	private static Date GenerarFechaExpiracion() {
		Date dFecha = new Date();
		dFecha.setYear(dFecha.getYear()+4);
		return dFecha;
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

	public String getStringTipoTarjeta() throws Exception {
		switch(_tipoTarjeta.getTipo()) {
		case Debito: return "Débito";
		case Prepago: return "Prepago";
		case Credito: return "Crédito";
		default: throw new Exception("El tipo de la tarjeta no esta entre los esperados");
		}
	}

	public UUID getId() { return _iId; }
	public String getNumTarjeta() { return _sNumTarjeta; }
	public void setNumTarjeta(String sNumTarjeta) { this._sNumTarjeta = sNumTarjeta; }
	public int getiPin() { return _iPin; }
	public void setiPin(int iPin) { this._iPin = iPin; }
	public String getFechaExpiracion() { return (String.valueOf(_fechaExpiracion.getMonth()) + "/" + String.valueOf(_fechaExpiracion.getYear()-100)); }
	public void setFechaExpiracion(Date fechaExpiracion) { this._fechaExpiracion = fechaExpiracion; }
	public Date getFechaCreacion() { return _fechaCreacion; }
	public void setFechaCreacion(Date fechaCreacion) { this._fechaCreacion = fechaCreacion; }
	public EnumTarjeta getTipoTarjeta() { return _tipoTarjeta.getTipo(); }
	public Date getFechaCancelacion() { return _fechaCancelacion; }
	public void setFechaCancelacion(Date fechaCancelacion) { _fechaCancelacion = fechaCancelacion; }
	public void setCliente(Cliente cliente) {this._clienteTitular = cliente; }
	public String getCVC() { return _cvc; }
	public void setCVC(String cvc) { _cvc = cvc; }
	public void setCVC(int cvc) { _cvc = GenerarCVC(cvc); }
	public Cuenta getCuenta() { return _cuenta; }
	public Cliente getCliente() { return this._clienteTitular; }
}
