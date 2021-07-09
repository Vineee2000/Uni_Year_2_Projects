public class dequeue <T> extends DoubleLinkedList {
    public dequeue () {
        super();
    }

    public boolean isEmpty () {
        if (this.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public T left() throws queueException {
        if (this.isEmpty()) {
            throw new queueException("The queue is empty");
        }
        return (T) this.getFront().getData();
    }

    public T right() throws queueException {
        if (this.isEmpty()) {
            throw new queueException("The queue is empty");
        }
        return (T) this.getBack().getData();
    }

    public void addLeft (T data) {
        this.addToFront(data);
    }

    public void addRight (T data) {
        this.addToBack(data);
    }

    public void removeLeft() throws queueException {
        if (this.isEmpty()) {
            throw new queueException("The queue is empty");
        }
        this.removeFront();
    }

    public void removeRight () throws queueException {
        if (this.isEmpty()) {
            throw new queueException("The queue is empty");
        }
        this.removeBack();
    }
}
