package com.instagram.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.instagram.instagram.adapter.HomeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String nomeUsuario;

    private ArrayList<ParseObject> lista;
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuario);

        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("username");

        toolbar = findViewById(R.id.id_toolbar);
        toolbar.setTitle(nomeUsuario);
        toolbar.setTitleTextColor(getResources().getColor(R.color.preto));
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);

        lista = new ArrayList<>();
        listView = findViewById(R.id.lista_feed);
        adapter = new HomeAdapter(getApplicationContext(), lista);
        listView.setAdapter(adapter);

        getPostagens();
    }

    private void getPostagens() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("usuario", nomeUsuario);
        query.orderByAscending("creatdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                lista.clear();

                if(e==null) {

                    if(objects != null) {
                        for(ParseObject object : objects) {
                            lista.add(object);
                        }

                        adapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar o feed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
