package com.muhammedalmaz.sosyalyardim.fragmentler;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.muhammedalmaz.sosyalyardim.R;
import com.muhammedalmaz.sosyalyardim.adaptorler.RecyclerViewIhtiyacSahibiListeAdapter;
import com.muhammedalmaz.sosyalyardim.api.APIClient;
import com.muhammedalmaz.sosyalyardim.api.APIInterface;
import com.muhammedalmaz.sosyalyardim.ekstralar.HesapBilgileri;
import com.muhammedalmaz.sosyalyardim.fonksiyonlar.DialogMesajlari;
import com.muhammedalmaz.sosyalyardim.pojo.IhtiyacSahibi;
import com.muhammedalmaz.sosyalyardim.pojo.IhtiyacSahibiListeSonuc;
import com.muhammedalmaz.sosyalyardim.pojo.IhtiyacSahibiSilmeSonuc;


import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IhtiyacSahibiFragment extends Fragment {


    String TAG=IhtiyacSahibiFragment.class.getName();
    APIInterface apiInterface;
    RecyclerViewIhtiyacSahibiListeAdapter recyclerViewIhtiyacSahibiListeAdapter;
    RecyclerView recyclerIhtiyacSahibiListe;
    DialogMesajlari dialogMesajlari;

    public IhtiyacSahibiFragment() {

    }

    public void ihtiyacSahibiYukle() {
        Log.i(TAG,HesapBilgileri.androidToken);
        Call<IhtiyacSahibiListeSonuc> ihtiyacSahibiListeSonucCall = apiInterface.ihtiyacSahibiListe(HesapBilgileri.androidToken);
        ihtiyacSahibiListeSonucCall.enqueue(new Callback<IhtiyacSahibiListeSonuc>() {
            @Override
            public void onResponse(Call<IhtiyacSahibiListeSonuc> call, Response<IhtiyacSahibiListeSonuc> response) {
                IhtiyacSahibiListeSonuc ihtiyacSahibiListeSonuc = response.body();
                if (!dialogMesajlari.hataMesajiGoster(ihtiyacSahibiListeSonuc.hataKodu)) {
                    recyclerViewIhtiyacSahibiListeAdapter = new RecyclerViewIhtiyacSahibiListeAdapter(ihtiyacSahibiListeSonuc.ihtiyacSahibiListe, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerIhtiyacSahibiListe.setLayoutManager(mLayoutManager);
                    recyclerIhtiyacSahibiListe.setItemAnimator(new DefaultItemAnimator());
                    recyclerIhtiyacSahibiListe.setAdapter(recyclerViewIhtiyacSahibiListeAdapter);
                }

            }

            @Override
            public void onFailure(Call<IhtiyacSahibiListeSonuc> call, Throwable t) {
                Log.e(TAG,"Hata Oldu");
                Log.e(TAG,t.getMessage());
                call.cancel();
                dialogMesajlari.hataMesajiGoster();
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView= inflater.inflate(R.layout.fragment_ihtiyac_sahibi, container, false);



        recyclerIhtiyacSahibiListe = fragmentView.findViewById(R.id.RecyclerIhtiyacSahibiListe);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        dialogMesajlari = new DialogMesajlari(getActivity());
        ihtiyacSahibiYukle();

        ItemTouchHelper.SimpleCallback simpleCallbackSil = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final IhtiyacSahibi ihtiyacSahibi = recyclerViewIhtiyacSahibiListeAdapter.getItem(position);
                if (direction == ItemTouchHelper.LEFT) {
                    recyclerViewIhtiyacSahibiListeAdapter.listeyiGuncelle();
                    dialogMesajlari.evetHayirDialogGoster("Şubeyi Silmek Istediğinizden Emin Misiniz", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            Call<IhtiyacSahibiSilmeSonuc> ihtiyacSahibiSilmeSonucCall = apiInterface.ihtiyacSahibiSil(HesapBilgileri.androidToken
                                    , ihtiyacSahibi.getIhtiyacSahibiID());
                            ihtiyacSahibiSilmeSonucCall.enqueue(new Callback<IhtiyacSahibiSilmeSonuc>() {
                                @Override
                                public void onResponse(Call<IhtiyacSahibiSilmeSonuc> call, Response<IhtiyacSahibiSilmeSonuc> response) {
                                    IhtiyacSahibiSilmeSonuc ihtiyacSahibiListeSonuc = response.body();
                                    if (!dialogMesajlari.hataMesajiGoster(ihtiyacSahibiListeSonuc.hataKodu)) {
                                        ihtiyacSahibiYukle();
                                        dialogMesajlari.basariliIslemDialogGoster();
                                    }
                                }

                                @Override
                                public void onFailure(Call<IhtiyacSahibiSilmeSonuc> call, Throwable t) {
                                    call.cancel();
                                    dialogMesajlari.hataMesajiGoster();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                try {
                    Bitmap icon;
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 5;
                        viewHolder.itemView.setTranslationX(dX);
                        Paint paint = new Paint();
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sil);
                        RectF icon_dest = new RectF((float) (itemView.getRight() + dX / 5), (float) itemView.getTop() + width, (float) itemView.getRight() + dX / 20, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    } else {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        ItemTouchHelper.SimpleCallback simpleCallbackDuzenle = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {


                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(
                                    R.id.ContentFrame, new IhtiyacSahibiDuzenleFragment(
                                            recyclerViewIhtiyacSahibiListeAdapter.getItem(position))
                                    , "IhtiyacSahibiDuzenleFragment"
                            ).commit();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                try {
                    Bitmap icon;
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 5;
                        viewHolder.itemView.setTranslationX(dX);
                        Paint paint = new Paint();
                        paint.setColor(Color.parseColor("#224AF3"));
                        RectF background = new RectF((float) itemView.getLeft() + dX, (float) itemView.getTop(), (float) itemView.getLeft(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_duzenle);
                        RectF icon_dest = new RectF((float) (itemView.getLeft() + dX / 5), (float) itemView.getTop() + width, (float) itemView.getLeft() + dX / 20, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    } else {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        ItemTouchHelper itemTouchHelperSil = new ItemTouchHelper(simpleCallbackSil);
        ItemTouchHelper itemTouchHelperDuzenle = new ItemTouchHelper(simpleCallbackDuzenle);
        itemTouchHelperSil.attachToRecyclerView(recyclerIhtiyacSahibiListe);
        itemTouchHelperDuzenle.attachToRecyclerView(recyclerIhtiyacSahibiListe);

        ((BootstrapButton)fragmentView.findViewById(R.id.BtnIhtiyacSahibiEkle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.ContentFrame, new IhtiyacSahibiEkleFragment(), "IhtiyacSahibiEkleFragment"
                        ).commit();
            }
        });


        return fragmentView;
    }

}
