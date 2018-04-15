package main;

import gui.AppWindow;

import javax.swing.SwingUtilities;

import controller.HandbookClientController;

public class ThriftClientApp {

	public static void main(String[] args) {
		
		HandbookClientController controller = new HandbookClientController();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AppWindow window = new AppWindow(controller);
				window.start();
			}
		});
	}

}
