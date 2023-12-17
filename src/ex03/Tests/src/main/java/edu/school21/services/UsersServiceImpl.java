package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;


public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);
        if (user == null) return false;
        if (user.isAuthentication()) {
            throw new AlreadyAuthenticatedException("Authentication has already been performed");
        }
        if (user.getPassword().equals(password)) {
            user.setAuthentication(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
