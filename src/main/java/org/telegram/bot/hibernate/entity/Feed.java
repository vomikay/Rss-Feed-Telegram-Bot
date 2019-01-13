package org.telegram.bot.hibernate.entity;

import javax.persistence.*;
import java.net.URL;
import java.sql.Timestamp;

@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    @Column(name = "url")
    private URL url;

    @Column(name = "title")
    private String title;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Feed() {
    }

    public Feed(Subscriber subscriber, URL url, String title, Timestamp lastUpdate) {
        this.subscriber = subscriber;
        this.url = url;
        this.title = title;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
