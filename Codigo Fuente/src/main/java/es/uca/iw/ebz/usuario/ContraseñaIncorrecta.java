package es.uca.iw.ebz.usuario;

public class Contrase├▒aIncorrecta extends Throwable {
    private String error;
    public  Contrase├▒aIncorrecta (String e){ error = e; }
    @Override
    public String toString() { return error; }
}
