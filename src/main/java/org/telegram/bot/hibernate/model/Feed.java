package org.telegram.bot.hibernate.model;

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

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "url")
    private URL url;

    @Column(name = "title")
    private String title;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Feed() {
    }

    public Feed(Long chatId, URL url, String title, Timestamp lastUpdate) {
        this.chatId = chatId;
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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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
