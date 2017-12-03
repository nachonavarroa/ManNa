package com.example.nacho.manna.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.nacho.manna.activity.EditOrdenActivity;
import com.example.nacho.manna.crud.CrudUsuarios;
import com.example.nacho.manna.pojos.Usuario;
import com.example.nacho.manna.proveedorDeContenido.Contrato;
import com.example.nacho.manna.R;
import com.example.nacho.manna.crud.CrudOrdenes;
import com.example.nacho.manna.adapter.VerOrdenesAdapter;
import com.example.nacho.manna.aplication.AppController;
import com.example.nacho.manna.sync.Sincronizacion;

public class VerOrdenesFragmentList extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {


    VerOrdenesAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    ActionMode mActionMode;
    View viewselect;
    boolean permisoAdministrador;

    public VerOrdenesFragmentList() {
    }
    private boolean permisoAdministrador() {
        permisoAdministrador = false;
        AppController empleado = (AppController) getActivity().getApplication();
        Usuario empleadoAdmin = CrudUsuarios.login(getActivity().getContentResolver(),
                String.valueOf(empleado.getCodigo()), empleado.getNombre());
        String admin = empleadoAdmin.getAdmin();
        if (admin.equals(getResources().getString(R.string.string_si))) {
            permisoAdministrador = true;

        } else {

            permisoAdministrador = false;

        }
        return permisoAdministrador;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mCallbacks = this;
        getLoaderManager().initLoader(0, null, mCallbacks);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mActionMode != null) {
                    return false;
                }
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);
                viewselect = view;
                mActionMode.setTitle(getResources().getText(R.string.app_name));
                mActionMode.setSubtitle("Orden elegida");

                view.setBackgroundColor
                        (ContextCompat.getColor(getContext(), R.color.colorPrimary));

                return true;
            }
        });
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            if(permisoAdministrador()) {
                menu.add(Menu.NONE, R.integer.indice_icono_borrar_orden, Menu.NONE, R.string.string_borrar)
                        .setIcon(R.drawable.ic_delete)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            }
            menu.add(Menu.NONE, R.integer.indice_icono_editar_orden, Menu.NONE, R.string.string_editar)
                    .setIcon(R.drawable.ic_edit)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.integer.indice_icono_borrar_orden:
                    preguntarBorrar();
                    break;

                case R.integer.indice_icono_editar_orden:
                    long ordenId = (long) viewselect.getTag();
                    Intent intent = new Intent(getActivity(), EditOrdenActivity.class);
                    ordenId = (Long) viewselect.getTag();
                    intent.putExtra(Contrato.Orden._ID, ordenId);
                    startActivity(intent);
                    getActivity().finish();
                    break;


            }
            return false;
        }

        private void preguntarBorrar() {

            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            long ordenId = (Long) viewselect.getTag();
            dialog.setTitle("Borrar");
            dialog.setMessage("¿Desea borrar esta orden?");
            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long ordenId = (Long) viewselect.getTag();
                    CrudOrdenes.deleteOrdenConBitacora(getActivity().getContentResolver()
                            , ordenId,getContext());

                    mActionMode.finish();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }


        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            viewselect.setBackgroundColor
                    (ContextCompat.getColor(getContext(), R.color.colorFondo));

            mActionMode = null;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_orden_list, container, false);

        mAdapter = new VerOrdenesAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] campos = {
                Contrato.Orden._ID,
                Contrato.Orden.CODIGO_EMPLEADO,
                Contrato.Orden.FECHA,
                Contrato.Orden.PRIORIDAD,
                Contrato.Orden.SINTOMA,
                Contrato.Orden.UBICACION,
                Contrato.Orden.DESCRIPCION,
                Contrato.Orden.ESTADO,

        };

        Uri baseUri = Contrato.Orden.CONTENT_URI;
        String selection = null;

        return new CursorLoader(getActivity(), baseUri, campos, selection, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Uri laUriBase = Contrato.Orden.CONTENT_URI;
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }


}
