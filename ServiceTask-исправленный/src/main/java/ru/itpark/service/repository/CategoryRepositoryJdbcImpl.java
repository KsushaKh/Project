package ru.itpark.service.repository;

import ru.itpark.service.models.Category;
import ru.itpark.service.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepositoryJdbcImpl implements CategoryRepository {

    //language=SQL
    private static final String SQL_FIND_CATEGORY_BY_ID =
            "SELECT * FROM  category WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT  * FROM category";

    //language=SQL
    private static final String SQL_INSERT_CATEGORY =
            "INSERT INTO category(nameCategory) VALUES (?)";

    //language=SQL
    private static final String SQL_ALL_WITH_PRODUCTS =
            "SELECT c.*, p.category_id, p.name_product, p.article, p.availability_product, p.size, p.color, p.material, p.price, p.id AS product_id FROM category c LEFT JOIN product p on c.id = p.category_id;";


    private Connection connection = null;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement insertStatement;
    private PreparedStatement findAllWithProductsStatement;

    private RowMapper<Category> categoryRowMapper = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet row) throws  SQLException {
            return Category.builder()
                    .id(row.getInt("id"))
                    .nameCategory(row.getString("name_category"))
                    .build();
        }
    };
    //создадим еще один новый RowMapper, он уже будет работать еще и с продуктами
    private RowMapper<Category> categoryWithProductsRowMapper = new RowMapper<Category>() {
        @Override
        //mapRow вызывается для одной отдельной строки
        public Category mapRow(ResultSet row) throws SQLException {
            //получили категорию
            //создаем категорию на основе текущей строки
            Category category = categoryRowMapper.mapRow(row);
            //если не содержал такой категории, мы ее добавим
            if(!categoryMap.containsKey(category.getId())) {
                //создадим пустой список этой категории которую будем добавлять
                category.setProducts(new ArrayList<Product>());
                //добавляем, под его же ключом, если не был обнаружен
                //всегда на каждой строчке будет новый продукт
                categoryMap.put(category.getId(), category);
            }
            //вытаскиваем id продукции из текущей строки
            Integer productId = row.getInt("id");
            //если id продукции был
            if(!row.wasNull()) {
                //создаем продукцию на основе этой строки
              Product product = Product.builder()
                      .id(productId)
                      .name(row.getString("name_product"))
                      .article(row.getInt("article"))
                      .material(row.getString("material"))
                      .color(row.getString("color"))
                      .size(row.getString("size"))
                      .availability(row.getInt("availability_product"))
                      .price(row.getInt("price"))
                      .build();
              //достали из map-а категорию по его id
                //получили его список продуктов и добавили этот продукт этой категории
              // в карте находим категорию по id и забираем список продуктов и кладем в продукты
                categoryMap.get(category.getId()).getProducts().add(product);
            }
            return category;
        }
    };

    //будем хранить id категорию и саму категорию
    private Map<Integer, Category> categoryMap = new HashMap<>();

    public CategoryRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        try {
            this.findByIdStatement = connection.prepareStatement(SQL_FIND_CATEGORY_BY_ID);
            this.findAllStatement = connection.prepareStatement(SQL_FIND_ALL);
            this.insertStatement = connection.prepareStatement(SQL_INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            this.findAllWithProductsStatement = connection.prepareStatement(SQL_ALL_WITH_PRODUCTS);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Category find(int id) {
        try {
            findByIdStatement.setInt(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            resultSet.next();
            return categoryRowMapper.mapRow(resultSet);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Category> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Category newCategory = categoryRowMapper.mapRow(resultSet);
                resultList.add(newCategory);
            }
            return resultList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(Category model) {
        try {
            insertStatement.setString(1, model.getNameCategory());
            //выполнили запрос и получили количество добавленных строк
            int affectedRows = insertStatement.executeUpdate();

            if(affectedRows == 0) {
                throw new IllegalArgumentException("Something wrong");
            }
            //получили список сгенерированных ключей бд
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            generatedKeys.next();
            //получаем сгенерированный id и кладем в модель
            model.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<Category> findAllWithProducts() {
        try {
            //получили результат запроса
            ResultSet resultSet = findAllWithProductsStatement.executeQuery();
            //для каждой строки запроса
            while (resultSet.next()) {
                //вызвали метод rowmap, он либо кладет новую категорию в map с новым продуктом
                //либо просто добавляет новый продукт категории
                categoryWithProductsRowMapper.mapRow(resultSet);
            }
            //создаем список категорий на основе значений из map
            List<Category> result = new ArrayList<>(categoryMap.values());
            //сам categoryMap нужно почистить,  чтобы больше не мешался
            categoryMap.clear();
            // вернули результат
            return result;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
