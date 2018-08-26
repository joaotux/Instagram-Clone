package com.instagram.instagram.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.instagram.instagram.FeedUsuarioActivity;
import com.instagram.instagram.R;
import com.instagram.instagram.adapter.UsuariosAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseUser> usuarios;
    private UsuariosAdapter adapter;
    private ParseQuery<ParseUser> parseQuery;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        listView = view.findViewById(R.id.lista_usuarios);
        usuarios = new ArrayList<>();
        adapter = new UsuariosAdapter(getActivity(), usuarios);
        listView.setAdapter(adapter);

        listaUsuarios();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseUser user = new ParseUser();
                user = usuarios.get(i);

                Intent intent = new Intent(getActivity(), FeedUsuarioActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
            }
        });

        return view;
    }

    private void listaUsuarios() {
        parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.orderByAscending("username");

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                usuarios.clear();

                if(e==null) {
                    if(!objects.isEmpty()) {
                        for(ParseUser parseUser : objects) {
                            usuarios.add(parseUser);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Erro ao tentar listar usuários!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
