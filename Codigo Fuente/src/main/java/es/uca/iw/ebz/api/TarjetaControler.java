package es.uca.iw.ebz.api;

import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tarjeta")
public class TarjetaControler {

    @PostMapping("/transferencia")
    public Movimiento transferencia(){

    }

    @PostMapping("/tarjeta")
    public Tarjeta tarjeta(){

    }
}
//crear calase intermedia para recivir los datos