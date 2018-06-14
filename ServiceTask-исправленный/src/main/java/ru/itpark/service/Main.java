package ru.itpark.service;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "381012";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/jewelry_DB";

    // (1) Получение данных из таблицы(пример):
    //language=SQL
    public static final String SQL_SELECT_NAMES_FROM_BUYER = "SELECT name FROM buyer";

    //(3) Решение исключения инъекции
    //language=SQL
    public static final String  SQL_INSERT_BUYER =
            "INSERT INTO buyer(name, surname, login_buyer, password_buyer, address_buyer, mail, mobile) VALUES (?, ?, ?, ?, ?, ?, ?);";

    public static void main(String[] args) {

        //(2)и (3).Ввнесение данных в БД:
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //(1) Через Statement отправляем запрос в БД и получаем результат через ResultSet
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_NAMES_FROM_BUYER);//выводит результат из БД
            //т.к ResultSet указывает на позицию перед строкой, нужно вызвать next()
            resultSet.next();
            System.out.println(resultSet.getString("name")); //выведет первую сторку из бд, результат будет из Java


            /*(2)-ТАК ДЕЛАТЬ ПЛОХО.НУЖНО ДЕЛАТЬ -(3). Считываем данные с клавиатуры для вноса в БД
            System.out.println("Please enter the name");
            String name = scanner.nextLine();
            System.out.println("Please enter the age");
            int age = scanner.nextInt();
            System.out.println("Please enter the height");
            double height = scanner.nextDouble();
            String query = "INSERT INTO owner(name, age, height) VALUES ('"
                    + name + "', " + age + ", " + height + ");";
            //посылаем запрос в БД, на добавление строки в таблицу
            statement.execute(query);
            */

            //(3)Всегда лучше использовать preparedStatement
            System.out.println("Please enter the name");
            String name = scanner.nextLine();
            System.out.println("Please enter the surname");
            String surname = scanner.nextLine();
            System.out.println("Please enter the login_buyer");
            String login_buyer = scanner.nextLine();
            System.out.println("Please enter the password_buyer");
            String password_buyer = scanner.nextLine();
            System.out.println("Please enter the address_buyer");
            String address_buyer = scanner.nextLine();
            System.out.println("Please enter the mail");
            String mail = scanner.nextLine();
            System.out.println("Please enter the mobile");
            int mobile = scanner.nextInt();

            //Создали перекомпилированный запрос(он его сразу скомпилирует в БД)
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BUYER);
            //под первым ? - подаем имя, 2 - возраст, 3 - рост
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, login_buyer);
            preparedStatement.setString(4, password_buyer);
            preparedStatement.setString(5, address_buyer);
            preparedStatement.setString(6, mail);
            preparedStatement.setInt(7, mobile);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


    }
}