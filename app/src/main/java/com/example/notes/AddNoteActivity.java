package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        //Присваеваем значения полям
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
    }

    //После нажатия на кнопку..
    public void onClickSaveNote(View view) {
        //Получение данных с полей
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dayOfWeek = spinnerDaysOfWeek.getSelectedItem().toString();

        //Для его нахождения priority нужно найти id выбраного радио батона и получить значение
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        //Находим радиоБатон по его id
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        //Используя полученые данные, создаем новую заметку
        Note note = new Note(title,description,dayOfWeek,priority);
        //Добавляем заметку в массив заметок
        MainActivity.notes.add(note);
        //Запускаем активность с заметками
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
