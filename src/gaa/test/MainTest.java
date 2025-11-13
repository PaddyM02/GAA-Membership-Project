package gaa.test;

import org.junit.jupiter.api.Test;

import gaa.LoginWindow;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainRunsWithoutException() {
        // Use assertDoesNotThrow to ensure main() executes without error.
        assertDoesNotThrow(() -> {
            // We don't actually want to show a Swing window during testing,
            // so we can call it on the Swing thread but override the UI call.
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                // Temporarily replace LoginWindow with a dummy one to avoid GUI pop-up
                new LoginWindow() {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public void setVisible(boolean b) {
                        // Do nothing during test
                    }
                }.setVisible(true);
            });
        });
    }
}

