package gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;



public class ButtonsPanel {

	private AppWindow parentWindow;
	
	private JPanel buttonsPanel;
	
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;


	public ButtonsPanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;
		
		initializeControls();
	}

	
	private void initializeControls() {
		buttonsPanel = new JPanel(new FlowLayout());

		// add button
		
		addButton = new JButton("Add");
		buttonsPanel.add(addButton);
		
		// edit button
		
		editButton = new JButton("Edit");
		buttonsPanel.add(editButton);
		
		// delete button
		
		deleteButton = new JButton("Delete");
		buttonsPanel.add(deleteButton);
	}
	
	public JComponent getRootComponent() {
		return buttonsPanel;
	}

}
