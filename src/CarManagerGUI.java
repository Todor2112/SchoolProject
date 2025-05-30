// CarManagerGUI.java
// Графичен интерфейс за управление на автомобили в автосалон.
// Създаден с Java Swing. Позволява добавяне, редакция, търсене, сортиране, запис и зареждане на данни за автомобили.
// Използва JTable за визуализиране на автомобилите и MyArrayList за съхранение.

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Comparator;

public class CarManagerGUI extends JFrame {
    // JTable и модел за визуализиране на списъка с автомобили
    private JTable table;
    private DefaultTableModel tableModel;
    // Собствена имплементация на списък за съхранение на обектите Car
    private MyArrayList<Car> carList = new MyArrayList<>();
    private final String[] columns = {"Марка", "Модел", "Година", "Цена"};

    public CarManagerGUI() {
        // Основна настройка на прозореца
        setTitle("Управление автосалон");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Създаване и настройка на JTable и JScrollPane
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        // Панел с бутони
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Създаване на бутони и добавяне към панела
        String[] btnLabels = {"Добави", "Редактирай", "Изтрий", "Търси", "Сортирай", "Зареди", "Запази"};
        JButton[] buttons = new JButton[btnLabels.length];

        for (int i = 0; i < btnLabels.length; i++) {
            buttons[i] = new JButton(btnLabels[i]);
            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 13));
            buttons[i].setBackground(new Color(70, 130, 180));
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setFocusPainted(false);
            panel.add(buttons[i]);
        }
        // Свързване на функционалности към всеки бутон
        buttons[0].addActionListener(e -> addCar());
        buttons[1].addActionListener(e -> editCar());
        buttons[2].addActionListener(e -> deleteCar());
        buttons[3].addActionListener(e -> searchCar());
        buttons[4].addActionListener(e -> sortCars());
        buttons[5].addActionListener(e -> loadFromFile());
        buttons[6].addActionListener(e -> saveToFile());

        // Заглавие на прозореца
        JLabel title = new JLabel("Управление на автомобили", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Добавяне на компоненти към основния прозорец
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    // Метод за добавяне на нов автомобил чрез диалогови прозорци
    private void addCar() {
        try {
            String brand = JOptionPane.showInputDialog(this, "Марка:");
            String model = JOptionPane.showInputDialog(this, "Модел:");
            int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Година:"));
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Цена:"));
            Car car = new Car(brand, model, year, price);
            carList.add(car);
            tableModel.addRow(new Object[]{brand, model, year, price});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Грешка: " + ex.getMessage());
        }
    }

    // Метод за редакция на съществуващ автомобил
    private void editCar() {
        int sel = table.getSelectedRow();
        if (sel >= 0) {
            try {
                Car car = carList.get(sel);
                String brand = JOptionPane.showInputDialog(this, "Нова марка:", car.getBrand());
                String model = JOptionPane.showInputDialog(this, "Нов модел:", car.getModel());
                int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Нова година:", car.getYear()));
                double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Нова цена:", car.getPrice()));
                car.setBrand(brand);
                car.setModel(model);
                car.setYear(year);
                car.setPrice(price);
                updateTable(carList);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Грешка: " + ex.getMessage());
            }
        }
    }

    // Метод за изтриване на избран автомобил от списъка и таблицата
    private void deleteCar() {
        int sel = table.getSelectedRow();
        if (sel >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Сигурни ли сте, че искате да изтриете избрания автомобил?",
                    "Потвърждение", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                carList.remove(sel);
                tableModel.removeRow(sel);
            }
        }
    }
    // Метод за търсене по марка, модел или максимална цена
    private void searchCar() {
        String input = JOptionPane.showInputDialog(this, "Търси по марка/модел/макс. цена:");
        if (input == null || input.isBlank()) return;
        MyArrayList<Car> filtered = new MyArrayList<>();
        try {
            double maxPrice = Double.parseDouble(input);
            for (Car c : carList) if (c.getPrice() <= maxPrice) filtered.add(c);
        } catch (NumberFormatException e) {
            for (Car c : carList) if (c.getBrand().equalsIgnoreCase(input) || c.getModel().equalsIgnoreCase(input)) filtered.add(c);
        }
        updateTable(filtered);
    }
    // Метод за сортиране на автомобилите по цена чрез Merge Sort
    private void sortCars() {
        carList = MergeSort.mergeSort(carList, Comparator.comparingDouble(Car::getPrice));
        updateTable(carList);
    }
    // Метод за записване на данните във файл
    private void saveToFile() {
        try {
            FileManager.save(carList, "cars.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Грешка при запис: " + e.getMessage());
        }
    }

    // Метод за зареждане на данни от файл
    private void loadFromFile() {
        try {
            carList = FileManager.load("cars.txt");
            updateTable(carList);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Грешка при зареждане: " + e.getMessage());
        }
    }

    // Метод за обновяване на таблицата с нови данни
    private void updateTable(MyArrayList<Car> list) {
        tableModel.setRowCount(0);
        for (Car c : list) {
            tableModel.addRow(new Object[]{c.getBrand(), c.getModel(), c.getYear(), c.getPrice()});
        }
    }

    public static void main(String[] args) {
        // Задаване на системната визуализаци
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        // Стартиране на GUI
        SwingUtilities.invokeLater(() -> new CarManagerGUI().setVisible(true));
    }
}
