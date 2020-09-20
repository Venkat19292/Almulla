package com.exchange.almulla.ui.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exchange.almulla.AddToDoActivity;
import com.exchange.almulla.R;
import com.exchange.almulla.adapter.ToDoPOJO;
import com.exchange.almulla.adapter.TodoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ToDoFragment extends Fragment {
    private RecyclerView rec_todo;
    ArrayList<ToDoPOJO> toList = new ArrayList<>();
    private FloatingActionButton fab_add;
    private int LAUNCH_SECOND_ACTIVITY = 1;
    private TodoAdapter adapter;
    private int adapterPosition=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        initControlls(view);
        return view;
    }

    private void initControlls(View view) {
        rec_todo = view.findViewById(R.id.rec_todo);
        fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTask();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rec_todo.setLayoutManager(linearLayoutManager);
        adapter = new TodoAdapter(getContext(), toList);
        rec_todo.setAdapter(adapter);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int adapterPosition= viewHolder.getAdapterPosition();
                showAlertOption(adapterPosition);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed))
                        .addActionIcon(R.drawable.ic_delete_black_24dp)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rec_todo);

        populateDummyData();
    }

    //add dummy data
    private void populateDummyData() {

        ToDoPOJO obj = new ToDoPOJO();
        obj.setmTitle("Pay Bill");
        obj.setMdate("17/3/2020 9:10");
        ToDoPOJO obj_1 = new ToDoPOJO();
        obj_1.setmTitle("Pay InternetBill");
        obj_1.setMdate("14/7/2020 11:1");
        ToDoPOJO obj_2 = new ToDoPOJO();
        obj_2.setMdate("12/2/2020 10:1");
        obj_2.setmTitle("Buy milk");
        toList.add(obj);
        toList.add(obj_1);
        toList.add(obj_2);

    }

    //open activity task
    private void openAddTask() {
        Intent i = new Intent(getContext(), AddToDoActivity.class);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }

    //add data to arraylist from onActivityResult
    private void addTask(String title, String data) {
        ToDoPOJO obj = new ToDoPOJO();
        obj.setMdate(data);
        obj.setmTitle(title);
        toList.add(obj);
        adapter.notifyDataSetChanged();
    }

    private void showAlertOption(final int adapterPosition){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });

        alertDialogBuilder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        toList.remove(adapterPosition);
                        adapter.notifyItemRemoved(adapterPosition);
                    }
                });

        alertDialogBuilder.setTitle("Delete?");
        alertDialogBuilder.setMessage("Are you sure you want to delete this task?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {

            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String dateTime = data.getStringExtra("dateTime");
                addTask(title, dateTime);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
