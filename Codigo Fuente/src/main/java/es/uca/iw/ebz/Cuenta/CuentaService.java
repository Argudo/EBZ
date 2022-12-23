package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CuentaService {
    private CuentaRepository _cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        _cuentaRepository = cuentaRepository;
    }

    public Cuenta añadirCuenta(Cuenta cuenta) {
        //generar numero de cuenta aleatoria y comprobar que no existe
        String sNumeroCuenta = generarNumeroCuenta();
        while (_cuentaRepository.findBysNumeroCuenta(sNumeroCuenta).isPresent()) {
            sNumeroCuenta = generarNumeroCuenta();
        }
        cuenta.setNumeroCuenta(sNumeroCuenta);
        cuenta.setFechaCreacion(new Date());
        _cuentaRepository.save(cuenta);
        return cuenta;
    }

    public List<Cuenta> loadCuentas() {
        return _cuentaRepository.findByFechaEliminacionIsNull();
    }

    public void delete(Cuenta cuenta) {
        cuenta.setFechaEliminacion(new Date());
        _cuentaRepository.save(cuenta);
    }

    public void update(String sNumeroCuenta, float fSaldo) {
        Optional<Cuenta> cuenta = _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta);
        cuenta.get().setSaldo(cuenta.get().getSaldo() + fSaldo);
        _cuentaRepository.save(cuenta.get());
    }

    public Optional<Cuenta> findByNumeroCuenta(String sNumeroCuenta) {
        return _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta);
    }

    public List<Cuenta> findByCliente(Cliente cliente) {
        return _cuentaRepository.findByCliente(cliente);
    }

    private String generarNumeroCuenta() {
        String sNumeroCuenta = "";
        for (int i = 0; i < 20; i++) {
            sNumeroCuenta += (int) (Math.random() * 10);
        }
        return sNumeroCuenta;
    }

    public Cuenta save(Cuenta cuenta) {
        return _cuentaRepository.save(cuenta);
    }
}
