// $Log: TabAdministration.java,v $
// Revision 1.14  2015/05/19 19:58:46  tw
// CR Feng-ID: 3947#11
//
// Revision 1.13  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.12  2015/02/11 14:08:12  tw
// Haldenbearbeitung: Auskommentieren der Ansicht fuer Vorab-Realease.
//
// Revision 1.11  2015/02/07 17:07:54  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle angebunden.
//
// Revision 1.10  2015/02/01 11:08:42  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.9  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.8  2015/01/25 14:34:34  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.7  2014/10/14 16:32:02  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.6  2014/10/03 15:06:33  tw
// Implementierung: Haldenstatus.
//
// Revision 1.5  2014/02/04 13:21:51  tw
// Refresh-Button Adminbereich. Treffermengenanzeige/Schnittstellenanpassung Benutzerverwaltung.
//
// Revision 1.4  2014/02/04 02:09:21  tw
// Implementierung Administrationsbereich.
//
// Revision 1.3  2014/02/03 16:41:28  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.2  2014/01/29 19:52:54  tw
// Umbau: Belegart-Auswahl.
//
// Revision 1.1  2014/01/29 09:52:44  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
//

package de.decodetron.tab.administration;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardCollectionModel;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Section;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.SectionComparator;
import de.decodetron.tab.administration.benutzerstatus.ViewContBenutzerStatus;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeiten;
import de.decodetron.tab.administration.haldenstatus.ViewContHaldenStatus;
import de.decodetron.tab.administration.lueckenprotokoll.Lueckenprotokoll;
import de.decodetron.tab.empty.ViewContEmpty;

/**
 * @author Thomas Winter
 * @since 23.01.2014
 */
public class TabAdministration extends Panel {

    private Panel listviewcontainer;
    public static String MENUEITEM = "Administration"; // Fix in der DB vergeben !!!

    public TabAdministration(String id, final IModel<AEPModel> model) {

        super(id, model);
        setOutputMarkupId(true);

        final Select<Section> listenTyp = new Select<Section>("listentyp");
        List<Section> secList = LoginSession.get().getSections4MenuItem(MENUEITEM);

        Section firstItemSection = new Section();
        firstItemSection.setSection(Const.KEY_LISTENTYP_DEFAULTEMPTY);
        secList.add(firstItemSection);

        List<Section> list = new ArrayList<Section>();
        list.addAll(secList);

        Collections.sort(list, new SectionComparator());
        WildcardCollectionModel<Section> wcm = new WildcardCollectionModel<Section>(list);
        listenTyp.add(new SelectOptions<Section>("listentypitem", wcm, renderer));

        listenTyp.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {

                Section selectedSection = listenTyp.getModelObject();
                // ValueMap map = (ValueMap) getDefaultModelObject();
                LoginSession.get().setCurrentSection(selectedSection);

                // ///////////////////////////////////////////////////
                // /// Add Panels here ...
                // /
                if (Const.KEY_ADMIN_BENSTAT.equals(selectedSection.getKz())) {
                    Panel p1 = new ViewContBenutzerStatus("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p1);
                } else if (Const.KEY_ADMIN_HALDENSTAT.equals(selectedSection.getKz())) {
                    Panel p2 = new ViewContHaldenStatus("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else if (//
                Const.KEY_ADMIN_LUECKENPROT.equals(selectedSection.getKz()) || //
                        Const.KEY_ADMIN_LUECKENPROT_SR.equals(selectedSection.getKz())//
                ) {
                    Panel p2 = new Lueckenprotokoll("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else if (Const.KEY_ADMIN_HALDEBEARBEITEN.equals(selectedSection.getKz())) {

                    Panel p2 = new HaldeBearbeiten("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else {
                    Panel p9 = new ViewContEmpty("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p9);
                }

                // /
                // ///
                // ///////////////////////////////////////////////////
                target.add(listviewcontainer);
                // target.appendJavaScript("$.fn.initFixedTableArcNew()");
                // target.appendJavaScript("$.fn.initFixedTableHeaderRec()");
                // target.prependJavaScript("$.fn.initFixedTableHeaderRec()");
                // target.appendJavaScript("$.fn.hideViewBusy();");
            }
        });

        add(listenTyp);

        // ///////////////////////////////////////////////////////////////
        // /// Voreinstellung. Bewirkt ein korrektes zurücksezten beim Klick auf Reiter
        // /
        ((ValueMap) model.getObject()).put(Const.KEY_NAVI_COMBOACTION, "Bitte wählen Sie eine Aktion aus.");
        listviewcontainer = new ViewContEmpty("listviewcontainer", model);
        add(listviewcontainer);
        listenTyp.setDefaultModelObject(Const.KEY_LISTENTYP_DEFAULTEMPTY);
    }

    IOptionRenderer<Section> renderer = new IOptionRenderer<Section>() {
        private static final long serialVersionUID = 1L;

        @Override
        public String getDisplayValue(Section object) {
            return object.getSection();
        }

        @Override
        public IModel<Section> getModel(Section value) {
            return new Model<Section>(value);
        }
    };

}
