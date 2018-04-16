package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import handbook.ArticleHeader;



public class ButtonsPanel {

	private AppWindow parentWindow;
	
	private JPanel buttonsPanel;
	
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


	public ButtonsPanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;
		
		initializeControls();
	}

	
	public JComponent getRootComponent() {
		return buttonsPanel;
	}

	
	private void initializeControls() {
		buttonsPanel = new JPanel(new FlowLayout());
		
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
		
		buttonsPanel.add(editButton);
		
		// delete button
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDeleteButtonPressed();
			}
		});
		
		buttonsPanel.add(deleteButton);
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
		if (parentWindow.getArticlePanel().isEditModeEnabled()) {
			editButton.setText("Edit");
			addButton.setEnabled(true);
			deleteButton.setEnabled(true);
			
			parentWindow.getArticlePanel().updateArticle();			
		} else {
			editButton.setText("Submit");
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);
			
			parentWindow.getArticlePanel().enableEditMode(true);
		}
	}
	
	private void onDeleteButtonPressed() {
		int articleId = parentWindow.getTreePanel().getSelectedArticleId();
		parentWindow.getController().deleteArticle(articleId);
		parentWindow.getTreePanel().updateArticlesTree();
	}
	
	private String getCurrentDate() {
		return dateFormat.format(new Date());
	}
}
