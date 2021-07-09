import java.util.ArrayList;
import java.util.HashMap;

public class ExpTree {
    private String data;
    private int dataType;
    private ExpTree left, right;
    private Boolean isLeaf;

    static private int NUMBER = 1;
    static private int IDENTIFIER = 2;
    static private int OPERATOR = 3;
    static public int LET = 4;
    static public int AND = 5;
    static public int EQUALS = 6;

    /**
     * Creates a number leaf
     * @param inputInt
     */
    ExpTree(int inputInt) {
        data = String.valueOf(inputInt);
        dataType = NUMBER;
        left = null;
        right = null;
        isLeaf = true;
    }

    /**
     * Creates an identifier leaf
     * @param inputString
     */
    ExpTree(String inputString) {
        data = inputString;
        dataType = IDENTIFIER;
        left = null;
        right = null;
        isLeaf = true;
    }

    /**
     * Creates an operator node
     * @param inputOperator
     * @param leftChild
     * @param rightChild
     */
    ExpTree(String inputOperator, ExpTree leftChild, ExpTree rightChild) {
        data = inputOperator;
        dataType = OPERATOR;
        left = leftChild;
        right = rightChild;
        isLeaf = false;
    }

    /**
     * Creates a let, and or equals node.
     * This constructor is here mostly to be used by appropriate subclasses.
     * @param type
     * @param leftChild
     * @param rightChild
     */
    ExpTree(int type, ExpTree leftChild, ExpTree rightChild) {
        dataType = type;
        switch (type) {
            case 4:
                data = "let ";
                break;
            case 5:
                data = " and ";
                break;
            case 6:
                data = " = ";
        }
        isLeaf = false;
        left = leftChild;
        right = rightChild;
    }

    /**
     * Sets a new right child of the node, as long as it is not a leaf
     * @param child
     */
    public void setRightChild(ExpTree child) {
        if (!this.isLeaf) {
            right = child;
        } else throw new IllegalArgumentException("You cannot add children to non-operator nodes");
    }

    /**
     * Sets a new left child of the node, as long as it is not a leaf
     * @param child
     */
    public void setLeftChild(ExpTree child) {
        if (!this.isLeaf) {
            left = child;
        } else throw new IllegalArgumentException("You cannot add children to non-operator nodes");
    }

    /**
     * Calculates and returns the value of the tree
     * @return
     */
    public int evaluate() {
        int answer = 0;
        if (this.dataType == IDENTIFIER) {
            String alphabaet = "abcdefghijklmnopqrstuvwxyz";
            answer = 25 - alphabaet.indexOf(this.data.toLowerCase().charAt(0));
        }
        if (this.dataType == NUMBER) {
            answer = Integer.valueOf(data);
        }
        if (this.dataType == OPERATOR) {
            int leftValue = 0;
            if (left != null) {
                leftValue = left.evaluate();
            }
            int rightValue = 0;
            if (right != null) {
                rightValue = answer + right.evaluate();
            }
            answer = operatorArithmetic(leftValue, rightValue, this.data);
        }
        return answer;
    }

    /**
     * Performs operator calculations in a separate method to make evaluate() more readable
     * @param leftInt
     * @param rightInt
     * @param operator
     * @return
     */
    private static int operatorArithmetic(int leftInt, int rightInt, String operator) {
        int answer = 0;
        switch (operator) {
            case "+":
                answer = leftInt + rightInt;
                break;
            case "-":
                answer = leftInt - rightInt;
                break;
            case "*":
                answer = leftInt * rightInt;
                break;
            case "/":
                answer = leftInt / rightInt;
                break;
            case "%":
                answer = leftInt % rightInt;
                break;
            case "^":
                answer = (int) Math.pow((double) leftInt, (double) rightInt);
        }

        return answer;
    }

    /**
     * Replaces parameter nodes with values supplied for said parametes.
     * Takes a HashMap of parameters to the root nodes of the assigned values.
     * @param identifiersMap
     */
    public void assignIdentifiers (HashMap<String, ExpTree> identifiersMap) {
        if (left != null) {
            if (left.getDataType() == IDENTIFIER) {
                if (identifiersMap.get(left.getData()) == null) {
                    System.out.println("No value provided for identifier " + left.getData() +
                                        "\n It will be treated as 0");
                    setLeftChild(new ExpTree(0));
                }
                setLeftChild(identifiersMap.get(left.getData()));
            }
            else {
                left.assignIdentifiers(identifiersMap);
            }
        }
        if (right != null) {
            if (right.getDataType() == IDENTIFIER) {
                if (identifiersMap.get(right.getData()) == null) {
                    System.out.println("No value provided for identifier " + right.getData() +
                            "\n It will be treated as 0");
                    setRightChild(new ExpTree(0));
                }
                setRightChild(identifiersMap.get(right.getData()));
            }
            else {
                right.assignIdentifiers(identifiersMap);
            }
        }
    }

    /**
     * Provides the output of the tree as a readable string
     * @return
     */
    @Override
    public String toString() {
        String string = "";
        ArrayList<String> stringList = this.getInOrder();
        for (String cell : stringList) {
            string = string + cell;
        }

        return string;
    }

    /**
     * Gets the tree via in-order traversal
     * @return
     */
    protected ArrayList<String> getInOrder() {
        ArrayList<String> returnArray = new ArrayList<>();
        if (left != null) {
            returnArray.addAll(left.getInOrder());
        }
        returnArray.add(this.data);
        if (right != null) {
            returnArray.addAll(right.getInOrder());
        }
        return returnArray;
    }

    /**
     * Gets the tree via post-order traversal
     * @return
     */
    public ArrayList<ExpTree> getTree() {
        ArrayList<ExpTree> returnArray = new ArrayList<>();
        if (left != null) {
            returnArray.addAll(left.getTree());
        }
        if (right != null) {
            returnArray.addAll(right.getTree());
        }
        returnArray.add(this);
        return returnArray;
    }

    public String getData() {
        return data;
    }

    public int getDataType() {
        return dataType;
    }

    public ExpTree getLeft() {
        return left;
    }

    public ExpTree getRight() {
        return right;
    }

    public boolean getIfLeaf() {
        return isLeaf;
    }
}
