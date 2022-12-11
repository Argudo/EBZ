package es.uca.iw.ebz.Movimiento.Interno;

import es.uca.iw.ebz.ObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class InternoTest {
    private Interno internoTest = ObjectMother.createTestInterno();

    @Test
    public void shouldProvideId() {
        assertThat(internoTest.getId() == null).isTrue();
    }

    @Test
    public void shouldProvideMovimiento() {
        assertThat(internoTest.getMovimiento() != null).isTrue();
    }

    @Test
    public void shouldProvideCuentaOrigen() {
        assertThat(internoTest.getCuentaOrigen() != null).isTrue();
    }

    @Test
    public void shouldProvideCuentaDestino() {
        assertThat(internoTest.getCuentaDestino() != null).isTrue();
    }

    @Test
    public void shouldProvideImporte() {
        assertThat(internoTest.getImporte() == 150).isTrue();
        internoTest.setImporte(9.99F);
        assertThat(internoTest.getImporte() == 9.99F).isTrue();
    }

}
