package es.uca.iw.ebz.views.component;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import es.uca.iw.ebz.noticia.Noticia;
import es.uca.iw.ebz.noticia.NoticiaService;

import java.util.Date;


public class CreateNoticeDialog extends Dialog {
    private Button _btnCreateAccount = new Button(getTranslation("notice.create"));
    private Paragraph pTitulo = new Paragraph(getTranslation("notice.title"));
    private Paragraph pDescripcion = new Paragraph(getTranslation("notice.description"));
    private TextField txtTitulo = new TextField();
    private TextArea txtDescripcion = new TextArea();

    public CreateNoticeDialog(NoticiaService noticiaService) {
        Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnCancelar.getElement().addEventListener("click", e -> close());
        getHeader().add(btnCancelar);
        setHeaderTitle(getTranslation("notice.create"));

        add(pTitulo, txtTitulo, pDescripcion, txtDescripcion, _btnCreateAccount);

        _btnCreateAccount.addClickListener(e -> {
            com.vaadin.flow.component.confirmdialog.ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader(getTranslation("confirm.title"));
            dialog.setText(getTranslation("confirm.body"));

            dialog.setCancelable(true);

            dialog.setCancelText(getTranslation("confirm.no"));

            dialog.setConfirmText(getTranslation("confirm.yes"));
            dialog.addConfirmListener(event ->  {
                if(txtTitulo.getValue() != null && txtDescripcion.getValue() != null && noticiaService.Save(new Noticia(txtTitulo.getValue(), txtDescripcion.getValue(), new Date())) != null) {
                Notification notification = Notification.show(getTranslation("notice.success"));
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                fireEvent(new UpdateNoticeEvent(this, false));
                close();
            }
            else {
                Notification notification = Notification.show(getTranslation("notice.error"));
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                close();
            }
                ;});
            dialog.open();

        });
    }

    public Registration addUpdateListener(ComponentEventListener<UpdateNoticeEvent> listener) {
        return addListener(UpdateNoticeEvent.class, listener);
    }
}
