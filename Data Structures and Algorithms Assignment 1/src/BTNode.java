class BTNode<T> {
    T data;
    BTNode<T> left, right;

    BTNode(T o) {
        data = o;
        left = right = null;
    }

    public int nonleaves() {
        int result = 0;
        if (left != null) {
            result = result + left.nonleaves();
        }
        else {
            result++;
        }
        if (right != null) {
            result = result + right.nonleaves();
        }
        else {
            result++;
        }
        return result;
    }

    public int depth () {
        int depth = 0;
        int leftDepth;
        if (left != null) {
            leftDepth = left.depth();
            if (leftDepth > depth) {
                depth = leftDepth;
            }
        }
        int rightDepth;
        if (right != null) {
            rightDepth = right.depth();
            if (rightDepth > depth) {
                depth = rightDepth;
            }
        }
        depth++;
        return depth;
    }

    public int range (int min, int max) throws IllegalArgumentException {
        if (!(this.data instanceof Integer)) {
            throw new IllegalArgumentException("Range method cannot be applied to non-integer data");
        }

        int result = 0;
        if (left != null) {
            result = result + left.range(min, max);
        }
        if (right != null) {
            result = result + right.range(min, max);
        }

        if ((Integer)this.data >= min && (Integer)this.data <= max) {
            result++;
        }

        return result;
    }
}
