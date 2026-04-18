// $Log: BenRechte.java,v $
// Revision 1.13  2015/02/27 17:23:48  tw
// Haldenbearbeitung: Datepicker Bugfix.
//
// Revision 1.12  2014/12/17 22:07:29  tw
// Recherche: aktuelles Jahr verwenden.
//
// Revision 1.11  2014/10/14 16:32:03  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.10  2014/10/11 16:28:04  tw
// Implementierung: Benutzerrechte Suchfelder.
//
// Revision 1.9  2014/10/10 12:05:21  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.8  2014/09/30 13:01:04  tw
// geaenderte Benutzer nach Suche loeschen.
//
// Revision 1.7  2014/08/26 14:37:22  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
// Revision 1.6  2014/08/24 22:18:54  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.5  2014/08/19 10:41:17  tw
// Aep-Benutzerr-Rechteverwaltung: Layout, Textfeld-tests.
//
// Revision 1.4  2014/08/09 00:16:20  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.3  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.2  2014/08/07 21:16:54  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.1  2014/08/07 00:15:43  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.event.ChangeEvent;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Die Rechte der Benutzer sollen durch direkte Vergabe der Sektionen verwaltet werden können. Das
 * ist die Sichtweise von AEP auf die Benutzer. Es muss unterschieden werden zwischen AEP-,
 * Apotheken-, Lieferanten- Mitarbeitern. Diese Art der Rechtevergabe ist wahrscheinlich nur für die
 * AEP-Mitarbeiter interessant.<br>
 * <br>
 * 
 * 
 * @author Thomas Winter
 * @since 07.08.2014
 */
public class BenRechte extends Panel {
    final BenListe benListe;
    public static String MODULNAME = "brv"; // Fix in der DB vergeben!

    public void renderHead(IHeaderResponse response) {
        // response.render(OnDomReadyHeaderItem.forScript("$.fn.initSearchButtonSelector()"));
    }

    public BenRechte(String id, IModel<AEPModel> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
        add(benListe = new BenListe("viewListTable", (IModel<AEPModel>) model));
    }

    public class ViewFilter extends Form {

        public void renderHead(IHeaderResponse response) {
            // response.render(OnDomReadyHeaderItem.forScript("$.fn.initSearchButtonSelector()"));
            // response.render(OnDomReadyHeaderItem.forScript("$.fn.deleteSearchTextfields()"));
        }

        public ViewFilter(String id, final IModel<AEPModel> model) {

            super(id, model);

            // //////////////////////////////////////////////////////////////////////////
            // /// Suchfeldliste ...
            // /
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
            List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);

            TxtFilter login;
            TxtFilter vorname;
            TxtFilter nachname;
            TxtFilter filter;
            TxtFilter gebiet;
            TxtFilter vertriebsleitung;

            add(login = new TxtFilter("login", this));
            add(vorname = new TxtFilter("vorname", this));
            add(nachname = new TxtFilter("nachname", this));
            add(filter = new TxtFilter("Filter", this));
            add(gebiet = new TxtFilter("Gebiet", this));
            add(vertriebsleitung = new TxtFilter("Vertriebsleitung", this));

            list.add(login);
            list.add(vorname);
            list.add(nachname);
            list.add(filter);
            list.add(gebiet);
            list.add(vertriebsleitung);

            // final BenListe benListe;
            // add(benListe = new BenListe("viewListTable", model));

            AjaxButton btn;
            add(btn = new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                    AEPModel model = (AEPModel) BenRechte.this.getDefaultModelObject();
                    if (model.getBenVerwaltungModel().getUserList2Change().size() > 0) {
                        target.appendJavaScript("$.fn.showUser2Change()");
                        // benListe.saveUser2Change(); // Zwangsspeicherung, berücksichtigt die
                        // Fehler nicht!
                    }

                    // ValueMap map = (ValueMap) ViewFilter.this.getDefaultModelObject();
                    // map.put(Const.KEY_BTN_FILTERSUCHE, hpf);
                    target.add(benListe);
                    // Tabellenaktualisierung NUR hier vornehmen !!!
                    //target.appendJavaScript("$.fn.initFixedTableHeaderRights()");

                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();

                    // ////////////////////////////////////////////////////////////////
                    // /// Es sei ...: Suche löscht alle geänderten Benutzer. Es gibt ja keine
                    // /// Vorgaben!
                    // /
                    // model.getBenVerwaltungModel().initUserList2Change();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

            add(new ChangedUserListWrapper("dummycontainer", model.getObject()));
        }

    }
}
