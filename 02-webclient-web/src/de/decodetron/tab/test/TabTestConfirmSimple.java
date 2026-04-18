// $Log: TabTestConfirmSimple.java,v $
// Revision 1.3  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.2  2014/02/14 17:03:24  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.1  2014/02/11 16:30:03  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
//

package de.decodetron.tab.test;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public class TabTestConfirmSimple extends Panel {

    public TabTestConfirmSimple(String id, IModel<?> model) {
        super(id, model);

        Form form = new Form("formWithJavaScript"){
            @Override
            public void onEvent(IEvent<?> event) {
                // TODO Auto-generated method stub
                super.onEvent(event);
            }
            @Override
            protected void onError() {
                System.out.println("onError-form");
            }
        };
        Button buttonWithJavaScript = new Button("buttonWithJavaScript") {
            
            @Override
            public void onError() {
                System.out.println("onError-button");
            }
            
            @Override
            public void onSubmit() {
                System.out.println("Doing my job");
            }
        };
        buttonWithJavaScript.setDefaultFormProcessing(false);
        buttonWithJavaScript.add(AttributeModifier.append("onclick",
            "if(!confirm('Do you really want to perform this action?')) return false;"));
        form.add(buttonWithJavaScript);
        add(form);

        Link link = new Link("confirmtest1") {
            @Override
            public void onClick() {
                System.out.println("Doing my job onClick");
            }
        };
        link.add(AttributeModifier.append("onclick",
            "if(!confirm('Do you really want to perform this action?')) return false;"));
        add(link);
    }
}
