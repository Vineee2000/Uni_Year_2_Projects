import java.util.Iterator;

class ListController {
    WordArray theList;
    TextInput input;
    SystemMessage feedback;

    /**
     * @param listRef
     * @param inputRef
     */
    ListController(WordArray listRef, TextInput inputRef, SystemMessage feedbackRef) {
        theList = listRef;
        input = inputRef;
        feedback = feedbackRef;
    }

    /**
     * @return
     */
    //Checks if input contains any symbols that are not allowed to be present in a word.
    //Returns false if such symbols are present, true otherwise
    boolean inputIsValid() {
        String inputWord = input.getText();
        boolean isWord = true;
        for (int i = 0; i < inputWord.length(); i++) {
            Character currentChar = inputWord.charAt(i);
            if ((Character.isLetterOrDigit(currentChar) == false) & (currentChar.equals('-') == false)) {
                isWord = false;
            }
        }
        if (isWord == true)
            return true;
        return false;
    }

    /**
     * @return
     */
    //Checks if the input is a valid word
    //Returns true if it is, false otherwise
    boolean isWord() {
        Character firstChar = input.getText().charAt(0);
        if ((inputIsValid() == true) & (Character.isLetter(firstChar) == true)) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    //Checks if the input is a valid character and that it is only a single character
    //Returns true if that is the case, otherwise prints an appropriate prompt in the feedback
    boolean charCheck() {
        if (inputIsValid() == false | input.getText().length() > 1) {
            feedback.setText(input.getText() + " is not a valid character");
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    //If input is an invalid word, returns false and prints an appropriate prompt in the feedback
    //Otherwise returns true
    boolean wordCheck() {
        if (isWord() == false) {
            feedback.setText(input.getText() + " is not a valid word");
            return false;
        }
        return true;
    }

    //Clears user input
    //In a method since it is done a lot
    void clearInput() {
        input.setText("");
    }

    //Adds a word to the list
    //If user input is invalid, doesn't alter the list and prompts the user
    void addWord() {
        if (input.getText().equals("") == false) {
            if (wordCheck() == true) {
                theList.add(input.getText());
                feedback.setText("The word " + '"' + input.getText() + '"' + " has been added to the list");
            }
            clearInput();
        } else {
            feedback.setText("No input provided");
        }
    }

    /**
     * @return
     */
    //Displays only words that end in a character user entered
    //If user input is invalid, does nothing and prompts the user
    String displayByLastChar() {
        if (input.getText().equals("") == false) {
            if (charCheck() == true) {
                String output = "";
                String inputChar = input.getText();
                inputChar = inputChar.toLowerCase();
                Character checkChar = inputChar.charAt(0);

                for (int i = 0; i < theList.size(); i++) {
                    String currentWord = (String) theList.get(i);
                    Integer lastIndex = currentWord.length() - 1;
                    if (checkChar == currentWord.toLowerCase().charAt(lastIndex)) {
                        output = output + currentWord + ", ";
                    }
                }

                feedback.setText("Displaying words ending in " + '"' + inputChar + '"');
                clearInput();
                output = output.substring(0, output.length() - 2);

                return output;
            }
        } else {
            feedback.setText("No input provided");
        }
        clearInput();
        return input.getText();
    }

    /**
     *
     */
    //Deletes all given matches for a word from the list
    //If user input is invalid, doesn't alter the list and prompts the user
    void deleteWord() {
        if (input.getText().equals("") == false) {
            if (wordCheck() == true) {
                String toDelete = input.getText();
                Iterator<String> iterator = theList.iterator();
                while (iterator.hasNext()) {
                    String temp = (String) iterator.next();
                    if (temp.toLowerCase().equals(toDelete.toLowerCase())) {
                        iterator.remove();
                    }
                }
                feedback.setText("All occurrences of word " + '"' + toDelete + '"' + " have been deleted from the list");
            }
            clearInput();
        } else {
            feedback.setText("No input provided");
        }
    }

    //Clears the word list
    void clearList() {
        theList.clear();
        feedback.setText("The list has been cleared");
    }
}
