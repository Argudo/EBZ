package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.ObjectMother;
import es.uca.iw.ebz.cliente.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTest {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
   public void shouldFindCuentaByNumeroCuenta() {
        //No existing cuenta
        //UUID id = UUID.randomUUID();
        Optional<Cuenta> cuenta = cuentaRepository.findBysNumeroCuenta("12345678901234567890");
        assertThat(cuenta.isPresent()).isFalse();

        //Existing cuenta
        Cuenta cuentaTest = ObjectMother.createTestCuenta();
        cuentaRepository.save(cuentaTest);
        cuenta = cuentaRepository.findBysNumeroCuenta("ES12345678901234567890");
        assertThat(cuenta.get()).isEqualTo(cuentaTest);
    }

    @Test
    public void shouldFindByFechaEliminacionIsNull() {
        List<Cuenta> cuentas = cuentaRepository.findByFechaEliminacionIsNull();
        assertThat(cuentas.size() == 0).isTrue();

        Cuenta cuentaTest = ObjectMother.createTestCuenta();
        cuentaRepository.save(cuentaTest);
        assertThat(cuentaRepository.findByFechaEliminacionIsNull().size() == 1).isTrue();
    }

    @Test
    public void shouldFindByCliente() {
        Cliente clienteTest = ObjectMother.createTestCliente();
        List<Cuenta> cuentasTest = cuentaRepository.findByCliente(clienteTest);
        assertThat(cuentasTest.size() == 0).isTrue();

        Cuenta cuentaTest = ObjectMother.createTestCuenta();
        cuentaTest.setCliente(clienteTest);
        cuentaRepository.save(cuentaTest);
        cuentasTest =  cuentaRepository.findByCliente(clienteTest);
        assertThat(cuentasTest.size() == 1).isTrue();
    }

}
