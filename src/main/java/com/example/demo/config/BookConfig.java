package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@ConfigurationProperties(prefix = "book")
@Configuration
public class BookConfig {

    String title;
    String author;

    @Override
    public String toString() {
        return "BookConfig{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
    //@ConstructorBinding
//    public BookConfig(String title, String author) {
//        this.title = title;
//        this.author = author;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
