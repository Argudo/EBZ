package es.uca.iw.ebz;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.*;
import es.uca.iw.ebz.Movimiento.CompraTarjeta.CompraTarjeta;
import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.cliente.Cliente;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;

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

    public static Movimiento createTestMovimiento() {
        Movimiento movimiento = new Movimiento(new Date(), "Pago hipoteca", TipoMovimiento.INTERNO);
        return movimiento;
    }

    public static CompraTarjeta createTestCompraTarjeta() {
        CompraTarjeta compraTarjeta = new CompraTarjeta(createTestTarjeta(), "Amazon", 19.9F, ObjectMother.createTestMovimiento());
        return compraTarjeta;
    }

    public static Tarjeta createTestTarjeta() {
        Tarjeta tarjeta = new Tarjeta(1234, new TipoTarjeta(EnumTarjeta.Debito));
        return tarjeta;
    }

    public static Interno createTestInterno() {
        Interno interno = new Interno(150, ObjectMother.createTestCuenta(), ObjectMother.createTestCuenta(), ObjectMother.createTestMovimiento());
        return interno;
    }
}

