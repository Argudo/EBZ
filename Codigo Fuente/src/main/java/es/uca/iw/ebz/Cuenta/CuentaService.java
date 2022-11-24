package es.uca.iw.ebz.Cuenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;


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
        while (_cuentaRepository.findBysNumeroCuenta(sNumeroCuenta) != null) {
            sNumeroCuenta = generarNumeroCuenta();
        }
        cuenta.setNumeroCuenta(sNumeroCuenta);
        cuenta.setFechaCreacion(new Date());
        return _cuentaRepository.save(cuenta);
       // return cuenta;
    }

    public List<Cuenta> loadCuentas() {
        return _cuentaRepository.findByFechaEliminacionIsNull();
    }

    public void delete(Cuenta cuenta) {
        cuenta.setFechaEliminacion(new Date());
        _cuentaRepository.save(cuenta);
    }

    public void update(String sNumeroCuenta, float fSaldo) {
        Cuenta cuenta = _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta);
        cuenta.setSaldo(cuenta.getSaldo() + fSaldo);
        _cuentaRepository.save(cuenta);
    }

    public Cuenta findByNumeroCuenta(String sNumeroCuenta) {
        return _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta);
    }

    private String generarNumeroCuenta() {
        String sNumeroCuenta = "";
        for (int i = 0; i < 20; i++) {
            sNumeroCuenta += (int) (Math.random() * 10);
        }
        return sNumeroCuenta;
    }
}
