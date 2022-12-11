package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.ObjectMother;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompraTarjetaRepositoryTest {
    @Autowired
    private CompraTarjetaRepository compraTarjetaRepository;

    //@Test
    /*public void shouldFindCompraTarjetaByMovimiento() {
        //No existing compraTarjeta
        //UUID id = UUID.randomUUID();
        Optional<CompraTarjeta> compraTarjeta = compraTarjetaRepository.findByMovimiento(ObjectMother.createTestMovimiento());
        assertThat(compraTarjeta.isPresent()).isFalse();

        //Existing compraTarjeta
        CompraTarjeta compraTarjetaTest = ObjectMother.createTestCompraTarjeta();
        compraTarjetaRepository.save(compraTarjetaTest);
        compraTarjeta = compraTarjetaRepository.findByMovimiento(movimiento);
        assertThat(compraTarjeta.get()).isEqualTo(compraTarjetaTest);
    }*/

    @Test
    public void shouldFindByTarjeta() {
        Tarjeta tarjetaTest = ObjectMother.createTestTarjeta();
        List<CompraTarjeta> compraTarjetasTest = compraTarjetaRepository.findByTarjeta(tarjetaTest);
        assertThat(compraTarjetasTest.size() == 0).isTrue();

        CompraTarjeta compraTarjetaTest = ObjectMother.createTestCompraTarjeta();
        compraTarjetaTest.setTarjeta(tarjetaTest);
        compraTarjetaRepository.save(compraTarjetaTest);
        compraTarjetasTest =  compraTarjetaRepository.findByTarjeta(tarjetaTest);
        assertThat(compraTarjetasTest.size() == 1).isTrue();
    }
}
