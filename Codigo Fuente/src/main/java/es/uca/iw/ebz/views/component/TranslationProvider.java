package es.uca.iw.ebz.views.component;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
@Component
public class TranslationProvider implements I18NProvider {
    public static final String BUNDLE_PREFIX = "messages";
    public final Locale LOCALE_EN = new Locale("en", "GB");
    public final Locale LOCALE_ES = new Locale("es", "ES");

    private List<Locale> locales = Collections.unmodifiableList(Arrays.asList(LOCALE_EN, LOCALE_ES));

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if(key == null) {
            return "";
        }
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try{
            value = bundle.getString(key);
        } catch (MissingResourceException e) {
            value = "!" + key + "!";
        }

        if(params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }
}
