package com.example.notes;

import android.provider.BaseColumns;

//Этот класс предназначен для хранение названий полей таблицы
public class NotesContract {
    //Класс должен содержать вложенные классы,
    //столько сколько у нас будет таблиц
    //Для заметок будем использовать только одну таблицу
    //Все классы которые хранят таблицы заканчиваются словом ...Entry
    //..и наследуем его от класса BaseColumns, что бы показать что это таблица в базе данных
    public static final class NotesEntry implements BaseColumns{
        //Создаем константы заголовков таблицы и первым делом название таблицы
        //Колонка _ID добавляется автоматически, так как мы наследуемся от BaseColumns
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PRIORITY = "priority";

        //названия констант тип данных для таблицы
        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        //создаем команду для создания таблицы
        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID +" "+ TYPE_INTEGER + " " +"PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
                " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_DAY_OF_WEEK +
                " " + TYPE_INTEGER + ", " + COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";
        //..команда для удаления таблицы
        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
