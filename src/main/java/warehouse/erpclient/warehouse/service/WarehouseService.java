package warehouse.erpclient.warehouse.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import warehouse.erpclient.AppStarter;
import warehouse.erpclient.utils.dao.ExecutorServiceProvider;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.controller.WarehouseController;
import warehouse.erpclient.warehouse.controller.WarehouseInfoController;
import warehouse.erpclient.warehouse.dto.WarehouseDTO;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.rest.WarehouseClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static warehouse.erpclient.utils.AlertUtils.createExceptionAlert;
import static warehouse.erpclient.utils.StageUtils.centerPane;

public class WarehouseService {

    private final String WAREHOUSE_INFO_FXML_PATH = "/warehouse/erpclient/warehouse/warehouse_info.fxml";

    private final WarehouseClient warehouseClient;
    private final ExecutorService executorService;
    private final WarehouseController warehouseController;

    public WarehouseService(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
        this.warehouseClient = new WarehouseClient();
    }

    public void getWarehousesList(ComboBox<Warehouse> warehouseList, String authorizationToken) {
        executorService.submit(() -> {
            ResponseEntity<RequestResult<WarehouseDTO>> requestResult = warehouseClient.getWarehousesList(authorizationToken);
            handleRequestResult(requestResult, warehouseList);
        });
    }

    public void handleRequestResult(ResponseEntity<RequestResult<WarehouseDTO>> requestResult, ComboBox<Warehouse> warehouseComboBox) {
        HttpStatus responseStatus = requestResult.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            List<WarehouseDTO> warehouseDTOS = requestResult.getBody().getResource();
            if (!warehouseDTOS.isEmpty()) {
                Platform.runLater(() -> {
                    List<Warehouse> warehouses = warehouseDTOS.stream()
                            .map(Warehouse::of)
                            .collect(Collectors.toList());
                    warehouseComboBox.setItems(FXCollections.observableArrayList(warehouses));
                    warehouseComboBox.getSelectionModel().select(warehouses.stream().findFirst().get());
                });
            }
        }
        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            Platform.runLater(() -> {
                String errors = requestResult.getBody().getError().stream()
                        .map(Error::getMessage)
                        .collect(Collectors.joining(" "));
                createExceptionAlert(errors).show();
            });
        }
    }

    public void initializeWarehouseInfo(Pane mainPane, Warehouse warehouse, WarehouseController warehouseController) {
        try {
            mainPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(AppStarter.class.getResource(WAREHOUSE_INFO_FXML_PATH));
            Pane loadedPane = loader.load();
            WarehouseInfoController warehouseInfoController = loader.getController();
            warehouseInfoController.getWarehouseName().setText(warehouse.getName());
            warehouseInfoController.getWarehouseAddress().setText(warehouse.getAddress().toString());
            warehouseInfoController.setWarehouseController(warehouseController);
            warehouseInfoController.setWarehouse(warehouse);
            centerPane(loadedPane);
            mainPane.getChildren().add(loadedPane);
        } catch (Exception e) {
            createExceptionAlert(e.getMessage());
        }
    }

}
