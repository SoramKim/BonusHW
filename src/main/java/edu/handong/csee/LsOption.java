package edu.handong.csee;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class LsOption {
	
	private boolean allFile;
	private boolean directory;
	private boolean size;
	private boolean reverse;
	private boolean time;
	private boolean help;

	public void run(String[] args) {
		Options options = new Options();
		createOptions(options);

		String path = System.getProperty("user.dir");
		File currentDirectory= new File(path);
		File[] allFiles=currentDirectory.listFiles();
		ArrayList<String> fileNames= new ArrayList<String>();
		
		if(parseOptions(options, args)) {
			if (help){
				printHelp(options);
				return;
			}
			if(allFile) {
				for(File file:allFiles) {
					System.out.println(file.getName());
				}
			}
			if(directory) {
				for(File file:allFiles) {
					if(file.isDirectory()) {
						if(!file.getName().startsWith(".")) {
							System.out.println(file.getName());
						}
					}
				}				
			}
			
			if(size) {
				for(File file:allFiles) {
					if(!file.getName().startsWith("."))
					System.out.println(file.getName()+" "+file.length()+ " bytes");
				}
			}
			
			if(reverse) {
				for(File file:allFiles) {
					if(!file.getName().startsWith("."))
					fileNames.add(file.getName());					
				}
				Collections.sort(fileNames);
		        Collections.reverse(fileNames);
		        
		        for(String reversedFileName:fileNames) {
		        	System.out.println(reversedFileName);
		        }
			}

			if(time) {
				for(File file:allFiles) {
					String d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(file.lastModified()));
					if(!file.getName().startsWith("."))
						System.out.println(file.getName() + " " + d);
				}		
			}
			
		}
			
	}
	
	private void createOptions(Options options) {
		
		options.addOption(Option.builder("a").longOpt("allFile")
				.desc("List all files including hidden file")
				//.hasArg()
				.argName("all files")
				//.required()
				.build());
		
		options.addOption(Option.builder("d").longOpt("direcotry")
				.desc("List directories")
				//.hasArg()
				.argName("List directories")
				//.required()
				.build());

		options.addOption(Option.builder("s").longOpt("size")
				.desc("List file with size")
				//.hasArg()
				.argName("File size")
				//.required()
				.build());
		
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("List the Files in reverse order")
				//.hasArg()
				.argName("Reverse order")
				//.required()
				.build());
		
		
		options.addOption(Option.builder("t").longOpt("time")
				.desc("List the files with time")
				//.hasArg()
				.argName("Files with time")
				//.required()
				.build());

		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required()
				.build());
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);
			allFile= cmd.hasOption("a");
			directory = cmd.hasOption("d");
			size = cmd.hasOption("s");
			reverse = cmd.hasOption("r");
			time = cmd.hasOption("t");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
		
	}
	private void printHelp(Options options) {
				HelpFormatter formatter = new HelpFormatter();
				String header = "Ls Command Option ";
				String footer ="";
				formatter.printHelp("Java Bonus HW", header, options, footer, true);
	}
}