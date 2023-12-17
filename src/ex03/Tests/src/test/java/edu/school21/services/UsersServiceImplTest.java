package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    UsersServiceImpl userService;
    UsersRepository usersRepository;


    @BeforeEach
    public void init() {
        usersRepository = mock(UsersRepository.class);
        userService = new UsersServiceImpl(usersRepository);
    }

    @Test
    public void authenticateCorrectLoginTest() {
        User user = new User(3L, "Nikita", "742642", false);
        when(usersRepository.findByLogin("Nikita")).thenReturn(user);
        boolean result = userService.authenticate("Nikita", "742642");
        verify(usersRepository).update(user);
        assertTrue(result);
    }

    @Test
    public void authenticateIncorrectLoginTest() {
        when(usersRepository.findByLogin("nonExistentUser")).thenReturn(null);
        boolean result = userService.authenticate("nonExistentUser", "742642");
        verify(usersRepository, never()).update(any());
        assertFalse(result);
    }

    @Test
    public void authenticateIncorrectPasswordTest() {
        User user = new User(3L, "Nikita", "742642", false);
        when(usersRepository.findByLogin("Nikita")).thenReturn(user);
        boolean result = userService.authenticate("Nikita", "7425241");
        verify(usersRepository, never()).update(any());
        assertFalse(result);
    }

    @Test
    public void authenticateThrowTest() {
        User user = new User(3L, "Nikita", "742642", true);
        when(usersRepository.findByLogin("nonExistentUser")).thenReturn(user);
        try {
            boolean result = userService.authenticate("nonExistentUser", "742642");
            fail();
        } catch (AlreadyAuthenticatedException e) {
            assertTrue(true);
        }
    }
}
