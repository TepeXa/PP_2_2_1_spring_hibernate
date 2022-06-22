package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User Denis = (new User("Denis", "Terentyev", "Denis@mail.ru"));
      User Daniil =(new User("Daniil", "Pyrkh", "Daniil@mail.ru"));
      User Aleksey =(new User("Aleksey", "Terentyev", "Aleksey@mail.ru"));
      User Renat =(new User("Renat", "Araslanov", "Renat@mail.ru"));

      Car Audi = new Car("Audi", 5);
      Car AudiBad = new Car("Audi", 6);
      Car Mercedes = new Car("Mercedes", 200);
      Car Volkswagen = new Car("Volkswagen", 2);
      Car Toyota = new Car("Toyota", 1);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

     userService.add(Denis.setCar(Audi).setUser(Denis));
     userService.add(Renat.setCar(Toyota).setUser(Renat));
     userService.add(Daniil.setCar(Mercedes).setUser(Daniil));
     userService.add(Aleksey.setCar(Volkswagen).setUser(Aleksey));

      // пользователи с машинами
      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      // достать юзера, владеющего машиной по ее модели и серии
      System.out.println(userService.getUserByCar("Audi", 5));

      try {
      System.out.println(userService.getUserByCarS(Audi));
       } catch (NoResultException e) {
           System.out.println("Пользователь с авто " + Audi + " не найден");
       }

      // не существующий user+car
       try {
           System.out.println(userService.getUserByCarS(AudiBad));
       } catch (NoResultException e) {
           System.out.println("Пользователь с авто " + AudiBad + " не найден");
       }

      // не существующий user+car
       try {
           System.out.println(userService.getUserByCar("Audi", 6));
       } catch (NoResultException e) {
        System.out.println("Пользователь с авто " + AudiBad + " не найден");
       }

      // не существующий user+car
      try {
        User notFoundUser = userService.getUserByCar("Audi", 6);
     } catch (NoResultException e) {
        System.out.println("Пользователь с авто " + AudiBad + " не найден");
     }

      context.close();
   }
}
