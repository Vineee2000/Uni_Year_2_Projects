class ReprintListener implements ChangeCompleteListener {
    WordListDisplay outputText;

    /**
     * @param outputTextRef
     */
    ReprintListener(WordListDisplay outputTextRef) {
        outputText = outputTextRef;
    }

    @Override
    /**
     *
     */
    //Updates the output with the current state of the list
    public void operationComplete() {
        outputText.reprint();
    }

    @Override
    /**
     *
     * @param diffOutput
     */
    //Updates the output with a string that's passed to it
    public void operationComplete(String diffOutput) {
        if (diffOutput.equals("") == false) {
            outputText.printGiven(diffOutput);
        }
    }
}
