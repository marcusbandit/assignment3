package dk.dtu.compute.course02324.assignment3.lists;

import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.implementations.SortedArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This test class sets up ({@link #setUp()}}) and adds some more
 * tests for {@link SortedArrayList} based on {@link TestForAllLists}.
 */
public class TestSortedArrayLists extends TestForAllLists{

    @Before
    public void setUp() throws Exception {
        this.list = new SortedArrayList<>();
    }

    @Test
    public void testAddingElementsInFront() {

        for (int i = TEST_SIZE-1; i >= 0; i--) {
            Assert.assertTrue(
                    "Add should return true",
                    list.add(format.format(i) + ". Test"));
        }

        // the following test is redundant with the test after it!
        for (int i = 0; i + 1 < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "Without sorting, neighbouring elements should be in order",
                    list.get(i).compareTo(list.get(i + 1)) < 0);
        }

        // the following test should cover the test before
        for (int i = 0; i < TEST_SIZE; i++) {
            Assert.assertEquals(
                    "Element i is at wrong position",
                    i,
                    list.indexOf(format.format(i) + ". Test"));
        }

        Assert.assertThrows(
                "Calling sort(null) on sorted lists should throw UnsupportedOperationException",
                UnsupportedOperationException.class,
                () -> { list.sort(null); } );

        Assert.assertThrows(
                "Calling sort(comparator) on sorted lists should throw UnsupportedOperationException",
                UnsupportedOperationException.class,
                () -> { list.sort(new GenericComparator<>()); });


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
    public void testSetThrowsUnsupportedOperationException() {
        list.add("Alpha");
        list.add("Beta");

        Assert.assertThrows(
                "set should throw UnsupportedOperationException on SortedList",
                UnsupportedOperationException.class,
                () -> list.set(0, "Gamma"));
    }

    @Test
    public void testAddAtPositionThrowsUnsupportedOperationException() {
        list.add("Alpha");

        Assert.assertThrows(
                "add(int, E) should throw UnsupportedOperationException on SortedList",
                UnsupportedOperationException.class,
                () -> list.add(0, "Beta"));
    }

}
