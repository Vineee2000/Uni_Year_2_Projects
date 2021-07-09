import java.awt.*;

class ColourController {
    WordListDisplay listOutput;
    SystemMessage sysOutput;
    int red;
    int green;
    int blue;

    /**
     * @param listOutputRef
     * @param sysOutputRef
     */
    ColourController(WordListDisplay listOutputRef, SystemMessage sysOutputRef) {
        listOutput = listOutputRef;
        sysOutput = sysOutputRef;
        try {
            getColour();
        } catch (OutputColoursOutOfSych outputColoursOutOfSych) {
            setAllColours();
        }
    }

    //Obtains the current colour of the output
    void getColour() throws OutputColoursOutOfSych {
        red = listOutput.getForeground().getRed();
        green = listOutput.getForeground().getGreen();
        blue = listOutput.getForeground().getBlue();
        if (red != sysOutput.getForeground().getRed() | green != sysOutput.getForeground().getGreen() | blue != sysOutput.getForeground().getBlue()) {
            throw new OutputColoursOutOfSych();
        }
    }

    //Sets the colour on output and system feedback objects
    void setAllColours() {
        listOutput.setForeground(new Color(red, green, blue));
        sysOutput.setForeground(new Color(red, green, blue));
    }

    //Changes red part of RGB to and integer passed to it
    void setRed(int newRed) throws OutputColoursOutOfSych {
        red = newRed;
        setAllColours();
        getColour();
    }

    //Changes blue part of RGB to and integer passed to it
    void setBlue(int newBlue) throws OutputColoursOutOfSych {
        blue = newBlue;
        setAllColours();
        getColour();
    }

    //Changes green part of RGB to and integer passed to it
    void setGreen(int newGreen) throws OutputColoursOutOfSych {
        green = newGreen;
        setAllColours();
        getColour();
    }
}
