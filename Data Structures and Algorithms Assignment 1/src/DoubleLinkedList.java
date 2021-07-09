import java.util.NoSuchElementException;

public class DoubleLinkedList<T>
{
  private ListCell front;
  private ListCell back;

  class Iterator implements LListIterator {
    ListCell prev;
    ListCell next;
    ListCell beforePrev;
    boolean removed;

    Iterator () {
      next = front;
      prev = null;
      beforePrev = null;
      removed = false;
    }

    @Override
    public boolean hasNext() {
      if (next != null) {
        return true;
      }

      else {
        return false;
      }
    }

    @Override
    public T next() throws NoSuchElementException {
      if (next == null) {
        throw new NoSuchElementException();
      }
      else {
        beforePrev = prev;
        prev = next;
        next = next.getNext();
        removed = false;
        return (T) prev.getData();
      }
    }

    @Override
    public void remove() {
      if (next==front || removed == true) {
        throw new IllegalStateException();
      }
      else {
        if (prev == front) {
          front = next;
          prev = null;
        }
        else {
          beforePrev.setNext(next);
        }
        removed = true;
      }
    }
  }

  public DoubleLinkedList() {
    front = null;
    back = null;
  }

  public ListCell getFront() {
    return front;
  }

  public ListCell getBack() {
    return back;
  }

  public void createFirstCell (T data) {
    ListCell fistCell = new ListCell(data, null, null);
    fistCell.setNext(fistCell);
    fistCell.setPrevious(fistCell);
    front = fistCell;
    back = fistCell;
  }

  public void addToFront(T data) {
    if (this.length() == 0) {
      createFirstCell(data);
    }
    else {
      ListCell newFront = new ListCell(data, front, back);
      front.setPrevious(newFront);
      front = newFront;
      back.setNext(front);
    }
  }

  public void addToBack(T data) {
    if (this.length() == 0) {
      createFirstCell(data);
    }
    else {
      ListCell newBack = new ListCell(data, front, back);
      back.setNext(newBack);
      back = newBack;
      front.setPrevious(back);
    }
  }

  public void removeFront () {
    front = front.getNext();
    back.setNext(front);
  }

  public void removeBack () {
    back = back.getPrevious();
    front.setPrevious(back);
  }

  public T elementAt(int n) {
    ListCell c = front;
    if (n<0)
      throw new ListException("elementAt called with negative argument");
    for (int i = 0; i<n; i++)
    {
      if (c == null)
        throw new ListException("no element at position "+n);
      c = c.getNext();
    }
    if (c == null)
      throw new ListException("no element at position "+n);
    return (T) c.getData();
  }

  public int length () {
    ListCell c = front;
    if (c== null) {
      return 0;
    }
    else {
      int length = 1;
      while (c.getNext() != front) {
        c = c.getNext();
        length++;
      }
      return length;
    }
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("<");
    ListCell c = front;
    while (c.getNext() != front)
    {
      sb.append(c.getData());
      sb.append(", ");
      c = c.getNext();
    }
    sb.append(c.getData());
    return(sb+">");
  }

  public int find (T x) {
    int i = 0;
    ListCell c = front;
    while (c.getNext() != front) {
      if (c.getData().equals(x)) {
        return i;
      }
      i++;
      c = c.getNext();
    }
    return -1;
  }

  public LListIterator iterator () {
    return new Iterator();
  }
}
