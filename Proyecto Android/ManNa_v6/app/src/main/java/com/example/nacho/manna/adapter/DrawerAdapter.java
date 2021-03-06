package com.example.nacho.manna.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nacho.manna.fragment.DrawerFragment;
import com.example.nacho.manna.tabs.TabsDrawer;


public class DrawerAdapter extends FragmentPagerAdapter {
    public DrawerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return new DrawerFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        TabsDrawer tabsDrawer = new TabsDrawer();

        switch (position) {
            case 0:
                return tabsDrawer.getVerOrdenes();
            case 1:
                return tabsDrawer.getVerOrdenesPendientes();
            case 2:
                return tabsDrawer.getVerOrdenesProceso();
            case 3:
                return tabsDrawer.getVerOrdenesRealizado();
            case 4:
                return tabsDrawer.getVerEmpleados();
            case 5:
                return tabsDrawer.getAyuda();
            case 6:
                return tabsDrawer.getAutor();


        }
        return super.getPageTitle(position);
    }
}
