package gaa;

import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args){
    
		SwingUtilities.invokeLater(() -> {
        
			LoginWindow login = new LoginWindow();
            
			login.setVisible(true);
        
		});
    }
}
