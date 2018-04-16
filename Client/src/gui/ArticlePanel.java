package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handbook.ArticleHeader;
import handbook.Author;



public class ArticlePanel {

	private AppWindow parentWindow;

	private JPanel articlePanel;
	
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JTextField titleLabel;
	
	private JTextArea articleContent;
	
	private ArticleHeader currentArticle;
	
	private boolean editMode;
	
	
	public ArticlePanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;

		initializeControls();
	}
	

	public JComponent getRootComponent() {
		return articlePanel;
	}

	public void showArticle(ArticleHeader article) {
		currentArticle = article;
		
		String content = parentWindow.getController().getArticleContent(article.getId());
		Author author = parentWindow.getController().getAuthorById(article.getAuthorId());
		
		authorLabel.setText(author.getName());
		dateLabel.setText(article.getDate());
		titleLabel.setText(article.getTitle());
		articleContent.setText(content);
	}

	public boolean isEditModeEnabled() {
		return editMode;
	}
	
	public void enableEditMode(boolean enable) {
		titleLabel.setEditable(enable);
		articleContent.setEditable(enable);
		
		editMode = enable;
	}
	
	public void updateArticle() {
		currentArticle.setTitle(titleLabel.getText());
		
		parentWindow.getController().updateArticle(currentArticle, articleContent.getText());
		parentWindow.getTreePanel().updateArticlesTree();
		enableEditMode(false);
	}

	
	private void initializeControls() {
		articlePanel = new JPanel(new GridBagLayout());
		
		// article content pane
		
		articleContent = new JTextArea("article's content");
		articleContent.setEditable(false);
		
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
		
		titleLabel = new JTextField("Title");
		titleLabel.setEditable(false);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.1;
		
		articlePanel.add(titleLabel, constr);
	}

}
