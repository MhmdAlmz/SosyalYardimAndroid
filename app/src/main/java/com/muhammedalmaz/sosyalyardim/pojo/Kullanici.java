package com.muhammedalmaz.sosyalyardim.pojo;

import com.google.gson.annotations.SerializedName;

public class Kullanici {
    @SerializedName("KullaniciAdi")
    private String kullaniciAdi;
    @SerializedName("KullaniciSoyadi")
    private String kullaniciSoyadi;
    @SerializedName("EPosta")
    private String ePosta;
    @SerializedName("TelegramKullaniciAdi")
    private String telegramKullaniciAdi;
    @SerializedName("TCKimlikNo")
    private String tcKimlikNo;
    @SerializedName("Merkezde")
    private int merkezde;
    @SerializedName("Onayli")
    private int onayli;
    @SerializedName("Tel")
    private String tel;
    @SerializedName("Adres")
    private String adres;
    @SerializedName("Sehir")
    private String sehir;
    @SerializedName("SehirID")
    private int sehirId;
    @SerializedName("KullaniciID")
    private int kullaniciID;

    public String getePosta() {
        return ePosta;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }

    public String getTelegramKullaniciAdi() {
        return telegramKullaniciAdi;
    }

    public void setTelegramKullaniciAdi(String telegramKullaniciAdi) {
        this.telegramKullaniciAdi = telegramKullaniciAdi;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public int getMerkezde() {
        return merkezde;
    }

    public void setMerkezde(int merkezde) {
        this.merkezde = merkezde;
    }

    public int getOnayli() {
        return onayli;
    }

    public void setOnayli(int onayli) {
        this.onayli = onayli;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public int getSehirId() {
        return sehirId;
    }

    public void setSehirId(int sehirId) {
        this.sehirId = sehirId;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getKullaniciSoyadi() {
        return kullaniciSoyadi;
    }

    public void setKullaniciSoyadi(String kullaniciSoyadi) {
        this.kullaniciSoyadi = kullaniciSoyadi;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String  getSehir() {
        return sehir;
    }

    public void setSehir(String  sehir) {
        this.sehir = sehir;
    }

}
