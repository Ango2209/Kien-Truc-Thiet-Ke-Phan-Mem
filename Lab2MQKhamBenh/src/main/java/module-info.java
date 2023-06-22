module com.example.lab2mqbenhvien {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.naming;
    requires javax.jms.api;
    requires log4j;
    requires jakarta.xml.bind;
    opens com.example.lab2mqbenhvien.data to javafx.base,jakarta.xml.bind;
    opens com.example.lab2mqbenhvien to javafx.fxml,jakarta.xml.bind;
    opens com.example.lab2mqbenhvien.receiver to javafx.fxml;
    opens com.example.lab2mqbenhvien.controller to javafx.fxml;
    exports com.example.lab2mqbenhvien;
    exports com.example.lab2mqbenhvien.controller;
    exports com.example.lab2mqbenhvien.receiver;
}