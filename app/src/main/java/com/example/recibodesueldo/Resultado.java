package com.example.recibodesueldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Resultado extends AppCompatActivity {
    String dni,ftimes,nombre,apellido,hijos,profesion,sh100,sh50,snominal,spercent,sdtdeducciones;
    int ihijos,iprofesion,ih100,ih50,inominal,ipercent,isueldo,ivh100,ivtitulo;
    boolean btitulo;
    DatabaseReference databaseReference;
    TextView usuario,sueldob,h50,ch50,h100,ch100,titulo,bruto,jubilacion,cjubilacion,ley,cley,sindicato,csindicato,os,conv,cconv,deducciones,
            liquido,asignacion,adicionales,neto,bbase;
    double dvh50,dbasic,dvjubilacion,dley,dsind,dconv,dliquid,dafamiliar,dneto,dtdeducciones;
    String sdbasic,sdvjubilacion,sdley,sdsind,sdconv,sdliquid,sdafamiliar,sdneto;
    Button ready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        ready = findViewById(R.id.ready);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        usuario = findViewById(R.id.name);
        sueldob = findViewById(R.id.sueldobruto);
        h50 = findViewById(R.id.h50);
        ch50 = findViewById(R.id.ch50);
        h100 = findViewById(R.id.h100);
        ch100 = findViewById(R.id.ch100);
        bbase = findViewById(R.id.basico);
        titulo = findViewById(R.id.titulo);
        jubilacion = findViewById(R.id.jubilacion);
        cjubilacion = findViewById(R.id.pjubilacion);
        ley = findViewById(R.id.inssjyd);
        cley = findViewById(R.id.cinssjyd);
        sindicato = findViewById(R.id.sindicato);
        csindicato = findViewById(R.id.csindicato);
        os = findViewById(R.id.obrasocial);
        conv = findViewById(R.id.convenioal);
        cconv = findViewById(R.id.cconvenioal);
        deducciones = findViewById(R.id.totaldeducciones);
        liquido = findViewById(R.id.liquido);
        asignacion = findViewById(R.id.asignacion);
        adicionales = findViewById(R.id.adicionales);
        neto = findViewById(R.id.neto);

        boolean origin;
        origin = getIntent().getBooleanExtra("origin", false);
        if (origin) {
            ready.setVisibility(View.VISIBLE);
            ready.setEnabled(true);
            dni = getIntent().getStringExtra("dni");
            ftimes = getIntent().getStringExtra("ftimes");
            GetValue();
        } else {
            Toast.makeText(this, "Se sentó en el choclo a desgranar el maiz", Toast.LENGTH_SHORT).show();
            ready.setVisibility(View.INVISIBLE);
            ready.setEnabled(false);
            final String receipt = getIntent().getStringExtra("receipt");
            databaseReference.child("Horas y valores").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dni = (String) dataSnapshot.child(receipt).child("dni").getValue();
                    ftimes = (String) dataSnapshot.child(receipt).child("time").getValue();
                    GetValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void GetValue(){
        databaseReference.child("Empleados").child(dni).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombre = dataSnapshot.child("nombre").getValue().toString();
                apellido= dataSnapshot.child("apellido").getValue().toString();
                hijos= dataSnapshot.child("hijos").getValue().toString();
                profesion= dataSnapshot.child("profesion").getValue().toString();
                btitulo = (Boolean) dataSnapshot.child("titulo").getValue();

                ihijos= Integer.valueOf(hijos);
                iprofesion= Integer.valueOf(profesion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Horas y valores").child(dni+" "+ftimes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sh50 = dataSnapshot.child("h50").getValue().toString();
                sh100 = dataSnapshot.child("h100").getValue().toString();
                spercent = dataSnapshot.child("percent").getValue().toString();
                snominal = dataSnapshot.child("nominal").getValue().toString();

                ih50 = Integer.valueOf(sh50);
                ih100 = Integer.valueOf(sh100);
                ipercent= Integer.valueOf(spercent);
                inominal= Integer.valueOf(snominal);
                Settext();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void Settext(){
        String oficio;
        if (btitulo)
            ivtitulo=800;
        else ivtitulo=0;
        String stitulo = String.valueOf(ivtitulo);
        switch (iprofesion){
            case 1:isueldo=12000;
                oficio="administración";
                break;
            case 2: isueldo=11000;
                oficio="obrero";
                break;
            case 3:isueldo=10000;
                oficio="secretaría";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + iprofesion);
        }

        dvh50 = (inominal*ih50*1.5);
        ivh100 = inominal*ih100*2;
        String dsivh100=String.valueOf(ivh100);
        String dsdvh50=String.valueOf(dvh50);

        dbasic = ivtitulo+ivh100+dvh50+isueldo;
        sdbasic = String.valueOf(dbasic);

        dvjubilacion = (11*dbasic)/100;
        sdvjubilacion = String.valueOf(dvjubilacion);

        String base = String.valueOf(isueldo);

        dley= (3*dbasic)/100;
        sdley = String.valueOf(dley);

        dsind= (2*dbasic)/100;
        sdsind = String.valueOf(dsind);

        dconv=(ipercent*dbasic)/100;
        sdconv=String.valueOf(dconv);

        dtdeducciones = dvjubilacion+dley+dsind-dconv;
        sdtdeducciones = String.valueOf(dtdeducciones);

        dliquid = dbasic - dtdeducciones;
        sdliquid = String.valueOf(dliquid);

        dafamiliar = ihijos*800;
        sdafamiliar=String.valueOf(dafamiliar);

        dneto=dliquid+dafamiliar;
        sdneto=String.valueOf(dneto);

        usuario.setText(nombre+" "+apellido+" DNI:" + dni+" Oficio:"+oficio);
        ch50.setText(sh50+" horas");
        ch100.setText(sh100+" horas");

        sueldob.setText("$"+sdbasic);
        jubilacion.setText("$"+sdvjubilacion);
        cjubilacion.setText("11%");
        ley.setText("$"+sdley);
        cley.setText("3%");
        sindicato.setText("$"+sdsind);
        csindicato.setText("2%");
        os.setText("$"+sdley);
        conv.setText("$"+sdconv);
        cconv.setText(spercent+"%");
        deducciones.setText("$"+sdtdeducciones);
        liquido.setText("$"+sdliquid);
        asignacion.setText(sdafamiliar);
        adicionales.setText(sdafamiliar);
        neto.setText(sdneto);
        titulo.setText(stitulo);
        h100.setText(dsivh100);
        h50.setText(dsdvh50);
        bbase.setText(base);



    }

    public void Ready (View view){
        Map<String,Object> map = new HashMap<>();
        map.put("jubilacion",sdvjubilacion);
        map.put("ley",sdley);
        map.put("sindicato",sdsind);
        map.put("os",sdley);
        map.put("convenio",sdconv);
        map.put("porciento convenio",spercent);
        map.put("deducciones",sdtdeducciones);
        map.put("liquido",sdliquid);
        map.put("asignacion",sdafamiliar);
        map.put("adicionales",sdafamiliar);
        map.put("neto",sdneto);
        databaseReference.child("Recibos").child(dni+" "+ftimes).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Resultado.this,"Datos guardados", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Resultado.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}