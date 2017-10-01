package com.koala.log;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * log configure tool, users should get log agent through instance of this class
 * 
 * LogConfigurator read configur from a properties with content such as:
 * =========== log.properties =================
 * 		LogAgents=WebServerLoger,DatabaseLoger
 * 		
 *		# Web Server Logger
 * 		WebServerLoger.level=Warn 
 * 		WebServerLoger.output.type=AppendFile
 * 		WebServerLoger.output.filename=log_database.txt
 * 		WebServerLoger.output.maxsize=100K
 * 		
 *		# Database Logger
 * 		DatabaseLoger.level=Warn 
 * 		DatabaseLoger.output.type=Console
 */
public class LogConfigurator {
	protected static Vector agents = new Vector();
	protected static boolean printTrace = true;
//	protected String logPath;
	
	/**
	 *
	 */
	public static void loadConfigure(String configFile) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(configFile));
		}
		catch (FileNotFoundException fnfe) {
			writeTrace("error in reading log configuration file:\n\t can't find log configure file! No log delegatee created!");
			return;
		}
		catch (IOException ioe) {
			writeTrace("error in reading log configuration file:\n\t can't find log configure file! No log delegatee created!");
			ioe.printStackTrace();
			return;			
		}
		
		String agentNames = (String) props.get("LogAgents");
		StringTokenizer tokens = new StringTokenizer(agentNames,",");
		
		while (tokens.hasMoreTokens()){
			String agentName = tokens.nextToken();
			int level = LogAgent.DEBUG;
			int type = LogAgent.CONSOLE;
			int maxSize = 0;
			String file=null,tmpStr=null;
			
			tmpStr = (String) props.get(agentName + ".level");
			if (tmpStr != null) {
				tmpStr = tmpStr.trim();
				if (tmpStr.equalsIgnoreCase("DEBUG")) {
					level = LogAgent.DEBUG;
				}
				else if (tmpStr.equalsIgnoreCase("INFO")) {
					level = LogAgent.INFO;
				}
				else if (tmpStr.equalsIgnoreCase("WARN")) {
					level = LogAgent.WARN;
				}
				else if (tmpStr.equalsIgnoreCase("ERROR")) {
					level = LogAgent.ERROR;
				}
			}				
			tmpStr = (String) props.get(agentName + ".output.type");
			if (tmpStr != null) {
				tmpStr = tmpStr.trim();
				if (tmpStr.equalsIgnoreCase("CONSOLE")) {
					type = LogAgent.CONSOLE;
				}
				else if (tmpStr.equalsIgnoreCase("APPENDFILE")) {
					type = LogAgent.APPENDFILE;
				}
			}	
			tmpStr = (String) props.get(agentName + ".output.filename");
			if (tmpStr != null) {
				file = tmpStr.trim();
			}
			tmpStr = (String) props.get(agentName + ".output.maxSize");
			if (tmpStr != null) {
				tmpStr = tmpStr.trim();
				if (tmpStr.length() < 2) {
					writeTrace("error in reading log configuration file:\n\t invalid max filesize format");
					return;
				}
				
				int unit = 1;
				if (tmpStr.endsWith("K")) {
					unit = 1024;
					tmpStr = tmpStr.substring(0,tmpStr.length()-1);
				}
				else if (tmpStr.endsWith("M")) {
					unit = 1024 * 1024;
					tmpStr = tmpStr.substring(0,tmpStr.length()-1);
				}
				try {
					int size = Integer.parseInt(tmpStr);
					maxSize = size * unit;
				}
				catch (NumberFormatException nfe) {
					writeTrace("error in reading log configuration file:\n\t invalid max filesize format");
					return;
				}
            }
            
            LogAgent agent = new LogAgent(agentName);
            agent.level = level;
            agent.outputType = type;
            if (type == LogAgent.CONSOLE) {
            	agent.out = System.out;
            }
            else {
            	try {
            		agent.out = new PrintStream(new FileOutputStream(file));
		            writeTrace("agent output setted to file " + file);
            	}
            	catch (FileNotFoundException e) {
            		writeTrace("error in reading log configuration file:\n\t can't open file " + file);
            		return;
            	}
            	catch (SecurityException  e) {
            		writeTrace("error in reading log configuration file:\n\t file " + file + " is read-only");
            		return;
            	}
            	agent.maxSize = maxSize;
            }
            
            agents.addElement(agent);
            writeTrace("agent created: {" + agentName + "," + agent.level + "," + agent.outputType + "}");
			
		}
		
	}
	
	public static LogAgent getAgent(String agentName) {
		for (int i = 0; i < agents.size(); i++) {
			LogAgent agent = (LogAgent) agents.elementAt(i);
	        writeTrace("agent name is " + agent.name);
			if (agent.name.equals(agentName)) 
				return agent;
		}
        writeTrace("no agent matching found, return default agent");
		return getDefaultAgent();
	}
	
	public static LogAgent getDefaultAgent() {
		return new LogAgent("DefaultLogAgent");
	}
	
	protected static void writeTrace(String info) {
		if (printTrace)
			System.out.println(info);
	}
}
