package hiber.service;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();

    User getUserByCar(String model, int series);

    @Transactional(readOnly = true)
    User getUserByCarS(Car car);
}
