import java.util.Comparator;

/**
 * MergeSort е помощен клас, който предоставя метод за сортиране на MyArrayList
 * чрез алгоритъма Merge Sort. Използва се Comparator за гъвкаво сравнение.
 */
public class MergeSort {

    /**
     * Сортира списъка по подаден компаратор чрез рекурсивен Merge Sort.
     * @param list Списъкът, който ще се сортира
     * @param cmp Компаратор за определяне на реда на елементите
     * @return нов сортиран списък
     * @param <T> Типът на елементите в списъка
     */
    public static <T> MyArrayList<T> mergeSort(MyArrayList<T> list, Comparator<T> cmp) {
        // Базов случай: списък с 0 или 1 елемент вече е сортиран
        if (list.size() <= 1) return list;

        // Разделяне на списъка на две половини
        int mid = list.size() / 2;
        MyArrayList<T> left = new MyArrayList<>();
        MyArrayList<T> right = new MyArrayList<>();

        // Добавяне на първата половина в left
        for (int i = 0; i < mid; i++) left.add(list.get(i));

        // Добавяне на втората половина в right
        for (int i = mid; i < list.size(); i++) right.add(list.get(i));

        // Рекурсивно сортиране на двете половини
        left = mergeSort(left, cmp);
        right = mergeSort(right, cmp);

        // Обединяване на двете сортирани половини
        return merge(left, right, cmp);
    }

    /**
     * Обединява два сортирани списъка в един сортиран.
     * @param left първият сортиран списък
     * @param right вторият сортиран списък
     * @param cmp компаратор за сравнение
     * @return обединен и сортиран списък
     */
    private static <T> MyArrayList<T> merge(MyArrayList<T> left, MyArrayList<T> right, Comparator<T> cmp) {
        MyArrayList<T> result = new MyArrayList<>();
        int i = 0, j = 0;

        // Обхождане на двата списъка и добавяне на по-малкия елемент
        while (i < left.size() && j < right.size()) {
            if (cmp.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        // Добавяне на останалите елементи от left, ако има
        while (i < left.size()) result.add(left.get(i++));

        // Добавяне на останалите елементи от right, ако има
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}
