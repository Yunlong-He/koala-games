package com.koala.log;

import java.io.PrintStream;
import java.util.Date;

public class LogAgent {
	//���󼶱�Ķ���
	public static final int DEBUG = 0;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;

	//������ļ����ǿ���̨
	public static final int APPENDFILE = 1;
	public static final int CONSOLE = 0;

//	public Boolean enabled;

    public void debug(String msg) {
    	if (level <= DEBUG)
    		writeLog("DEBUG",msg);
    }
    public void info(String msg) {
    	if (level <= INFO)
	    	writeLog("INFO",msg);
    }
    public void warn(String msg) {
    	if (level <= WARN)
	    	writeLog("WARN",msg);
    }
    public void error(String msg) {
    	writeLog("ERROR",msg);
    }

    public void log(String msg) {
    	switch(level) {
    		case DEBUG:
    			writeLog("DEBUG",msg);
    			break;
    		case INFO:
    			writeLog("INFO",msg);
    			break;
    		case WARN:
    			writeLog("WARN",msg);
    			break;
    		case ERROR:
    			writeLog("ERROR",msg);
    			break;
    		default:
    			break;
    	}
    }

	LogAgent(String name) {
		this.name = name;
		this.level = DEBUG;
		this.outputType = CONSOLE;
		this.out = System.out;
	}

	int level;			//����ļ���
	String name;
    int outputType;
    PrintStream out; 		//���
    int maxSize;		//���������ļ�������ļ���������޶Ⱦ�ת��

    protected void writeLog(String level, String msg) {
    	if (out == null) return;
        long dt = (new Date()).getTime();
        String th = Thread.currentThread().getName();

        out.println(dt + "\t" + th + "\t" + level + "\t" + msg);

    }

//    protected String Format = "%date%\t%thread%\t%delegatee%\t%level%\n%msg%\n";
}
