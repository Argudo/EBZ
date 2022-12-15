package es.uca.iw.ebz.Cuenta;


import es.uca.iw.ebz.ObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CuentaTest {
    private Cuenta cuentaTest = ObjectMother.createTestCuenta();
    @Test
    public void shouldProvideNumeroCuenta() {
        assertThat(cuentaTest.getNumeroCuenta().equals("ES12345678901234567890")).isTrue();

    }

    @Test
    public void shouldProvideSaldo() {
        assertThat(cuentaTest.getSaldo() == 0).isTrue();
        cuentaTest.setSaldo(9.99F);
        assertThat(cuentaTest.getSaldo() == 9.99F).isTrue();
    }

    @Test
    public void shouldProvideFechaCreacion() {
        assertThat(cuentaTest.getFechaCreacion() != null ).isTrue();
        //date of 1990-01-01
        Date date = new Date(631152000000L);
        cuentaTest.setFechaCreacion(date);
        assertThat(cuentaTest.getFechaCreacion().equals(date)).isTrue();
    }

    @Test
    public void shouldProvideFechaEliminacion() {
        assertThat(cuentaTest.getFechaEliminacion() == null ).isTrue();
        //date of 1990-01-01
        Date date = new Date(631152000000L);
        cuentaTest.setFechaEliminacion(date);
        assertThat(cuentaTest.getFechaEliminacion().equals(date)).isTrue();
    }

    @Test
    public void shouldProvideId() {
        assertThat(cuentaTest.getId() == null).isTrue();
    }

    @Test
    public void shouldProvideCliente() {
        assertThat(cuentaTest.getCliente() == null).isTrue();
        cuentaTest.setCliente(ObjectMother.createTestCliente());
        assertThat(cuentaTest.getCliente() != null).isTrue();
    }
}
