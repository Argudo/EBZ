package es.uca.iw.ebz.Cuenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {
    private Cuenta cuenta;

    @Autowired
    public CuentaService(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void añadirCuenta() {

    }
}
