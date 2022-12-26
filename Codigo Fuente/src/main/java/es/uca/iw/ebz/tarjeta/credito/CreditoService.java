package es.uca.iw.ebz.tarjeta.credito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.ebz.tarjeta.Tarjeta;

@Service
public class CreditoService {
	CreditoRepository _credRepo;
	
	@Autowired
	public CreditoService(CreditoRepository credRepo) {
		_credRepo = credRepo;
	}
	
	public Credito Save(Credito C) {
		return _credRepo.save(C);
	}
	
	public Credito findByTarjeta(Tarjeta T) {
		return _credRepo.findBy_tarjeta(T);
	}	
	
	public long Count() {
		return _credRepo.count();
	}
}
