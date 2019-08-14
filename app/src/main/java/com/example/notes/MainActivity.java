package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes =findViewById(R.id.recycleViewNotes);
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

        NotesAdapter adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new GridLayoutManager(this,3));
        recyclerViewNotes.setAdapter(adapter);
    }
}
