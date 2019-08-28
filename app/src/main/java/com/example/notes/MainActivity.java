package com.example.notes;

import androidx.annotation.NonNull;
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
    public static final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    //Создаем помошник для работы с базой данных
    private NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Присваеваем значения
        dbHelper = new NotesDBHelper(this);
        //Создаем БД, получаем её из NotesDBHelper
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        recyclerViewNotes =findViewById(R.id.recycleViewNotes);
//        if(notes.isEmpty()){
//            notes.add(new Note("Парикхмахер","сделать пирческу","Понедельник",2));
//            notes.add(new Note("Магазин","купить корм коту","Вторник",3));
//            notes.add(new Note("Магазин","купить новые джинсы","Среда",1));
//            notes.add(new Note("Диета","начать правильно питаться","Понедельник",3));
//            notes.add(new Note("Программирование","сделать новую игру","Понедельник",2));
//            notes.add(new Note("Больница","сдать анализы крови","Вторник",2));
//            notes.add(new Note("Стоматолог","вылечить зуб","Среда",3));
//            notes.add(new Note("Программирование","сделать новую игру","Понедельник",2));
//            notes.add(new Note("Больница","сдать анализы крови","Вторник",2));
//            notes.add(new Note("Стоматолог","вылечить зуб","Среда",3));
//            notes.add(new Note("Магазин","купить корм коту","Вторник",3));
//            notes.add(new Note("Магазин","купить новые джинсы","Среда",1));
//        }
//        //Перебор списка заметок и добавляем их в ContentValues а его уже в БД
//        for (Note note: notes){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
//            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
//        }

        //Проверяем что записи в БД добавленны
        ArrayList<Note> notesFromDB = new ArrayList<>();
        //Вытаскиваем ВСЕ данные из БД, передаем имя таблицы, а остальные значение null
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,null,null,null,null,null);
        //Получение значений
        while (cursor.moveToNext()){//Переводит курсор с значения -1 к позиции 0 и т.д.
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));

            Note note = new Note(title,description,dayOfWeek,priority);
            notesFromDB.add(note);

        }
        //Обязательно закрывать курсор
        cursor.close();

        //Объявляем адаптер заметок и передаём в него список заметок
        adapter = new NotesAdapter(notesFromDB);
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
        //Удалим элемент из списка заметок
        notes.remove(position);
        //Адаптер обрати внимание что данные изменились!
        adapter.notifyDataSetChanged();
    }

    //Метод для создания новой заметки
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}
