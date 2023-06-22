package com.example.lab2mqbenhvien.receiver;

import com.example.lab2mqbenhvien.data.Patient;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.StringReader;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;


import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.BasicConfigurator;


public class QueueReceiverController {
//    @FXML
//    private Button btnCapNhat;

//    @FXML
//    private Button btnGoiKham;

    @FXML
    private TableColumn<Patient, String> tableColumn;

    @FXML
    private TableView<Patient> tableView;



    @FXML
    private TextField soCMND;

    @FXML
    private TextArea diaChi;

    @FXML
    private TextField hoTen;

    @FXML
    private TextField maSoBenhNhan;

    @FXML
    private TextArea noiDungKham;


    @FXML
    private  ObservableList<Patient> list;


    public void initialize() throws Exception {
//thiết lập môi trường cho JMS
        BasicConfigurator.configure();
//thiết lập môi trường cho JJNDI
        Properties settings=new Properties();
        settings.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
//tạo context
        Context ctx=new InitialContext(settings);
//lookup JMS connection factory
        Object obj=ctx.lookup("ConnectionFactory");
        ConnectionFactory factory=(ConnectionFactory)obj;
//lookup destination
        Destination destination
                =(Destination) ctx.lookup("dynamicQueues/thanthidet");
//tạo connection
        Connection con=factory.createConnection("admin","admin");
//nối đến MOM
        con.start();
//tạo session
        Session session=con.createSession(
                /*transaction*/false,
                /*ACK*/Session.CLIENT_ACKNOWLEDGE
        );
//tạo consumer
        MessageConsumer receiver = session.createConsumer(destination);
//blocked-method for receiving message - sync
//receiver.receive();
//Cho receiver lắng nghe trên queue, chừng có message thì notify - async
        System.out.println("Tý was listened on queue...");
        receiver.setMessageListener(new MessageListener() {
            @Override
//có message đến queue, phương thức này được thực thi
            public void onMessage(Message msg) {//msg là message nhận được
                try {
                    if(msg instanceof TextMessage){
                        TextMessage tm=(TextMessage)msg;
                        String txt=tm.getText();
                        System.out.println("Nhận được "+txt);
                        msg.acknowledge();//gửi tín hiệu ack


                        // convert mxl string to Oject java
                        StringReader sr = new StringReader(txt);
                        JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        Patient response = (Patient) unmarshaller.unmarshal(sr);


                        //add oject table view
                        list= tableView.getItems();
                        list.add(response);

                        tableColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("hoVaTen"));
                        tableView.setItems(list);

                        System.out.println("Oject nhan duoc la:"+list);
                    }
                    else if(msg instanceof ObjectMessage){
                        ObjectMessage om=(ObjectMessage)msg;
                        System.out.println(om);
                    }
//others message type....
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onCapNhat(ActionEvent e){
        Patient person=  tableView.getSelectionModel().getSelectedItem();
        list.remove(person);
    }

    public void onGoiKham(ActionEvent e){
        Patient patient=  tableView.getSelectionModel().getSelectedItem();
        maSoBenhNhan.setText(patient.getMaBenhNhan());
        soCMND.setText(patient.getSoCMND());
        hoTen.setText(patient.getHoVaTen());
        diaChi.setText(patient.getDiaChi());
    }
}