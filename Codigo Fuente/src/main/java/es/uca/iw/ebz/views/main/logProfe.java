package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.ebz.views.main.component.AuthenticatedUser;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class logProfe extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public logProfe(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("tacticas");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (authenticatedUser.get().isPresent()){
            setOpened(false);
            //beforeEnterEvent.forwardTo("");
        }

        //setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
