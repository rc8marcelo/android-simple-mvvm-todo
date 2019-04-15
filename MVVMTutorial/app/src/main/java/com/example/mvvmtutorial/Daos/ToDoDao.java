package com.example.mvvmtutorial.Daos;

import com.example.mvvmtutorial.Models.ToDo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ToDoDao {

    @Insert
    void insert(ToDo todo);

    @Update
    void update(ToDo todo);

    @Delete
    void delete(ToDo todo);

    @Query("DELETE FROM todo_table")
    void deleteAllToDos();

    @Query("SELECT * from todo_table ORDER by priority DESC")
    LiveData<List<ToDo>> getAllToDos();
}
