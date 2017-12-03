package com.example.nacho.manna.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nacho.manna.R;


public class AyudaFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int mPosition;
    private OnFragmentInteractionListener mListener;
    public AyudaFragment() {

    }

    public static AyudaFragment newInstance(int position) {
        AyudaFragment fragment = new AyudaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition= getArguments().getInt(ARG_POSITION);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_ayuda, container, false);

        TextView textViewfragmentoTitulo =
                view.findViewById(R.id.textView_fragmen_ayuda_titulo);

        TextView textViewfragmentoContenido =
                view.findViewById(R.id.textView_fragmen_ayuda_contenido);

        switch (mPosition){
            case 0:
                textViewfragmentoTitulo.setText(getResources().getString(R.string
                        .subtititulo_menu));
                textViewfragmentoContenido.setText(getResources()
                        .getString(R.string.contenido_ayuda_menu));
                break;
            case 1:
                textViewfragmentoTitulo.setText(getResources().getString(R.string
                        .subtitulo_datos_empleado));
                textViewfragmentoContenido.setText(getResources()
                        .getString(R.string.contenido_ayuda_datos_usuario));
                break;
            case 2:
                textViewfragmentoTitulo.setText(getResources().getString(R.string.generar_ordende_trabajo));
                textViewfragmentoContenido.setText(getResources()
                        .getString(R.string.contenido_ayuda_orden_trabajo));
                break;
            case 3:
                textViewfragmentoTitulo.setText(getResources().getString(R.string.codigo));
                textViewfragmentoContenido.setText(getResources()
                        .getString(R.string.contenido_ayuda_codigo));
                break;
        }

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
