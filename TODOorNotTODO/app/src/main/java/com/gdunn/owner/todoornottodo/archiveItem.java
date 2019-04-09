package com.gdunn.owner.todoornottodo;

public class archiveItem {
    private String PostedDate;
    private String ListTitle;
    private String archiveContent;
    private int completed;

    public archiveItem(){}

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }

    public String getListTitle() {
        return ListTitle;
    }

    public void setListTitle(String listTitle) {
        ListTitle = listTitle;
    }

    public String getArchiveContent() {
        return archiveContent;
    }

    public void setArchiveContent(String content) {
        this.archiveContent = content;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
