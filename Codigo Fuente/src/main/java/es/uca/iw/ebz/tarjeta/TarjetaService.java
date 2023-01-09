package es.uca.iw.ebz.tarjeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.usuario.cliente.Cliente;


@Service
public class TarjetaService {

	private TarjetaRepository _tarRepository;
	
	@Autowired
	public TarjetaService(TarjetaRepository tarRepository) {
		_tarRepository = tarRepository;
	}
	
	public void Save(Tarjeta T) throws Exception {
		if(findByNumTarjeta(T.getNumTarjeta()) == null)
			_tarRepository.save(T);
		else
			throw new Exception("El número de tarjeta creado ya existe");
	}
	
	public void Update(Tarjeta T) throws Exception {
		if(findByNumTarjeta(T.getNumTarjeta()) != null)
			_tarRepository.save(T);
		else
			throw new Exception("No se encontro la tarjeta");
	}
	
	public void Delete(Tarjeta T) throws Exception {
		T.setFechaCancelacion(new Date());
		Save(T);
	}
	
	public Tarjeta findById(UUID Id) throws Exception {
		Optional<Tarjeta> optT =_tarRepository.findById(Id);
		if (optT.isPresent() && optT.get().getFechaCancelacion() != null) return optT.get();
	    throw new Exception("No se encontró la tarjeta para el id dado");
	}
	
	public List<Tarjeta> findAll() {
		List<Tarjeta> aT = new ArrayList<Tarjeta>();
		for(Tarjeta T: _tarRepository.findAll()) 
			if(T.getFechaCancelacion() == null) aT.add(T);
		return aT;
	}
	
	public List<Tarjeta> findAllById(Iterable<UUID> aId){
		List<Tarjeta> aT = new ArrayList<Tarjeta>();
		_tarRepository.findAllById(aId).forEach(T -> {
			if(T.getFechaCancelacion() == null) aT.add(T);
		});
		return aT;
	}
	
	public List<Tarjeta> findByCliente(Cliente cliente){
		List<Tarjeta> aT = new ArrayList<Tarjeta>();
		_tarRepository.findBy_clienteTitular(cliente).forEach(T -> {
			if(T.getFechaCancelacion() == null) aT.add(T);
		});
		return aT;
	}
	
	public List<Tarjeta> findByCuenta(Cuenta cuenta){
		return _tarRepository.findAllBy_cuenta(cuenta);
	}
	
	public Tarjeta findByNumTarjeta(String sNumTarjeta) {
		return _tarRepository.findByNumTarjeta(sNumTarjeta);
	}
	
	public long Count() {
		return _tarRepository.count();
	}
	
	
	public Component GenerarTarjeta(Tarjeta t) {
		VerticalLayout vlTarjeta = new VerticalLayout();
        vlTarjeta.add(new H4("EBZ"),
        			   new H5(t.getNumTarjeta()),
        			   new H6(t.getFechaExpiracion().toString()));
        vlTarjeta.setClassName("tarjeta-mid");
        vlTarjeta.setWidth("300px");
        vlTarjeta.setHeight("200px");
        vlTarjeta.setPadding(false);
        vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return vlTarjeta;
	}
}
