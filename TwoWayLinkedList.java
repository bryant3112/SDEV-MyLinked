import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<E> implements MyList<E> {

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(E element) {
            this.element = element;
        }
    }
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public TwoWayLinkedList() {
    }

    @Override
    public void add(E e) {
        add(size, e);
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<E> newNode = new Node<>(e);

        if (size == 0) {
            head = tail = newNode;
        }
        else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        else {
            Node<E> current = getNode(index);
            newNode.previous = current.previous;
            newNode.next = current;
            current.previous.next = newNode;
            current.previous = newNode;
        }

        size++;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) >= 0;
    }

    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.previous;
        }

        return current;
    }

    @Override
    public int indexOf(E e) {
        int index = 0;
        for (Node<E> current = head; current != null; current = current.next) {
            if (current.element.equals(e))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int lastIndexOf(E e) {
        int index = size - 1;
        for (Node<E> current = tail; current != null; current = current.previous) {
                return index;
            index--;
        }
        return -1;
    }

    public E remove(int index) {
            throw new IndexOutOfBoundsException();
            Node<E> removed;

        if (size == 1) 
            head = tail = null;
        }
        else if (index == 0) {
            removed = head;
            head = head.next;
            head.previous = null;
        }
        else if (index == size - 1) {
            removed = tail;
            tail = tail.previous;
            tail.next = null;
        }
        else {
            removed = getNode(index);
            removed.previous.next = removed.next;
            removed.next.previous = removed.previous;
        }

        size--;
        return removed.element;
    }

    
    public boolean remove(E e) {
        int index = indexOf(e);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    
    public int size() {
        return size;
    }

    // ---------- ListIterator Methods ----------

    
    public ListIterator<E> listIterator() {
        return new TwoWayListIterator(0);
    }

    
    public ListIterator<E> listIterator(int index) {
        return new TwoWayListIterator(index);
    }

    private class TwoWayListIterator implements ListIterator<E> {

        private Node<E> current;
        private int index;

        public TwoWayListIterator(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException();

            this.index = index;
            current = (index == size) ? null : getNode(index);
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            E e = current.element;
            current = current.next;
            index++;
            return e;
        }

        @Override
        public boolean hasPrevious() {
            return (current == null && size > 0) || current.previous != null;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();

            if (current == null)
                current = tail;
            else
                current = current.previous;

            index--;
            return current.element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            if (current == null)
                throw new IllegalStateException();
            current.element = e;
        }

        @Override
        public void add(E e) {
            TwoWayLinkedList.this.add(index, e);
            index++;
        }
    }
}
