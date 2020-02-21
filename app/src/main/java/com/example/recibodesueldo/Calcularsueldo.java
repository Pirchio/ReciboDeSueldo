package com.example.recibodesueldo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Calcularsueldo extends AppCompatActivity {
    private String snominal, sh50, sh100, sporcentaje,dni,times,ftimes;
    private TextView nominal,h50,h100,porcentaje;
    int itimes,iftimes;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcularsueldo);

        Button calc = findViewById(R.id.calcular);
        nominal = findViewById(R.id.horastxt);
        h50 = findViewById(R.id.h50txt);
        h100 = findViewById(R.id.h100txt);
        porcentaje = findViewById(R.id.conveniotxt);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        dni = getIntent().getStringExtra("dni");

        databaseReference.child("Empleados").child(dni).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                times = (String) dataSnapshot.child("times").getValue();
                itimes = Integer.valueOf(times);
                itimes++;
                ftimes = String.valueOf(itimes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snominal = nominal.getText().toString();
                sh50 = h50.getText().toString();
                sh100 = h100.getText().toString();
                sporcentaje = porcentaje.getText().toString();
                    databaseReference.child("Empleados").child(dni).child("times").setValue(ftimes);

                Map<String, Object> map = new HashMap<>();
                map.put("nominal", snominal);
                map.put("h50", sh50);
                map.put("h100", sh100);
                map.put("percent", sporcentaje);
                databaseReference.child("Horas y valores").child(dni + " " + ftimes).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Calcularsueldo.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                            Intent result = new Intent(Calcularsueldo.this, Resultado.class);
                            result.putExtra("dni", dni);
                            result.putExtra("ftimes", times);
                            startActivity(result);
                            finish();
                        } else
                            Toast.makeText(Calcularsueldo.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });


        }

            });
        }
}