package com.example.recibodesueldo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button MostrarA,IngresarU,CalcularS;
    private boolean es;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MostrarA = (Button) findViewById(R.id.Mostrar);
        IngresarU = (Button)findViewById(R.id.Ingresar);
        CalcularS = (Button)findViewById(R.id.Calcular);
        final Intent calc = new Intent(MainActivity.this,Intermediator.class);

        IngresarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ing = new Intent(MainActivity.this,Ingresarusuarios.class);
                startActivity(ing);
            }
        });
        CalcularS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                es = true;
                calc.putExtra("es",es);
                startActivity(calc);
            }
        });
        MostrarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                es = false;
                calc.putExtra("es",es);
                startActivity(calc);
            }
        });
    }
}
