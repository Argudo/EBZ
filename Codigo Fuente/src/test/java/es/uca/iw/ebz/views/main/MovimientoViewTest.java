package es.uca.iw.ebz.views.main;

import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.ObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MovimientoViewTest {
    @Qualifier("movimientoView")
    @Autowired
    private MovimientoView movimientoViewTest;

    @MockBean
    private MovimientoService movimientoService;

    @Test
    public void shouldShowInformationMovimiento() {
        Movimiento movimiento = ObjectMother.createTestMovimiento();
        Interno interno = ObjectMother.createTestInterno();

        /*movimientoViewTest.pOrigen.setText(interno.getCuentaOrigen().getNumeroCuenta());
        movimientoViewTest.pDestino.setText(interno.getCuentaDestino().getNumeroCuenta());
        movimientoViewTest.pConcepto.setText(movimiento.getsConcpeto());
        movimientoViewTest.pFecha.setText(movimiento.getFecha().toString());
        movimientoViewTest.pImporte.setText(String.valueOf(interno.getImporte()));

        assertThat(movimientoViewTest.pOrigen.getText().equals(interno.getCuentaOrigen().getNumeroCuenta())).isTrue();
        assertThat(movimientoViewTest.pDestino.getText().equals(interno.getCuentaDestino().getNumeroCuenta())).isTrue();
        assertThat(movimientoViewTest.pConcepto.getText().equals(movimiento.getsConcpeto())).isTrue();
        assertThat(movimientoViewTest.pFecha.getText().equals(movimiento.getFecha().toString())).isTrue();
        assertThat(movimientoViewTest.pImporte.getText().equals(String.valueOf(interno.getImporte()))).isTrue();
        */



    }

}
