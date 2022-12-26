package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        String sNumeroCuenta = new Iban.Builder()
                .countryCode(CountryCode.ES)
                .bankCode("62022")
                .buildRandom().toString();
        while (_cuentaRepository.findBysNumeroCuenta(sNumeroCuenta).isPresent()) {
            sNumeroCuenta = new Iban.Builder()
                    .countryCode(CountryCode.ES)
                    .bankCode("62022")
                    .buildRandom().toString();
        }
        cuenta.setNumeroCuenta(sNumeroCuenta);
        cuenta.setFechaCreacion(new Date());
        _cuentaRepository.save(cuenta);
        return cuenta;
    }

    public List<Cuenta> loadCuentas() {
        return _cuentaRepository.findByFechaEliminacionIsNull();
    }

    public boolean delete(Cuenta cuenta) {
        cuenta.setFechaEliminacion(new Date());
        //cuenta.setNumeroCuenta("1234");  error para preguntar: si tiene el mismo numero da error duplicate entry y si ponemos otro numero crea otra fila
        if(_cuentaRepository.save(cuenta) != null) {
            return true;
        }else {
            return false;
        }
    }

    public boolean delete2(String sNumeroCuenta) {
        Cuenta cuenta = _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta).get();
        cuenta.setFechaEliminacion(new Date());
        if(_cuentaRepository.save(cuenta) != null) {
            return true;
        }else {
            return false;
        }
    }
    public void update(String sNumeroCuenta, BigDecimal fSaldo) {
        Optional<Cuenta> cuenta = _cuentaRepository.findBysNumeroCuenta(sNumeroCuenta);
        cuenta.get().setSaldo(cuenta.get().getSaldo().add(fSaldo));
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
