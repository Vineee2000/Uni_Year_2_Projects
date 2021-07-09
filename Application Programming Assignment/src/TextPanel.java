import javax.swing.*;

class TextPanel extends JPanel {

    TextInput inputField;

    /**
     * @param colourControllerRef
     */
    TextPanel(ColourController colourControllerRef) {
        //Textfield for user input
        TextInput input = new TextInput(20);
        input.setFont(input.getFont().deriveFont((float) 32.0));
        input.setHorizontalAlignment(JTextField.CENTER);

        //The panel that sets the colour of the output text
        RGBPanel rgb = new RGBPanel(colourControllerRef);

        this.add(input);
        this.add(rgb);

        inputField = input;
    }
}
