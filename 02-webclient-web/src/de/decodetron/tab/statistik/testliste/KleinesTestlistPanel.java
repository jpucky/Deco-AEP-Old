// $Log: KleinesTestlistPanel.java,v $
// Revision 1.3  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.2  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.1  2013/11/07 21:58:52  tw
// aufraumen 1. welle.
//
//

package de.decodetron.tab.statistik.testliste;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.dao.DAOI;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * Experiementierecke.
 * 
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class KleinesTestlistPanel extends Panel {

    public KleinesTestlistPanel(String id, IModel model, Select listenTyp) {
        super(id);
        add(new FormListenSuche("formListSuche", model, listenTyp));
    }

    public class FormListenSuche extends Form {

        private TxtFilter txtDate;
        private TxtFilter txtPosT;
        private TxtFilter txtLfsr;
        private TxtFilter txtLfsN;
        private TxtFilter txtpezn;
        private TxtFilter txtabez;
        private TxtFilter txtbckz;
        private TxtFilter txtbest;
        private TxtFilter txtbufd;

        private Panel listView;

        private final Select<String> listenTypp;

        public FormListenSuche(String id, final IModel<IModel<ValueMap>> m, final Select listenTyp) {
            super(id, m);
            listenTypp = listenTyp;

            // /////////////////////////////////////////////////////////////////////////////////////////
            // /// ClubListe
            // /
            add(txtDate = new TxtFilter("txtd", "txtdd", Arrays.asList(Const.KEY_LISTENTYP_KLEIN,
                Const.KEY_LISTENTYP_GROSS)));
            add(txtPosT = new TxtFilter("txtt", "txttt", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtLfsr = new TxtFilter("txtr", "txtrr", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtLfsN = new TxtFilter("txta", "txtaa", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtpezn = new TxtFilter("txtp", "txtpp", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtabez = new TxtFilter("txtz", "txtzz", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtbckz = new TxtFilter("txtc", "txtcc", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));
            add(txtbest = new TxtFilter("txtb", "txtbb", Arrays.asList(Const.KEY_LISTENTYP_KLEIN)));

            // /////////////////////////////////////////////////////////////////////////////////////////
            // /// Listentyp2, Testliste
            // /
            add(txtbufd = new TxtFilter("txth", "txt-habbel", Arrays.asList(Const.KEY_LISTENTYP_GROSS)));

            AjaxButton btn;
            add(btn = new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                    System.out.println("ListenTyp  : " + listenTyp.getModelObject());
                    System.out.println("-------------");
                    System.out.println("Datum-     : " + txtDate.getText());
                    System.out.println("PosTyp-    : " + txtPosT.getText());
                    System.out.println("LfNummer   : " + txtLfsr.getText());
                    System.out.println("LfName     : " + txtLfsN.getText());
                    System.out.println("PZN-       : " + txtpezn.getText());
                    System.out.println("Artikelbez : " + txtabez.getText());
                    System.out.println("ClubKz-    : " + txtbckz.getText());
                    System.out.println("Bestand    : " + txtbest.getText());
                    System.out.println("Habbel-    : " + txtbufd.getText());

                    ValueMap map = (ValueMap) FormListenSuche.this.getDefaultModelObject();
                    if (Const.KEY_LISTENTYP_KLEIN.equals(listenTyp.getModelObject())) {

//                        String clubdbLocation = AEPApplication.get().getModel().getFileClubTestDB();
//                        List<DataRecord> list2check = (List<DataRecord>) map.get(Const.KEY_LISTEN_DATA);
//
//                        // Neue DB
//                        DAOI clubDBDao = DAOFactoryJDBC.getInstance(clubdbLocation).getDAO();
//                        List<DataRecord> cbl = clubDBDao.getAllRecordsFilterBy("ClubBestand", txtDate.getText(),
//                            txtPosT.getText(), txtLfsr.getText(), txtLfsN.getText(), txtpezn.getText(),
//                            txtabez.getText(), txtbckz.getText(), txtbest.getText());
//                        list2check.clear();
//                        list2check.addAll(cbl);
                    } else if (Const.KEY_LISTENTYP_GROSS.equals(listenTyp.getModelObject())) {

                    }

                    target.add(listView);
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

            add(listView = new ClubBestandPanel("listview", m));
        }

        private class TxtFilter extends WebMarkupContainer {

            private String txt;
            private List<String> allowedTypes;

            public TxtFilter(String id, String idtxtcomp, List<String> at) {
                super(id);
                allowedTypes = at;
                final TextField<String> txtComp = new TextField<String>(idtxtcomp);
                // txtComp.setEnabled(false);
                txtComp.setType(String.class);
                txtComp.add(new AjaxFormComponentUpdatingBehavior("onChange") {
                    protected void onUpdate(AjaxRequestTarget target) {
                        txt = txtComp.getDefaultModelObjectAsString();
                        System.out.println("\n" + txtComp.getId() + "  : " + txtComp.getDefaultModelObjectAsString());
                    }
                });
                add(txtComp);
                setDefaultModel(txtComp.getDefaultModel());

                // ///////////////////////////////////////////////////
                // /// Neue Seite angefordert, loeschen der Eingabefelder
                // /
                ValueMap map = (ValueMap) FormListenSuche.this.getDefaultModelObject();
                map.put(idtxtcomp, "");
            }

            public String getText() {
                return isVisible() ? txt : "";
            }

            public boolean isVisible() {
                if (allowedTypes.contains(listenTypp.getModelObject())) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        IOptionRenderer<String> renderer = new IOptionRenderer<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getDisplayValue(String object) {
                return object;
            }

            @Override
            public IModel<String> getModel(String value) {
                return new Model<String>(value);
            }
        };
    }

}
