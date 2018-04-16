package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ClientController;
import gui.tree.ContentTreePanel;



public class AppWindow {
	
	private ClientController controller;

	private JFrame mainFrame;
	
	private ServerPanel serverPanel;
	private ArticlePanel articlePanel;
	private ContentTreePanel treePanel;
	private AuthorPanel authorPanel;
	

	public AppWindow(ClientController controller) {
		this.controller = controller;
		if (controller != null) {
			controller.setView(this);
		}

		initialize();
	}
	

	public void start() {
		mainFrame.setVisible(true);
	}
	
	public void showAlertMessage(String message) {
		JOptionPane.showMessageDialog(mainFrame, message, "Message", JOptionPane.WARNING_MESSAGE);
	}

	
	public ClientController getController() {
		return controller;
	}
	
	public ServerPanel getServerPanel() {
		return serverPanel;
	}
	
	public ArticlePanel getArticlePanel() {
		return articlePanel;
	}
	
	public ContentTreePanel getTreePanel() {
		return treePanel;
	}
	
	public AuthorPanel getAuthorPanel() {
		return authorPanel;
	}
	
	
	public void onConnectToServer() {
		enableComponent(authorPanel.getRootComponent(), true);
	}


	public void onDisconnectFromServer() {
		enableComponent(articlePanel.getArticlePanel(), false);
		enableComponent(articlePanel.getButtonsPanel(), false);
		enableComponent(treePanel.getRootComponent(), false);
		enableComponent(authorPanel.getRootComponent(), false);
		
		authorPanel.resetAuthor();
	}
	
	public void onChooseAuthor() {
		enableComponent(articlePanel.getArticlePanel(), true);
		enableComponent(articlePanel.getButtonsPanel(), true);
		enableComponent(treePanel.getRootComponent(), true);
		
		treePanel.updateArticlesTree();
	}
	
	
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Pascal Handbook Client");
		mainFrame.setBounds(100, 100, 800, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				serverPanel.disconnectFromServer();
				super.windowClosing(e);
			}
		});
		
		
		mainFrame.setLayout(new GridBagLayout());

		// server panel
		
		serverPanel = new ServerPanel(this);
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.FIRST_LINE_START;
		constr.weightx = 0.7;
		constr.weighty = 0.05;
		constr.insets = new Insets(5, 5, 5, 5);
		
		mainFrame.add(serverPanel.getRootComponent(), constr);
		
		// article panel
		
		articlePanel = new ArticlePanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.9;
		constr.insets = new Insets(5, 5, 5, 5);
		
		mainFrame.add(articlePanel.getArticlePanel(), constr);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.05;
		constr.insets = new Insets(5, 5, 5, 5);
		
		mainFrame.add(articlePanel.getButtonsPanel(), constr);
		
		// tree panel
		
		treePanel = new ContentTreePanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.gridheight = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.PAGE_START;
		constr.weightx = 0.3;
		constr.insets = new Insets(5, 5, 5, 5);
		
		mainFrame.add(treePanel.getRootComponent(), constr);
		
		// author panel
		
		authorPanel = new AuthorPanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.CENTER;
		constr.insets = new Insets(5, 5, 5, 5);
		
		mainFrame.add(authorPanel.getRootComponent(), constr);
		
		enableComponent(articlePanel.getArticlePanel(), false);
		enableComponent(articlePanel.getButtonsPanel(), false);
		enableComponent(treePanel.getRootComponent(), false);
		enableComponent(authorPanel.getRootComponent(), false);
	}
	
	private void enableComponent(Component component, boolean enable) {
		component.setEnabled(enable);
	    if (component instanceof Container) {
	        for (Component child : ((Container) component).getComponents()) {
	            enableComponent(child, enable);
	        }
	    }
	}
}
