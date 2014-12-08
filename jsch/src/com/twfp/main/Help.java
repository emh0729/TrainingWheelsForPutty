package com.twfp.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Help {
	public static final String fileLoc = "descr\\";
	Map commands;
	public Help()
	{
		 loadCmdDescrs();
	}
	
	public void loadCmdDescrs()
	{
		try{
			Vector<String> commandNameVector = new Vector<String>();
			commandNameVector.add("cd");
			commandNameVector.add("cp");
			commandNameVector.add("ls");
			commandNameVector.add("mkdir");
			commandNameVector.add("rm");
			commandNameVector.add("tar");
			
			commands = new HashMap();
					
			for (Enumeration e = commandNameVector.elements(); e.hasMoreElements();)
			{
				String commandName = (String) e.nextElement();
				Vector<String> commandInfoVector = new Vector<String>();
				
				File file = new File(fileLoc + commandName + ".txt");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					commandInfoVector.addElement(line);
				}
				fileReader.close();
	
				commands.put(commandName, commandInfoVector);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public Vector<String> keyDescr(String searchKey)
	{
		//String searchKey = "cd";
		if(commands.containsKey(searchKey)) {
			Vector<String> temp = (Vector<String>) commands.get(searchKey);
			return temp;
		}
		return null;
	}

}
