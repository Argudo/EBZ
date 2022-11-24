package es.uca.iw.ebz.Cuenta;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class CuentaServiceTest {
    @Autowired
    private CuentaService cuentaService;

    @MockBean
    private CuentaRepository cuentaRepository;

    private TestEntityManager entityManager;

    Cuenta cuentaTest;


    @Test
    public void testAñadirCuenta() {
        Cuenta cuenta;
        cuentaTest = new Cuenta();
        cuentaTest = cuentaService.añadirCuenta(cuentaTest);
        cuenta = cuentaService.findByNumeroCuenta(cuentaTest.getNumeroCuenta());
        assertEquals(cuentaTest, cuenta);
    }

    @Test
    public void testUpdate() {
        Cuenta cuenta;
        cuentaTest = new Cuenta();
        cuentaTest.setNumeroCuenta("12345678901234567890");
        cuentaService.update(cuentaTest.getNumeroCuenta(), 10);
        cuenta = cuentaService.findByNumeroCuenta("12345678901234567890");
        assertTrue(cuentaTest.getSaldo() == 10);
    }

}
