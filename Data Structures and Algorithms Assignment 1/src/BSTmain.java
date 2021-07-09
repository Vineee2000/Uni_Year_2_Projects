public class BSTmain {
    public static void main(String[] args) {
        emptyTest();
        normalTest();
    }

    public static void emptyTest () {
        BST tree = new BST();
        System.out.println(tree.nonleaves());
        System.out.println(tree.depth());
        System.out.println(tree.range(1, 999));
    }

    public static void normalTest () {
        BST tree = new BST();
        tree.insert(15);
        tree.insert(33);
        tree.insert(85);
        tree.insert(7);
        tree.insert(3);
        tree.insert(99);
        System.out.println(tree.nonleaves());
        System.out.println(tree.depth());
        System.out.println(tree.range(15,33));
        System.out.println(tree.range(0, 999));
        System.out.println(tree.range(900, 999));
    }
}
