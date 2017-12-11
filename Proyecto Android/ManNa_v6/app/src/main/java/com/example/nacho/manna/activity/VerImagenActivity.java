package com.example.nacho.manna.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.example.nacho.manna.R;
import com.example.nacho.manna.auxiliar.Utilidades;
import com.example.nacho.manna.proveedorDeContenido.Contrato;

import java.io.FileNotFoundException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class VerImagenActivity extends AppCompatActivity {
    long ordenId;
    ImageView imageView;
    PhotoViewAttacher  mAttacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        //ActionBar----------------------------
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getResources().getText(R.string.app_name));

        //----------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivityDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
                startActivity(intent);
                finish();

            }
        });
    //-------------------

        ordenId = this.getIntent().getExtras().getLong(Contrato.Orden._ID);
        imageView = findViewById(R.id.imageViewOrden);
        actionBar.setSubtitle("img_" + ordenId + ".jpg");


        try {
            Utilidades.loadImageFromStorage(getApplicationContext(), "img_" + ordenId + ".jpg", imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mAttacher = new PhotoViewAttacher(imageView);

    }
    //----------------------------------------------------------------------------------------------


}
