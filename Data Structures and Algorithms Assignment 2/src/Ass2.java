import java.util.ArrayList;
import java.util.HashMap;

public class Ass2 {
    public static void main(String[] args) {
        System.out.println(
                "Registration No. 1804987 \n" +
                "Welcome to my expression evaluation programme. \n" +
                "Please enter an expression \n" +
                "All expressions must end in semicolon");
        Parser parser = new Parser();
        parseTree(parser);

        boolean sessionOver = false;
        while (!sessionOver) {
            System.out.println("Another expression? [y/n]");
            String answer = parser.getLine();
            if (answer.equals("n")) {
                sessionOver = true;
            }
            else {
                if (answer.equals("y")) {
                    parseTree(parser);
                } else {
                    System.out.println("Unrecognised input. \n" +
                            "Do you wish to enter another expression?\n" +
                            "Enter 'y' to accept or 'n' to end the session");
                }
            }
        }
    }

    private static void parseTree (Parser parser) {
        try {
            ExpTree tree = parser.parseLine();
            System.out.println("Tree in post-order is: " + printTree(tree) +
                            "\n The expression is: " + tree.toString());
            if (tree.getDataType() == ExpTree.LET) {
                HashMap<String, ExpTree> map = ((LetNode) tree).mapIdentifiers();
                tree.assignIdentifiers(map);
            }
            System.out.println("Value of the expression is: " + tree.evaluate());
        } catch (ParseException exception) {
            System.out.println(
                    "Invalid input. \n" +
                    "Make sure all your input ends in a semicolon.");
        }
    }

    private static String printTree (ExpTree tree) {
        ArrayList<ExpTree> treeList = tree.getTree();
        String output = "";
        for (ExpTree node : treeList) {
            output = output + " " + node.getData();
        }
        return output;
    }
}
