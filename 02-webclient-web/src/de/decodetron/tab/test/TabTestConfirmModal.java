// $Log: TabTestConfirmModal.java,v $
// Revision 1.6  2015/02/11 11:47:49  tw
// Haldenbearbeitung: Aktivieren der Autocomplete-Funktion. Verlagern der Skripte.
//
// Revision 1.5  2014/05/12 16:05:58  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.3  2014/02/18 02:23:51  tw
// Individualisierung: Modaler Dialog.
//
// Revision 1.2  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.1  2014/02/11 16:30:03  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
//

package de.decodetron.tab.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.Strings;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public class TabTestConfirmModal extends Panel {

    private StringBuilder values = new StringBuilder();
    
    public TabTestConfirmModal(String id, IModel<?> mm) {
        super(id, mm);

        // //////////////////////////////////////////////////////////////////
        // /// Simplere Js-Variante
        // /
        Form formWithJavaScript = new Form("formWithJavaScript");
        Button buttonWithJavaScript = new Button("buttonWithJavaScript") {

            @Override
            public void onSubmit() {
                System.out.println("Doing my job");
            }
        };
        buttonWithJavaScript.add(AttributeModifier.append("onclick",
            "if(!confirm('Do you really want to perform this action?')) return false;"));
        formWithJavaScript.add(buttonWithJavaScript);
        add(formWithJavaScript);

        // //////////////////////////////////////////////////////////////////
        // /// Aufwändigere Modal-Dialog-Variante
        // /
        AreYouSurePanel yesNoPanel = new AreYouSurePanel("yesNoPanel", "Bitte bestätigen", "Ajax Action!",
                "Wollen Sie Hans Hannebambel wirklich löschen?") {

            @Override
            protected void onConfirm(AjaxRequestTarget target) {
                System.out.println("Doing my job after ajax modal");
            }

            @Override
            protected void onCancel(AjaxRequestTarget target) {
                System.out.println("Cancel");
            }

        };

        add(yesNoPanel);
        
        
        // //////////////////////////////////////////////////////////////////
        // /// Autocomplete - Kram
        // /
        add(new SearchForm("srcItmBeamer"));
    }
    
    private class SearchForm extends Form {
        public SearchForm(String id) {
            super(id);
            add(new SearchAutoCompleteField("autoFieldText", SearchForm.this));
        }

        @Override
        protected void onSubmit() {
            //ValueMap map = (ValueMap) getDefaultModelObject();
            //startSearch(map.getString("autoFieldText"), AjaxRequestTarget.get());
        }
        
        
        private class SearchAutoCompleteField extends AutoCompleteTextField {

            public SearchAutoCompleteField(String id, Form form) {
                super(id);
                setType(String.class);
            }

            @Override
            protected Iterator<String> getChoices(String input) {

                if (Strings.isEmpty(input)) {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<String> choices = new ArrayList<String>(10);

                List<String> index = Arrays.asList("aaa","aaaa", "bbb", "ccc", "ddd");
                for (final String item : index) {

                    if (item.toUpperCase().startsWith(input.toUpperCase())) {
                        choices.add(item);
                        if (choices.size() == 10) {
                            break;
                        }
                    }
                }

                return choices.iterator();
            }
        }
    }
}
