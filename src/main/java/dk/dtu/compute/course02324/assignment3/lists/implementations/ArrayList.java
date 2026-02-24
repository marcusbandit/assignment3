package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * An implementation of the interface {@link List} based on basic Java
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class ArrayList<E> implements List<E> {

    /**
     * Constant defining the default size of the array when the
     * list is created. The value can be any (strictly) positive
     * number. Here, we have chosen <code>10</code>, which is also
     * Java's default for some array-based collection implementations.
     */
    final private int DEFAULT_SIZE = 10;

    /**
     * Current size of the list.
     */
    private int size = 0;

    /**
     *  The array for storing the elements of the
     */
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull E get(int pos) throws IndexOutOfBoundsException {
        // checks if the position is out of bounds
        if (pos < 0 || pos >= size) {
            // throws an exception if the position is out of bounds in nice colored text
            throw new IndexOutOfBoundsException("\033[31mPosition is out of bounds\033[0m");
        }
        // returns the element at the given position
        return list[pos];
    }

    @Override
    public E set(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        // checks if the position is out of bounds
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("\033[31mPosition is out of bounds\033[0m");
        }
        // checks if the element is null
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        E old = list[pos];
        list[pos] = e;
        return old;
    }

    @Override
    public boolean add(@NotNull E e) {
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        if (size == list.length) {
            list = resize(list.length * 2);
        }
        list[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean add(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("\033[31mPosition is out of bounds\033[0m");
        }
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        if (size == list.length) {
            list = resize(list.length * 2);
        }
        for (int i = size; i > pos; i--) {
            list[i] = list[i - 1];
        }
        list[pos] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("\033[31mPosition is out of bounds\033[0m");
        }
        E old = list[pos];
        for (int i = pos; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        size--;
        return old;
    }

    @Override
    public boolean remove(E e) {
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
        if (c == null) {
            throw new IllegalArgumentException("\033[31mComparator is null\033[0m");
        }
        java.util.Arrays.sort(list, 0, size, c);
    }

    /**
     * Creates a new array of type <code>E</code> with the given size.
     *
     * @param length the size of the array
     * @return a new array of type <code>E</code> and the given length
     */
    private E[] createEmptyArray(int length) {
        // there is unfortunately no really easy and elegant way to initialize
        // an array with a type coming in as a generic type parameter, but
        // the following is simple enough. And it is OK, since the array
        // is never passed out of this class.
        return (E[]) new Object[length];
    }

    private E[] resize(int newSize) {
        E[] newList = createEmptyArray(newSize);
        System.arraycopy(list, 0, newList, 0, size);
        return newList;
    }

}
