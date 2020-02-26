package com.example.recibodesueldo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Intermediator2 extends AppCompatActivity {
    private String dni, user1;
    private DatabaseReference databaseReference;
    private Button elegir;
    private int i;

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediator2);
        Intent intent = getIntent();
        dni = intent.getStringExtra("dni");
        databaseReference = FirebaseDatabase.getInstance().getReference("Horas y valores");
        listView = findViewById(R.id.userlist2);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        elegir = findViewById(R.id.elegir2);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //String value = dataSnapshot.child("nombre").getValue().toString();
                ///String value2 = dataSnapshot.child("apellido").getValue().toString();
                String value3 = dataSnapshot.child("dni").getValue().toString();
                String value1 = dataSnapshot.child("time").getValue().toString();
                if (value3.equals(dni)) {
                    arrayList.add(" DNI: " + value3 + " time: " + value1);
                    arrayAdapter.notifyDataSetChanged();
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String user = (String) listView.getItemAtPosition(position);

                        user1 = user.replaceAll("\\D+","");

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
