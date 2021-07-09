import javax.swing.*;

class SystemMessage extends JTextField {

    SystemMessage() {
        this.setEditable(false);
        this.setColumns(20);
        this.setFont(this.getFont().deriveFont((float) 20.0));
        this.setHorizontalAlignment(JTextField.CENTER);
    }
}
