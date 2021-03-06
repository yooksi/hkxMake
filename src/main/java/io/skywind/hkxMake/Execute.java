package io.skywind.hkxMake;

import java.io.IOException;
import java.util.Arrays;

public class Execute {

	/**
	 *  This method blocks until thread execution until input data is available<br>
	 *  User must press Enter to continue running the application
	 */
	public static void pause() {
		UserInput.waitForEnter();
	}
	
	/**
	 * Use {@code ProcessBuilder} to start a new application or script process.<br>
	 * <i>Note that if you start a console application this way it will run hidden</i>
	 * 
	 * @param process path to the application or script we want to start
	 * @param wait pause current thread and wait for process to terminate
	 * @param log redirect process input stream to console
	 * @return instance of the process started or {@code null} if an error occurred
	 */
	public static Process start(String process, boolean wait, boolean log, String...args) {
		
		try {			
			Logger.print(Logger.Level.DEBUG, "Starting new process %s" +
					((wait) ? " and waiting for it to terminate" : ""), process);
			
			java.util.ArrayList<String> cmd = new java.util.ArrayList<String>();
			cmd.add(process); cmd.addAll(Arrays.asList(args));
			
			Logger.debug("Running with arguments: " + String.join(" ", args));
			
			ProcessBuilder builder = new ProcessBuilder(cmd);
			if (log == true) {
				builder.inheritIO();
			}
			Process proc = builder.start();
			if (wait == true) proc.waitFor();
			return proc;
		} 
		catch (IOException | InterruptedException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to start new process %s", process);
			return null;
		}
	}
}
