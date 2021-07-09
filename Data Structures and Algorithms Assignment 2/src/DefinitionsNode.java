import javax.naming.OperationNotSupportedException;
import java.util.HashMap;

public class DefinitionsNode extends ExpTree {

    DefinitionsNode(int type, ExpTree left, ExpTree right) {
        super(type, left, right);
    }

    /**
     * Since you are never supposed to actually use evaluate() on "and" and "equals" nodes,
     * this simply throws an error in case you do
     * @return
     */
    @Override
    public int evaluate() {
        try {
            throw new OperationNotSupportedException("And identifier-defining node was called for evaluation");
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Returns a map of identifiers to their values
     * @return
     */
    public HashMap<String, ExpTree> mapIdentifiers() {
        HashMap<String, ExpTree> map = new HashMap<>();
        if (this.getDataType() == ExpTree.AND) {
            if (this.getLeft() != null) {
                DefinitionsNode left = (DefinitionsNode) this.getLeft();
                map.putAll(left.mapIdentifiers());
            }
            if (this.getRight() != null) {
                DefinitionsNode right = (DefinitionsNode) this.getRight();
                map.putAll(right.mapIdentifiers());
            }
        }
        else {
            map.put(this.getLeft().getData(), this.getRight());
        }
        return map;
    }

}
