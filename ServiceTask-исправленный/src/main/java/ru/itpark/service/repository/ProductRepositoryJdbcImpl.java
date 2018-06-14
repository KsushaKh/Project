package ru.itpark.service.repository;

import ru.itpark.service.models.Category;
import ru.itpark.service.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryJdbcImpl implements ProductRepository {

    //language=SQL
    public static final String SQL_SELECT_PRODUCT_WITH_CATEGORY_BY_ID =
            "SELECT p.*, c.name_category, c.id AS category_id FROM  product p " +
                    "LEFT  JOIN category c ON p.category_id = c.id WHERE p.id = ?";
    //language=SQL
    public static final String SQL_SELECT_ALL_WITH_CATEGORY = "SELECT p.*, c.name_category, c.id " +
            "AS category_id  FROM product p " +
            "LEFT JOIN category c ON p.category_id = c.id";

    private Connection connection;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;

    public ProductRepositoryJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
            this.findByIdStatement = connection.prepareStatement(SQL_SELECT_PRODUCT_WITH_CATEGORY_BY_ID);
            this.findAllStatement = connection.prepareStatement(SQL_SELECT_ALL_WITH_CATEGORY);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    private RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet row) throws SQLException {
            Integer productId = row.getInt("id");
            String name = row.getString("name_product");
            Integer article = row.getInt("article");
            String material = row.getString("material");
            String color = row.getString("color");
            String size = row.getString("size");
            Integer availability = row.getInt("availability_product");
            Integer price = row.getInt("price");


            Integer categoryId = row.getInt("id");
            Category category = null;
            if (!row.wasNull()) {
                category = Category.builder()
                        .id(categoryId)
                        .nameCategory(row.getString("name_category"))
                        .build();
            }
            return Product.builder()
                    .id(productId)
                    .idCategory(category)
                    .name(name)
                    .article(article)
                    .material(material)
                    .color(color)
                    .size(size)
                    .availability(availability)
                    .price(price)
                    .build();//вызывает конструктор, который принимаент на вход build
        }
    };

    @Override
    public Product find(int id) {
        try {
            findByIdStatement.setInt(1, id);
            ResultSet result = findByIdStatement.executeQuery();
            result.next();
            return productRowMapper.mapRow(result);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Product> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Product Product = productRowMapper.mapRow(resultSet);
                resultList.add(Product);
            }
            return resultList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(Product model) {
       /*try {
            //insertStatement.setInt(1, model.getIdCategory());
            insertStatement.setString(2, model.getName());
            insertStatement.setInt(3, model.getArticle());
            insertStatement.setString(4, model.getMaterial());
            insertStatement.setString(5, model.getColor());
            insertStatement.setString(6, model.getSize());
            insertStatement.setInt(7, model.getAvailability());
            insertStatement.setInt(8, model.getPrice());

            int affectedRows = insertStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new IllegalArgumentException("Something wrong");
            }
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            model.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }*/


    }
}
