package es.uca.iw.ebz.usuario;

public class ContraseñaIncorrecta extends Throwable {
    private String error;
    public  ContraseñaIncorrecta (String e){ error = e; }
    @Override
    public String toString() { return error; }
}
