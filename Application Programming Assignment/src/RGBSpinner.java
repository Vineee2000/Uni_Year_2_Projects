import javax.swing.*;

class RGBSpinner extends JSpinner {
    int value;
    String colour;
    static String RED = "Red";
    static String GREEN = "Green";
    static String BLUE = "Blue";

    RGBSpinner(String colourRef) {
        SpinnerModel model = new SpinnerNumberModel(0, 0, 255, 1);
        this.setModel(model);
        colour = colourRef;
    }
}
