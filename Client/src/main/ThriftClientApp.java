package main;

import gui.AppWindow;
import javax.swing.SwingUtilities;


public class ThriftClientApp {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AppWindow window = new AppWindow();
				window.start();
			}
		});
	}

}
