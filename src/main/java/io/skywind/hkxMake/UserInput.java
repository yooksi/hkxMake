package io.skywind.hkxMake;

import java.io.IOException;
import java.util.Scanner;

/**
 * Always use this instead of System InputStream to read user input.<br>
 */
public class UserInput {
	
	private final Scanner reader = new Scanner(System.in);
	private final java.io.InputStream is = System.in;
	private static final UserInput ui = new UserInput();
	
	/** 
	 *  Close the system input stream. Note that you will not be able to read user input<br> 
	 * 	after doing this so this should only be done just before exiting the application
	 */
	public static void close() {
		ui.reader.close();
	}
	/**
	 * Read last user keyboard input <i>(safe to call each operation cycle)</i>.
	 * @return last user keyboard input
	 */
	public static String read() {
		return ui.reader.next();
	}

	/** Block thread execution until the user presses {@code enter} key */
	public static void waitForEnter() {
		/*
		 *  Do not use scanner to scan for user input, I've been getting unknown exceptions being 
		 *  thrown with no message or stack trace. It just doesn't seem to work for some reason
		 *  
		 *  Using direct System InputStream seems like the best idea, and although it only works
		 *  for ENTER at least it works and won't crash
		 */
		try {
			Logger.print("Press Enter to continue...");
			ui.is.read();
		}
		catch(IOException e) {
			Logger.error("Something went wrong while reading user input", e);
			System.exit(1);
		}
	}
}
