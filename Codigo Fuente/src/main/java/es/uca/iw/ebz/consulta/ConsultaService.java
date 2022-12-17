package es.uca.iw.ebz.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsultaService {

    private ConsultaRepository _consultaRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) { _consultaRepository = consultaRepository; }

    public void Save(Consulta c) { _consultaRepository.save(c);}

    public void Delete(Consulta c) {
        c.setFechaEliminacion(new Date());
        Save(c);
    }


}
