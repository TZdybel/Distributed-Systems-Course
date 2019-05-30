package pl.edu.agh.sr.akka.model;

public class SearchableBook extends Book {
    private String database;

    public SearchableBook(String title, String database) {
        super(title);
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
