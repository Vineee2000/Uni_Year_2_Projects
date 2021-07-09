public class main {
    public static void main(String[] args) {
        emptyQueueTest();
        nonEmptyQueueTest();
    }

    private static void emptyQueueTest () {
        System.out.println("Testing empty queue");
        dequeue <Integer> queue = new dequeue<>();
        if (queue.isEmpty()) {
            System.out.println("Queue recognised as empty correctly");
        }
        else {
            System.out.println("Queue not recognised as empty");
        }
        try {
            queue.left();
        }
        catch (queueException exception) {
            System.out.println("Left throws exception");
        }

        try {
            queue.right();
        }
        catch (queueException exception) {
            System.out.println("Right throws exception");
        }

        try {
            queue.removeLeft();
        }
        catch (queueException exception) {
            System.out.println("RemoveLeft throws exception");
        }

        try {
            queue.removeRight();
        }
        catch (queueException exception) {
            System.out.println("RemoveRight throws exception");
        }
    }

    private static void nonEmptyQueueTest () {
        System.out.println("Testing a non-empty queue");
        dequeue <Integer> queue = new dequeue();
        System.out.println("Populating queue");
        queue.addLeft(1);
        System.out.println("Added 1");
        queue.addRight(2);
        System.out.println("Added 2");
        queue.addRight(3);
        System.out.println("Added 3");
        queue.addLeft(0);
        System.out.println("Added 0");
        System.out.println("Queue is: " + queue.toString());
        queue.removeRight();
        queue.removeLeft();
        System.out.println("Queue middle is: " + queue.toString());
    }
}
