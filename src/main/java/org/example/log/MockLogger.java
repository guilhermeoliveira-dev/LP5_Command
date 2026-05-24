package org.example.log;

import java.util.ArrayList;
import java.util.List;

public class MockLogger implements ILogger {

    private final List<String> logs = new ArrayList<>();

    @Override
    public void log(LogType type, String text) {
        String formatedLog = logFormat(type, text);
        logs.add(formatedLog);
        System.out.println(formatedLog);
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
