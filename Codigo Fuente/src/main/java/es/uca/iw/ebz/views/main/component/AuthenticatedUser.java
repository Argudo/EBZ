package es.uca.iw.ebz.views.main.component;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;



import java.util.Optional;


@Component
public class AuthenticatedUser {
    private final UsuarioRepository userRepository;

    @Autowired
    public AuthenticatedUser(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication()).filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<Usuario> get() {
            return getAuthentication().map(authentication -> userRepository.findBysUsuario(authentication.getName()));
    }

    /*
    public void logout() {
        UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }*/
}
