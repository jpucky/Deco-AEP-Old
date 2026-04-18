// $Log: TxtFilter.java,v $
// Revision 1.9  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.8  2014/05/06 00:43:57  tw
// Backup: Benutzer anlegen.
//
// Revision 1.7  2014/04/28 21:57:52  tw
// Backup: Benutzer anlegen.
//
// Revision 1.6  2014/04/14 13:41:00  tw
// Fix. f. Recherche-Textfelder.
//
// Revision 1.5  2014/03/28 23:58:14  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.4  2014/03/28 17:23:44  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.3  2014/03/04 16:21:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/02/13 02:03:19  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
// Revision 1.1  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
//

package de.decodetron.tab.statistik;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Textfeldkomponente für alle Statistik-Filter.
 * 
 * @author Thomas Winter
 * @since 09.11.2013
 */
public class TxtFilter extends TextField {

    private String txt;
    private String tblName;

    public TxtFilter(String id, String tname) {
        this(id, Model.of(""));
        tblName = tname;
    }

    /**
     * Konstruktor f. Recherche. Alte Werte bleiben bestehen.
     * 
     * @param id
     */
    public TxtFilter(String id) {
        super(id); // !
        setType(String.class);
        add(new AjaxFormComponentUpdatingBehavior("onChange") {
            protected void onUpdate(AjaxRequestTarget target) {
                txt = getDefaultModelObjectAsString();
            }
        });
    }

    /**
     * Konstruktor f. Defektenlisten, Benutzer anlegen. Alte Werte bitte löschen!
     * 
     * @param String
     *            id
     * @param IModel
     *            model
     */
    public TxtFilter(String id, IModel model) {
        super(id, model, String.class); // !
        add(new AjaxFormComponentUpdatingBehavior("onChange") {
            protected void onUpdate(AjaxRequestTarget target) {
                txt = getDefaultModelObjectAsString();
            }
        });
    }

    /**
     * Ok der Konstruktor ist für den Popo. Ich hab keine Lust mehr das an 1000 Stellen zu ändern.
     * 
     * @param id
     * @param f
     */
    public TxtFilter(String id, Form f) {
        super(id, Model.of(""), String.class);// Model.of("") wichtig f. Defektenlisten !?
        add(new AjaxFormComponentUpdatingBehavior("onChange") {
            protected void onUpdate(AjaxRequestTarget target) {
                txt = getDefaultModelObjectAsString();
            }
        });
    }

    public String getTblIdent() {
        return tblName;
    }

    public String getText() {
        return isVisible() ? txt : "";
    }

    public void setText(String t) {
        setDefaultModel(Model.of(t));
        txt = t;
    }

}
