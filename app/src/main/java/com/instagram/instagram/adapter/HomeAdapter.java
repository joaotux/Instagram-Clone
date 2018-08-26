package com.instagram.instagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.instagram.instagram.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> listaPostagens;

    public HomeAdapter(@NonNull Context context, @NonNull ArrayList<ParseObject> objects) {
        super(context, 0, objects);

        this.context = context;
        this.listaPostagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_postagem, parent, false);

        }

        if(!listaPostagens.isEmpty()) {
            ImageView imageView = view.findViewById(R.id.imagem_postagem);

            //recupera os dados das postagens que vinheram do servidor
            ParseObject parseObject = listaPostagens.get(position);

            parseObject.getParseFile("imagem");

            Picasso.get()
                    .load(parseObject.getParseFile("imagem").getUrl())
                    .into(imageView);
        }

        return view;
    }
}
