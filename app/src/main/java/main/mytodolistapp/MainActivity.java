package main.mytodolistapp;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import main.mydolistapp.R;

public class MainActivity extends AppCompatActivity {



    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper= new DbHelper(this);

        lstTask = (ListView) findViewById(R.id.lstTask);
        loadTaskList();

    }

    private  void loadTaskList()
    {
        ArrayList<String> tasklist = dbHelper.getTaskList();
        if(mAdapter==null)
        {
            mAdapter= new ArrayAdapter<String>(this,R.layout.row,R.id.text_title,tasklist);
            lstTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(tasklist);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        //change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_action_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Add new Task")
                        .setMessage("what do you want  to do next ")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("cancel",null)
                        .create();
                alertDialog.show();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    public  void deleteTask(View view)
    {
        View parent = (View)view.getParent();
        TextView taskTextView =(TextView)findViewById(R.id.text_title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }



}
