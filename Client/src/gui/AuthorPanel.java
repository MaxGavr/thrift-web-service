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
		
		
		Author author;
		try {
			author = parentWindow.getController().getAuthorByName(authorName);
			isAuthorised = true;
			
		} catch (NoAuthorException e) {
			String authorCountry = JOptionPane.showInputDialog("Enter country:");
			
			author = new Author(-1, authorName, authorCountry);
			
			parentWindow.getController().addAuthor(author);
			isAuthorised = true;
		}
		
		showAuthorInfo(author);
		parentWindow.onChooseAuthor();
	}
	
	private void showAuthorInfo(Author author) {
		authorNameLabel.setText("Name: " + author.getName());
		authorCountryLabel.setText("Country: " + author.getCountry());
		
		authorNameLabel.setEnabled(true);
		authorCountryLabel.setEnabled(true);
	}
}
