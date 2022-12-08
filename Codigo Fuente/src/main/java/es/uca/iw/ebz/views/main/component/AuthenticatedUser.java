package es.uca.iw.ebz.views.main.component;

import es.uca.iw.ebz.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthenticatedUser {
    private final UsuarioRepository userRepository;

    @Autowired
    public AuthenticatedUser(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication()).filter(authentication -> !(authentication instanceof  AnonymousAuthenticationToken));
    }*/
}
