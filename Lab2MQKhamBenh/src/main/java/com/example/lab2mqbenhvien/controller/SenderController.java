package com.example.lab2mqbenhvien.controller;

import com.example.lab2mqbenhvien.data.Patient;
import com.example.lab2mqbenhvien.sender.QueueSender;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SenderController {
    @FXML
    private TextField maBenhNhan;
    @FXML
    private TextField soCMND;
    @FXML
    private TextField hoVaTen;
    @FXML
    private TextField diaChi;
    private QueueSender sender;
    public SenderController() throws Exception {

        sender=new QueueSender();
    }
    @FXML
    public void onLuuBtnClick() throws Exception{
        String maBN=maBenhNhan.getText();
        String cmnd=soCMND.getText();
        String hoTen=hoVaTen.getText();
        String dc=diaChi.getText();
        Patient patient=new Patient(maBN,cmnd,hoTen,dc);
       sender.senRequest(patient);
    }
}
