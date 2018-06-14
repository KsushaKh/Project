package ru.itpark.service;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itpark.service.models.Category;
import ru.itpark.service.repository.CategoryRepository;
import ru.itpark.service.repository.CategoryRepositoryJdbcTemplateImpl;

import java.sql.SQLException;
import java.util.List;

public class MainUsers {

    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "381012";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/jewelry_DB";

    public static void main(String[] args) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        //механизм usersRepository, который по id находит пользователя


        CategoryRepository categoriesRepository = new CategoryRepositoryJdbcTemplateImpl(dataSource);
        Category diadema = Category.builder()
                .nameCategory("Диадемы")
                .build();

        categoriesRepository.save(diadema);

        List<Category> categories = categoriesRepository.findAll();
        System.out.println(categories);

        //находим пользователя под id = 1
       // User buyer = usersRepository.find(1);
        //всех пользователей из БД вытянули и распечатали
        //List<User> users = usersRepository.findAll();
       // System.out.println(users);
        //выводим строку под id = 1
       // System.out.println(buyer);
        /*User newUser = new User("Kseniya",
                "Khasanova",
                "ksusha",
                "qwaszx01",
                "г.Казань",
                "qwaszx@mail.ru",
                1111111);*/
        //красота этого патерна в том, что не нкжно писать много конструкторов

        /*User newUser = User.builder()
            .name("Denis")
            .surname("Kuznicov")
            .login_buyer("denis")
            .password_buyer("qwaszx04")
            .address_buyer("г.Санкт-Петербург")
            .mail("rtfgvb@mail.ru")
            .mobile(1111114)
            .build();
        System.out.println(newUser);
        usersRepository.save(newUser);
        System.out.println(newUser);
*/
        /*CategoryRepository categoryRepository = new CategoryRepositoryJdbcImpl(connection);
        Category newCategory = Category.builder()
                .name_category("Заколки")
                .build();
        System.out.println(newCategory);
        categoryRepository.save(newCategory);
        System.out.println(newCategory);
        */
        /*ProductRepository productRepository = new ProductRepositoryJdbcImpl(connection);
        Product.ProductBuilder builder = Product.builder();
        builder.idCategory(1);
        builder.name("Donatella");
        builder.article(101);
        builder.material("Жемчуг, Хрусталь, Кристалы Swarovski");
        builder.color("Серебро, белый");
        builder.size("10 см");
        builder.availability(1);
        builder.price(850);
        Product newProduct = builder.build();
        System.out.println(newProduct);
        productRepository.save(newProduct);
        System.out.println(newProduct);*/
        // Вывод всех продуктов с категориями
       /* ProductRepository productRepository = new ProductRepositoryJdbcImpl(connection);
        List<Product> products = productRepository.findAll();
        for (Product product: products) {
            System.out.println(product);
        }
        //Вывод продукта под id = 1 название категории и иднекс категории
        System.out.println(products.get(1).getIdCategory());*/

        /*CategoryRepository categoryRepository = new CategoryRepositoryJdbcImpl(dataSource);
        List<Category> categories = categoryRepository.findAllWithProducts();
        System.out.println(categories);*/

}
}
//интерфейс CrudRepository->UsersRepository->UsersRepositoryImpl,
// который принимает на вход connection8
