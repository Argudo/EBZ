package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.ObjectMother;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
        Cuenta cuenta = ObjectMother.createTestCuenta();
        cuentaTest = ObjectMother.createTestCuenta();
        cuentaTest = cuentaService.añadirCuenta(cuentaTest);
        System.out.println(cuentaTest.getId());
        given(cuentaRepository.findBysNumeroCuenta(cuentaTest.getNumeroCuenta())).willReturn(Optional.of(cuenta));
        cuenta = cuentaRepository.findBysNumeroCuenta(cuentaTest.getNumeroCuenta()).get();
        assertEquals(cuentaTest.getNumeroCuenta(), cuenta.getNumeroCuenta());
    }

    @Test
    public void testUpdate() {
        cuentaTest = new Cuenta();
        cuentaTest.setNumeroCuenta("12345678901234567890");
        cuentaService.update(cuentaTest.getNumeroCuenta(), 10);
        Optional<Cuenta> cuenta = cuentaService.findByNumeroCuenta("12345678901234567890");
        assertTrue(cuenta.get().getSaldo() == 10);
    }

}
