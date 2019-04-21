package io.skywind.hkxMake;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Config {

	private static final String name = "hkxMake.ini";
	private static java.io.File instance;
	
	java.util.Map<Entry, String> entries;
	
	public enum Entry {
		
		ANIMATIONSDIR("animationsDir");
		
		private final String entry;
		
		Entry(String e) {
			entry = e;
		}
		public String get() {
			return entry;
		}
	}
	
	Config() {
		instance = new java.io.File(name);
		entries = new java.util.HashMap<Entry, String>();
	}
	
	public static void createNew() {
		if (instance.exists())
			instance.delete();
		try {
			instance.createNewFile();
			PrintWriter writer = new PrintWriter(instance);
			for (Entry e : Entry.values()) {
				writer.println(e.entry + "=");
			}
			writer.close();
		} catch (IOException e) {
			Logger.error("Unable to create new config file", e);
		}
	}
	
	public Config read() {
		try {
			FileReader reader = new FileReader(instance);
			BufferedReader br = new BufferedReader(reader);
			for (Entry e : Entry.values()) {
				
				Logger.print(Logger.Level.DEBUG, "Reading config entry: %s", e.name());
				
				String line = br.readLine();
				String[] elements = line.split("=");
				if (elements.length != 2 || !elements[0].equals(e.entry))
					Logger.error("Config entry is corrupt");
				else {
					String read = elements[1].replaceAll("\"", "");
					Logger.print(Logger.Level.DEBUG, "Read config value: %s", read);
					entries.put(e, read);
				}
			}
			return this;
		} 
		catch(IOException e) {
			Logger.error("An IO exception occured while reading config", e);
			return null;
		}
	}
	
	public String getAnimationsDir() {
		return entries.get(Entry.ANIMATIONSDIR);
	}
}
