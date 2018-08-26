package com.instagram.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.instagram.instagram.adapter.TabsAdapter;
import com.instagram.instagram.fragment.HomeFragment;
import com.instagram.instagram.util.SlidingTabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configurar toolbar
        toolbar = findViewById(R.id.toolbar_principal);
        toolbar.setLogo(R.drawable.instagramlogo);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.id_viewpager);
        slidingTabLayout = findViewById(R.id.id_abas);

        //configurar abas
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_layout, R.id.id_txt);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinzaEscuro));
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair:

                deslogar();

                return true;
            case R.id.menu_compartilhar:

                salvarImagem();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void salvarImagem() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //recupera local da imagem
            Uri uri = data.getData();

            try {
                //recupera imagem do local onde ela foi salva
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                //comprime no formato PNG
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, arrayOutputStream);

                //cria um array de bytes da imagem
                byte[] byteArray = arrayOutputStream.toByteArray();

                //criar nome unico para imagem
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmyyhhss");
                String nomeImagem = dateFormat.format(new Date());

                //cria um arquivo com formato proprio do parser
                ParseFile parseFile = new ParseFile(nomeImagem + "imagem.png", byteArray);

                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("usuario", ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem", parseFile);

                parseObject.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(getApplicationContext(), "Imagem postada com sucesso!", Toast.LENGTH_SHORT).show();

                            //recupera o fragmento Homefragment para atualizar com a nova postagem
                            TabsAdapter tabsNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment fragment = (HomeFragment) tabsNovo.getFragment(0);
                            fragment.atualizaPostagens();

                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao tentar postar imagem!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Não deu certo!" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void deslogar() {
        ParseUser.logOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
