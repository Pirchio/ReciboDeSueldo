package com.example.recibodesueldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Ingresarusuarios extends AppCompatActivity {
    private TextView nombre, apellido, dni, hijos,dia,mes,año;
    private String name;
    private String sname;
    private String sdni;
    private String shijos;
    private String sdia,smes,saño;
    private String profesion;
    private RadioButton casado , administracion,obrero;
    private Boolean bestado,btitulo;//bestadotrue=casado else soltero
    private CheckBox titulo;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresarusuarios);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dia = (TextView) findViewById(R.id.day);
        mes = (TextView) findViewById(R.id.month);
        año = (TextView) findViewById(R.id.year);

        nombre = (TextView) findViewById(R.id.nametxt);
        apellido = (TextView) findViewById(R.id.snametxt);
        dni = (TextView) findViewById(R.id.dnitxt);
        hijos = (TextView) findViewById(R.id.hijostxt);
        Button save = (Button) findViewById(R.id.guardar);
        casado = (RadioButton) findViewById(R.id.casado);
        administracion = (RadioButton) findViewById(R.id.administracion);
        obrero = (RadioButton) findViewById(R.id.obrero);
        titulo = (CheckBox) findViewById(R.id.title);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nombre.getText().toString();
                sname = apellido.getText().toString();
                sdni = dni.getText().toString();
                shijos = hijos.getText().toString();
                sdia = dia.getText().toString();
                smes = mes.getText().toString();
                saño = año.getText().toString();

                int d = Integer.parseInt(sdia);
                int m = Integer.parseInt(smes);

                bestado = casado.isChecked();
                btitulo = titulo.isChecked();

                if (administracion.isChecked()) //1admin 2obrero 3secre
                    profesion = "1";
                else {
                    if (obrero.isChecked())
                        profesion = "2";
                    else
                        profesion = "3";
                }

                if (name.isEmpty() || sname.isEmpty() || sdni.isEmpty() || sdia.isEmpty() || smes.isEmpty() || saño.isEmpty()||shijos.isEmpty())
                    Toast.makeText(Ingresarusuarios.this, "Completá todos los campos", Toast.LENGTH_SHORT).show();
                else
                    if (saño.length()!=4)
                        Toast.makeText(Ingresarusuarios.this,"Ingresá un año válido", Toast.LENGTH_SHORT).show();
                    else
                        Guardar();
                }
        });
    }







        public void Guardar(){
           if (!(sdni.length()<7)||!(sdni.length()>8)){
               String times = "0" ;
            Map<String,Object> map = new HashMap<>();
            map.put("nombre",name);
            map.put("apellido",sname);
            map.put("dni",sdni);
            map.put("hijos",shijos);
            map.put("profesion",profesion);
            map.put("titulo",btitulo);
            map.put("times",times);
            map.put("día",sdia);
            map.put("mes",smes);
            map.put("año",saño);
            mDatabase.child("Empleados").child(sdni).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Ingresarusuarios.this,"Datos guardados", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Ingresarusuarios.this,MainActivity.class));
                        finish();
                    }
                    else Toast.makeText(Ingresarusuarios.this,"fail", Toast.LENGTH_SHORT).show();
                }
            });}
           else Toast.makeText(Ingresarusuarios.this,"Ingresá un dni válido", Toast.LENGTH_SHORT).show();
        }
}