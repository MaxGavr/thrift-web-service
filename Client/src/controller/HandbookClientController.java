package controller;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import gui.AppWindow;
import handbook.Handbook;



public class HandbookClientController {
	
	AppWindow view;
	
	TTransport transport;
	Handbook.Client client;

	
	public HandbookClientController() {}

	
	public void connect(String host, int port) {
		// close previous connection
		disconnect();
		
		try {
			transport = new TFramedTransport(new TSocket(host, port));
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			client = new Handbook.Client(protocol);

		} catch (TTransportException ex) {
			ex.printStackTrace();
			disconnect();
			return;
		}
	}
	
	public void disconnect() {
		if (isConnected()) {
			transport.close();
		}
	}
	
	public boolean isConnected() {
		return (transport != null && transport.isOpen());
	}
	
	public void setView(AppWindow view) {
		this.view = view;
	}
}
