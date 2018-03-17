public class Productions {
    boolean isLabel;
    String [] grammar;
    String labelName;

    public Productions(String[] grammar) {
        this.isLabel = false;
        this.grammar = grammar;
    }

    public Productions(String labelName) {
        this.isLabel = true;
        this.labelName = labelName;
        this.grammar = null;
    }

    public boolean isLabel() {
        return isLabel;
    }

    public void setLabel(boolean label) {
        isLabel = label;
    }

    public String[] getGrammar() {
        return grammar;
    }

    public void setGrammar(String[] grammar) {
        this.grammar = grammar;
    }
}
