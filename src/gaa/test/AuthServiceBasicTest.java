package gaa.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import gaa.AuthService;
import gaa.User;

public class AuthServiceBasicTest {

    private User user;
    private AuthService auth;

    @Before
    public void setup() {
        user = new User();
        auth = new AuthService(user);
    }

    @Test
    public void testSignupValid() {
        boolean ok = auth.signUp("test@gmail.com", "password123", "John");
        assertTrue(ok);
        assertEquals("test@gmail.com", user.getEmail());
        assertNotNull(user.getPasswordHash());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignupInvalidEmail() {
        auth.signUp("bademail", "password123", "John");
    }

    @Test
    public void testLoginCorrect() {
        auth.signUp("alice@gmail.com", "pass12345", "Alice");
        boolean passwordOk = auth.login("alice@gmail.com", "pass12345");
        assertTrue(passwordOk);
    }

    @Test
    public void testLoginWrongPassword() {
        auth.signUp("bob@gmail.com", "mypassword", "Bob");
        boolean ok = auth.login("bob@gmail.com", "wrongpass");
        assertFalse(ok);
    }

    @Test
    public void testLockoutAfterFailures() {
        auth.signUp("test@gmail.com", "StrongPass1", "TestUser");

        // 4 wrong attempts
        for (int i = 0; i < 4; i++) {
            auth.login("test@gmail.com", "wrong");
        }

        assertTrue(user.isLocked());
        assertNotNull(user.getLockoutUntil());
        assertTrue(user.getLockoutUntil().isAfter(LocalDateTime.now()));
    }
}
