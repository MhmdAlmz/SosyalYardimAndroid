package com.muhammedalmaz.sosyalyardim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.muhammedalmaz.sosyalyardim.adaptorler.TabAdapter;
import com.muhammedalmaz.sosyalyardim.ekstralar.HesapBilgileri;
import com.muhammedalmaz.sosyalyardim.fragmentler.AnaSayfaFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.EsyaDepoFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.EsyaFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.IhtiyacSahibiFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.KullaniciFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.SubeFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.TABFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.OBISFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.TEBFragment;
import com.muhammedalmaz.sosyalyardim.fragmentler.YetkilerimFragment;

public class AnaEkranActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ContentFrame, new AnaSayfaFragment(), "AnaSayfaFragment")
                //.addToBackStack("AnaSayfaFragment")
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationBaslikView = navigationView.getHeaderView(0);
        TextView txtNavigationAdSoyad = navigationBaslikView.findViewById(R.id.TxtKullaniciAdiSoyadi);
        txtNavigationAdSoyad.setText(HesapBilgileri.kullaniciAdiSoyadi);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (id) {
            case R.id.NavAnaSayfa:
                transaction.replace(R.id.ContentFrame, new AnaSayfaFragment(), "AnaSayfaFragment");
                break;
            case R.id.NavSubeIslemleri:
                transaction.replace(R.id.ContentFrame, new SubeFragment(), "SubeFragment");
                break;
            case R.id.NavKullanici:
                transaction.replace(R.id.ContentFrame, new KullaniciFragment(), "KullaniciFragment");
                break;
            case R.id.NavYetkiIslemleri:
                transaction.replace(R.id.ContentFrame, new YetkilerimFragment(), "YetkilerimFragment");
                break;
            case R.id.NavIhtiyacSahibi:
                transaction.replace(R.id.ContentFrame, new IhtiyacSahibiFragment(), "IhtiyacSahibiFragment");
                break;
            case R.id.NavEsyaIslemleri:
                transaction.replace(R.id.ContentFrame, new EsyaFragment(), "EsyaFragment");
                break;
            case R.id.NavDepoIslemleri:
                transaction.replace(R.id.ContentFrame, new EsyaDepoFragment(), "EsyaDepoFragment");
                break;
            case R.id.NavCikisYap:
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().remove("KullaniciBilgileri").apply();
                startActivity(new Intent(AnaEkranActivity.this,GirisEkranActivity.class));
                break;
            default:
                transaction.replace(R.id.ContentFrame, new AnaSayfaFragment(), "AnaSayfaFragment");
                break;

        }


        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
