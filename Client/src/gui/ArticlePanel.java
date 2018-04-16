package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handbook.ArticleHeader;
import handbook.Author;



public class ArticlePanel {

	private AppWindow parentWindow;

	private JPanel articlePanel;
	private JPanel buttonsPanel;
	
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JTextField titleLabel;
	private JTextArea articleContent;
	
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	
	private ArticleHeader currentArticle;
	
	private boolean editMode;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public ArticlePanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;

		initializeControls();
		enableEditMode(false);
	}
	

	public JComponent getArticlePanel() {
		return articlePanel;
	}
	
	public JComponent getButtonsPanel() {
		return buttonsPanel;
	}

	public void showArticle(ArticleHeader article) {
		currentArticle = article;
		
		enableEditMode(false);
		
		if (article != null) {
			String content = parentWindow.getController().getArticleContent(article.getId());
			Author author = parentWindow.getController().getAuthorById(article.getAuthorId());
			
			authorLabel.setText(author.getName());
			dateLabel.setText(article.getDate());
			titleLabel.setText(article.getTitle());
			articleContent.setText(content);
		} else {
			authorLabel.setText("");
			dateLabel.setText("");
			titleLabel.setText("");
			articleContent.setText("");
		}
	}

	public boolean isEditModeEnabled() {
		return editMode;
	}
	
	public void enableEditMode(boolean enable) {
		
		if (enable && currentArticle != null) {
			editButton.setText("Submit");
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);
			
			articleContent.setEditable(true);
			titleLabel.setEditable(true);
			
			editMode = true;
		} else {
			editButton.setText("Edit");
			addButton.setEnabled(true);
			deleteButton.setEnabled(true);
			
			articleContent.setEditable(false);
			titleLabel.setEditable(false);
			
			editMode = false;
		}
	}
	
	public void updateArticle() {
		currentArticle.setTitle(titleLabel.getText());
		
		parentWindow.getController().updateArticle(currentArticle, articleContent.getText());
		parentWindow.getTreePanel().updateArticlesTree();
		enableEditMode(false);
	}
	
	public void reset() {
		showArticle(null);
	}

	
	private void initializeControls() {
		initializeArticlePanel();
		initializeButtonsPanel();
	}
	
	private void initializeArticlePanel() {
		articlePanel = new JPanel(new GridBagLayout());
		articlePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// article content pane
		
		articleContent = new JTextArea();
		articleContent.setLineWrap(true);
		articleContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		JScrollPane articleView = new JScrollPane(articleContent);
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.8;
		
		articlePanel.add(articleView, constr);
		
		// author label
		
		authorLabel = new JLabel("Author");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START; 
		constr.weightx = 0.6;
		constr.weighty = 0.1;
		
		articlePanel.add(authorLabel, constr);
		
		// date label
		
		dateLabel = new JLabel("Date");
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.4;
		
		articlePanel.add(dateLabel, constr);
		
		// title label
		
		titleLabel = new JTextField("Title");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.1;
		
		articlePanel.add(titleLabel, constr);
	}
	
	private void initializeButtonsPanel() {
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		
		// add button
		
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAddButtonPressed();
			}
		});
		
		buttonsPanel.add(addButton);
		
		// edit button
		
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onEditButtonPressed();
			}
		});
		
		buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonsPanel.add(editButton);
		
		// delete button
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDeleteButtonPressed();
			}
		});
		
		buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(Box.createHorizontalGlue());
	}
	
	private void onAddButtonPressed() {
		int parentId = parentWindow.getTreePanel().getSelectedArticleId();
		int authorId = parentWindow.getAuthorPanel().getCurrentAuthor().getId();
		
		String currentDate = getCurrentDate();
		String title = "New article";
		
		ArticleHeader newArticle = new ArticleHeader(-1, parentId, authorId, currentDate, title);
		
		parentWindow.getController().addArticle(newArticle);
		parentWindow.getTreePanel().updateArticlesTree();
	}
	
	private void onEditButtonPressed() {
		int articleId = parentWindow.getTreePanel().getSelectedArticleId();
		if (articleId == -1) {
			parentWindow.showAlertMessage("Select article first.");
			return;
		}
		
		if (isEditModeEnabled()) {
			updateArticle();
		}
		enableEditMode(!isEditModeEnabled());
	}
	
	private void onDeleteButtonPressed() {
		int articleId = parentWindow.getTreePanel().getSelectedArticleId();
		if (articleId == -1) {
			parentWindow.showAlertMessage("Select article first.");
			return;
		}
		
		parentWindow.getController().deleteArticle(articleId);
		parentWindow.getTreePanel().updateArticlesTree();
		reset();
	}
	
	private String getCurrentDate() {
		return dateFormat.format(new Date());
	}
}
