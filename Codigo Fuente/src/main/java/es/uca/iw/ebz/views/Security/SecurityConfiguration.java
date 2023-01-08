package es.uca.iw.ebz.views.Security;


import com.vaadin.flow.spring.security.VaadinWebSecurity;
import es.uca.iw.ebz.views.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    public static final String LOGOUT_URL = "/";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"));
        http.authorizeRequests()
                .requestMatchers(
                        new AntPathRequestMatcher("/images/*.png"),
                        new AntPathRequestMatcher("/swagger-ui.html"),
                        new AntPathRequestMatcher("/swagger-ui/**")
                ).permitAll().requestMatchers(new AntPathRequestMatcher("api/**")).authenticated();
        super.configure(http);
        setLoginView(http, LoginView.class, LOGOUT_URL);
    }

}
