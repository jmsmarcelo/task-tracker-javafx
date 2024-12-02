import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskApp extends Application {
    private ChoiceBox<String> actionChoiceBox;
    private TextField idField;
    private TextField descriptionField;
    private Text actionInfo;
    private ChoiceBox<String> listChoiceBox;
    private TableView<Task> taskTableView;
    private ObservableList<Task> taskData;
    private TaskService service = new TaskService();

    @Override
    public void start(Stage stage) throws Exception {
        GridPane taskGridPane = new GridPane();
        taskGridPane.setPadding(new Insets(10, 10, 10, 10));
        taskGridPane.setVgap(10);
        taskGridPane.setHgap(10);
        taskGridPane.setAlignment(Pos.BASELINE_CENTER);

        Label actionLabel = new Label("Action:");
        actionChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
            "add", "update", "delete","mark-in-progress", "mark-done", "list"
        ));
        HBox actionHBox = new HBox();
        actionHBox.setSpacing(5);
        actionHBox.getChildren().addAll(actionLabel, actionChoiceBox);
        taskGridPane.add(actionHBox, 0, 0, 2, 1);

        HBox argsHBox = new HBox();
        argsHBox.setSpacing(5);

        HBox inputHBox = new HBox(actionHBox, argsHBox);
        inputHBox.setSpacing(10);
        inputHBox.setAlignment(Pos.CENTER);
        taskGridPane.add(inputHBox, 2, 0, 2, 1);

        Label idLabel = new Label("ID:");
        idField = new TextField();
        idField.setPrefWidth(50);

        Label descriptionLabel = new Label("Description:");
        descriptionField = new TextField();
        descriptionField.setPrefWidth(200);

        listChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
            "all", "todo", "in-progress", "done"
        ));
        listChoiceBox.setValue("all");

        actionChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                argsHBox.getChildren().clear();
                idField.setText("");
                descriptionField.setText("");
                if(actionChoiceBox.getValue().matches("update|delete|mark-.*")) {
                    argsHBox.getChildren().addAll(idLabel, idField);
                }
                if(actionChoiceBox.getValue().matches("add|update")) {
                    argsHBox.getChildren().addAll(descriptionLabel, descriptionField);
                }
                if(actionChoiceBox.getValue().equals("list")) {
                    argsHBox.getChildren().add(listChoiceBox);
                }
            }
        });

        Button submitBtn = new Button("Submit");
        HBox submitBox = new HBox();
        submitBox.setAlignment(Pos.BASELINE_CENTER);
        submitBox.setPadding(new Insets(10, 0, 0, 0));
        submitBox.getChildren().add(submitBtn);
        taskGridPane.add(submitBox, 0, 1, 4, 1);

        actionInfo = new Text();
        HBox infoBox = new HBox();
        infoBox.setAlignment(Pos.BASELINE_CENTER);
        infoBox.getChildren().addAll(actionInfo);
        actionInfo.setText("");
        taskGridPane.add(infoBox, 0, 2, 4, 1);

        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                actionInfo.setText("");
                if(actionChoiceBox.getValue() == null) {
                    actionInfo.setFill(Color.RED);
                    actionInfo.setText("you need to select an Action");
                    return;
                }
                switch(actionChoiceBox.getValue()) {
                    case "add" -> handleAddAction();
                    case "update" -> handleUpdateAction();
                    case "delete" -> handleDeleteAction();
                    case "mark-in-progress" -> handleMarkAsAction(TaskStatus.IN_PROGRESS);
                    case "mark-done" -> handleMarkAsAction(TaskStatus.DONE);
                    case "list" -> handleListAction();
                }
            }
        });

        taskTableView = new TableView<>();
        taskTableView.setMinWidth(734);
   
        TableColumn<Task, Long> taskIdTableColumn = new TableColumn<>("ID");
        taskIdTableColumn.setPrefWidth(50);
        taskIdTableColumn.setStyle("-fx-alignment: BASELINE_CENTER");
        taskIdTableColumn.setCellValueFactory(
            new PropertyValueFactory<Task, Long>("id")
        );
        taskTableView.getColumns().add(taskIdTableColumn);

        TableColumn<Task, String> taskDescriptionTableColumn = new TableColumn<>("Description");
        taskDescriptionTableColumn.setMinWidth(230);
        taskDescriptionTableColumn.setStyle("-fx-alignment: BASELINE_CENTER");
        taskDescriptionTableColumn.setCellValueFactory(
            new PropertyValueFactory<Task, String>("description")
        );
        taskTableView.getColumns().add(taskDescriptionTableColumn);

        TableColumn<Task, String> taskStatusTableColumn = new TableColumn<>("Status");
        taskStatusTableColumn.setMinWidth(70);
        taskStatusTableColumn.setStyle("-fx-alignment: BASELINE_CENTER");
        taskStatusTableColumn.setCellValueFactory(
            new PropertyValueFactory<Task, String>("status")
        );
        taskTableView.getColumns().add(taskStatusTableColumn);

        TableColumn<Task, LocalDateTime> taskCreatedAtTableColumn = new TableColumn<>("CreatedAt");
        taskCreatedAtTableColumn.setMinWidth(179);
        taskCreatedAtTableColumn.setStyle("-fx-alignment: BASELINE_CENTER");
        taskCreatedAtTableColumn.setCellValueFactory(
            new PropertyValueFactory<Task, LocalDateTime>("createdAt")
        );
        taskTableView.getColumns().add(taskCreatedAtTableColumn);

        TableColumn<Task, LocalDateTime> taskUpdatedAtTableColumn = new TableColumn<>("UpdatedAt");
        taskUpdatedAtTableColumn.setMinWidth(179);
        taskUpdatedAtTableColumn.setStyle("-fx-alignment: BASELINE_CENTER");
        taskUpdatedAtTableColumn.setCellValueFactory(
            new PropertyValueFactory<Task, LocalDateTime>("updatedAt")
        );
        taskTableView.getColumns().add(taskUpdatedAtTableColumn);

        taskData = FXCollections.observableArrayList();
        taskTableView.setItems(taskData);
        taskGridPane.add(taskTableView, 0, 3, 3, 1);

        Button clearButton = new Button("Clear List");
        HBox clearHBox = new HBox();
        clearHBox.getChildren().add(clearButton);
        clearHBox.setAlignment(Pos.BASELINE_RIGHT);
        taskGridPane.add(clearHBox, 0, 4, 3, 1);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                taskData.clear();
            }
            
        });

        stage.setTitle("Task Tracker");
        stage.setScene(new Scene(taskGridPane, 754, 540));
        stage.show();
    }
    private void handleAddAction() {
        if(descriptionField.getText().isBlank()) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("Task no description is not allowed");
            return;
        }
        try {
            long id = service.add(descriptionField.getText().replaceAll("[\\\\\"]", ""));
            if(id == 0) {
                actionInfo.setFill(Color.RED);
                actionInfo.setText("no Task found for add");
            } else {
                actionInfo.setFill(Color.GREEN);
                actionInfo.setText("Task added successfully (ID: %s)".formatted(id));
                descriptionField.setText("");
            }
        } catch (IOException e) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("""
                error:    could not add the Task to data file
                possible reason:    no permission to write to file
                """);
        }
    }
    private void handleUpdateAction() {
        if(idField.getText().isBlank()) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID is required");
            return;
        }
        if(idField.getText().matches(".*[^\\d]+.*")) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID must be number");
            return;
        }
        if(descriptionField.getText().isBlank()) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("Task no description is not allowed");
            return;
        }
        try {
            if(service.update(Long.parseLong(idField.getText()), descriptionField.getText())) {
                actionInfo.setFill(Color.GREEN);
                actionInfo.setText("Task(ID: %s) updated successfully".formatted(idField.getText()));
                idField.setText("");
                descriptionField.setText("");
            } else {
                actionInfo.setFill(Color.RED);
                actionInfo.setText("Task(ID: %s) not found".formatted(idField.getText()));
            }
        } catch (NumberFormatException | IOException e) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("""
                error:    Could not update the Task to data file
                possible reason:    no permission to read/write to file
                """);
        }
    }
    private void handleDeleteAction() {
        if(idField.getText().isBlank()) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID is required");
            return;
        }
        if(idField.getText().matches(".*[^\\d]+.*")) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID must be number");
            return;
        }
        try {
            if(service.delete(Long.parseLong(idField.getText()))) {
                actionInfo.setFill(Color.GREEN);
                actionInfo.setText("Task(ID: %s) deleted successfully".formatted(idField.getText()));
            } else {
                actionInfo.setFill(Color.RED);
                actionInfo.setText("Task(ID: %s) not found".formatted(idField.getText()));
            }
        } catch (NumberFormatException | IOException e) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("""
                error:    could not delete the Task to data file
                possible reason:    no permission to read/write to file
                """);
        }
    }
    private void handleMarkAsAction(TaskStatus status) {
        if(idField.getText().isBlank()) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID is required");
            return;
        }
        if(idField.getText().matches(".*[^\\d]+.*")) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("ID must be number");
            return;
        }
        try {
            if(service.update(Long.parseLong(idField.getText()), status)) {
                actionInfo.setFill(Color.GREEN);
                actionInfo.setText("Task(ID: %s) marked as %s successfully".formatted(idField.getText(), status.toString()));
            } else {
                actionInfo.setFill(Color.RED);
                actionInfo.setText("Task(ID: %S) not found".formatted(idField.getText()));
            }
        } catch (NumberFormatException | IOException e) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("""
                error:    could not mark the Task as %s in the data file
                possible reason:    no permission to read/write to file
                """.formatted(status.toString()));
        }
    }
    private void handleListAction() {
        try {
            taskData.clear();
            List<Task> tasks = service.find(listChoiceBox.getValue());
            taskData.addAll(tasks);
        } catch (IOException e) {
            actionInfo.setFill(Color.RED);
            actionInfo.setText("""
                error:    could not load the Tasks from data file
                possible reason:    no permission to read to file
                """);
        }
    }
}