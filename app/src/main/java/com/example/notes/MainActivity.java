package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Отключение ЭкшнБара
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }


        recyclerViewNotes =findViewById(R.id.recycleViewNotes);

        //Объявляем адаптер заметок и передаём в него список заметок
        adapter = new NotesAdapter(notes);
        //Говорим как хотим видеть отображение заметок
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        //Установка адаптера для RecyclerView
        recyclerViewNotes.setAdapter(adapter);

        //Устанавливаем слушатель событий(который сами сделали) у адаптера
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                //выведем сообщение
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                //удалим позицию
                remove(position);
            }
        });

        //Настраиваем свайпы для удаления элементов.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        //Применение itemTouchHelper для нашего ресайклВью
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    //Метод удаления элементов по позиции
    private void remove(int position){
        int id = notes.get(position).getId();
        //Адаптер обрати внимание что данные изменились!
        adapter.notifyDataSetChanged();
    }

    //Метод для создания новой заметки
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }


}
