package com.pluswe.webcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Request {
	private String method;// 请求方法
	private String protocol;// 协议版本
	private String requestURL;
	private String requestURI;// 请求的URI地址 在HTTP请求的第一行的请求方法后面
	private String host;// 请求的主机信息
	private String Connection;// Http请求连接状态信息 对应HTTP请求中的Connection
	private String agent;// 代理，用来标识代理的浏览器信息 ,对应HTTP请求中的User-Agent:
	private String language;// 对应Accept-Language
	private String encoding;// 请求的编码方式 对应HTTP请求中的Accept-Encoding
	private String charset;// 请求的字符编码 对应HTTP请求中的Accept-Charset
	private String accept;// 对应HTTP请求中的Accept;

	private String body;
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getConnection() {
		return Connection;
	}

	public void setConnection(String connection) {
		Connection = connection;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static Request getRequest(String message) throws IOException {
		Request request = new Request();
		BufferedReader br = new BufferedReader(new StringReader(message));
		String str;
		boolean isBody = false;
		while((str = br.readLine()) != null){
			if (str.startsWith("GET")) {
				String method = "Get";
				request.setMethod(method);
				
				int index = str.indexOf("HTTP");
				System.out.println("index--->" + index);
				String uri = str.substring(3 + 1, index - 1);// 用index-1可以去掉连接中的空格
				System.out.println("uri--->" + uri);
				request.setRequestURI(uri);
				
				String protocol = str.substring(index);
				System.out.println("protocol---->" + protocol);
				request.setProtocol(protocol);
			} else if (str.startsWith("POST")) {
				String method = "POST";
				request.setMethod(method);
				
				int index = str.indexOf("HTTP");
				System.out.println("index--->" + index);
				String uri = str.substring(3 + 1, index - 1);// 用index-1可以去掉连接中的空格
				System.out.println("uri--->" + uri);
				request.setRequestURI(uri);
				
				String protocol = str.substring(index);
				System.out.println("protocol---->" + protocol);
				request.setProtocol(protocol);
				
			} else if (str.startsWith("Accept:")) {
				String accept = str.substring("Accept:".length() + 1);
				System.out.println("accept--->" + accept);
				request.setAccept(accept);
				
			} else if (str.startsWith("User-Agent:")) {
				String agent = str.substring("User-Agent:".length() + 1);
				System.out.println("agent--->" + agent);
				request.setAgent(agent);
				
			} else if (str.startsWith("Host:")) {
				String host = str.substring("Host:".length() + 1);
				System.out.println("host--->" + host);
				request.setHost(host);
				
			} else if (str.startsWith("Accept-Language:")) {
				String language = str.substring("Accept-Language:".length() + 1);
				System.out.println("language--->" + language);
				request.setLanguage(language);
				
			} else if (str.startsWith("Accept-Charset:")) {
				String charset = str.substring("Accept-Charset:".length() + 1);
				System.out.println("charset--->" + charset);
				request.setCharset(charset);
			} else if (str.startsWith("Accept-Encoding:")) {
				String encoding = str.substring("Accept-Encoding:".length() + 1);
				System.out.println("encoding--->" + encoding);
				request.setEncoding(encoding);
				
			} else if (str.startsWith("Connection:")) {
				String connection = str.substring("Connection:".length() + 1);
				System.out.println("connection--->" + connection);
				request.setConnection(connection);
			} else if("".equals(str)){ // Head与body以空行分隔
				isBody = true;
			} else if(isBody){
				request.setBody(str);
			}
		}
		br.close();
		return request;
	}
}
