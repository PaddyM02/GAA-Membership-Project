package gaa.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gaa.User;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");

        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("Gold", user.getMembership());
        assertEquals("2025-12-31", user.getExpiry());
    }

    @Test
    void testSetName() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");
        user.setName("Jane Smith");
        assertEquals("Jane Smith", user.getName());
    }

    @Test
    void testSetMembership() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");
        user.setMembership("Silver");
        assertEquals("Silver", user.getMembership());
    }
}

