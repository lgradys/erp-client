module warehouse.erpclient {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires spring.core;

    exports warehouse.erpclient;
    opens warehouse.erpclient;

    exports warehouse.erpclient.warehouse.model;
    opens warehouse.erpclient.warehouse.model;

    exports warehouse.erpclient.login;
    opens warehouse.erpclient.login;
    exports warehouse.erpclient.login.dto;
    opens warehouse.erpclient.login.dto;

    exports warehouse.erpclient.utils;
    opens warehouse.erpclient.utils;
    exports warehouse.erpclient.utils.dao;
    opens warehouse.erpclient.utils.dao;
    exports warehouse.erpclient.utils.dto;
    opens warehouse.erpclient.utils.dto;

    exports warehouse.erpclient.warehouse.controller;
    opens warehouse.erpclient.warehouse.controller;
    exports warehouse.erpclient.warehouse.dto;
    opens warehouse.erpclient.warehouse.dto;
    exports warehouse.erpclient.warehouse.rest;
    opens warehouse.erpclient.warehouse.rest;
    exports warehouse.erpclient.warehouse.service;
    opens warehouse.erpclient.warehouse.service;

}