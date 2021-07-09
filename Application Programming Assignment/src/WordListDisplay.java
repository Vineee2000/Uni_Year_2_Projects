import javax.swing.*;

class WordListDisplay extends JTextArea {

    WordArray stringList;

    /**
     * @param listRef
     */
    WordListDisplay(WordArray listRef) {
        stringList = listRef;

        //Styling the display of the word list
        this.setEditable(false);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFont(this.getFont().deriveFont((float) 40));

        this.reprint();
    }

    /**
     *
     */
    //Method that displays the list
    void reprint() {
        this.setText("");
        this.append(stringList.trimmedToString());
    }

    /**
     * @param output
     */
    //Method that displays the string passed to it
    void printGiven(String output) {
        this.setText("");
        this.append(output);
    }
}
