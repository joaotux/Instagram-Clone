package com.instagram.instagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.instagram.instagram.R;
import com.parse.ParseUser;

import java.util.ArrayList;

public class UsuariosAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(@NonNull Context context, @NonNull ArrayList<ParseUser> objects) {
        super(context, 0, objects);

        this.context = context;
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_usuarios, parent, false);
        }

        if(!usuarios.isEmpty()) {
            TextView textView = view.findViewById(R.id.tv_usuario);

            ParseUser user = usuarios.get(position);

            textView.setText(user.getUsername());
        }

        return view;
    }
}
