module warehouse.erpclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires spring.web;

    opens warehouse.erpclient to javafx.fxml;
    exports warehouse.erpclient;
    exports warehouse.erpclient.controller;
    exports warehouse.erpclient.dto;
    opens warehouse.erpclient.controller to javafx.fxml;
    opens warehouse.erpclient.dto;
    exports warehouse.erpclient.controller.warehouse;
    opens warehouse.erpclient.controller.warehouse to javafx.fxml;

}