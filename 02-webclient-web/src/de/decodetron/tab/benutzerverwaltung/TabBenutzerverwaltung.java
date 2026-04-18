// $Log: TabBenutzerverwaltung.java,v $
// Revision 1.20  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.19  2015/02/01 11:08:43  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.18  2015/01/25 14:34:35  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.17  2014/10/14 16:32:03  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.16  2014/10/11 16:28:04  tw
// Implementierung: Benutzerrechte Suchfelder.
//
// Revision 1.15  2014/08/19 10:41:16  tw
// Aep-Benutzerr-Rechteverwaltung: Layout, Textfeld-tests.
//
// Revision 1.14  2014/08/09 00:16:19  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.13  2014/08/07 00:15:10  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.12  2014/07/17 15:03:25  tw
// Bugfix f. Filter anlegen/aendern.
//
// Revision 1.11  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.10  2014/05/12 16:32:57  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.9  2014/05/11 10:59:56  tw
// Backup: Benutzer loeschen.
//
// Revision 1.8  2014/05/02 09:51:50  tw
// Backup: Benutzer anlegen.
//
// Revision 1.7  2014/04/28 12:38:56  tw
// Backup: Benutzer anlegen.
//
// Revision 1.6  2014/02/13 02:03:18  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
// Revision 1.5  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.4  2014/02/04 13:21:51  tw
// Refresh-Button Adminbereich. Treffermengenanzeige/Schnittstellenanpassung Benutzerverwaltung.
//
// Revision 1.3  2014/02/03 22:45:36  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.2  2014/02/03 16:41:28  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.1  2014/01/31 01:42:41  tw
// .
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardCollectionModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Section;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.SectionComparator;
import de.decodetron.tab.benutzerverwaltung.pwdreset.ViewContResetPwd;
import de.decodetron.tab.benutzerverwaltung.rechtevergabe.BenRechte;
import de.decodetron.tab.empty.ViewContEmpty;

/**
 * @author Thomas Winter
 * @since 31.01.2014
 */
public class TabBenutzerverwaltung extends Panel {

    private Panel listviewcontainer;
    public static String MENUEITEM = "Benutzerverwaltung"; // Fix in der DB vergeben !!!

    public TabBenutzerverwaltung(String id, final IModel<AEPModel> model) {
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
                ValueMap map = (ValueMap) getDefaultModelObject();
                LoginSession.get().setCurrentSection(selectedSection);

                map.put(Const.KEY_BTN_FILTERSUCHE, null);
                map.put(Const.KEYDOCTOTAL, null); // alle Dokumente
                map.put(Const.KEYHITSPERPAGE, null); // HITSPERPAGE, alle Treffer
                map.put(Const.KEYSHOWPERPAGE, null); // SHOWPERPAGE

                // ///////////////////////////////////////////////////
                // /// Add Panels here ...
                // /

                if (Const.KEY_BENW_RESETPWD_PANEL.equals(selectedSection.getKz())) {
                    Panel p1 = new ViewContResetPwd("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p1);
                } else if (ViewBenutzerBearbeiten.MODULNAME.equals(selectedSection.getKz())) {
                    Panel p2 = new ViewBenutzerBearbeiten("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else if (BenRechte.MODULNAME.equals(selectedSection.getKz())) {
                    IModel<AEPModel> m = Model.of(model);
                    Panel p3 = new BenRechte("listviewcontainer", m);
                    listviewcontainer.replaceWith(listviewcontainer = p3);
                } else {
                    Panel p9 = new ViewContEmpty("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p9);
                }

                // /
                // ///
                // ///////////////////////////////////////////////////

                // AEPModel aepm = (AEPModel) model.getObject();
                // if(aepm.getBenVerwaltungModel().getUserList2Change().size() > 0){
                // target.appendJavaScript("$.fn.showUser2Change()");
                // benListe.saveUser2Change(); // Zwangsspeicherung, berücksichtigt die Fehler
                // nicht!
                // }

                // target.add(listviewcontainer);
                target.add(listviewcontainer);
            }
        });

        add(listenTyp);

        // ///////////////////////////////////////////////////////////////
        // /// Voreinstellung. Bewirkt ein korrektes zurücksezten beim Klick auf Reiter
        // /

        ((ValueMap) model.getObject()).put(Const.KEY_NAVI_COMBOACTION, "Bitte wählen Sie eine Aktion aus.");
        listenTyp.setDefaultModelObject(Const.KEY_LISTENTYP_DEFAULTEMPTY);
        add(listviewcontainer = new ViewContEmpty("listviewcontainer", model));

        // add(new AjaxLink<String>("testlink"){
        //
        // @Override
        // public void onClick(AjaxRequestTarget target) {
        // //target.appendJavaScript("$.fn.showViewBusy();");
        // target.add(listviewcontainer);
        // }
        //
        // });
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
