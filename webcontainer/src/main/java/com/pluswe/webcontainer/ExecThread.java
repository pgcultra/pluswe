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
		System.out.println("Thread-" + Thread.currentThread().getId());
		try {
			read();
			write();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.clear();
		List<Byte> httpmsg = new ArrayList<Byte>();
		try {
			while (sc.read(buffer) > 0) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					byte bt = buffer.get();
					httpmsg.add(bt);
				}
				// buffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] result = new byte[httpmsg.size()];
		int i = 0;
		for (Byte bb : httpmsg) {
			result[i++] = bb;
		}
		Request request = Request.getRequest(new String(result));
	}

	private void write() {
		Response response = new Response();
		response.setProtocol("HTTP/1.1");
		response.setStatusCode(200);
		response.setReasonPhrase("OK");
		response.setContentType("text/json;charset=UTF-8");
		response.setBody("{\"appId\":2155616,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150415/d8c0690aa4041614.png\",\"name\":\"photodirector\",\"packageName\":\"com.cyberlink.photodirector\"},{\"appId\":2155608,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150415/ceb318a064ed4712.png\",\"name\":\"BaiduMap\",\"packageName\":\"com.baidu.BaiduMap\"},{\"appId\":2155618,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150415/aaef52ea68458970.png\",\"name\":\"taobao\",\"packageName\":\"com.taobao.taobao\"},{\"appId\":2155975,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150525/b063ee2526d4b320.png\",\"name\":\"K客音乐\",\"packageName\":\"com.multak.LoudSpeakerKaraoke\"},{\"appId\":2155974,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150525/95655b5f7485ad9e.png\",\"name\":\"咪咕音乐\",\"packageName\":\"com.iflytek.aichang.tv\"},{\"appId\":2155625,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150420/f55c4429943f38d0.png\",\"name\":\"不一样的妈妈\",\"packageName\":\"com.tinmanarts.TheIncredibleMomTV\"},{\"appId\":2155967,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150521/6bc7e5db0173319c.png\",\"name\":\"电视淘宝\",\"packageName\":\"com.yunos.tvtaobao\"},{\"appId\":2155956,\"count\":8,\"logoAddr\":\"http://alitvappstore.cn-hangzhou.oss.aliyun-inc.com/test/img/20150519/04717ea747ba6631.png\",\"name\":\"虾米音乐\",\"packageName\":\"com.xiami.tv\"}");
		String newData = response.getResponseMes();

		ByteBuffer buf = ByteBuffer.allocate(1024000);
		buf.put(newData.getBytes());
		buf.flip();
		while (buf.hasRemaining()) {
			try {
				sc.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
