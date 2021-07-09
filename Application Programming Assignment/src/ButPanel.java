import javax.swing.*;

class ButPanel extends JPanel {

    /**
     * @param listRef
     * @param textRef
     * @param wordDisplayRef
     */
    ButPanel(WordArray listRef, TextPanel textRef, WordListDisplay wordDisplayRef, SystemMessage feedbackRef) {
        //The buttons for user to interact with the program
        JButton addBut = new JButton("Add word");
        JButton filterBut = new JButton("Filter by last letter");
        JButton deleteBut = new JButton("Delete word");
        JButton clearBut = new JButton("Clear list");

        //Action listeners that wait fo button presses and perform actions with the list
        ArrayChangeListener addWord = new ArrayChangeListener(ArrayChangeListener.ADD, listRef, textRef, feedbackRef);
        ArrayChangeListener filter = new ArrayChangeListener(ArrayChangeListener.FILTER, listRef, textRef, feedbackRef);
        ArrayChangeListener delete = new ArrayChangeListener(ArrayChangeListener.DELETE, listRef, textRef, feedbackRef);
        ArrayChangeListener clear = new ArrayChangeListener(ArrayChangeListener.CLEAR, listRef, textRef, feedbackRef);

        //Event listeners that will register when is changing the word list complete and update the text output
        addWord.addCompleteListener(new ReprintListener(wordDisplayRef));
        filter.addCompleteListener(new ReprintListener(wordDisplayRef));
        delete.addCompleteListener(new ReprintListener(wordDisplayRef));
        clear.addCompleteListener(new ReprintListener(wordDisplayRef));

        //Add appropriate action listeners to buttons
        addBut.addActionListener(addWord);
        filterBut.addActionListener(filter);
        deleteBut.addActionListener(delete);
        clearBut.addActionListener(clear);

        //Add buttons to the panel
        this.add(addBut);
        this.add(filterBut);
        this.add(deleteBut);
        this.add(clearBut);
    }
}
