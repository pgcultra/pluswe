package com.pluswe.webcontainer;

public class Response {
	
	private String protocol;
	private int statusCode;
	private String reasonPhrase;
	
	private String contentType;
	
	private String body;
	
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getResponseMes(){
		StringBuilder message = new StringBuilder();
		message.append(getProtocol()).append(" ")
				.append(getStatusCode()).append(" ")
				.append(getReasonPhrase()).append("\n");
		message.append("Content-type:").append(getContentType());
		message.append("\r\n\r\n");
		message.append(getBody());
		return message.toString();
	}
}
