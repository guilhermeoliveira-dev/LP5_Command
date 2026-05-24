package org.example.log;

import java.util.ArrayList;
import java.util.List;

public class MockLogger extends PrintLogger {

    private static final ILogger instance = new MockLogger();

    public static ILogger get(){
        return instance;
    }

    protected MockLogger(){
        super();
    }

    private final List<String> logs = new ArrayList<>();

    @Override
    public void log(LogType type, String text) {
        super.log(type, text);
        logs.add(logFormat(type, text));
    }

    public boolean containsLog(String text){
        for(String log : logs){
            if(log.contains(text)){
                return true;
            }
        }
        return false;
    }
}
