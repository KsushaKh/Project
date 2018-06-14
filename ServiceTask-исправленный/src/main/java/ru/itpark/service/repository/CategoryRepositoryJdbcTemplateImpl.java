package ru.itpark.service.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itpark.service.models.Category;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CategoryRepositoryJdbcTemplateImpl implements CategoryRepository {

    //language=SQL
    private static final String SQL_INSERT_CATEGORY =
            "INSERT INTO category(name_category) VALUES (?)";

    //language=SQL
    private static final String SQL_FIND_ALL =
            "SELECT * FROM category";

    private JdbcTemplate jdbcTemplate;

    public CategoryRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Category> findAllWithProducts() {
        return null;
    }

    private RowMapper<Category> categoryRowMapper = new RowMapper<Category>() {

        @Override
        public Category mapRow(ResultSet row, int i) throws SQLException {
            return Category.builder()
                    .id(row.getInt("id"))
                    .nameCategory(row.getString("name_category"))
                    .build();

        }
    };




    @Override
    public Category find(int id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, categoryRowMapper);
    }

    @Override
    public void save(Category model) {
        jdbcTemplate.update(SQL_INSERT_CATEGORY, model.getNameCategory());

    }
}
