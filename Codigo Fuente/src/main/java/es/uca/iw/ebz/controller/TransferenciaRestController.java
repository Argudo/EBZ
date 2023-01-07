package es.uca.iw.ebz.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.Movimiento.TipoMovimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.views.main.DashBoardView;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.PermitAll;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TransferenciaRestController {

    @Autowired
    private TarjetaService _tarjetaService;

    @Autowired
    private MovimientoService _movimientoService;

    @Autowired
    private CuentaService _cuentaService;


    @PostMapping("/api/transactions")
    public TransaccionMovimiento transferencia(@RequestBody TransaccionMovimiento requestMov) {
       Movimiento movimiento = new Movimiento(new Date(), requestMov.getConcept(), TipoMovimiento.RECIBO);
        Optional<Cuenta> cuenta = _cuentaService.findByNumeroCuenta(requestMov.getIban());
        if(cuenta.isPresent()){

            if (requestMov.getTransactionType().equals("WITHDRAWAL")){
                requestMov.setValue(-requestMov.getValue());
            }
            try{
                _movimientoService.añadirRecibo(movimiento, cuenta.get(), requestMov.getValue());
                requestMov.setTransactionStatus("ACCEPTED");
                requestMov.setId(UUID.randomUUID().toString());
            } catch (Exception e) {
                requestMov.setTransactionStatus("REJECTED");
            }

        }else {
            requestMov.setTransactionStatus("REJECTED");
        }
        return requestMov;
    }

    @PostMapping("/api/payments")
    public TransaccionTarjeta compraTarjeta(@RequestBody TransaccionTarjeta requestTarjeta) {
        Movimiento movimiento = new Movimiento(new Date(), "Compra " + requestTarjeta.getType() + " en " + requestTarjeta.getShop(), TipoMovimiento.COMPRATARJETA);
        Tarjeta tarjeta = _tarjetaService.findByNumCuenta(requestTarjeta.getCardNumber());
        if (tarjeta != null) {
            try{
                _movimientoService.compraTarjeta(movimiento, tarjeta, requestTarjeta.getShop(), requestTarjeta.getValue(),
                        requestTarjeta.getExpirationMonth(), requestTarjeta.getExpirationYear(), requestTarjeta.getCsc());
                requestTarjeta.setPaymentStatus("ACCEPTED");
                requestTarjeta.setId(UUID.randomUUID().toString());
                requestTarjeta.setSecurityToken(000);
            } catch (Exception e) {
                //requestTarjeta.setPaymentStatus("REJECTED" + e.getMessage() + tarjeta.getFechaExpiracion());
                requestTarjeta.setPaymentStatus("REJECTED");
            }
        }else{
            requestTarjeta.setPaymentStatus("REJECTED");
        }

        return requestTarjeta;
    }

   /* @PostMapping("/prueba")
    public void prueba(){
        UI.getCurrent().navigate(DashBoardView.class);
    }*/
}