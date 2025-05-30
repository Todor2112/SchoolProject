// Car.java
// Клас, представляващ автомобил с основни свойства – марка, модел, година и цена.
// Съдържа конструктор, get/set методи и проверка за валидни стойности на годината и цената.

public class Car {
    private String brand;
    private String model;
    private int year;
    private double price;

    // Конструктор с проверка за коректност на данните
    public Car(String brand, String model, int year, double price) {
        if (brand == null || brand.isBlank() || model == null || model.isBlank())
            throw new IllegalArgumentException("Марка и модел не могат да бъдат празни.");
        if (year < 1886 || year > 2100)
            throw new IllegalArgumentException("Невалидна година.");
        if (price < 0)
            throw new IllegalArgumentException("Цената не може да е отрицателна.");
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank())
            throw new IllegalArgumentException("Марка не може да бъде празна.");
        this.brand = brand;
    }
    public void setModel(String model) {
        if (model == null || model.isBlank())
            throw new IllegalArgumentException("Модел не може да бъде празен.");
        this.model = model;
    }
    public void setYear(int year) {
        if (year < 1886 || year > 2100)
            throw new IllegalArgumentException("Невалидна година.");
        this.year = year;
    }
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Цената не може да е отрицателна.");
        this.price = price;
    }

    @Override
    public String toString() {
        return brand + "," + model + "," + year + "," + price;
    }

    public static Car fromString(String line) {
        String[] parts = line.split(",");
        return new Car(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]));
    }
}


