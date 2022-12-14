package com.liceu.geom.DAO;

import com.liceu.geom.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //Se declara una lista de usuarios estatica para que se almacene en la RAM una única lista de usuarios por ejecución de la aplicación
    static List<User> userRegistered = new ArrayList<>();
    static User currentUser;
    static int currentID = 0;

    @Override

    public void login(User user) {
        //Si el usuario no existe lo crea. Exista o no lo declara como usuario actual.
        for (User u : userRegistered) {
            if (u.getName().equals(user.getName())) {
                currentUser = user;
                return;
            }
        }
        saveUser(user);
        currentUser = user;
    }

    @Override
    public void saveUser(User user) {
        user.setId(currentID);
        userRegistered.add(user);
        currentID++;
    }

    @Override
    public User getUser(String userName) {
        for (User u : userRegistered) {
            if (u.getName().equals(userName)) {
                return u;
            }
        }
        return null;
    }
}
