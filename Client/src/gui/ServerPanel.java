package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;



public class ServerPanel {

	private AppWindow parentWindow;
	
	private JPanel serverPanel;

	private JFormattedTextField ipField;
	private JFormattedTextField portField;
	private JButton connectButton;
	private JLabel statusLabel;

	
	public ServerPanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;
		
		initializeControls();
		createActionListeners();
	}

	
	public JComponent getRootComponent() {
		return serverPanel;
	}


	private void connectToServer() {
		if (!validateAddress()) {
			parentWindow.showAlertMessage("Enter valid host address and port.");
			return;
		}
		
		String host = ipField.getText().replace(" ", "");
		int port = Integer.parseInt(portField.getText().replace(" ", ""));
		
		parentWindow.getController().connect(host, port);
	
		if (parentWindow.getController().isConnected()) {
			ipField.setEnabled(false);
			portField.setEnabled(false);
			
			connectButton.setText("Disconnect");
			
			parentWindow.onConnectToServer();
		}
	}
	
	public void disconnectFromServer() {
		parentWindow.getController().disconnect();
		
		ipField.setEnabled(true);
		portField.setEnabled(true);
		connectButton.setText("Connect");
		
		parentWindow.onDisconnectFromServer();
	}

	
	private boolean validateAddress() {
		try {
			for (String token : ipField.getText().split(".")) {
				Integer.parseInt(token.replace(" ", ""));
			}
			Integer.parseInt(portField.getText().replace(" ", ""));
			
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	private void initializeControls() {
		serverPanel = new JPanel(new GridBagLayout());
		serverPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		statusLabel = new JLabel("Server status");
		
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridwidth = 3;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.weightx = 0.2;
		constr.insets = new Insets(0, 0, 3, 0);
		
		serverPanel.add(statusLabel, constr);
		
		JLabel ipLabel = new JLabel("Address:");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.insets = new Insets(0, 0, 0, 5);
		
		serverPanel.add(ipLabel, constr);
		
		try {
			MaskFormatter ipFormatter = new MaskFormatter("###.###.###.###");
			ipField = new JFormattedTextField(ipFormatter);
			ipField.setFocusLostBehavior(JFormattedTextField.PERSIST);
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
		constr.anchor = GridBagConstraints.CENTER;
		constr.insets = new Insets(0, 10, 0, 0);
		constr.weightx = 0.3;
		
		serverPanel.add(connectButton, constr);
		
		JLabel portLabel = new JLabel("Port:");
		
		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		constr.insets = new Insets(0, 0, 0, 5);
		
		serverPanel.add(portLabel, constr);
		
		try {
			MaskFormatter portFormatter = new MaskFormatter("#####");
			portField = new JFormattedTextField(portFormatter);
			portField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.anchor = GridBagConstraints.LINE_START;
		
		serverPanel.add(portField, constr);
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
}
