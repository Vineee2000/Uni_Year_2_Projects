import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class ColourListener implements ChangeListener {
    RGBSpinner parentSpinner;
    ColourController colourController;

    /**
     * @param parentSpinnerRef
     * @param colourControllerRef
     */
    ColourListener(RGBSpinner parentSpinnerRef, ColourController colourControllerRef) {
        parentSpinner = parentSpinnerRef;
        colourController = colourControllerRef;
    }

    /**
     * @param changeEvent
     */
    @Override
    //Fires when colour spinners are changed and sets the new colour
    public void stateChanged(ChangeEvent changeEvent) {
        try {
            if ((Integer) parentSpinner.getValue() > 255) {
                parentSpinner.setValue(255);
            }
            if ((Integer) parentSpinner.getValue() < 0) {
                parentSpinner.setValue(0);
            }
            if (parentSpinner.colour.equals(RGBSpinner.RED)) {
                colourController.setRed((Integer) parentSpinner.getValue());
            }
            if (parentSpinner.colour.equals(RGBSpinner.GREEN)) {
                colourController.setGreen((Integer) parentSpinner.getValue());
            }
            if (parentSpinner.colour.equals(RGBSpinner.BLUE)) {
                colourController.setBlue((Integer) parentSpinner.getValue());
            }
        } catch (OutputColoursOutOfSych e) {
            colourController.setAllColours();
        }
    }
}
