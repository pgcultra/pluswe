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
		read2();
		write();
		try {
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read(){
		System.out.println("    read");
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] tempB = new byte[1024];
		List<Byte> httpmsg = new ArrayList<Byte>();
		try {
			int i ;
			while(sc.read(buffer) != -1){
				i = 0;
				while(buffer.hasRemaining()) {
					byte bt = buffer.get();
					httpmsg.add(bt);
					tempB[i++] = bt;
				}
				System.out.println(tempB.toString());
				buffer.clear();
				System.out.println("        start 中间值");
				System.out.println(new String(tempB));
				System.out.println("        end 中间值");
				tempB = new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("    http head");
		byte[] result = new byte[httpmsg.size()];
		int i=0;
		for(Byte bb : httpmsg){
			result[i++] = bb;
		}
		System.out.println("    result "+new String(result));
	}
	
	private void read2(){
		System.out.println("    read2");
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			sc.read(buffer);
			String message = new String(buffer.array());
			System.out.println("    result start");
			System.out.println(message);
			System.out.println("    result end");
			Request request = Request.getRequest(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
