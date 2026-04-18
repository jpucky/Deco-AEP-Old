// $Log: AreYouSurePanel.java,v $
// Revision 1.4  2014/05/02 09:51:50  tw
// Backup: Benutzer anlegen.
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

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;

import de.decodetron.dlgcomponents.ConfirmationAnswer;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public abstract class AreYouSurePanel extends Panel {

    protected ModalWindow confirmModal;
    protected ConfirmationAnswer answer;

    public AreYouSurePanel(String id, String title, String buttonName, String modalMessageText) {
        super(id);
        answer = new ConfirmationAnswer(false);
        addElements(id, title, buttonName, modalMessageText);
    }

    protected void addElements(String id, String title, String buttonName, String modalMessageText) {

        confirmModal = createConfirmModal(id, title, modalMessageText);
        Form form = new Form("confirmForm");
        add(form);

        AjaxButton confirmButton = new AjaxButton("confirmButton", new Model(buttonName)) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                confirmModal.show(target);
            }
        };
        form.add(confirmButton);
        form.add(confirmModal);

//        PasswordTextField password = new PasswordTextField("passwort2", Model.of(""));
//        form.add(password);
    }

    protected abstract void onConfirm(AjaxRequestTarget target);

    protected abstract void onCancel(AjaxRequestTarget target);

    protected ModalWindow createConfirmModal(String id, String title, String modalMessageText) {

        //final ResourceReference JAVASCRIPT = new JavaScriptResourceReference(ModalWindow.class, "../../../../../../../../js/w-modal.js");
        final ResourceReference CSS = new CssResourceReference(ModalWindow.class, "../../../../../../../../css/w_modal.css");

        ModalWindow modalWindow = new ModalWindow("modal") {
            public void renderHead(final IHeaderResponse response) {
                super.renderHead(response);

//                CoreLibrariesContributor.contributeAjax(getApplication(), response);
//                response.render(JavaScriptHeaderItem.forReference(JAVASCRIPT));

                if (CSS != null) {
                    response.render(CssHeaderItem.forReference(CSS));
                }
            }
        };

        modalWindow.setCssClassName("custom");
        modalWindow.setHeightUnit("px");
        modalWindow.setInitialHeight(150);
        modalWindow.setMinimalHeight(150);
        
        modalWindow.setWidthUnit("px");
        modalWindow.setInitialWidth(300);
        modalWindow.setMinimalWidth(300);

        //modalWindow.setCookieName(id);
        YesNoPanel yesNoPanel = new YesNoPanel(modalWindow.getContentId(), title, modalMessageText, modalWindow, answer);
        modalWindow.setContent(yesNoPanel);
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

            @Override
            public void onClose(AjaxRequestTarget target) {
                if (answer.isAnswer()) {
                    onConfirm(target);
                } else {
                    onCancel(target);
                }
            }
        });

        return modalWindow;
    }

}
