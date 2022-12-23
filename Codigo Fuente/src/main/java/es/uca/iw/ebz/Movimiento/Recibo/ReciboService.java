package es.uca.iw.ebz.Movimiento.Recibo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboService {

    private ReciboRepository _reciboRepository;

    @Autowired
    public ReciboService(ReciboRepository reciboRepository) {
        _reciboRepository = reciboRepository;
    }

    public Recibo añadirRecibo(Recibo recibo) {
        return _reciboRepository.save(recibo);
    }
}
