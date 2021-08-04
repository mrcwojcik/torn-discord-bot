package pl.mrwojcik.tornbot.entity;
import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

public class Event {

    private Long id;
    private String title;
    private String description;
    private Instant eventDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }
}
