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

import java.util.ArrayList;

public class Intermediator extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Button elegir;
    private String user1;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediator);

        databaseReference = FirebaseDatabase.getInstance().getReference("Empleados");
        listView = (ListView) findViewById(R.id.userlist);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        elegir = (Button)findViewById(R.id.elegir);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("nombre").getValue().toString();
                String value2 = dataSnapshot.child("apellido").getValue().toString();
                String value3 = dataSnapshot.child("dni").getValue().toString();
                arrayList.add(value + " " + value2 + " DNI: "+value3);
                arrayAdapter.notifyDataSetChanged();

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

        elegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user1!=null){
                    Intent calc = new Intent(Intermediator.this,Calcularsueldo.class);
                    calc.putExtra("dni",user1);
                    startActivity(calc);
                }else Toast.makeText(Intermediator.this,"Seleccionar un empleado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
