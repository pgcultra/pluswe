package com.pluswe.webcontainer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
	
	public void start() throws IOException{
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(8080));
		int i = 0 ;
		while(true) {
			SocketChannel sc = server.accept();
			System.out.println("request "+ ++i);
			new Thread(new ExecThread(sc)).start();
		}
	}
}
