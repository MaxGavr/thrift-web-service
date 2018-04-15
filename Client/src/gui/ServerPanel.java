package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;



public class ServerPanel {

	private AppWindow parentWindow;
	
	private JPanel serverPanel;

	private JFormattedTextField ipField;
	private JTextField portField;
	private JButton connectButton;
	private JLabel statusLabel;

	
	public ServerPanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;
		
		initializeControls();
		createActionListeners();
	}

	
	private void createActionListeners() {
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!parentWindow.getController().isConnected()) {
					connectToServer();
				} else {
					disconnectFromServer();
				}
			}
		});
	}


	private void connectToServer() {
		// TODO: validate fields
		String host = ipField.getText();
		// TODO: add number MaskFormatter for port field
		int port = Integer.parseInt(portField.getText());
		
		parentWindow.getController().connect(host, port);
	
		if (parentWindow.getController().isConnected()) {
			ipField.setEnabled(false);
			portField.setEnabled(false);
			
			connectButton.setText("Disconnect");
			
			parentWindow.onConnectToServer();
		}
	}
	
	protected void disconnectFromServer() {
		parentWindow.getController().disconnect();
		
		ipField.setEnabled(true);
		portField.setEnabled(true);
		connectButton.setText("Connect");
		
		parentWindow.onDisconnectFromServer();
	}


	private void initializeControls() {
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
		
		try {
			MaskFormatter ipFormatter = new MaskFormatter("###.###.###.###");
			ipField = new JFormattedTextField(ipFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
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
	
	public JComponent getRootComponent() {
		return serverPanel;
	}

}
