package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;
import dk.dtu.compute.course02324.assignment3.lists.types.SortedList;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * An implementation of the interface {@link SortedList} based on the
 * {@link ArrayList} implementation of the interface{@link List}
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> implements SortedList<E> {

    @Override
    public boolean add(@NotNull E e) {
        if (e == null) {
            throw new IllegalArgumentException("\033[31mElement is null\033[0m");
        }
        int index = findIndexToInsert(e);
        super.add(index, e);
        return true;
    }

    /**
     * Finds the position at which a given element should be inserted
     * to the sorted list. The element must not be <code>null</code>.
     * The implementation finds this position by linearly going through
     * the array, stopping at the first element greater or equal to
     * this element.
     *
     * @param e the given element to be inserted
     * @return the position at which the element should be inserted
     */
    private int findIndexToInsert(@NotNull E e) {
        for (int i = 0; i < size(); i++) {
            if (get(i).compareTo(e) >= 0) {
                return i;
            }
        }
        return size();
    }

    @Override
    public boolean add(int index, E element) {
        throw new UnsupportedOperationException("\033[31mCannot add at a specific index in SortedArrayList\033[0m");
    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) {
        // No-op: SortedArrayList maintains sort order on insert, nothing to do
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("\033[31mCannot set element at a specific index in SortedArrayList\033[0m");
    }
}
