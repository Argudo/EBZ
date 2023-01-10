package es.uca.iw.ebz.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.noticia.Noticia;
import es.uca.iw.ebz.noticia.NoticiaService;
import es.uca.iw.ebz.views.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Noticias")
@Route(value = "noticias", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class NoticiasView extends VerticalLayout {

    @Autowired
    private NoticiaService _noticiaService;

    private List<Noticia> _noticias;

    public NoticiasView(NoticiaService noticiaService){

        //Services initialization section
        _noticiaService = noticiaService;
        _noticias = _noticiaService.findAll();
        //End services initialization section

        setMargin(false);
        setPadding(false);
        setSpacing(true);
        setWidthFull();
        setAlignItems(Alignment.CENTER);
            //Title layout section
            VerticalLayout vlTitle = new VerticalLayout();

            vlTitle.setWidth("80vw");
            vlTitle.setPadding(true);
            vlTitle.setMargin(true);
            vlTitle.setClassName("box");

            H1 hTitle = new H1(getTranslation("notice.main"));
            hTitle.setClassName("title");

            vlTitle.add(hTitle);
            add(vlTitle);
            //End title layout section

            //Notices layout section
            if(_noticias.size() >= 1) {

                for (Noticia n : _noticias) {
                    add(CreateNoticeLayout(n));
                }

            }else{

                H2 hNotices = new H2(getTranslation("notice.no"));
                hNotices.setClassName("title");
                add(hNotices);

            }

            //End notices layout section

    }

    private Component CreateNoticeLayout(Noticia n) {

        VerticalLayout vlMain = new VerticalLayout();
        vlMain.setSpacing(false);
        vlMain.setPadding(true);
        vlMain.setWidth("80vw");
        vlMain.setClassName("box");

        H2 hTitle = new H2(n.getTitulo());

        Paragraph pDescription = new Paragraph(n.getDescripcion());

        vlMain.add(
                hTitle,
                new Hr(),
                pDescription);

        return vlMain;

    }

}
