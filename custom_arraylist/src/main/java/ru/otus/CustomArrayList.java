package ru.otus;

import java.util.*;
import java.util.function.Consumer;

public class CustomArrayList<E> implements List<E> {
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elementData;
    private int size;
    private long modCount = 0;

    public CustomArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(int capacity) {
        if (capacity > 0)
            elementData = new Object[capacity];
        else if (capacity == 0)
            elementData = EMPTY_ELEMENTDATA;
        else
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    private void checkCapacity(int minCapacity) {
        modCount++;
        if (minCapacity > elementData.length)
            grow(minCapacity);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if (newCapacity > MAX_ARRAY_SIZE || newCapacity < 0)
            throw new OutOfMemoryError();

        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        checkCapacity(size + 1);

        elementData[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkCapacity(size + 1);

        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;

        return oldValue;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }

        return -1;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int moved = size - index - 1;
        System.arraycopy(elementData, index + 1, elementData, index, moved);

        elementData[--size] = null;

        return oldValue;
    }

    @Override
    public void clear() {
        modCount++;

        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListItr(0);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index);
        return new CustomListItr(index);
    }

    private class CustomListItr implements ListIterator<E> {
        int cursor;
        int lastRet = -1;
        long expectedModCount = modCount;

        CustomListItr(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkMod();

            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();

            Object[] elementData = CustomArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();

            cursor = i + 1;

            //noinspection unchecked
            return (E) elementData[lastRet = i];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            checkMod();

            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();

            Object[] elementData = CustomArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();

            cursor = i;

            //noinspection unchecked
            return (E) elementData[lastRet = i];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            //TODO
        }

        @Override
        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkMod();

            try {
                CustomArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            checkMod();

            try {
                int i = cursor;
                CustomArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            //TODO
        }

        void checkMod() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public void sort(Comparator<? super E> c) {
        long expectedModCount = modCount;

        //noinspection unchecked
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();

        modCount++;
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    @Override
    public Object[] toArray() {
        //TODO
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        //TODO
        return null;
    }

    @Override
    public boolean remove(Object o) {
        //TODO
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        //TODO
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //TODO
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        //TODO
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //TODO
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //TODO
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        //TODO
        return null;
    }
}