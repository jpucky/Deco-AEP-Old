// $Log: PreparedStatementO.java,v $
// Revision 1.2  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.1  2014/04/15 09:10:12  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
//

package de.decodetron.bo;

/**
 * @author Thomas Winter
 * @since 14.04.2014
 */
public class PreparedStatementO {

    private String sql;
    private String[] values;
    
    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }
    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
    /**
     * @return the values
     */
    public String[] getValues() {
        return values;
    }
    /**
     * @param values the values to set
     */
    public void setValues(String[] values) {
        this.values = values;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(this.values != null){
            for (int i = 0; i < this.values.length; i++) {
                sb.append(this.values[i] + ", ");
            }   
        }
        return sb.toString();
    }
}
