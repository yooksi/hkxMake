package io.skywind.hkxMake;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	
	private static Path actorsPath = Paths.get("meshes" + File.separator + "actors");
	
	public static void main(String[] args) {
		
		Logger.init(args, false);
		Config config = new Config().read();
		
		java.io.File hkxCmd = new java.io.File("hkxcmd.exe");
		if (hkxCmd == null || !hkxCmd.exists()) {
			System.out.println("Unable to locate hkxCMD");
			return;
		}

		Path animPath = config.getAnimationsDir();
		Path outputPath = config.getOuputDir();
		
		Logger.print("Please input the name of the actor you want to process:");
		String actor = UserInput.read();
		Logger.print("");
		
		Path inputPath = animPath.resolve(actorsPath.resolve(actor));
		
		Logger.print(Logger.Level.DEBUG, "Finding actor %s in %s", actor, inputPath.toString());
		
		//hkxcmd.exe -v:XML -i <input_file> -o <output_file>
		String[] cmdArgs = { "convert", "-v:XML -i", inputPath.toString(), "-o", outputPath.toString()};
		
		Logger.print("Converting hkx files to xml:\n");
		Execute.start(hkxCmd.getName(), true, true, cmdArgs);
		Logger.print("\nWe're all done, check your output directory");
	}
	
}
