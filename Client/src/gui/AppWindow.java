package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.HandbookClientController;
import gui.tree.ContentTreePanel;



public class AppWindow {
	
	private HandbookClientController controller;

	private JFrame mainFrame;
	
	private ServerPanel serverPanel;
	private ArticlePanel articlePanel;
	private ButtonsPanel buttonsPanel;
	private ContentTreePanel treePanel;
	private AuthorPanel authorPanel;
	

	public AppWindow(HandbookClientController controller) {
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
		JOptionPane.showMessageDialog(mainFrame, message);
	}

	
	public HandbookClientController getController() {
		return controller;
	}
	
	
	public void onConnectToServer() {
		enableComponent(authorPanel.getRootComponent(), true);
	}


	public void onDisconnectFromServer() {
		enableComponent(articlePanel.getRootComponent(), false);
		enableComponent(treePanel.getRootComponent(), false);
		enableComponent(buttonsPanel.getRootComponent(), false);
		enableComponent(authorPanel.getRootComponent(), false);
	}
	
	public void onChooseAuthor() {
		enableComponent(articlePanel.getRootComponent(), true);
		enableComponent(treePanel.getRootComponent(), true);
		enableComponent(buttonsPanel.getRootComponent(), true);
	}
	
	
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 450, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				serverPanel.disconnectFromServer();
				super.windowClosing(e);
			}
		});
		
		
		mainFrame.setLayout(new GridBagLayout());

		serverPanel = new ServerPanel(this);
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.FIRST_LINE_START;
		constr.weightx = 0.7;
		constr.weighty = 0.05;
		
		mainFrame.add(serverPanel.getRootComponent(), constr);
		
		articlePanel = new ArticlePanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.9;
		
		mainFrame.add(articlePanel.getRootComponent(), constr);
		
		buttonsPanel = new ButtonsPanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.05;
		
		mainFrame.add(buttonsPanel.getRootComponent(), constr);
		
		treePanel = new ContentTreePanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.gridheight = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.PAGE_START;
		constr.weightx = 0.3;
		
		mainFrame.add(treePanel.getRootComponent(), constr);
		
		authorPanel = new AuthorPanel(this);
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.CENTER;
		
		mainFrame.add(authorPanel.getRootComponent(), constr);
		
		enableComponent(articlePanel.getRootComponent(), false);
		enableComponent(treePanel.getRootComponent(), false);
		enableComponent(buttonsPanel.getRootComponent(), false);
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
