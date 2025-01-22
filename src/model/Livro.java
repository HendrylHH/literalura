package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Livro {
    private String title;
    private List<String> authors;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("authors")
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
