package com.example.lab2mqbenhvien.data;

import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
@XmlType(propOrder={"maBenhNhan","soCMND","hoVaTen","diaChi"})
public class Patient implements Serializable {
    private String maBenhNhan;
    private String soCMND;
    private String hoVaTen;
    private String diaChi;
    public Patient(){

    }
    public Patient(String maBenhNhan, String soCMND, String hoVaTen, String diaChi) {
        this.maBenhNhan = maBenhNhan;
        this.soCMND = soCMND;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "maBenhNhan='" + maBenhNhan + '\'' +
                ", soCMND='" + soCMND + '\'' +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
