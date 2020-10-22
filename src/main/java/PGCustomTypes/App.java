package PGCustomTypes;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class App {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/OODB";
            connection = DriverManager.getConnection(dbURL, "postgres", "131214");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        assert connection != null;
        addData(connection);
        showData(connection);
        editData(connection);
        System.out.println(
                "\n                 Updated the DB\n======================================================================\n");
        showData(connection);
    }

    public static void addData(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO actors (person, role) VALUES (row (?,?,?),?)");
            statement.setString(1, "Иван Ургант");
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(1998, Calendar.DECEMBER, 25);
            statement.setDate(2, new Date(calendar.getTimeInMillis()));
            statement.setString(3, "российский актёр, шоумен, теле- и радиоведущий, певец, музыкант, композитор, продюсер и сценарист. Ведущий программы «Вечерний Ургант» на «Первом канале»");
            statement.setString(4, "Самого себя");
            statement.executeUpdate();
            calendar = new GregorianCalendar();
            calendar.set(1986, Calendar.MAY, 13);
            statement.setDate(2, new Date(calendar.getTimeInMillis()));
            statement.setString(3, "британский актёр, фотомодель и музыкант. Мировую известность ему принесли роли Седрика Диггори в фильме «Гарри Поттер и Кубок огня» и Эдварда Каллена в серии фильмов «Сумерки»");
            statement.setString(4, "Вампира из будущего");
            statement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void showData(Connection connection) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "Select (person).name, (person).dateofbirth, (person).bio, role from actors");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) +
                        ",\n    Дата рождения: " + resultSet.getDate(2) +
                        ",\n    Биография: " + resultSet.getString(3) +
                        ",\n    В роли: " + resultSet.getString(4) + "\n"
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void editData(Connection connection) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE actors set person.dateOfBirth = ? where (person).name LIKE '%ван%'");
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(1976, Calendar.JANUARY, 30);
            statement.setDate(1, new Date(calendar.getTimeInMillis()));
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
