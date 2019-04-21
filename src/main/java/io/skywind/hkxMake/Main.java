package io.skywind.hkxMake;

public class Main {
	
	public static void main(String[] args) {
		
		Logger.init(args, false);
		Config config = new Config().read();
	}

	
	
}
