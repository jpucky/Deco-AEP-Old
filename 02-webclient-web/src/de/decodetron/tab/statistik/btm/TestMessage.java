// $Log: TestMessage.java,v $
// Revision 1.1  2014/01/29 09:52:44  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
//

package de.decodetron.tab.statistik.btm;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;

/**
 * @author Thomas Winter
 * @since 17.01.2014
 */
public class TestMessage extends ModalWindow {

    public TestMessage(String id, IModel<?> model) {
        super(id, model);
        // setContent(new HelpPanel(getContentId()));
        setTitle("Systeminfo");
        setCookieName("helpWindow");
        setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                // setResult("Modal window 2 - close button");
                return true;
            }
        });
        setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            public void onClose(AjaxRequestTarget target) {
                // target.add(result);
            }
        });
    }

    public void showNoAjax() {
        add(new AbstractAjaxTimerBehavior(Duration.milliseconds(1)) {
            protected void onTimer(AjaxRequestTarget target) {
                show(target);
                // stop();
            }
        });
    }

    public void closeNoAjax() {
        add(new AbstractAjaxTimerBehavior(Duration.milliseconds(1)) {
            protected void onTimer(AjaxRequestTarget target) {
                close(target);
                // stop();
            }
        });
    }

}
