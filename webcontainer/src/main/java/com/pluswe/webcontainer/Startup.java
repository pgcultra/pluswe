package com.pluswe.webcontainer;

import java.io.IOException;

public class Startup {
	public static void main(String[] args) {
		try {
			new Server().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
