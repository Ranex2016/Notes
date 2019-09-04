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
    //Создаем помошник для работы с базой данных
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Отключение ЭкшнБара
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Присваеваем значения
        dbHelper = new NotesDBHelper(this);
        //Создаем БД, получаем её из NotesDBHelper
        database = dbHelper.getWritableDatabase();

        recyclerViewNotes =findViewById(R.id.recycleViewNotes);

        //Вытаскиваем ВСЕ данные из БД
        getData();

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
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME,where,whereArgs);
        //Удалим элемент из списка заметок
        getData();
        //Адаптер обрати внимание что данные изменились!
        adapter.notifyDataSetChanged();
    }

    //Метод для создания новой заметки
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData(){
        //Чистим массив от данных
        notes.clear();
        //Вытаскиваем ВСЕ данные из БД, передаем имя таблицы, а остальные значение null
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,null,null,null,NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK,null);
        //Получение значений
        while (cursor.moveToNext()){//Переводит курсор с значения -1 к позиции 0 и т.д.
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));

            Note note = new Note(id,title,description,dayOfWeek,priority);
            notes.add(note);
        }
        //Обязательно закрывать курсор
        cursor.close();
    }
}
