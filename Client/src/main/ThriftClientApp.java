package main;

import gui.AppWindow;

import javax.swing.SwingUtilities;

import controller.ClientController;

public class ThriftClientApp {

	public static void main(String[] args) {
		
		ClientController controller = new ClientController();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AppWindow window = new AppWindow(controller);
				window.start();
			}
		});
	}

}
