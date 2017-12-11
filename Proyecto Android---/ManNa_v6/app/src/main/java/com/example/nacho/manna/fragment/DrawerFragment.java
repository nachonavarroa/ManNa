package com.example.nacho.manna.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.nacho.manna.R;
import com.example.nacho.manna.activity.AyudaActivity;

public class DrawerFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int mPosition;
    private OnFragmentInteractionListener mListener;

    public DrawerFragment() {

    }

    public static DrawerFragment newInstance(int position) {
        DrawerFragment fragment = new DrawerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;

        switch (mPosition) {
            case 0:
                view = inflater.inflate(R.layout.fragment_drawer_ver_ordenes,container,false);
                VerOrdenesFragmentList verOrdenesFragmentList = new VerOrdenesFragmentList();
                FragmentTransaction transactionOrdenes = getFragmentManager().beginTransaction();
                transactionOrdenes.add(R.id.frameLayout_fragment_drawer_ver_ordenes,verOrdenesFragmentList);
                transactionOrdenes.commit();
                break;

            case 1:
                view = inflater.inflate(R.layout.fragment_drawer_ver_usuarios,container,false);
                VerUsuariosFragmentList verEmpleadosFragmentList = new VerUsuariosFragmentList();
                FragmentTransaction transactionEmpleados = getFragmentManager().beginTransaction();
                transactionEmpleados.add(R.id.frameLayout_fragment_drawer_ver_empleados, verEmpleadosFragmentList);
                transactionEmpleados.commit();
                break;

            case 2:
                view = inflater.inflate(R.layout.fragment_drawer_ayuda, container, false);
                TextView textViewfragmentoTituloAyuda =
                        view.findViewById(R.id.textView_fragmen_drawer_titulo_ayuda);
                ImageButton imageButtonAyuda =
                        view.findViewById(R.id.imageButton_fragment_ayuda);
                textViewfragmentoTituloAyuda.setText(getResources().getString(R.string.string_ayuda));
                imageButtonAyuda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AyudaActivity.class);
                        startActivity(intent);
                    }
                });

                break;

            case 3:
                view = inflater.inflate(R.layout.activity_autor, container, false);
                FragmentTransaction transactionAutor = getFragmentManager().beginTransaction();
                transactionAutor.commit();
                break;
        }


        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException (context.toString ()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach ();
        mListener = null;
    }
*/

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
