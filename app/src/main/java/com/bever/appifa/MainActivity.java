package com.bever.appifa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseHelper myDb;

    EditText editFirstName, editLastName, editEmail;
    Button addButton;
    Button boutonChangerActivite;
    EditText coordinateXtext;
    EditText coordinateYtext;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Double> coordinatesX = new ArrayList<>();
    private ArrayList<Double> coordinatesY = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: stared");
        myDb = new DatabaseHelper(this);

        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        addButton = findViewById(R.id.addButton);
        boutonChangerActivite = findViewById(R.id.buttonChangeActivite);
        coordinateXtext = findViewById(R.id.coordinateXtext);
        coordinateYtext = findViewById(R.id.coordinateYtext);

        addData();
        viewAllData();
        initRecyclerView();
        changeActivity();
    }

    public void changeActivity(){
        boutonChangerActivite.setOnClickListener((View v) -> {
            Intent intent = new Intent(this,mapActivity.class);
            intent.putExtra("coordinatesX",  coordinatesX);
            intent.putExtra("coordinatesY",  coordinatesY);
            startActivity(intent);
        });
    }

    public void addData(){
        addButton.setOnClickListener(
            (v) -> {
                double X = Double.parseDouble(coordinateXtext.getText().toString());
                double Y = Double.parseDouble(coordinateYtext.getText().toString());
                boolean isInserted = myDb.insertData(editFirstName.getText().toString(),
                    editLastName.getText().toString(),
                    X,
                    Y);

                Log.d(TAG, "addData: coordinateX" + X);
                Log.d(TAG, "addData: coordinateX" + Y);

                if(isInserted){
                    showToast("Success: Data inserted ! ");
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

       //StringBuffer strbuffer= new StringBuffer();
       while (res.moveToNext()) {
           mNames.add(res.getString(1) + " " + res.getString(2) + " (" + res.getDouble(3) + ", " + res.getDouble(4) + ")" );
           coordinatesX.add(res.getDouble(3));
           coordinatesY.add(res.getDouble(4));
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
