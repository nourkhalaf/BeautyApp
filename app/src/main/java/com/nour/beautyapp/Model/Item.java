package com.nour.beautyapp.Model;

public class Item {
    private String title, details, notes ,image,itemId;

    public Item() {
    }

    public Item(String title, String details, String notes, String image, String itemId) {
        this.title = title;
        this.details = details;
        this.notes = notes;
        this.image = image;
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
