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
    }

    public static void addData(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO actors (person, role) VALUES (row (?,?,?),?)");
            statement.setString(1, "Иван");
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(1998, Calendar.DECEMBER, 25);
            statement.setDate(2, new Date(calendar.getTimeInMillis()));
            statement.setString(3, "Популярный актер");
            statement.setString(4, "Палка");
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
                        ",\n    Роль в фильме: " + resultSet.getString(4) + "\n"
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
