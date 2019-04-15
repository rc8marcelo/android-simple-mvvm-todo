package com.example.mvvmtutorial.ViewModels;

import android.app.Application;

import com.example.mvvmtutorial.Models.ToDo;
import com.example.mvvmtutorial.DataServices.ToDoRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepository repository;
    private LiveData<List<ToDo>> allToDos;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoRepository(application);
        allToDos = repository.getAllToDos();
    }

    public void insert(ToDo todo) {
        repository.insert(todo);
    }

    public void update(ToDo todo) {
        repository.update(todo);
    }

    public void delete(ToDo todo) {
        repository.delete(todo);
    }

    public void deleteAllToDo() {
        repository.deleteAllToDos();
    }

    public LiveData<List<ToDo>> getAllToDos() {
        return allToDos;
    }

}
