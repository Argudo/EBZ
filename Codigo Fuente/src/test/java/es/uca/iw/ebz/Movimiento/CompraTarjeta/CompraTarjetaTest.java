package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.ObjectMother;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompraTarjetaTest {
    private CompraTarjeta compraTarjetaTest = ObjectMother.createTestCompraTarjeta();

    @Test
    public void shouldProvideId() {
        assertThat(compraTarjetaTest.getId() == null).isTrue();
    }
    @Test
    public void shouldProvideTarjeta() {
        assertThat(compraTarjetaTest.getTarjeta().getNumTarjeta().equals(ObjectMother.createTestTarjeta().getNumTarjeta())).isTrue();
        Tarjeta tarjeta = ObjectMother.createTestTarjeta();
        tarjeta.setNumTarjeta();
        compraTarjetaTest.setTarjeta(tarjeta);
        assertThat(compraTarjetaTest.getTarjeta().getNumTarjeta() == "1234567890123456").isTrue();
    }

    @Test
    public void shouldProvideDestino() {
        assertThat(compraTarjetaTest.getDestino().equals("Amazon")).isTrue();
        compraTarjetaTest.setDestino("Carrefour");
        assertThat(compraTarjetaTest.getDestino().equals("Carrefour")).isTrue();
    }

    @Test
    public void shouldProvideImporte() {
        assertThat(compraTarjetaTest.getImporte() == 19.9F).isTrue();
        compraTarjetaTest.setImporte(200);
        assertThat(compraTarjetaTest.getImporte() == 200).isTrue();

    }

    @Test
    public void shouldProvideMovimiento() {
        assertThat(compraTarjetaTest.getMovimiento().getConcepto().equals("Pago hipoteca")).isTrue();
        Movimiento movimiento = ObjectMother.createTestMovimiento();
        movimiento.setsConcpeto("Pago de la luz");
        compraTarjetaTest.setMovimiento(movimiento);
        assertThat(compraTarjetaTest.getMovimiento().getConcepto().equals("Pago de la luz")).isTrue();
    }

}
