module warehouse.erpclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires spring.web;
    requires org.jfxtras.styles.jmetro;

    opens warehouse.erpclient to javafx.fxml;
    exports warehouse.erpclient;
    exports warehouse.erpclient.controller;
    exports warehouse.erpclient.dto;
    opens warehouse.erpclient.controller to javafx.fxml;
    opens warehouse.erpclient.dto;

}