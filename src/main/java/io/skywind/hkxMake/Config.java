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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Config read() {
		try {
			FileReader reader = new FileReader(instance);
			BufferedReader br = new BufferedReader(reader);
			for (Entry e : Entry.values()) {
				
				System.out.println("DEBUG: Reading config entry: " + e.name());
				
				String line = br.readLine();
				String[] elements = line.split("=");
				if (elements.length != 2 || !elements[0].equals(e.entry))
					System.out.println("Error: Config entry is corrupt");
				else {
					String read = elements[1].replaceAll("\"", "");
					System.out.println("DEBUG: Read config value: " + read);
					entries.put(e, read);
				}
			}
			return this;
		} 
		catch(IOException e) {
			System.out.println("An IO exception occured while reading config");
			e.printStackTrace();
			return null;
		}
	}
	
	public String getAnimationsDir() {
		return entries.get(Entry.ANIMATIONSDIR);
	}
}
