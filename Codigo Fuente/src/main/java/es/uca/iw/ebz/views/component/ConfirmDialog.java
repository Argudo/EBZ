package es.uca.iw.ebz.views.component;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ConfirmDialog extends Dialog {
    Paragraph _p = new Paragraph(getTranslation("confirm.body"));
    private HorizontalLayout _hlButtons = new HorizontalLayout();
    Button _btnConfirm = new Button(getTranslation("confirm.yes"));
    Button _btnCancel = new Button(getTranslation("confirm.no"));
    public ConfirmDialog(){
        setHeaderTitle(getTranslation("confirm.title"));
        _hlButtons.add(_btnCancel, _btnConfirm);
        add(_p, _hlButtons);
    }
}
