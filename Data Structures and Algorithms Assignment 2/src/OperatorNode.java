import java.util.ArrayList;

public class OperatorNode extends ExpTree {
    private int priority;

    OperatorNode(String inputOperator, ExpTree leftChild, ExpTree rightChild) {
        super(inputOperator, leftChild, rightChild);
        switch (inputOperator) {
            case "+":
            case "-":
                priority = 3;
                break;
            case "*":
            case "/":
            case "%":
                priority = 2;
                break;
            case "^":
                 priority = 1;
        }
    }

    /**
     * Provides parentheses placement for the toString() method
     * @return
     */
    @Override
    protected ArrayList<String> getInOrder() {
        ArrayList<String> returnArray = new ArrayList<>();
        ExpTree left = this.getLeft();
        if (left != null) {
            if (leftParenthesesCheck()) {
                returnArray.add("(");
            }
            returnArray.addAll(left.getInOrder());
            if (leftParenthesesCheck()) {
                returnArray.add(")");
            }
        }
        returnArray.add(this.getData());
        ExpTree right = this.getRight();
        if (right != null) {
            if (rightParenthesesCheck()) {
                returnArray.add("(");
            }
            returnArray.addAll(right.getInOrder());
            if (rightParenthesesCheck()) {
                returnArray.add(")");
            }
        }
        return returnArray;
    }

    /**
     * A separate method to check wether parentheses are necessary to improve readability
     * @return
     */
    private boolean leftParenthesesCheck() {
        ExpTree left = this.getLeft();
        if (left != null) {
            if (!left.getIfLeaf()) {
                OperatorNode leftOperator = (OperatorNode) left;
                if (leftOperator.getPriority() > this.priority) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean rightParenthesesCheck() {
        ExpTree right = this.getRight();
        if (right != null) {
            if (!right.getIfLeaf()) {
                OperatorNode rightOperator = (OperatorNode) right;
                if (rightOperator.getPriority() >= this.priority) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getPriority() {
        return priority;
    }
}
