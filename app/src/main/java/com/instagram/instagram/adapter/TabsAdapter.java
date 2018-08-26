package com.instagram.instagram.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.instagram.instagram.R;
import com.instagram.instagram.fragment.HomeFragment;
import com.instagram.instagram.fragment.UsuariosFragment;

import java.util.HashMap;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int icons[] = {R.drawable.ic_action_home, R.drawable.ic_people};
    private int tamanhoIcon;
    private HashMap<Integer, Fragment> fragmentoCriado;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
        this.fragmentoCriado = new HashMap<>();

        //pega a dencidade da tela do dispositivo, assim podemos mudar o tamanho do icone.
        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcon = (int) (36 * escala);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0:
                fragment = new HomeFragment();
                fragmentoCriado.put(i, fragment);
                break;
            case 1:
                fragment = new UsuariosFragment();
                break;
        }
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        fragmentoCriado.remove(position);
    }

    public Fragment getFragment(Integer index) {
        return fragmentoCriado.get(index);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        //Recupera os icones de acordo com a posição
        Drawable drawable = ContextCompat.getDrawable(this.context, icons[position]);
        drawable.setBounds(0, 0, tamanhoIcon, tamanhoIcon);

        //Permite colocar imagem dentro de texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        //Classe utilizada para retornar charset
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public int getCount() {
        return icons.length;
    }
}
