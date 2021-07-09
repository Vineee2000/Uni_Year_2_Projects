import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class ArrayChangeListener implements ActionListener {
    String action;
    ListController listInterface;
    List<ChangeCompleteListener> listeners = new ArrayList<ChangeCompleteListener>();
    //Static variables to simplify instantiation
    static String ADD = "Add";
    static String DELETE = "Delete";
    static String FILTER = "Filter";
    static String CLEAR = "Clear";

    /**
     * @param actionRef
     * @param listRef
     * @param textRef
     */
    //ListController is an interface class that holds methods for interacting with the array of words
    ArrayChangeListener(String actionRef, WordArray listRef, TextPanel textRef, SystemMessage feedbackRef) {
        action = actionRef;
        listInterface = new ListController(listRef, textRef.inputField, feedbackRef);
    }

    /**
     * @param listenerRef
     */
    //Adds a listener that awaits the end of operation to update the display
    void addCompleteListener(ChangeCompleteListener listenerRef) {
        listeners.add(listenerRef);
    }

    @Override
    //Method that fires when the button is pressed and performs the manipulation with the list
    public void actionPerformed(ActionEvent actionEvent) {
        //Actions to perform based on what parameter the button has been passed
        if (action == "Add") {
            listInterface.addWord();
        }

        if (action == "Delete") {
            listInterface.deleteWord();
        }

        if (action == "Clear") {
            listInterface.clearList();
        }

        //Outputting the results of operation.
        //Filter button is the only button that needs to output something other than the list.
        if (action == "Filter") {
            String output = listInterface.displayByLastChar();
            for (ChangeCompleteListener listener : listeners) {
                listener.operationComplete(output);
            }
        }

        if (action != "Filter") {
            for (ChangeCompleteListener listener : listeners) {
                listener.operationComplete();
            }
        }
    }
}
