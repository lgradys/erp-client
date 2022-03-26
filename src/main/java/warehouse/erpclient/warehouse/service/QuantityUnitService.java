package warehouse.erpclient.warehouse.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import warehouse.erpclient.utils.dao.ExecutorServiceProvider;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.controller.ItemCrudController;
import warehouse.erpclient.warehouse.rest.QuantityUnitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static warehouse.erpclient.utils.AlertUtils.createExceptionAlert;

public class QuantityUnitService {

    private final ItemCrudController itemCrudController;
    private final QuantityUnitClient quantityUnitClient;
    private final ExecutorService executorService;

    public QuantityUnitService(ItemCrudController itemCrudController) {
        this.itemCrudController = itemCrudController;
        this.quantityUnitClient = new QuantityUnitClient();
        this.executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
    }


    public void getQuantityUnitList(ComboBox<String> unitComboBox, String authorizationToken) {
        executorService.submit(() -> {
            ResponseEntity<RequestResult<String>> requestResult = quantityUnitClient.getQuantityUnitList(authorizationToken);
            handleRequestResult(requestResult, unitComboBox);
        });
    }

    public void handleRequestResult(ResponseEntity<RequestResult<String>> requestResult, ComboBox<String> unitComboBox) {
        HttpStatus responseStatus = requestResult.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            List<String> unitsList = requestResult.getBody().getResource();
            if (!unitsList.isEmpty()) {
                Platform.runLater(() -> {
                    unitComboBox.setItems(FXCollections.observableArrayList(unitsList));
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

}
