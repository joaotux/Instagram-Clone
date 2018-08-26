package com.instagram.instagram.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.instagram.instagram.R;
import com.instagram.instagram.adapter.HomeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;
    private ParseQuery<ParseObject> parseQuery;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*
            monta lista e adaptar
         */
        listView = view.findViewById(R.id.lista_postagens);
        postagens = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), postagens);
        listView.setAdapter(adapter);

        getRecuperaPostagens();

        return view;
    }

    private void getRecuperaPostagens() {
        //recupera as imagens das postagens
        parseQuery = ParseQuery.getQuery("Imagem");
        parseQuery.whereEqualTo("usuario", ParseUser.getCurrentUser().getUsername());
        parseQuery.orderByDescending("createdAt");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                postagens.clear();
                if(e==null) {
                    if(!objects.isEmpty()) {
                        for(ParseObject object : objects) {
                            postagens.add(object);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void atualizaPostagens() {
        getRecuperaPostagens();
    }

}
