package org.example.model;

import java.time.LocalDate;
import java.util.List;

public record User(String name, int balance, LocalDate admissionDate){

    public List<String> toStringList(){
        return List.of(
                name,
                Integer.toString(balance),
                admissionDate.toString());
    }
}
