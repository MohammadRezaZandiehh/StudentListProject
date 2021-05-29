package com.example.studentlistproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.example.studentlistproject.R;
import com.example.studentlistproject.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    List<Student> studentList;
    Context context;

    public StudentAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    public void addStudent(Student student) {
        this.studentList.add(0, student);
        notifyItemInserted(0);
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.textViewFullName.setText(student.getFirst_name() + " " + student.getLast_name() + "");
        holder.textViewStudentCourse.setText(student.getCourse() + "");
        holder.textViewStudentScore.setText(student.getScore() + "");
        holder.textViewFirstCharacter.setText(student.getFirst_name().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFullName;
        private TextView textViewStudentCourse;
        private TextView textViewStudentScore;
        private TextView textViewFirstCharacter;
        private ImageView imageViewDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStudentCourse = itemView.findViewById(R.id.textViewStudentCourse);
            textViewFullName = itemView.findViewById(R.id.textViewfullName);
            textViewStudentScore = itemView.findViewById(R.id.textViewStudentScore);
            textViewFirstCharacter = itemView.findViewById(R.id.textViewFirstCharacter);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Delete :)", Toast.LENGTH_SHORT).show();
                    delete(getAdapterPosition());
                }
            });
        }

        public void delete(int position) {

            try {
                studentList.remove(position);
                notifyItemRemoved(position);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
    }
}

