package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;



public class AppWindow {

	private JFrame mainFrame;
	
	// server panel
	
	private JPanel serverPanel;

	private JTextField ipField;
	private JTextField portField;
	private JButton connectButton;
	private JLabel statusLabel;
	
	// article panel
	
	private JPanel articlePanel;
	
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JLabel titleLabel;
	
	private JTextArea articleContent;
	
	// buttons panel
	
	private JPanel buttonsPanel;
	
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;

	// contents tree
	
	private JTree articleTree;
	

	public AppWindow() {
		initialize();
	}
	
	public void start() {
		mainFrame.setVisible(true);
	}
	
	private void initializeServerPanel() {
		serverPanel = new JPanel(new GridBagLayout());
		
		statusLabel = new JLabel("Server status");
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 3;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.2;
		
		serverPanel.add(statusLabel, constr);
		
		JLabel ipLabel = new JLabel("Address:");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		
		serverPanel.add(ipLabel, constr);
		
		ipField = new JTextField();
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.5;
		
		serverPanel.add(ipField, constr);
		
		connectButton = new JButton("Connect");
		
		constr = new GridBagConstraints();
		constr.gridx = 2;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.3;
		
		serverPanel.add(connectButton, constr);
		
		JLabel portLabel = new JLabel("Port:");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		
		serverPanel.add(portLabel, constr);
		
		portField = new JTextField();
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		
		serverPanel.add(portField, constr);
	}
	
	private void initializeArticlePanel() { 
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
	
	private void initializeButtonsPanel() {
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

	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 450, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeServerPanel();
		initializeArticlePanel();
		initializeButtonsPanel();
		
		mainFrame.setLayout(new GridBagLayout());
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.FIRST_LINE_START;
		constr.weightx = 0.7;
		constr.weighty = 0.05;
		
		mainFrame.add(serverPanel, constr);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.9;
		
		mainFrame.add(articlePanel, constr);
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weighty = 0.05;
		
		mainFrame.add(buttonsPanel, constr);
		
		// tree
		
		articleTree = new JTree();
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.gridheight = 3;
		constr.fill = GridBagConstraints.BOTH;
		constr.anchor = GridBagConstraints.PAGE_START;
		constr.weightx = 0.3;
		
		mainFrame.add(articleTree, constr);
	}

}
