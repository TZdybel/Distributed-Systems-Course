package pl.edu.agh.sr.akka.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
