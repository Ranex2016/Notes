package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;

    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //Отключение ЭкшнБара
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Присваеваем значения полям
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    //После нажатия на кнопку..
    public void onClickSaveNote(View view) {
        //Получение данных с полей
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();

        //Для его нахождения priority нужно найти id выбраного радио батона и получить значение
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        //Находим радиоБатон по его id
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        //Проверяем что поля заполнены..
        if (isFilled(title,description)){
            //Добавление данных в ContentValues для последущего добавление в БД
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek + 1);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);

            //Добавление данных в БД
            database.insert(NotesContract.NotesEntry.TABLE_NAME,null,contentValues);

            //Запускаем активность с заметками
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, R.string.warning_fill_fileds, Toast.LENGTH_SHORT).show();
        }





    }
    private boolean isFilled(String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}
