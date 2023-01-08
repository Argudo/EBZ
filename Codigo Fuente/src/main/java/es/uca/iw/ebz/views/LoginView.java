package es.uca.iw.ebz.views;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("EBZ");
        i18nForm.setUsername("DNI");
        i18nForm.setPassword("Contraseña");
        i18nForm.setSubmit("Iniciar sesión");

        i18n.getErrorMessage().setTitle("DNI o contraseña incorrectos");
        i18n.setForm(i18nForm);
        i18n.getHeader().setTitle("EBZ");
        i18n.getHeader().setDescription("Tu banca online de confianza");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            if(authenticatedUser.get().get().getTipoUsuario().equals("Empleado")) {
                setOpened(false);
                event.forwardTo(DashBoardView.class);
            } else {
                setOpened(false);
                event.forwardTo("");
            }
        }
        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
