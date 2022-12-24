package es.uca.iw.ebz.tarjeta.prepago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.ebz.tarjeta.Tarjeta;

@Service
public class PrepagoService {
	PrepagoRepository _prepagoRepo;

	@Autowired
	public PrepagoService(PrepagoRepository prepagoRepo) {
		_prepagoRepo = prepagoRepo;
	}
	
	public Prepago Save(Prepago P) {
		return _prepagoRepo.save(P);
	}
	
	public long Count() {
		return _prepagoRepo.count();
	}
	
	public Prepago findByTarjeta(Tarjeta T) {
		return _prepagoRepo.findBy_tarjeta(T);
	}
}
