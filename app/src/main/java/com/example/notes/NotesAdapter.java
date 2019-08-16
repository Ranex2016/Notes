package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private ArrayList<Note> notes;

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        //Получаем поля заметки из массива и назначаим значения полям шаблона Holder
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(note.getDayOfWeek());

        //Устанавливаем цвета в зависимости от приоритета взятого из заметки
        int colorID;
        int priority = note.getPriority();
        switch (priority){
            case 1:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        //После получения цвета colorID устанавливаем цвет поля заголовка заметки
        holder.textViewTitle.setBackgroundColor(colorID);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
