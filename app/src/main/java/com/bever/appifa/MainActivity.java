package com.bever.appifa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseHelper myDb;

    EditText editFirstName, editLastName, editEmail;
    Button addButton;
    Button viewAllDataButton;

    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: stared");
        myDb = new DatabaseHelper(this);

        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        editEmail = findViewById(R.id.editTextEmail);
        addButton = findViewById(R.id.addButton);

        mNames.add("premier nom");

        addData();
        viewAllData();
        initRecyclerView();
    }

    public void addData(){
        addButton.setOnClickListener(
            (v) -> {
                boolean isInserted = myDb.insertData(editFirstName.getText().toString(),
                    editLastName.getText().toString(),
                    editLastName.getText().toString() );

                if(isInserted){
                    showToast("Success: Data inserted !");
                    viewAllData();
                } else {
                    showToast("Error: Data not inserted !");
                }
            }
        );
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void viewAllData(){
       showToast("Viewing all data");
       Cursor res = myDb.getAllData();
       if(res.getCount() == 0 ){
           showToast("No data");
           return;
       }

       StringBuffer strbuffer= new StringBuffer();
       while (res.moveToNext()) {
           mNames.add(res.getString(1) + " " + res.getString(2));
           /*strbuffer.append("ID : " + res.getString(0) + "\n");
           strbuffer.append("Firstname : " + res.getString(1) + "\n");
           strbuffer.append("Lastname : " + res.getString(2) + "\n");
           strbuffer.append("Email : " + res.getString(3) + "\n\n");*/
       }
    }

    /*
    public void showMessageWindowed(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    */

    private void showToast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
