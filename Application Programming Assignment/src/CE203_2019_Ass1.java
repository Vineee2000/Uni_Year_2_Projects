import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CE203_2019_Ass1 {

    public static void main (String[] args) {
            //List in which the words will be stored
            WordArray wordList = new WordArray();

            //The GUI application JFrame
            AppWindow theWindow = new AppWindow(wordList);
            theWindow.setVisible(true);
    }
}

