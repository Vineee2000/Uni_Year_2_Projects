import javax.swing.*;
import java.awt.*;

//The GUI window class
class AppWindow extends JFrame {

    int height = 768;
    int width = 1024;

    /**
     * @param listRef
     */
    public AppWindow(WordArray listRef) {
        setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //JPanel that displays the list of the words and system feedback
        WordPanel output = new WordPanel(listRef);
        add(output, BorderLayout.CENTER);

        //JPanel with the text field input
        TextPanel wordInput = new TextPanel(output.colourController);
        add(wordInput, BorderLayout.SOUTH);

        //JPanel with the buttons
        ButPanel butInput = new ButPanel(listRef, wordInput, output.childDisplay, output.childSysMessage);
        add(butInput, BorderLayout.NORTH);
    }
}
