package org.quandl.jfx.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.quandl.jfx.view.wiki.DataSetTableViewSkin;
import org.quandl.jfx.view.wiki.listener.DataSetFilterListener;
import org.quandl.jfx.view.wiki.listener.DataSetTableChangeListener;
import org.quandl.jfx.view.wiki.tasks.CreateWIKIDBTask;

/**
 *
 * @author frederic
 */
public class QuandlFX_Main extends Application {

    private final TableView<DataSetFX> table = new TableView<>();
    private final TabPane tabPane = new TabPane();
    private final ObservableList<DataSetFX> data = FXCollections.observableArrayList();
    private final ObservableList<DataSetFX> complete_data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        DataSetTableChangeListener listener = new DataSetTableChangeListener(table, tabPane);
        table.getSelectionModel().selectedItemProperty().addListener(listener);
        table.setEditable(true);
        
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
        TextField textField = new TextField();
        textField.textProperty().addListener(new DataSetFilterListener(data, complete_data));
        
        Button button = new Button("Select");
        button.setPrefWidth(Double.MAX_VALUE);

        TableColumn<DataSetFX, String> codeColumn = new TableColumn<DataSetFX, String>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn descriptionColumn = new TableColumn("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(codeColumn, descriptionColumn);
        table.setItems(data);

        VBox vBox = new VBox();
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().addAll(textField, table, button);
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

        Thread t = new Thread(new CreateWIKIDBTask(data,complete_data));
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
