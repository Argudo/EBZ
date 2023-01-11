package es.uca.iw.ebz.Movimiento;

import es.uca.iw.ebz.ObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MovimientoTest {
    private Movimiento movimientoTest = ObjectMother.createTestMovimiento();

    @Test
    public void shouldProvideFecha() {
        assertThat(movimientoTest.getFecha() != null ).isTrue();
        //date of 1990-01-01
        Date date = new Date(631152000000L);
        movimientoTest.setFecha(date);
        assertThat(movimientoTest.getFecha().equals(date)).isTrue();
    }

    @Test
    public void shouldProvideTipo() {
        assertThat(movimientoTest.getTipo() == TipoMovimiento.INTERNO).isTrue();
        movimientoTest.setTipo(TipoMovimiento.EXTERNO);
        assertThat(movimientoTest.getTipo() == TipoMovimiento.EXTERNO).isTrue();
    }

    @Test
    public void shouldProvideDescripcion() {
        assertThat(movimientoTest.getConcepto().equals("Pago hipoteca")).isTrue();
        movimientoTest.setsConcpeto("Pago de la luz");
        assertThat(movimientoTest.getConcepto().equals("Pago de la luz")).isTrue();
    }

    @Test
    public void shouldProvideId() {
        assertThat(movimientoTest.getId() == null).isTrue();
    }


}
