package com.example.mvvmtutorial.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mvvmtutorial.R;

public class AddEditToDoActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.mvvmtutorial.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.mvvmtutorial.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvmtutorial.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.mvvmtutorial.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Get intent info and details from Main Activity for edits
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit ToDo");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }
        else
            setTitle("Add ToDo");
    }

    private void saveToDo() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        //validation
        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this,"Please insert a title and description.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id  = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data); //send data back
        finish(); //close activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_todo:
                saveToDo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
