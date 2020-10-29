package PGCustomTypes;

import java.sql.*;
import java.util.ArrayList;
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

    public static void showData(Connection connection) {
        PreparedStatement statementUser;
        try {
            statementUser = connection.prepareStatement(
                    "SELECT id,name,email from \"user\"");
            ResultSet users = statementUser.executeQuery();
            while (users.next()) {
                System.out.println(
                        users.getString(2) + " " + users.getString(3)
                );
                for (String rating :
                        getRates(connection, users.getInt(1))) {
                    System.out.println(rating + "\n");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ArrayList<String> getRates(Connection connection, int userID) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement statementMovies = connection.prepareStatement(
                    "SELECT movie, value, dateofchange from rate where userid=?");
            statementMovies.setInt(1, userID);
            ResultSet resultSet = statementMovies.executeQuery();
            while (resultSet.next()) {
                result.add("    " + numToStars(resultSet.getInt(2)) + ", " + getMovie(connection,resultSet.getInt(1)) + ", " + resultSet.getDate(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private static String getMovie(Connection connection, int movieID) {
        StringBuilder result = new StringBuilder();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, actors, artists from movie where id=?");
            statement.setInt(1, movieID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.append("\t").append(resultSet.getString(1)).append(", \n\t\tВ фильме снимались: ").append(resultSet.getString(2)).append("\n\t\tТруппа:").append(resultSet.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result.toString();
    }

    private static String numToStars(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append("*");
        }
        return stringBuilder.toString();
    }

    public static void addData(Connection connection) {
        addMovie(connection);
        addUsers(connection);
        addRates(connection);
    }

    private static void addRates(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO rate (movie, userid, value) VALUES (?,?,?)");
            statement.setInt(1, 1);
            statement.setInt(2, 1);
            statement.setInt(3, 8);
            statement.executeUpdate();

            statement.setInt(1, 1);
            statement.setInt(2, 2);
            statement.setInt(3, 5);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void addUsers(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"user\" (name, passwordhash, email) values (?,?,?)");

            statement.setString(1, "Сирожиддин");
            statement.setString(2, "password1");
            statement.setString(3, "siroj.serj14@gmail.com");
            statement.executeUpdate();

            statement.setString(1, "Андрей");
            statement.setString(2, "password2");
            statement.setString(3, "andrew.august3@gmail.com");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addMovie(Connection connection) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO movie (name, actors, artists) " +
                            "VALUES (?," +
                            "ARRAY[((?,?,?),?),((?,?,?),?),((?,?,?),?)]::actor[]," +
                            "ARRAY[((?,?,?),?),((?,?,?),?),((?,?,?),?)]::artist[])");
            statement.setString(1, "Двойной форсаж");

            statement.setString(2, "Пол Уокер");
            statement.setString(3, "Пол Уи́льям Уо́кер IV — американский киноактёр и модель. Широкую известность получил благодаря роли Брайана О’Коннора в серии фильмов «Форсаж»");
            calendar.set(1973, Calendar.SEPTEMBER, 12);
            statement.setDate(4, new Date(calendar.getTimeInMillis()));
            statement.setString(5, "Брайан О’Коннор");

            statement.setString(6, "Тайриз Гибсон");
            statement.setString(7, "Тайри́з Дарне́лл Ги́бсон, также известный просто как Тайриз — американский рэпер, автор-исполнитель, виджей, актёр и продюсер");
            calendar.set(1978, Calendar.DECEMBER, 30);
            statement.setDate(8, new Date(calendar.getTimeInMillis()));
            statement.setString(9, "Роман Пирс");

            statement.setString(10, "Ева Мендес");
            statement.setString(11, "Е́ва Ме́ндес — американская актриса");
            calendar.set(1974, Calendar.MARCH, 5);
            statement.setDate(12, new Date(calendar.getTimeInMillis()));
            statement.setString(13, "Моника Фуэнтес");

            statement.setString(14, "Джон Синглтон");
            statement.setString(15, "Джон Дэ́ниел Си́нглтон — американский кинорежиссёр, сценарист и кинопродюсер. Синглтон является самым молодым афроамериканцем в истории кинематографа, который был номинирован на «Оскар»");
            calendar.set(1968, Calendar.JANUARY, 6);
            statement.setDate(16, new Date(calendar.getTimeInMillis()));
            statement.setString(17, "Режиссер");

            statement.setString(18, "Мэттью Ф. Леонетти");
            statement.setString(19, "Мэттью Ф. Леонетти A.S.C. — американский кинооператор. Его младший брат, Джон Р. Леонетти, также является кинооператором, а иногда и режиссёром. Мэттью был оператором первого фильма Джона, «Смертельная битва 2: Истребление»");
            calendar.set(1941, Calendar.JULY, 31);
            statement.setDate(20, new Date(calendar.getTimeInMillis()));
            statement.setString(21, "Оператор");

            statement.setString(22, "Дэвид Арнольд");
            statement.setString(23, "Дэвид Арнольд — английский кинокомпозитор, наиболее известный своей музыкой в пяти фильмах про Джеймса Бонда, в фильме «Звёздные врата», «День независимости», «Годзилла» и в телесериалах «Маленькая Британия» и «Шерлок».");
            calendar.set(1962, Calendar.JANUARY, 23);
            statement.setDate(24, new Date(calendar.getTimeInMillis()));
            statement.setString(25, "Композитор");

            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void editData(Connection connection) {

    }
}
