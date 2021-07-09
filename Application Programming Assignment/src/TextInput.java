import javax.swing.*;

class TextInput extends JTextField {

    TextInput() {

    }

    /**
     * @param width
     */
    TextInput(int width) {
        this.setColumns(width);
    }
}
