package com.gdunn.owner.todoornottodo;

import java.util.Date;

public class ListItem {
    private int id;
    private String content;
    private int listIdFK;
    private int status;
    private Date createdDate;


    public Date getCreatedDate() {
        return createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getListIdFK() {
        return listIdFK;
    }

    public void setListIdFK(int listIdFK) {
        this.listIdFK = listIdFK;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
