package com.example.mvvmtutorial.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "todo_table")//Responsible for creating all the sqlite-related stuff at runtime.
public class ToDo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;

    public ToDo(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //Setter for ID because ID is automatically generated
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
