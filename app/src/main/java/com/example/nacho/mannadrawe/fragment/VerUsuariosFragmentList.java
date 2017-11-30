package com.example.nacho.mannadrawe.fragment;

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
import android.widget.Toast;

import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.activity.EditUsuarioActivity;
import com.example.nacho.mannadrawe.adapter.VerUsuariosAdapter;
import com.example.nacho.mannadrawe.crud.CrudUsuarios;
import com.example.nacho.mannadrawe.pojos.Usuario;
import com.example.nacho.mannadrawe.proveedorDeContenido.Contrato;
import com.example.nacho.mannadrawe.aplication.AppController;

public class VerUsuariosFragmentList extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    VerUsuariosAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    ActionMode mActionMode;
    View viewselect;
    boolean permisoAdministrador;

    public VerUsuariosFragmentList() {
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
            Toast.makeText(getContext(), getResources().getString(R.string.toast_permiso_admin),
                    Toast.LENGTH_SHORT).show();
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

                if (permisoAdministrador()) {
                    mActionMode = getActivity().startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    viewselect = view;
                    mActionMode.setTitle(getResources().getText(R.string.app_name));
                    mActionMode.setSubtitle("Empleado elegido");

                    view.setBackgroundColor
                            (ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
                return true;
            }
        });
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            menu.add(Menu.NONE, R.integer.indice_icono_borrar_usuario, Menu.NONE, R.string.string_borrar)
                    .setIcon(R.drawable.ic_delete)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

            menu.add(Menu.NONE, R.integer.indice_icono_editar_usuario, Menu.NONE, R.string.string_editar)
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
            int empleadoId;
            switch (menuItem.getItemId()) {
                case R.integer.indice_icono_borrar_usuario:
                    if (permisoAdministrador()) {
                        preguntarBorrar();
                    }

                    break;
                case R.integer.indice_icono_editar_usuario:
                    if (permisoAdministrador()) {
                        Intent intent = new Intent(getActivity(), EditUsuarioActivity.class);
                        empleadoId = (Integer) viewselect.getTag();
                        intent.putExtra(Contrato.Usuario._ID, empleadoId);
                        mActionMode.finish();
                        startActivity(intent);
                        getActivity().finish();
                    }
                    break;

            }
            return false;
        }

        private void preguntarBorrar() {

            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            final int empleId = (Integer) viewselect.getTag();
            final Usuario datosEmpleado = CrudUsuarios.readRecord(getActivity().getContentResolver()
                    , empleId);
            dialog.setTitle("Borrar");
            dialog.setMessage("¿ Desea borrar al usuario " + datosEmpleado.getNombre() + " ?");
            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int ordenId = (Integer) viewselect.getTag();
                    AppController empleado = (AppController) getActivity().getApplication();
                    if (empleado.getCodigo() != datosEmpleado.getCodigo()) {
                        CrudUsuarios.deleteUsuarioConBitacora(getActivity().getContentResolver(), empleId);
                        mActionMode.finish();
                    } else {
                        Toast.makeText(getContext(),
                                "No se puede borrar a usuario con sesión activa", Toast.LENGTH_SHORT).show();
                    }
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

        View v = inflater.inflate(R.layout.fragment_usuario_list, container, false);

        mAdapter = new VerUsuariosAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] campos = {
                Contrato.Usuario._ID,
                Contrato.Usuario.CODIGO_USUARIO,
                Contrato.Usuario.NOMBRE_USUARIO,
                Contrato.Usuario.ADMIN_USUARIO,
        };

        Uri baseUri = Contrato.Usuario.CONTENT_URI;
        String selection = null;

        return new CursorLoader(getActivity(), baseUri, campos, selection, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Uri laUriBase = Contrato.Usuario.CONTENT_URI;
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

}
