package com.pluswe.webcontainer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ExecThread implements Runnable {
	private SocketChannel sc;
	
	public ExecThread(SocketChannel sc) {
		super();
		this.sc = sc;
	}

	public void run() {
		System.out.println("Thread-"+Thread.currentThread().getId());
		try {
			read();
			write();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read() throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.clear();
		List<Byte> httpmsg = new ArrayList<Byte>();
		try {
			while(sc.read(buffer) > 0){
				buffer.flip();
				while(buffer.hasRemaining()) {
					byte bt = buffer.get();
					httpmsg.add(bt);
				}
//				buffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[httpmsg.size()];
		int i=0;
		for(Byte bb : httpmsg){
			result[i++] = bb;
		}
		Request request = Request.getRequest(new String(result));
	}
	
	private void write(){
		Response response = new Response();
		response.setProtocol("HTTP/1.1");
		response.setStatusCode(200);
		response.setReasonPhrase("OK");
		response.setContentType("text/html");
		response.setBody("<html><body><a href='www.baidu.com'>New String to write to file...</a></html>");
		String newData = response.getResponseMes();
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(newData.getBytes());
		buf.flip();
		while(buf.hasRemaining()) {
			try {
				sc.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
