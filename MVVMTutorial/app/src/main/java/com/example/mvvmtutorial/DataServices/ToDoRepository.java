package com.example.mvvmtutorial.DataServices;

import java.util.List;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mvvmtutorial.Daos.ToDoDao;
import com.example.mvvmtutorial.Models.ToDo;

import androidx.lifecycle.LiveData;

public class ToDoRepository {
    private ToDoDao toDoDao;
    private LiveData<List<ToDo>> allToDos;

    public ToDoRepository(Application application) {
        ToDoDatabase database = ToDoDatabase.getInstance(application);
        toDoDao = database.toDoDao();
        allToDos = toDoDao.getAllToDos();
    }

    public void insert(ToDo todo) {
        new InsertToDoAsyncTask(toDoDao).execute(todo);
    }

    public void update(ToDo todo) {
        new UpdateToDoAsyncTask(toDoDao).execute(todo);
    }

    public void delete(ToDo todo) {
        new DeleteToDoAsyncTask(toDoDao).execute(todo);
    }

    public void deleteAllToDos() {
        new DeleteAllToDoAsyncTask(toDoDao).execute();
    }

    public LiveData<List<ToDo>> getAllToDos() {
        return allToDos;
    }

    private static class InsertToDoAsyncTask extends AsyncTask<ToDo, Void, Void> {
        private ToDoDao toDoDao;

        private InsertToDoAsyncTask(ToDoDao toDoDao) {
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            toDoDao.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateToDoAsyncTask extends AsyncTask<ToDo, Void, Void> {
        private ToDoDao toDoDao;

        private UpdateToDoAsyncTask(ToDoDao toDoDao) {
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            toDoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteToDoAsyncTask extends AsyncTask<ToDo, Void, Void> {
        private ToDoDao toDoDao;

        private DeleteToDoAsyncTask(ToDoDao toDoDao) {
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            toDoDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllToDoAsyncTask extends AsyncTask<Void, Void, Void> {
        private ToDoDao toDoDao;

        private DeleteAllToDoAsyncTask(ToDoDao toDoDao) {
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            toDoDao.deleteAllToDos();
            return null;
        }
    }
}
