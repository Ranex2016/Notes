package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes =findViewById(R.id.recycleViewNotes);
        if(notes.isEmpty()){
            notes.add(new Note("Парикхмахер","сделать пирческу","Понедельник",2));
            notes.add(new Note("Магазин","купить корм коту","Вторник",3));
            notes.add(new Note("Магазин","купить новые джинсы","Среда",1));
            notes.add(new Note("Диета","начать правильно питаться","Понедельник",3));
            notes.add(new Note("Программирование","сделать новую игру","Понедельник",2));
            notes.add(new Note("Больница","сдать анализы крови","Вторник",2));
            notes.add(new Note("Стоматолог","вылечить зуб","Среда",3));
            notes.add(new Note("Программирование","сделать новую игру","Понедельник",2));
            notes.add(new Note("Больница","сдать анализы крови","Вторник",2));
            notes.add(new Note("Стоматолог","вылечить зуб","Среда",3));
        }
        

        NotesAdapter adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}
