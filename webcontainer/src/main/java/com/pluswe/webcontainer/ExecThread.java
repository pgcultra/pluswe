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
		write2();
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
			System.out.println("    result start");
			System.out.println(new String(buffer.array()));
			System.out.println("    result end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void write(){
		String newData = "HTTP/1.1 200 OK\r\n\r\nNew String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.clear();
		buf.put(newData.getBytes());
		while(buf.hasRemaining()) {
			try {
				sc.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void write2(){
		String newData = "HTTP/1.1 200 OK\r\n\r\nNew String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.wrap(newData.getBytes());
		try {
			sc.write(buf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
