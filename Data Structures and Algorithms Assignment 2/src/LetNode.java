import java.util.ArrayList;
import java.util.HashMap;

public class LetNode extends ExpTree {

    LetNode(ExpTree left, ExpTree right) {
        super(ExpTree.LET, left, right);
    }

    /**
     * Calculates the value of the tree that is its right child,
     * since the let node and its left children are not part of calculating the value of the expression
     * @return
     */
    @Override
    public int evaluate() {
        return this.getRight().evaluate();
    }

    /**
     * Gets tree in post-order, ignoring left children of the let node
     * @return
     */
    @Override
    public ArrayList<ExpTree> getTree() {
        return getRight().getTree();
    }

    /**
     * Gets the tree via in-order traversal, but puts "Let" in front for better toString() output
     * @return
     */
    @Override
    protected ArrayList<String> getInOrder() {
        ArrayList<String> returnArray = new ArrayList<>();
        returnArray.add(this.getData());
        if (this.getLeft() != null) {
            returnArray.addAll(this.getLeft().getInOrder());
        }
        if (this.getRight() != null) {
            returnArray.addAll(this.getRight().getInOrder());
        }
        return returnArray;
    }

    /**
     * Calls its left child tree to create a HashMap of identifiers to their values
     * @return
     */
    public HashMap<String, ExpTree> mapIdentifiers() {
        DefinitionsNode left = (DefinitionsNode) this.getLeft();
        return left.mapIdentifiers();
    }
}
