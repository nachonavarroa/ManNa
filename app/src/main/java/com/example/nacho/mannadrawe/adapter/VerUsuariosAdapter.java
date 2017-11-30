package com.example.nacho.mannadrawe.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.nacho.mannadrawe.R;
import com.example.nacho.mannadrawe.proveedorDeContenido.Contrato;


public class VerUsuariosAdapter extends CursorAdapter{

    public VerUsuariosAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_usuario, parent, false);
        bindView(v, context, cursor);

        return v;
    }

    @Override

    public void bindView(View view, Context context, Cursor cursor) {

        int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Orden._ID));
        int codigoEmpleado = cursor.getInt(cursor.getColumnIndex(Contrato.Usuario.CODIGO_USUARIO));
        String nombreEmpleado = cursor.getString(cursor.getColumnIndex(Contrato.Usuario.NOMBRE_USUARIO));
        String admin = cursor.getString(cursor.getColumnIndex(Contrato.Usuario.ADMIN_USUARIO));
        String empl="Usuario: ";

        if(admin.equals("Si")) admin ="Usuario administrador";
        else admin="";

//        TextView textViewCodigoEmpleado = (TextView) view.findViewById(R.id.textView_item_empleado_codigo);
//        textViewCodigoEmpleado.setText(Contrato.Empleado._codigoEmpleado +": "+codEmplstring);

        TextView textViewNombreEmpleado = (TextView) view.findViewById(R.id.textView_item_empleado_nombre);
        textViewNombreEmpleado.setText(empl+nombreEmpleado);

        TextView textViewAdminEmpleado = (TextView) view.findViewById(R.id.textView_item_empleado_admin);
        textViewAdminEmpleado.setText(admin);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(nombreEmpleado); //Genera un color según el nombre

        int tamañoNombre = nombreEmpleado.length();
        int primerasLetrasNombre= 3;
        if(tamañoNombre == 1)primerasLetrasNombre = 1;
        if(tamañoNombre == 2)primerasLetrasNombre = 2;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(nombreEmpleado.substring(0,primerasLetrasNombre), color);

        ImageView image = (ImageView) view.findViewById(R.id.image_view_item_empleado);
        image.setImageDrawable(drawable);

        view.setTag(ID);



    }
}
