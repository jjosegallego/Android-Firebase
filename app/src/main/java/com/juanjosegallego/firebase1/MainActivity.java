package com.juanjosegallego.firebase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText nombre,valor,numero;
    Button agregar,cambiar,buscar,eliminar,observar,limpiar;
    TextView contenido;
    Integer id=0;
    private static final String FIREBASE_URL="https://ejemplomoviles.firebaseio.com/";
    private Firebase firebasedatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        firebasedatos= new Firebase(FIREBASE_URL);

        nombre=(EditText)findViewById(R.id.nombre);
        valor=(EditText)findViewById(R.id.valor);
        numero=(EditText)findViewById(R.id.id);

        agregar=(Button)findViewById(R.id.agregar);
        cambiar=(Button)findViewById(R.id.cambiar);
        buscar=(Button)findViewById(R.id.buscar);
        eliminar=(Button)findViewById(R.id.eliminar);
        observar=(Button)findViewById(R.id.ver);
        limpiar=(Button)findViewById(R.id.limpiar);

        contenido=(TextView)findViewById(R.id.basedatos);


        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenido.setText("");
                nombre.getText().clear();
                valor.getText().clear();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Articulo Ingresado a la base de datos ", Toast.LENGTH_SHORT).show();
                String articulo = nombre.getText().toString();
                String precio = valor.getText().toString();
                Firebase firebd = firebasedatos.child("producto " + id);
                Producto producto= new Producto(String.valueOf(id),articulo,precio);
                firebd.setValue(producto);
                id++;
            }
        });


        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String articulo = nombre.getText().toString();
                String precio = valor.getText().toString();
                String codigo = numero.getText().toString();
                Toast.makeText(MainActivity.this, "Articulo "+articulo+" Actualizado", Toast.LENGTH_SHORT).show();
                Firebase firebd = firebasedatos.child("producto " + codigo);
                Map<String, Object> nuevonombre = new HashMap <String, Object>();
                nuevonombre.put("nombre",articulo);
                firebd.updateChildren(nuevonombre);
                Map<String, Object> nuevovalor = new HashMap <String, Object>();
                nuevovalor.put("valor", precio);
                firebd.updateChildren(nuevovalor);
            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = numero.getText().toString();
                final String id= "producto "+codigo;
                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(id).exists()) {
                            contenido.setText(snapshot.child(id).getValue().toString());
                        } else {
                            Toast.makeText(MainActivity.this, id + " No encontrado", Toast.LENGTH_SHORT).show();
                            contenido.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
        });


        observar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        contenido.setText(snapshot.getValue().toString());
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = numero.getText().toString();
                Firebase firebd = firebasedatos.child("producto " + codigo);
                firebd.removeValue();
            }
        });







    }
}
