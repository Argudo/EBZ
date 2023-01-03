package es.uca.iw.ebz.consulta;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConsultaService {

    private ConsultaRepository _consultaRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) { _consultaRepository = consultaRepository; }

    public void Save(Consulta c) { _consultaRepository.save(c); }

    public void Delete(Consulta c) {
        c.setFechaEliminacion(new Date());
        Save(c);
    }

    public List<Consulta> findByCliente(Usuario usuario){
        return _consultaRepository.findBy_cliente(usuario);
    }

    public List<Consulta> findAll(){
        return _consultaRepository.findAll();
    }


}
