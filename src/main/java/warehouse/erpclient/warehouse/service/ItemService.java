package warehouse.erpclient.warehouse.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import warehouse.erpclient.AppStarter;
import warehouse.erpclient.utils.AlertUtils;
import warehouse.erpclient.utils.dao.ExecutorServiceProvider;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.controller.AddItemController;
import warehouse.erpclient.warehouse.controller.EditItemController;
import warehouse.erpclient.warehouse.controller.ItemCrudController;
import warehouse.erpclient.warehouse.controller.WarehouseController;
import warehouse.erpclient.warehouse.dto.ItemDTO;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.rest.ItemClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static warehouse.erpclient.utils.AlertUtils.createAlert;

public class ItemService {

    private final String ADD_ITEM_FXML_PATH = "/warehouse/erpclient/warehouse/add_item.fxml";
    private final String EDIT_ITEM_FXML_PATH = "/warehouse/erpclient/warehouse/edit_item.fxml";

    private final ItemClient itemClient;
    private final ExecutorService executorService;
    private final WarehouseController warehouseController;

    public ItemService(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.itemClient = new ItemClient();
        executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
    }

    public void getItems(TableView<Item> itemTable, long warehouseId) {
        itemTable.getItems().clear();
        executorService.submit(() -> {
            ResponseEntity<RequestResult<ItemDTO>> requestResult = itemClient.getItems(warehouseController.getMainController().getAuthorizationToken(), warehouseId);
            handleRequestResult(requestResult, itemTable);
        });
    }

    public void addItem(Item item) {
        executorService.submit(() -> {
            ResponseEntity<RequestResult<ItemDTO>> requestResult = itemClient.addItem(warehouseController.getMainController().getAuthorizationToken(), ItemDTO.of(item));
            handleRequestResult(requestResult, "Successfully added item!");
        });
    }

    public void editItem(Item item) {
        executorService.submit(() -> {
            ResponseEntity<RequestResult<ItemDTO>> requestResult = itemClient.editItem(warehouseController.getMainController().getAuthorizationToken(), ItemDTO.of(item));
            handleRequestResult(requestResult, "Successfully edited item!");
        });
    }

    public void deleteItem(Item item) {
        executorService.submit(() -> {
            ResponseEntity<RequestResult<ItemDTO>> requestResult = itemClient.deleteItem(warehouseController.getMainController().getAuthorizationToken(), item.getWarehouseId(), item.getId());
            handleRequestResult(requestResult, "Successfully deleted item!");

        });
    }

    private void handleRequestResult(ResponseEntity<RequestResult<ItemDTO>> requestResult, String message) {
        HttpStatus responseStatus = requestResult.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            Platform.runLater(() -> {
                createAlert(message, Alert.AlertType.ERROR).show();
                executorService.submit(() -> getItems(warehouseController.getItemTable(), warehouseController.getWarehouseComboBox().getSelectionModel().getSelectedItem().getId()));
            });
        }
        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            Platform.runLater(() -> {
                String errors = requestResult.getBody().getError().stream()
                        .map(Error::getMessage)
                        .collect(Collectors.joining(" "));
                createAlert(errors, Alert.AlertType.ERROR).show();
            });
        }
    }

    private void handleRequestResult(ResponseEntity<RequestResult<ItemDTO>> requestResult, TableView<Item> itemTable) {
        HttpStatus responseStatus = requestResult.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            List<ItemDTO> itemDTOS = requestResult.getBody().getResource();
            if (!itemDTOS.isEmpty()) {
                Platform.runLater(() -> {
                    List<Item> items = itemDTOS.stream().
                            map(Item::of)
                            .collect(Collectors.toList());
                    itemTable.setItems(FXCollections.observableArrayList(items));
                });
            }
        }
        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            Platform.runLater(() -> {
                String errors = requestResult.getBody().getError().stream()
                        .map(Error::getMessage)
                        .collect(Collectors.joining(" "));
                itemTable.setPlaceholder(new Label(errors));
            });
        }
    }

    public void openAddLayout() {
        openLayout(ADD_ITEM_FXML_PATH, new AddItemController(warehouseController));
    }

    public void openEditLayout() {
        openLayout(EDIT_ITEM_FXML_PATH, new EditItemController(warehouseController));
    }

    private void openLayout(String viewPath, ItemCrudController itemCrudController) {
        try {
            FXMLLoader loader = new FXMLLoader(AppStarter.class.getResource(viewPath));
            loader.setController(itemCrudController);
            Pane loadedPane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(loadedPane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> AlertUtils.createAlert(e.getMessage(), Alert.AlertType.ERROR).show());
        }
    }

}
