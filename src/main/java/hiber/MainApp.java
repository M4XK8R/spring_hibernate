package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {

  private static final Car[] cars = {
      new Car("ZEAL", 111),
      new Car("LADA", 666),
      new Car("VAZ", 777)
  };

  public static void main(String[] args) throws SQLException {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = context.getBean(UserService.class);

    userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
    userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
    userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
    userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

    List<User> users = userService.listUsers();
    for (User user : users) {
      System.out.println("Id = " + user.getId());
      System.out.println("First Name = " + user.getFirstName());
      System.out.println("Last Name = " + user.getLastName());
      System.out.println("Email = " + user.getEmail());
      System.out.println();
    }

    userService.add(
        new User(
            "User6",
            "Lastname6",
            "user6@mail.ru",
            cars[0]
        )
    );
    userService.add(
        new User(
            "User7",
            "Lastname7",
            "user7@mail.ru",
            cars[1]
        )
    );
    userService.add(
        new User(
            "User8",
            "Lastname8",
            "user8@mail.ru",
            cars[2]
        )
    );

    for (Car car : cars) {
      System.out.println(
          "Car owner User is " +
              userService.getCarByModelAndSeries(
                  car.getModel(),
                  car.getSeries()
              )
      );
    }

    System.out.println(
        "Car owner User is " +
            userService.getCarByModelAndSeries(
                "rjwklw",
                374190
            )
    );

    System.out.println();

    userService.listUsers()
        .forEach(System.out::println);

    context.close();
  }
}
