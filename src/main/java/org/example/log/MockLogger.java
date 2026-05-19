package org.example.log;

public class MockLogger implements ILogger{

    private static final ILogger instance = new MockLogger();

    public static ILogger get(){
        return instance;
    }

    private MockLogger(){}

    @Override
    public void log(String text) {
        System.out.println(text);
    }
}
