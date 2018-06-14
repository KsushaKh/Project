package ru.itpark.service.repository;

import ru.itpark.service.models.Delivery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepositoryJdbcImpl implements DeliveryRepository {

    //language=SQL
    private static final String SQL_FIND_DELIVERY_BY_ID =
            "SELECT * FROM  delivery WHERE id = ?";
    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT  * FROM delivery";

    //language=SQL
    private static final String SQL_INSERT_DELIVERY =
            "INSERT INTO delivery(name_delivery, price_delivery) VALUES (?, ?, ?)";

    private Connection connection;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement insertStatement;

    private RowMapper<Delivery> deliveryRowMapper = new RowMapper<Delivery>() {
        @Override
        public Delivery mapRow(ResultSet row) throws SQLException {
            return Delivery.builder()
                    .id(row.getInt("id"))
                    .nameDelivery(row.getString("name_delivery"))
                    .priceDelivery(row.getInt("price_delivery"))
                    .build();
        }
    };

    public DeliveryRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        try {
            this.findByIdStatement = connection.prepareStatement(SQL_FIND_DELIVERY_BY_ID);
            this.findAllStatement = connection.prepareStatement(SQL_FIND_ALL);
            this.insertStatement = connection.prepareStatement(SQL_INSERT_DELIVERY, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Delivery find(int id) {
        try {
            findByIdStatement.setInt(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            resultSet.next();
            return deliveryRowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Delivery> findAll() {
        try {
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Delivery> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Delivery newDelivery = deliveryRowMapper.mapRow(resultSet);
                resultList.add(newDelivery);
            }
            return resultList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(Delivery model) {
        try {
            insertStatement.setString(1, model.getNameDelivery());
            insertStatement.setInt(2, model.getPriceDelivery());
            int affectedRows = insertStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new IllegalArgumentException("Something wrong");
            }
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            model.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
