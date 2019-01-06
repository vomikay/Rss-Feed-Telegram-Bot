package org.telegram.bot.hibernate.model;

import javax.persistence.*;
import java.net.URL;
import java.sql.Timestamp;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat")
    private Long chat;

    @Column(name = "url")
    private URL url;

    @Column(name = "lastupdate")
    private Timestamp lastUpdate;

    public Subscription() {
    }

    public Subscription(Long chat, URL url, Timestamp lastUpdate) {
        this.chat = chat;
        this.url = url;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat() {
        return chat;
    }

    public void setChat(Long chat) {
        this.chat = chat;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
