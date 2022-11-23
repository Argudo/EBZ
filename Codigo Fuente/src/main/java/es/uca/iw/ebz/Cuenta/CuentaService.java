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

    public void añadirCuenta(Cuenta cuenta) {
        //generar numero de cuenta aleatoria y comprobar que no existe
        String sNumeroCuenta = generarNumeroCuenta();
        while (_cuentaRepository.findByNumeroCuenta(sNumeroCuenta) != null) {
            sNumeroCuenta = generarNumeroCuenta();
        }
        _cuentaRepository.save(cuenta);
    }

    public List<Cuenta> loadCuentas() {
        return _cuentaRepository.findByFechaEliminacionIsNull();
    }

    public void delete(Cuenta cuenta) {
        cuenta.setFechaEliminacion(new Date());
        _cuentaRepository.save(cuenta);
    }

    public void update(String sNumeroCuenta, float fSaldo) {
        Cuenta cuenta = _cuentaRepository.findByNumeroCuenta(sNumeroCuenta);
        cuenta.setSaldo(cuenta.getSaldo() + fSaldo);
        _cuentaRepository.save(cuenta);
    }

    private String generarNumeroCuenta() {
        String sNumeroCuenta = "";
        for (int i = 0; i < 20; i++) {
            sNumeroCuenta += (int) (Math.random() * 10);
        }
        return sNumeroCuenta;
    }
}
