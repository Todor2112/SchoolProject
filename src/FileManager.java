// FileManager.java
// Клас, отговарящ за записване и зареждане на обекти Car от и към .txt файл.
// Използва обекти от тип MyArrayList<Car> за работа със списъка с автомобили.

import java.io.*;

public class FileManager {

    // Метод за записване на списък с автомобили в текстов файл
    public static void save(MyArrayList<Car> list, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Car c : list) {
                writer.println(c);
            }
        }
    }

    // Метод за зареждане на автомобили от текстов файл в списък
    public static MyArrayList<Car> load(String filename) throws IOException {
        MyArrayList<Car> list = new MyArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Car.fromString(line));
            }
        }
        return list;
    }
}