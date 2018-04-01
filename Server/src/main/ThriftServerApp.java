package main;

import dao.DatabaseController;
import server.HandbookServer;
import server.HandbookService;

public class ThriftServerApp {

	public static void main(String[] args) {
		DatabaseController db = new DatabaseController();
		HandbookService service = new HandbookService(db);
		HandbookServer server = new HandbookServer(service);

		server.start();
	}

}
