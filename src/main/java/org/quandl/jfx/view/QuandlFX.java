package org.quandl.jfx.view;

import java.util.List;
import org.quandl.jfx.view.wiki.dataset.DataSetFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.quandl.jfx.view.wiki.dataset.DataSetTableViewSkin;
import org.quandl.jfx.view.wiki.dataset.event.DataSetFXEventHandler;
import org.quandl.jfx.view.wiki.dataset.listener.DataSetFilterListener;
import org.quandl.jfx.view.wiki.dataset.task.CreateWIKIDBTask;
import org.quandl.jfx.view.wiki.tasks.AddWikiStockTabTask;

/**
 *
 * @author frederic
 */
public class QuandlFX extends Application {

    private final TableView<DataSetFX> table = new TableView<>();
    private final TabPane tabPane = new TabPane();
    private final ObservableList<DataSetFX> data = FXCollections.observableArrayList();
    private final ObservableList<DataSetFX> complete_data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

//        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        BorderPane borderPane = new BorderPane();

//        MENU
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem menuItemFileExit = new MenuItem("Exit", new ImageView(new Image("icons/ic_exit_to_app_black_18dp.png", 14, 14, false, false)));
        menuFile.getItems().add(menuItemFileExit);

        Menu menuTabs = new Menu("Tabs");
        MenuItem menuItemTabExit = new MenuItem("Close all tabs", new ImageView(new Image("icons/ic_close_black_18dp.png", 14, 14, false, false)));
        menuTabs.getItems().add(menuItemTabExit);

        menuBar.getMenus().addAll(menuFile, menuTabs);

        menuItemFileExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        menuItemTabExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Tab> tabs = tabPane.getTabs();
                tabPane.getTabs().removeAll(tabs);
            }
        });

//        LEFT

        HBox hbox = new HBox();
        
        TextField textField = new TextField();
        textField.textProperty().addListener(new DataSetFilterListener(data, complete_data));
        hbox.setHgrow(textField, Priority.ALWAYS);

        Button clearTextFieldButton = new Button("",new ImageView(new Image("icons/ic_close_black_18dp.png", 14, 14, false, false)));
        hbox.getChildren().addAll(textField,clearTextFieldButton);
        
        clearTextFieldButton.setOnAction((ActionEvent event) -> { textField.clear();});
        
        Button selectDataSetButton = new Button("Select");
        selectDataSetButton.setPrefHeight(50D);
        selectDataSetButton.setPrefWidth(Double.MAX_VALUE);

        selectDataSetButton.setOnAction(new DataSetFXEventHandler(table, tabPane));

        TableColumn<DataSetFX, String> codeColumn = new TableColumn<DataSetFX, String>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn descriptionColumn = new TableColumn("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(codeColumn, descriptionColumn);
        table.setItems(data);

        VBox vBox = new VBox();
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().addAll(hbox, table, selectDataSetButton);
//        vBox.getChildren().addAll(textField, table, selectDataSetButton);
        vBox.setStyle("-fx-background-color: #336699;");
        vBox.setPrefWidth(500D);

        borderPane.setTop(menuBar);
        borderPane.setLeft(vBox);
        borderPane.setCenter(tabPane);

        StackPane root = new StackPane();
        root.getChildren().addAll(borderPane);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Quandl JAVA_FX");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        Thread t = new Thread(new CreateWIKIDBTask(data, complete_data));
        t.setDaemon(true);
        t.start();

        table.setSkin(new DataSetTableViewSkin(table));

        data.addListener(new ListChangeListener<DataSetFX>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends DataSetFX> c) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        DataSetTableViewSkin ds = (DataSetTableViewSkin) table.getSkin();
                        ds.resizeColumnToFitContent(table.getColumns().get(1), Integer.MAX_VALUE);
                    }

                });
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
