import java.util.Arrays;
import java.util.Iterator;

/**
 * MyArrayList е собствена имплементация на динамичен списък с generic типове.
 * Поддържа операции: добавяне, премахване, достъп по индекс, търсене, итерация и изчистване.
 */
public class MyArrayList<T> implements Iterable<T> {
    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException("Елементът не може да е null.");
        ensureCapacity();
        data[size++] = element;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Индексът е извън границите.");
        @SuppressWarnings("unchecked")
        T element = (T) data[index];
        return element;
    }

    public T remove(int index) {
        T removed = get(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
        return removed;
    }

    public int size() {
        return size;
    }

    /**
     * Изчиства всички елементи от списъка.
     */
    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    /**
     * Проверява дали даден елемент съществува в списъка.
     * @param element търсеният елемент
     * @return true ако елементът е намерен, иначе false
     */
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    /**
     * Връща индекса на даден елемент. Ако не е намерен, връща -1.
     * @param element елементът, чийто индекс се търси
     * @return индексът или -1 ако елементът не съществува
     */
    public int indexOf(T element) {
        if (element == null) return -1;
        for (int i = 0; i < size; i++) {
            if (element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                return get(cursor++);
            }
        };
    }
}
