package es.uca.iw.ebz;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.cliente.Cliente;

public class ObjectMother {
    public static Cuenta createTestCuenta() {
        Cuenta cuenta = new Cuenta();
        return cuenta;
    }

    public static Cliente createTestCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setUsuario("ROOT");
        cliente
        return cliente;
    }
}
}
