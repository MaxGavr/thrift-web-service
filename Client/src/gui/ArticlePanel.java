package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class ArticlePanel {

	private AppWindow parentWindow;

	private JPanel articlePanel;
	
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JLabel titleLabel;
	
	private JTextArea articleContent;
	
	
	
	public ArticlePanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;

		initializeControls();
	}
	
	private void initializeControls() {
		articlePanel = new JPanel(new GridBagLayout());
		
		// article content pane
		
		articleContent = new JTextArea("article's content");
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.8;
		
		articlePanel.add(articleContent, constr);
		
		// author label
		
		authorLabel = new JLabel("Author name");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START; 
		constr.weightx = 0.7;
		constr.weighty = 0.1;
		
		articlePanel.add(authorLabel, constr);
		
		// date label
		
		dateLabel = new JLabel("Date");
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.3;
		
		articlePanel.add(dateLabel, constr);
		
		// title label
		
		titleLabel = new JLabel("Title");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.1;
		
		articlePanel.add(titleLabel, constr);
	}

	public JComponent getRootComponent() {
		return articlePanel;
	}

}
