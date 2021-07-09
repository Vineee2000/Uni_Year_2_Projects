import javax.swing.*;
import java.awt.*;

class WordPanel extends JPanel {
    WordListDisplay childDisplay;
    SystemMessage childSysMessage;
    ColourController colourController;

    /**
     * @param listRef
     */
    WordPanel(WordArray listRef) {
        setLayout(new BorderLayout());

        //Child elements of the central JPanel -
        //display of the word list, system feedback and an object that controlls the text colour.
        WordListDisplay wordDisplay = new WordListDisplay(listRef);
        SystemMessage feedback = new SystemMessage();
        ColourController colourControllerRef = new ColourController(wordDisplay, feedback);

        childDisplay = wordDisplay;
        childSysMessage = feedback;
        colourController = colourControllerRef;


        add(wordDisplay, BorderLayout.NORTH);
        add(feedback, BorderLayout.SOUTH);
    }
}
