package dk.dtu.compute.course02324.assignment3.lists;

import dk.dtu.compute.course02324.assignment3.lists.implementations.ArrayList;
import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.implementations.SortedArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This test class sets up ({@link #setUp()}}) and adds some more
 * tests for {@link SortedArrayList} based on {@link TestForAllLists}.
 */
public class TestArrayLists extends TestForAllLists{

    @Before
    public void setUp() throws Exception {
        this.list = new ArrayList<>();
    }

    @Test
    public void testAddingElementsInFront() {

        for (int i = 0; i < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "Add should return true",
                    list.add(0, format.format(i) + ". Test"));
        }

        for (int i = 0; i + 1 < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "Initially, neighbouring elements should be out of order",
                    list.get(i).compareTo(list.get(i + 1)) > 0);
        }

        Assert.assertThrows(
                "Calling sort(null) should cause an exception",
                IllegalArgumentException.class,
                () -> { list.sort(null); } );

        list.sort(new GenericComparator<>());

        // the following test is redundant with the test after it!
        for (int i = 0; i + 1 < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "After sorting, neighbouring elements should be in order",
                    list.get(i).compareTo(list.get(i + 1)) < 0);
        }

        for (int i = 0; i < TEST_SIZE; i++) {
            Assert.assertEquals(
                    "Element i is at wrong position",
                    i,
                    list.indexOf(format.format(i) + ". Test"));
        }

        for (int i = list.size()-1; i > 0; i= i - 2) {
            String element = list.remove(i);
            String expected = format.format(i) + ". Test";
            Assert.assertEquals(
                    "Removed element has unexpected value",
                    expected,
                    element);
        }

        Assert.assertEquals(
                "List size is wrong",
                TEST_SIZE/2,
                list.size());
    }

    @Test
    public void testClearAndIsEmpty() {
        Assert.assertTrue("List should be empty initially", list.isEmpty());

        list.add("Alpha");
        list.add("Beta");
        Assert.assertFalse("List should not be empty after adding", list.isEmpty());
        Assert.assertEquals("List size should be 2", 2, list.size());

        list.clear();
        Assert.assertTrue("List should be empty after clear", list.isEmpty());
        Assert.assertEquals("List size should be 0 after clear", 0, list.size());
    }

    @Test
    public void testSet() {
        list.add("Alpha");
        list.add("Beta");
        list.add("Gamma");

        String old = list.set(1, "Delta");
        Assert.assertEquals("set should return old element", "Beta", old);
        Assert.assertEquals("Element at position 1 should be updated", "Delta", list.get(1));
        Assert.assertEquals("Size should not change after set", 3, list.size());

        Assert.assertThrows(
                "set with negative index should throw IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.set(-1, "X"));

        Assert.assertThrows(
                "set with index == size should throw IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.set(list.size(), "X"));

        Assert.assertThrows(
                "set with null element should throw IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.set(0, null));
    }

    @Test
    public void testAddAtPosition() {
        list.add("Alpha");
        list.add("Gamma");

        list.add(1, "Beta");
        Assert.assertEquals("Size should be 3", 3, list.size());
        Assert.assertEquals("Element at 0 should be Alpha", "Alpha", list.get(0));
        Assert.assertEquals("Element at 1 should be Beta", "Beta", list.get(1));
        Assert.assertEquals("Element at 2 should be Gamma", "Gamma", list.get(2));

        list.add(0, "First");
        Assert.assertEquals("Element at 0 should be First", "First", list.get(0));
        Assert.assertEquals("Size should be 4", 4, list.size());

        list.add(list.size(), "Last");
        Assert.assertEquals("Element at end should be Last", "Last", list.get(list.size() - 1));
        Assert.assertEquals("Size should be 5", 5, list.size());

        Assert.assertThrows(
                "add with negative index should throw IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.add(-1, "X"));

        Assert.assertThrows(
                "add with index > size should throw IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.add(list.size() + 1, "X"));

        Assert.assertThrows(
                "add with null element should throw IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.add(0, null));
    }

}
