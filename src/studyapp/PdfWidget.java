package studyapp;

import java.awt.Dimension;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.icepdf.ri.common.SwingController;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import javafx.stage.WindowEvent;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author Rashid Iqbal
 * @email v3rsion9@gmail.com
 * @github https://github.com/rashidcoder
 *
 */
public class PdfWidget {

    private SwingController swingController;

    private JComponent viewerPanel;

    public Scene start(String url) {

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);

        // add viewer content pane
        borderPane = createViewer(borderPane);

        borderPane.setPrefSize(1025, 600);

        createResizeListeners(scene, viewerPanel);

        Stage secondStage = new Stage();

        secondStage.setOnCloseRequest((WindowEvent arg2) -> {
            SwingUtilities.invokeLater(() -> swingController.dispose());
        });
        secondStage.setTitle("JavaFX PDF Viewer Demo");
        secondStage.setScene(scene);
        openDocument(url);
        return scene;
    }

    private void createResizeListeners(Scene scene, JComponent viewerPanel) {
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            SwingUtilities.invokeLater(() -> {
                viewerPanel.setSize(new Dimension(newValue.intValue(), (int) scene.getHeight()));
                viewerPanel.setPreferredSize(new Dimension(newValue.intValue(), (int) scene.getHeight()));
                viewerPanel.repaint();
            });
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            SwingUtilities.invokeLater(() -> {
                viewerPanel.setSize(new Dimension((int) scene.getWidth(), newValue.intValue()));
                viewerPanel.setPreferredSize(new Dimension((int) scene.getWidth(), newValue.intValue()));
                viewerPanel.repaint();
            });
        });
    }

    private BorderPane createViewer(BorderPane borderPane) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // create the viewer ri components.
                swingController = new SwingController();
                swingController.setIsEmbeddedComponent(true);

                swingController.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));

                SwingViewBuilder factory = new SwingViewBuilder(swingController);

                viewerPanel = factory.buildViewerPanel();
                viewerPanel.revalidate();

                SwingNode swingNode = new SwingNode();
                swingNode.setContent(viewerPanel);
                borderPane.setCenter(swingNode);

            });
        } catch (InterruptedException | InvocationTargetException e) {
        }
        return borderPane;
    }

    private void openDocument(String url) {
        SwingUtilities.invokeLater(() -> {
            swingController.openDocument(url);
            viewerPanel.revalidate();
        });

    }

    private void buildButton(FlowPane flowPane, AbstractButton jButton) {
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jButton);
        flowPane.getChildren().add(swingNode);
    }

    private void buildJToolBar(FlowPane flowPane, JToolBar jToolBar) {
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jToolBar);
        flowPane.getChildren().add(swingNode);
    }

}
