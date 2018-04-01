package server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import handbook.Handbook;
import handbook.Handbook.Iface;



public class HandbookServer {
	
	private final int DEFAULT_PORT = 9090;

	private Handbook.Processor<Iface> processor;
	
	
	public HandbookServer(HandbookService service) {
		processor = new Handbook.Processor<Iface>(service);
	}
	
	public void start() {
		try {
			TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(DEFAULT_PORT);
			TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
			
			server.serve();
			
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
