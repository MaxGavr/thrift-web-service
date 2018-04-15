package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import gui.AppWindow;
import handbook.ArticleHeader;
import handbook.Author;
import handbook.Handbook;
import handbook.NoAuthorException;



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


	public List<ArticleHeader> getArticlesList() {
		List<ArticleHeader> articles = new ArrayList<>();
		
		try {
			articles = client.getArticlesHeaders();
		} catch (TException e) {
			view.showAlertMessage("An error occured while processing task!");
		}
		
		return articles;
	}

	public Author getAuthorByName(String authorName) throws NoAuthorException {
		try {
			return client.getAuthorByName(authorName);
		} catch (NoAuthorException ex) {
			throw ex;
		} catch (TException e) {
			view.showAlertMessage("An error occured while processing task!");
			return null;
		}
	}

	public int addAuthor(Author newAuthor) {
		try {
			return client.addAuthor(newAuthor);
		} catch (TException e) {
			view.showAlertMessage("An error occured while processing task!");
			return -1;
		}
	}
}
