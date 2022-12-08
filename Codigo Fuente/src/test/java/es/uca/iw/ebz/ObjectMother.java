package es.uca.iw.ebz;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.cliente.Cliente;

import java.util.Date;

public class ObjectMother {
    public static Cuenta createTestCuenta() {
        Cuenta cuenta = new Cuenta(new Date());
        cuenta.setNumeroCuenta("ES12345678901234567890");
        return cuenta;
    }

    public static Cliente createTestCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setUsuario("ROOT");
        return cliente;
    }
}

