// $Log: ConfirmationAnswer.java,v $
// Revision 1.1  2014/05/02 09:52:28  tw
// Backup: Benutzer anlegen.
//
//

package de.decodetron.dlgcomponents;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 30.04.2014
 */
public class ConfirmationAnswer implements Serializable{
    
    private boolean answer;

    public ConfirmationAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
