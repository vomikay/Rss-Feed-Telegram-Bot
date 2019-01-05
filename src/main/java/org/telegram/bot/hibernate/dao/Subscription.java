package org.telegram.bot.hibernate.dao;

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

    @Column(name = "chat", nullable = false)
    private Long chat;

    @Column(name = "url", nullable = false)
    private URL url;

    @Column(name = "lastupdate", nullable = false)
    private Timestamp lastUpdate;

    private Subscription() {
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
