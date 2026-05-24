package org.example.log;

public enum LogType {
    SQL("SQL"),
    WARNING("WARN"),
    ERROR("ERROR"),
    SYSTEM("SYS");

    private final String label;

    LogType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
