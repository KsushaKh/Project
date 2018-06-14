package ru.itpark.service.repository;

import ru.itpark.service.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// UsersRepositoryImpl - класс, отвечающий за работу с данными
//(с пользователями) и вытаскивает данные из БД
public class UsersRepositoryJdbcImpl implements UsersRepository {

    //делаем поиск по id
    //language=SQL
    private static final String SQL_FIND_BUYER_BY_ID =
            "SELECT * FROM  buyer WHERE id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT  * FROM buyer";

    //запрос на добавление
    //language=SQL
    private static final String SQL_INSERT_BUYER =
            "INSERT INTO buyer(name, surname, login_buyer, password_buyer, address_buyer, mail, mobile) VALUES (?, ?, ?, ?, ?, ?, ?)";

    //связь с таблицей
    private Connection connection;
    //через объект типа PreparedStatement отправляем запрос в БД
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement insertStatement;

    //анонимный класс - т.к. нет имени у класса
    //сразу здесь напишем реализацию интерфейса
    //мы не делаем отдельную имплеметацию
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet row) throws  SQLException {
            return User.builder()
                    .id(row.getInt("id"))
                    .name(row.getString("name"))
                    .surname(row.getString("surname"))
                    .loginBuyer(row.getString("login_buyer"))
                    .passwordBuyer(row.getString("password_buyer"))
                    .addressBuyer(row.getString("address_buyer"))
                    .mail(row.getString("mail"))
                    .mobile(row.getInt("mobile"))
                    .build();//вызывает конструктор, который принимаент на вход build
        }
    };

    //конструктор
    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        try {
            //инициализация
            this.findByIdStatement = connection.prepareStatement(SQL_FIND_BUYER_BY_ID);
            this.findAllStatement = connection.prepareStatement(SQL_FIND_ALL);
            //ему передали строчку-запрос
            // и флаг Statement-а, который называется RETURN_GENERATED_KEYS,
            //который говорит, чтобы Statement вернул сгенерированное значение
            this.insertStatement = connection.prepareStatement(SQL_INSERT_BUYER, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    //делаем поиск по id*/
    @Override
    public User find(int id) {
        try {
            findByIdStatement.setInt(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            resultSet.next();
            return userRowMapper.mapRow(resultSet);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<User> findAll() {
        try {
            ResultSet resultSet = findAllStatement.executeQuery();
            List<User> resultList = new ArrayList<>();
            while (resultSet.next()) {
                //создаем пользователя на основе строки и resultSet
                User newUser = userRowMapper.mapRow(resultSet);
                //последовательно проходимся по списку всех пользователей
                // и далее преобразуем в объект и объект кладем в список
                resultList.add(newUser);
            }
            return resultList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    //в эту модель должны положить новый id? т.к мы не сами его генерируем
    public void save(User model) {
        try {
            insertStatement.setString(1, model.getName());
            insertStatement.setString(2, model.getSurname());
            insertStatement.setString(3, model.getLoginBuyer());
            insertStatement.setString(4, model.getPasswordBuyer());
            insertStatement.setString(5, model.getAddressBuyer());
            insertStatement.setString(6, model.getMail());
            insertStatement.setInt(7, model.getMobile());

            //выполнили запрос и получили количество добавленных строк
            //(affectedRows - строки, которые получили какой-то результат
            //покажет сколько строк в итоге изменено)
            int affectedRows = insertStatement.executeUpdate();
            // если все оказалось плохо
            if(affectedRows == 0) {
                //сразу выбрасываем исключение
                throw new IllegalArgumentException("Something wrong");
            }
            //получили список сгенерированных ключей из БД
            //( у запроса insertStatement забираем его сгенерированные ключи)

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            //получаем сгенерированный id и кладем в модель
            //(1 - это первая колонка)
            model.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


    }
}
