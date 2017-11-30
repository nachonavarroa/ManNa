package com.example.nacho.mannadrawe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nacho.mannadrawe.fragment.AyudaFragment;

import com.example.nacho.mannadrawe.tabs.TabsAyuda;

public class AyudaAdapter extends FragmentPagerAdapter {



    public AyudaAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return  new AyudaFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        TabsAyuda tabsDatosEmpleado = new TabsAyuda ();

        switch (position){
            case 0:
                return tabsDatosEmpleado.getTabMenu() ;
            case 1:
                return tabsDatosEmpleado.getTabDatosEmpleado() ;
            case 2:
                return tabsDatosEmpleado.getTabGenerarOrden() ;
            case 3:
                return tabsDatosEmpleado.getTabCodigoOrden() ;

        }

        return super.getPageTitle(position);
    }
}
