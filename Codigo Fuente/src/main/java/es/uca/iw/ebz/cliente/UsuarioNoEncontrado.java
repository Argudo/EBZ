package es.uca.iw.ebz.cliente;

public class UsuarioNoEncontrado extends Throwable {
    private String error;
    public  UsuarioNoEncontrado (String e){ error = e; }
    @Override
    public String toString() { return error; }
}
