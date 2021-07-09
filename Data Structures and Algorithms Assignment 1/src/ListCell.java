public class ListCell<T> {
    private T data;
    private ListCell next;
    private ListCell previous;

    public ListCell(T constructData, ListCell constructNext, ListCell constructPrevious) {
        setNext(constructNext);

        setPrevious(constructPrevious);

        setData(constructData);
    }

    public void setPrevious (ListCell newPrev) {
        previous = newPrev;
    }

    public ListCell getPrevious () {
        return previous;
    }

    public ListCell getNext() {
        return next;
    }

    public void setNext(ListCell newNext) {
        next = newNext;
    }

    public T getData() {
        return data;
    }

    private void setData(T newData) {
        data = newData;
    }
}