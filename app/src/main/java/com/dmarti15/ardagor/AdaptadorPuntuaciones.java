package com.dmarti15.ardagor;

/**
 * Created by d.martin on 02/03/2015.
 */

import java.util.Vector;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

public class AdaptadorPuntuaciones extends BaseAdapter {
    private final Activity actividad;
    private final Vector<String> lista;

    public AdaptadorPuntuaciones(Activity actividad, Vector<String> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista, null,                                                                                                                             true);
        TextView titulo =(TextView)view.findViewById(R.id.titulo);
        TextView subtitulo =(TextView)view.findViewById(R.id.subtitulo);
        titulo.setText(lista.elementAt(position));
        ImageView imageView=(ImageView)view.findViewById(R.id.icono);
        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.number_1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.number_2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.number_3);
                break;
            case 3:
                imageView.setImageResource(R.drawable.number_4);
                break;
            case 4:
                imageView.setImageResource(R.drawable.number_5);
                break;
            case 5:
                imageView.setImageResource(R.drawable.number_6);
                break;
            case 6:
                imageView.setImageResource(R.drawable.number_7);
                break;
            case 7:
                imageView.setImageResource(R.drawable.number_8);
                break;
            case 8:
                imageView.setImageResource(R.drawable.number_9);
                break;
            default:
                imageView.setImageResource(R.drawable.number_0);
                break;
        }
        return view;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int arg0) {
        return lista.elementAt(arg0);
    }

    public long getItemId(int position) {
        return position;
    }
}

