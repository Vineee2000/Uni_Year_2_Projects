import javax.swing.*;

class RGBPanel extends JPanel {

    /**
     * @param colourControllerRef
     */
    RGBPanel(ColourController colourControllerRef) {
        //The three spinners that set corresponding RGB colours
        RGBSpinner redSpinner = new RGBSpinner(RGBSpinner.RED);
        RGBSpinner greenSpinner = new RGBSpinner(RGBSpinner.GREEN);
        RGBSpinner blueSpinner = new RGBSpinner(RGBSpinner.BLUE);

        //The three event listeners that will listen for changeon their related spinners and set new colours
        ColourListener redListener = new ColourListener(redSpinner, colourControllerRef);
        ColourListener greenListener = new ColourListener(greenSpinner, colourControllerRef);
        ColourListener blueListener = new ColourListener(blueSpinner, colourControllerRef);

        //Adding listeners to spinners
        redSpinner.addChangeListener(redListener);
        greenSpinner.addChangeListener(greenListener);
        blueSpinner.addChangeListener(blueListener);

        //The spinners with labels being added to the frame
        this.add(new JLabel("Red"));
        this.add(redSpinner);

        this.add(new JLabel("Green"));
        this.add(greenSpinner);

        this.add(new JLabel("Blue"));
        this.add(blueSpinner);
    }


}
