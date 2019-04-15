package com.example.mvvmtutorial.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvmtutorial.R;
import com.example.mvvmtutorial.Models.ToDo;
import com.example.mvvmtutorial.Adapters.ToDoAdapter;
import com.example.mvvmtutorial.ViewModels.ToDoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TODO_REQUEST = 1;
    public static final int EDIT_TODO_REQUEST = 2;
    private ToDoViewModel toDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddToDo = findViewById(R.id.button_add_note);
        buttonAddToDo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v ) {
                Intent intent = new Intent(MainActivity.this, AddEditToDoActivity.class);
                startActivityForResult(intent, ADD_TODO_REQUEST); //Similar to C# Winforms DialogResult
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ToDoAdapter adapter = new ToDoAdapter();
        recyclerView.setAdapter(adapter);

        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.getAllToDos().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                adapter.submitList(toDos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //To detect swipe left or right
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                toDoViewModel.delete(adapter.getToDoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"ToDo Deleted.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ToDoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ToDo todo) {
                Intent intent = new Intent(MainActivity.this, AddEditToDoActivity.class);
                intent.putExtra(AddEditToDoActivity.EXTRA_ID, todo.getId());
                intent.putExtra(AddEditToDoActivity.EXTRA_TITLE, todo.getTitle());
                intent.putExtra(AddEditToDoActivity.EXTRA_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddEditToDoActivity.EXTRA_PRIORITY, todo.getPriority());
                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditToDoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditToDoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditToDoActivity.EXTRA_PRIORITY, 1);

            ToDo todo = new ToDo(title, description, priority);
            toDoViewModel.insert(todo);

            Toast.makeText(this, "ToDo Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id =  data.getIntExtra(AddEditToDoActivity.EXTRA_ID, -1);
            
            if(id == -1) {
                //Something went wrong
                Toast.makeText(this, "ToDo can't be updated.", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditToDoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditToDoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditToDoActivity.EXTRA_PRIORITY, 1);
            
            ToDo todo = new ToDo(title, description, priority);
            todo.setId(id);
            toDoViewModel.update(todo);

            Toast.makeText(this, "Note Updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ToDo not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_notes:
                toDoViewModel.deleteAllToDo();
                Toast.makeText(this, "All ToDos deleted.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


/*
    For this project, MVVM is used with SQLite using Room and LiveData
    Room is used for ORM similar to Entity Framework
    LiveData is used to observe the data for changes so that the VM can react to the change which prompts the view to change

    What to create for this architecture:
    - Entity which is similar to a POCO class
    - DAO interface or abstract class which defines the methods used by Room
        - Contains your Basic CRUD Method signatures
        - Can contain SQLite Queries and return whichever you wanna pass/receive
    - A Database Class that extends from RoomDatabase (Similar to EF DBContext Classes_
    - A repository, a normal Java Class that acts as a gateway for external data sources such as Database or Web services (API)
    - A ViewModel where we store the state of the View
    - A RecyclerView Adapter to connect UI to Activity


 */