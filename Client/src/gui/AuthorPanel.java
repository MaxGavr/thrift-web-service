package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import handbook.Author;
import handbook.NoAuthorException;



public class AuthorPanel {
	
	private AppWindow parentWindow;
	
	private JPanel authorPanel;

	private JButton loginButton;
	private JLabel authorNameLabel;
	private JLabel authorCountryLabel;
	
	private boolean isAuthorised;
	private Author currentAuthor;
	

	public AuthorPanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;
		isAuthorised = false;
		
		initializeControls();
	}

	
	public void resetAuthor() {
		authorNameLabel.setVisible(false);
		authorCountryLabel.setVisible(false);
		currentAuthor = null;
	}

	public JComponent getRootComponent() {
		return authorPanel;
	}
	
	public boolean getIsAuthorised() {
		return isAuthorised;
	}
	
	public Author getCurrentAuthor() {
		return currentAuthor;
	}
	
	
	private void initializeControls() {
		authorPanel = new JPanel();
		authorPanel.setLayout(new BoxLayout(authorPanel, BoxLayout.Y_AXIS));
		authorPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		authorNameLabel = new JLabel();
		authorNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		authorNameLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		authorNameLabel.setVisible(false);
		authorPanel.add(authorNameLabel);
		
		authorCountryLabel = new JLabel();
		authorCountryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		authorCountryLabel.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 3));
		authorCountryLabel.setVisible(false);
		authorPanel.add(authorCountryLabel);
		
		loginButton = new JButton("Choose author");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		authorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		authorPanel.add(loginButton);
	}

	private void login() {
		String authorName = JOptionPane.showInputDialog("Enter your name:");
		
		if (authorName == null || authorName.isEmpty()) {
			return;
		}
		
		try {
			currentAuthor = parentWindow.getController().getAuthorByName(authorName);
			isAuthorised = true;
			
		} catch (NoAuthorException e) {
			String authorCountry = JOptionPane.showInputDialog("Enter country:");
			
			currentAuthor = new Author(-1, authorName, authorCountry);
			
			currentAuthor.setId(parentWindow.getController().addAuthor(currentAuthor));
			isAuthorised = true;
		}
		
		showAuthorInfo();
		parentWindow.onChooseAuthor();
	}
	
	private void showAuthorInfo() {
		authorNameLabel.setText("Name: " + currentAuthor.getName());
		authorCountryLabel.setText("Country: " + currentAuthor.getCountry());
		
		authorNameLabel.setVisible(true);
		authorCountryLabel.setVisible(true);
	}
}
