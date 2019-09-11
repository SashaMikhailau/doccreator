package com.mikhailau.ui;

import com.mikhailau.constants.PropertiesConstants;
import com.mikhailau.constants.UiFieldsConstants;
import com.mikhailau.constants.UiFieldsConstants.ExpertiseType;
import com.mikhailau.model.InventoryItem;
import com.mikhailau.model.UiResultModel;
import com.mikhailau.service.DocumentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainViewController {
    public static final String INVENTORY_ID_PREFIX = "inventoryItem";
    private DocumentService documentService = new DocumentService();

    private Stage primaryStage;


    @FXML
    private VBox inventoryBox;

    @FXML
    private VBox examinationTypesBox;
    @FXML
    private HBox materialTypeBox;

    @FXML
    private TextField expNumber;

    @FXML
    private TextField hoursCount;

    @FXML
    private DatePicker finish_date;

    @FXML
    private Label expertisePrefix;

    @FXML
    private ComboBox<ExpertiseType> expertiseTypesBox;

    private ToggleGroup materialTypeToggle;

    private List<InventoryItem> inventoryItems = new ArrayList<>();


    @FXML
    private void initialize() {
        finish_date.setValue(LocalDate.now());
        renderInventorySection();
        renderExpertisePrefix();
        renderExaminationTypes();
        renderMaterialTypes();
        renderExpertiseTypes();
    }

    private Stage getPrimaryStage() {
        if (Objects.isNull(primaryStage)) {
            primaryStage = (Stage) inventoryBox.getScene().getWindow();
        }
        return primaryStage;
    }

    private void renderMaterialTypes() {
        materialTypeToggle = new ToggleGroup();
        List<RadioButton> radioButtons = Arrays.stream(UiFieldsConstants.InvestigationType.values())
                .map(investigationType -> {
                    RadioButton radioButton = new RadioButton(investigationType.getRussianTranscr());
                    radioButton.setUserData(investigationType.name());
                    radioButton.setToggleGroup(materialTypeToggle);
                    return radioButton;
                })
                .collect(Collectors.toList());
        materialTypeBox.getChildren().addAll(radioButtons);
        if (!materialTypeToggle.getToggles().isEmpty()) {
            materialTypeToggle.getToggles().get(0).setSelected(true);
        }
    }

    private void renderExaminationTypes() {
        List<HBox> examinationTypes = Arrays.stream(UiFieldsConstants.ExaminationType.values())
                .map(examinationType -> {
                    Label label = new Label(examinationType.getRussianTranscr());
                    label.setMinWidth(100);
                    TextField textField = new TextField();
                    textField.setId(examinationType.name());
                    return new HBox(label, textField);
                })
                .collect(Collectors.toList());
        examinationTypesBox.getChildren().addAll(examinationTypes);
    }

    private void renderExpertiseTypes() {
        ObservableList<ExpertiseType> expertiseTypes = FXCollections.observableArrayList(ExpertiseType.values());
        expertiseTypesBox.setItems(expertiseTypes);
        if (!expertiseTypes.isEmpty()) {
            expertiseTypesBox.getSelectionModel().select(0);
        }
    }

    private void renderExpertisePrefix() {
        expertisePrefix.setText(documentService.getSettings().get(PropertiesConstants.EXPERT_DEPARTURE_CODE));

    }

    private void renderInventorySection() {
        inventoryItems = documentService.getInventoryItems();
        ObservableList<Node> children = inventoryBox.getChildren();
        for (int i = 0; i < inventoryItems.size(); i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setId(String.format("%s%d", INVENTORY_ID_PREFIX, i));
            Label label = new Label(inventoryItems.get(i).getName());
            children.addAll(new HBox(checkBox, label));
        }
    }

    @FXML
    private void handleCreateButtonClick(ActionEvent event) {
        String validationMessage = validateUiData();
        if(validationMessage!=null){
            showAlertDialog(validationMessage);
           return;
        }
        File dirToSave = getFolderTOSaveFromDirChooser();
        if (dirToSave != null && primaryStage != null) {
            handleFormDataAndCloseDialog(dirToSave);
        }
    }

    private void showAlertDialog(String validationMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ВНИМАНИЕ");
        alert.setHeaderText("Введены неверные данные");
        alert.setContentText(validationMessage);
        alert.showAndWait();
    }

    private String validateUiData() {
        StringBuilder sb = new StringBuilder();
        String errorMesage = "Некорректный номер экспертизы";
        String result = Optional.ofNullable(expNumber.getText())
                .filter(text -> text.matches("\\d+"))
                .map(text -> "")
                .orElse(errorMesage);
        sb.append(result);

        return sb.toString().isEmpty() ? null : sb.toString();

    }

    private File getFolderTOSaveFromDirChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File pathToExpFolder =
                new File(documentService.getSettings().get(PropertiesConstants.PATH_TO_EXPERTISES_FOLDER));
        directoryChooser.setInitialDirectory(pathToExpFolder);
        return directoryChooser.showDialog(getPrimaryStage());
    }

    private void handleFormDataAndCloseDialog(File dirToSave) {
        Map<String, String> primaryFieldValues = collectDataFromPrimaryFields();
        primaryFieldValues.put(UiFieldsConstants.FOLDER_TO_SAVE, dirToSave.getPath());
        UiResultModel uiResultModel = new UiResultModel();
        uiResultModel.setPrimaryFieldValues(primaryFieldValues);
        uiResultModel.setInventoryItems(collectInventoryItems());
        uiResultModel.setExaminationTypeTimeCosts(collectExaminationTypeCosts());
        documentService.pushData(uiResultModel);
        primaryStage.close();
    }

    private Map<UiFieldsConstants.ExaminationType, Integer> collectExaminationTypeCosts() {
        return getStreamOfUnderChilds(examinationTypesBox)
                .filter(child -> child instanceof TextField)
                .map(child -> ((TextField) child))
                .filter(textField -> textField.getText().matches("\\d+"))
                .collect(Collectors.toMap(
                        textField -> UiFieldsConstants.ExaminationType.valueOf(textField.getId()),
                        textField -> Integer.parseInt(textField.getText())));
    }

    private Map<String, String> collectDataFromPrimaryFields() {
        Map<String, String> dataFromUI = new HashMap<>();
        dataFromUI.put(UiFieldsConstants.EXPERTISE_FINISH_DATE,
                finish_date.getValue().format(UiFieldsConstants.DTF));
        dataFromUI.put(UiFieldsConstants.EXPERTISE_NUMBER, expNumber.getText());
        dataFromUI.put(UiFieldsConstants.EXPERTISE_TYPE,getFullExpType());
        dataFromUI.put(UiFieldsConstants.TOTAL_HOURS_COUNT, hoursCount.getText());
        dataFromUI.put(UiFieldsConstants.MATERIAL_TYPE,
                getInvestigationType());

        return dataFromUI;
    }

    private String getFullExpType() {
        return String.format("%s%s", UiFieldsConstants.EXP_TYPE_PREFIX,
                expertiseTypesBox.getValue().getRussianTranscr());
    }

    private String getInvestigationType() {
        return UiFieldsConstants.InvestigationType.valueOf(((String)
                materialTypeToggle.getSelectedToggle().getUserData())).name();
    }

    private List<InventoryItem> collectInventoryItems(){
            return getStreamOfUnderChilds(inventoryBox)
                    .filter(child -> child instanceof CheckBox)
                    .filter(checkbox -> ((CheckBox) checkbox).isSelected())
                    .map(Node::getId)
                    .map(id -> id.replace(INVENTORY_ID_PREFIX, ""))
                    .filter(id -> id.matches("\\d+"))
                    .map(Integer::valueOf)
                    .filter(id -> id < inventoryItems.size())
                    .map(id -> inventoryItems.get(id))
                    .collect(Collectors.toList());
        }

    private Stream<Node> getStreamOfUnderChilds(Parent parent) {
        return parent.getChildrenUnmodifiable()
                .stream()
                .filter(node -> node instanceof Parent)
                .flatMap(node -> ((Parent) node).getChildrenUnmodifiable().stream());
    }

}
