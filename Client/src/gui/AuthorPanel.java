package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		authorNameLabel = new JLabel();
		authorNameLabel.setEnabled(false);
		authorPanel.add(authorNameLabel);
		
		authorCountryLabel = new JLabel();
		authorCountryLabel.setEnabled(false);
		authorPanel.add(authorCountryLabel);
		
		loginButton = new JButton("Choose author");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		authorPanel.add(loginButton);
	}

	private void login() {
		String authorName = JOptionPane.showInputDialog("Enter your name:");
		
		if (authorName.isEmpty()) {
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
		
		authorNameLabel.setEnabled(true);
		authorCountryLabel.setEnabled(true);
	}
}
