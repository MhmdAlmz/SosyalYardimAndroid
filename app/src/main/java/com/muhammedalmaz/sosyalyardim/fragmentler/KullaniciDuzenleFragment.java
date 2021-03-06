package com.muhammedalmaz.sosyalyardim.fragmentler;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.muhammedalmaz.sosyalyardim.R;
import com.muhammedalmaz.sosyalyardim.api.APIClient;
import com.muhammedalmaz.sosyalyardim.api.APIInterface;
import com.muhammedalmaz.sosyalyardim.ekstralar.HesapBilgileri;
import com.muhammedalmaz.sosyalyardim.fonksiyonlar.DialogMesajlari;
import com.muhammedalmaz.sosyalyardim.onaylayici.Ayarlar;
import com.muhammedalmaz.sosyalyardim.onaylayici.OnaylanacakNesne;
import com.muhammedalmaz.sosyalyardim.onaylayici.Onaylayici;
import com.muhammedalmaz.sosyalyardim.onaylayici.OnaylayiciTip;
import com.muhammedalmaz.sosyalyardim.pojo.Kullanici;
import com.muhammedalmaz.sosyalyardim.pojo.KullaniciGuncelleSonuc;
import com.muhammedalmaz.sosyalyardim.pojo.KullaniciSpinner;
import com.muhammedalmaz.sosyalyardim.pojo.KullaniciSpinnerSonuc;
import com.muhammedalmaz.sosyalyardim.pojo.SehirSpinner;
import com.muhammedalmaz.sosyalyardim.pojo.SehirSpinnerSonuc;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KullaniciDuzenleFragment extends Fragment {

    Kullanici kullanici;

    public KullaniciDuzenleFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public KullaniciDuzenleFragment(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    APIInterface apiInterface;
    DialogMesajlari dialogMesajlari;
    ArrayList<String> sehirListe = new ArrayList<String>();
    ArrayList<SehirSpinner> sehirSpinnerArrayList;

    int seciliSehirPozisyon=0;

    public void spinnerDoldur() {
        Call<SehirSpinnerSonuc> sehirSpinnerSonucCall = apiInterface.sehirSpinnerGetir(HesapBilgileri.androidToken);
        try {
            SehirSpinnerSonuc sehirAutoCompleteSonuc = sehirSpinnerSonucCall.execute().body();
            if (!dialogMesajlari.hataMesajiGoster(sehirAutoCompleteSonuc.hataKodu)) {
                sehirSpinnerArrayList = sehirAutoCompleteSonuc.sehirSpinnerArrayList;
                for (int i = 0; i < sehirSpinnerArrayList.size(); i++) {
                    SehirSpinner sehirSpinner = sehirSpinnerArrayList.get(i);
                    sehirListe.add(sehirSpinner.getAd());
                    if(sehirSpinner.getId()==kullanici.getSehirId())
                    {
                        seciliSehirPozisyon=i;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            dialogMesajlari.hataMesajiGoster();
        }
    }

    Spinner
            spinnerSehir,
            spinnerMerkezde,
            spinnerOnayli;
    EditText
            txtKullaniciAdi,
            txtKullaniciSoyadi,
            txtEPosta,
            txtTelegramKullaniciAdi,
            txtTcKimlikNo,
            txtTelNo;

    ArrayList<OnaylanacakNesne> onaylanacakNesneArrayList = new ArrayList<OnaylanacakNesne>();
    String[] merkezdeDizi = new String[]{
            "Merkezde",
            "Merkezde Değil"
    };
    String[] onayliDizi = new String[]{
            "Onaylı",
            "Onaylı Değil"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_kullanici_duzenle, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        dialogMesajlari = new DialogMesajlari(getActivity());


        spinnerSehir = (Spinner) fragmentView.findViewById(R.id.SpinnerSehir);
        spinnerMerkezde = (Spinner) fragmentView.findViewById(R.id.SpinnerMerkezde);
        spinnerOnayli = (Spinner) fragmentView.findViewById(R.id.SpinnerOnayli);

        txtKullaniciAdi = (EditText) fragmentView.findViewById(R.id.TxtKullaniciAdi);
        txtKullaniciSoyadi = (EditText) fragmentView.findViewById(R.id.TxtKullaniciSoyadi);
        txtEPosta = (EditText) fragmentView.findViewById(R.id.TxtEPosta);
        txtTelegramKullaniciAdi = (EditText) fragmentView.findViewById(R.id.TxtTelegramKullaniciAdi);
        txtTcKimlikNo = (EditText) fragmentView.findViewById(R.id.TxtTcKimlikNo);
        txtTelNo = (EditText) fragmentView.findViewById(R.id.TxtTelNo);

        txtKullaniciAdi.setText(kullanici.getKullaniciAdi());
        txtKullaniciSoyadi.setText(kullanici.getKullaniciSoyadi());
        txtEPosta.setText(kullanici.getePosta());
        txtTelegramKullaniciAdi.setText(kullanici.getTelegramKullaniciAdi());
        txtTcKimlikNo.setText(kullanici.getTcKimlikNo());
        txtTelNo.setText(kullanici.getTel());


        spinnerDoldur();

        ArrayAdapter<String> spinnerArrayAdapterSehir = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, sehirListe);
        spinnerArrayAdapterSehir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> spinnerMerkezdeAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, merkezdeDizi);
        spinnerMerkezdeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> spinnerOnayliAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, onayliDizi);
        spinnerOnayliAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerSehir.setAdapter(spinnerArrayAdapterSehir);
        spinnerOnayli.setAdapter(spinnerOnayliAdapter);
        spinnerMerkezde.setAdapter(spinnerMerkezdeAdapter);

        spinnerSehir.setSelection(seciliSehirPozisyon);
        spinnerOnayli.setSelection(kullanici.getOnayli());
        spinnerMerkezde.setSelection(kullanici.getMerkezde());


        onaylayiciSinifiniOlustur();

        ((BootstrapButton) fragmentView.findViewById(R.id.BtnKullaniciGuncelle)).setOnClickListener(new Onaylayici(getActivity(),
                onaylanacakNesneArrayList) {
            @Override
            public void basarili() {
                Call<KullaniciGuncelleSonuc> kullaniciGuncelleSonucCall=apiInterface.kullaniciGuncelle(
                        HesapBilgileri.androidToken,kullanici.getKullaniciID()
                        ,sehirSpinnerArrayList.get(spinnerSehir.getSelectedItemPosition()).getId()
                        ,txtEPosta.getText().toString().trim()
                        ,txtTelegramKullaniciAdi.getText().toString().trim()
                        ,txtTcKimlikNo.getText().toString().trim()
                        ,spinnerMerkezde.getSelectedItemPosition()
                        ,spinnerOnayli.getSelectedItemPosition()
                        ,txtTelNo.getText().toString().trim()
                        ,txtKullaniciAdi.getText().toString().trim()
                        ,txtKullaniciSoyadi.getText().toString().trim()
                );
                kullaniciGuncelleSonucCall.enqueue(new Callback<KullaniciGuncelleSonuc>() {
                    @Override
                    public void onResponse(Call<KullaniciGuncelleSonuc> call, Response<KullaniciGuncelleSonuc> response) {
                        KullaniciGuncelleSonuc kullaniciGuncelleSonuc=response.body();
                        if(!dialogMesajlari.hataMesajiGoster(kullaniciGuncelleSonuc.hataKodu)){
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager
                                    .beginTransaction()
                                    .replace(
                                            R.id.ContentFrame, new KullaniciFragment(), "KullaniciFragment"
                                    ).commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<KullaniciGuncelleSonuc> call, Throwable t) {

                    }
                });

            }
        });


        return fragmentView;
    }

    public void onaylayiciSinifiniOlustur() {
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtKullaniciAdi, new Ayarlar.Yapici()
                        .setHataMesaji("Adınız En Az 1 En Fazla 25 Karakter Olmalıdır.")
                        .setMinimumKarakterSayisi(1)
                        .setMaksimumKarakterSayisi(25)
                        .setOnaylayiciTip(OnaylayiciTip.NORMAL_YAZI)
                        .Yap())
        );
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtKullaniciSoyadi, new Ayarlar.Yapici()
                        .setHataMesaji("SoyAdınız En Az 1 En Fazla 25 Karakter Olmalıdır.")
                        .setMinimumKarakterSayisi(1)
                        .setMaksimumKarakterSayisi(25)
                        .setOnaylayiciTip(OnaylayiciTip.NORMAL_YAZI)
                        .Yap())
        );
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtEPosta, new Ayarlar.Yapici()
                        .setHataMesaji("Lütfen Geçerli Bir E Posta Giriniz.")
                        .setOnaylayiciTip(OnaylayiciTip.EMAIL)
                        .Yap())
        );
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtTelegramKullaniciAdi, new Ayarlar.Yapici()
                        .setHataMesaji("Telegram Kullanıcı Adınız En Az 1 En Fazla 20 Karakter Olmalıdır.")
                        .setMinimumKarakterSayisi(1)
                        .setMaksimumKarakterSayisi(20)
                        .setOnaylayiciTip(OnaylayiciTip.NORMAL_YAZI)
                        .Yap())
        );
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtTcKimlikNo, new Ayarlar.Yapici()
                        .setHataMesaji("Lütfen Geçerli Bir TC Kimlik Numarası Giriniz.")
                        .setOnaylayiciTip(OnaylayiciTip.NORMAL_YAZI)
                        .Yap())
        );
        onaylanacakNesneArrayList.add(
                new OnaylanacakNesne(txtTelNo, new Ayarlar.Yapici()
                        .setHataMesaji("Lütfen Geçerli Bir Telefon Numarası Giriniz.")
                        .setMinimumKarakterSayisi(10)
                        .setMaksimumKarakterSayisi(10)
                        .setOnaylayiciTip(OnaylayiciTip.NORMAL_YAZI)
                        .Yap())
        );
    }

}
