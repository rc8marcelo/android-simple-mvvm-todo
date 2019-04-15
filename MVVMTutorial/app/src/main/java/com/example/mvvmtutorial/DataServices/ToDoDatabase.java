package com.example.mvvmtutorial.DataServices;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mvvmtutorial.Daos.ToDoDao;
import com.example.mvvmtutorial.Models.ToDo;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ToDo.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    private static ToDoDatabase instance; // To create a singleton

    public abstract ToDoDao toDoDao(); //To access the DAO

    //Synchronized  = only 1 thread can access this at a time
    public static synchronized ToDoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ToDoDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //Used to populate database with dummy data
                    .build();
        }
        return instance;
    }

    //Dummy Data implementation
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ToDoDao toDoDao;

        private PopulateDbAsyncTask(ToDoDatabase db) {
            toDoDao = db.toDoDao();
        }

        @Override
        protected Void doInBackground(Void...voids) {
//            toDoDao.insert(new ToDo("Do this 1", "Because it is needed.", 1));
//            toDoDao.insert(new ToDo("Do this 2", "Because it is needed..", 2));
//            toDoDao.insert(new ToDo("Do this 3", "Because it is needed...", 3));
            return null;
        }
    }
}
