package org.example.log;

import java.util.ArrayList;
import java.util.List;

public class MockLogger extends PrintLogger {

    private static final MockLogger instance = new MockLogger();

    public static MockLogger get(){
        return instance;
    }

    protected MockLogger(){
        super();
    }

    private final List<String> logs = new ArrayList<>();
    private List<String> lastInsertedData = new ArrayList<>();

    @Override
    public void log(LogType type, String text) {
        super.log(type, text);
        logs.add(logFormat(type, text));

        if (type == LogType.SQL && text.toUpperCase().startsWith("INSERT INTO")) {
            extractAndStoreValues(text);
        }
    }

    public boolean containsLog(String text){
        for(String log : logs){
            if(log.contains(text)){
                return true;
            }
        }
        return false;
    }

    public List<String> getLastInsertedData() {
        return lastInsertedData;
    }

    private void extractAndStoreValues(String query) {
        int valuesIndex = query.toUpperCase().indexOf("VALUES");
        if (valuesIndex != -1) {
            int startBracket = query.indexOf("(", valuesIndex);
            int endBracket = query.indexOf(")", startBracket);
            if (startBracket != -1 && endBracket != -1) {
                String rawValues = query.substring(startBracket + 1, endBracket);
                lastInsertedData = new ArrayList<>(List.of(rawValues.replace("'", "").split(",\\s*")));
            }
        }
    }
}