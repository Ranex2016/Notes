package com.example.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Класс помогающий рабете с базой данных, наследуемся от "SQLiteOpenHelper"
//и реализуем его методы и добавить конструктор с параметром
public class NotesDBHelper extends SQLiteOpenHelper {
    //Создаем поля с именем базы данных и версией
    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;

    //В конструктор будем передавать только Context, по этому остальные поля удаляем..
    public NotesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    //метод для создания таблицы в БД, принимает БД
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Выполняем запрос в БД
        sqLiteDatabase.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    @Override
    //..для обновления БД
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Если наша база данных обновилась то нужно удалить старую и создать новую
        sqLiteDatabase.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
