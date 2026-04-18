package de.decodetron.tab.test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import de.decodetron.dlgcomponents.ConfirmationAnswer;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public class YesNoPanel extends Panel {

    public YesNoPanel(String id, String title, String message, final ModalWindow modalWindow,
            final ConfirmationAnswer answer) {
        super(id);

        Form yesNoForm = new Form("yesNoForm");

        MultiLineLabel messageLabel = new MultiLineLabel("message", message);
        yesNoForm.add(messageLabel);
        modalWindow.setTitle(title);

        AjaxButton yesButton = new AjaxButton("yesButton", yesNoForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    answer.setAnswer(true);
                    modalWindow.close(target);
                }
            }
        };

        AjaxButton noButton = new AjaxButton("noButton", yesNoForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    answer.setAnswer(false);
                    modalWindow.close(target);
                }
            }
        };

        yesNoForm.add(yesButton);
        yesNoForm.add(noButton);

        add(yesNoForm);
    }
}
