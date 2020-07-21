package view.mapsimulation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class ZoomableScrollPane extends ScrollPane {
    private int deltaCount = 0;
    private final double DEFAULT_ZOOM = 1.0;
    
    private double scaleValue = 0.7;
    private final double zoomIntensity = 0.02;
    private final Node target;
    private final Node zoomNode;
    
    private final DoubleProperty zoomMax = new SimpleDoubleProperty(10.0);
    private final DoubleProperty zoomMin = new SimpleDoubleProperty(0.1);
    private final DoubleProperty zoomDelta = new SimpleDoubleProperty(1.2);
    private final DoubleProperty zoom = new SimpleDoubleProperty(DEFAULT_ZOOM);

    public ZoomableScrollPane(Group target) {
        super();
        this.target = target;
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));
        
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true); //center
        setFitToWidth(true); //center
        
        updateScale();
        
        setOnMousePressed(e -> {
            if ((e.getButton() == MouseButton.MIDDLE) || 
                (e.getButton() == MouseButton.SECONDARY)) setPannable(true);
        });
        setOnMouseReleased(e -> {
            if ((e.getButton() == MouseButton.MIDDLE) || 
                (e.getButton() == MouseButton.SECONDARY)) setPannable(false);
        });
        /*
        StackPane pane = new StackPane(content);
        
        Group group = new Group(pane);
        
        group.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // keep it at least as large as the content
            pane.setMinWidth(newBounds.getWidth());
            pane.setMinHeight(newBounds.getHeight());
        });
        setContent(pane);
        this.setPannable(true);
        
        addEventFilter(ScrollEvent.ANY, (ScrollEvent evt) -> {
            
            final double zoomFactor = evt.getDeltaY() > 0 ? 1.2 : 1 / 1.2;

            Bounds groupBounds = group.getLayoutBounds();
            final Bounds viewportBounds = getViewportBounds();

            // calculate pixel offsets from [0, 1] range
            double valX = getHvalue() * (groupBounds.getWidth() - viewportBounds.getWidth());
            double valY = getVvalue() * (groupBounds.getHeight() - viewportBounds.getHeight());

            // convert content coordinates to zoomTarget coordinates
            Point2D posInZoomTarget = pane.parentToLocal(group.parentToLocal(new Point2D(evt.getX(), evt.getY())));

            // calculate adjustment of scroll position (pixels)
            Point2D adjustment = pane.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

            // do the resizing
            pane.setScaleX(zoomFactor * pane.getScaleX());
            pane.setScaleY(zoomFactor * pane.getScaleY());

            // refresh ScrollPane scroll positions & content bounds
            layout();

            // convert back to [0, 1] range
            // (too large/small values are automatically corrected by ScrollPane)
            groupBounds = group.getLayoutBounds();
            setHvalue((valX + adjustment.getX()) / (groupBounds.getWidth() - viewportBounds.getWidth()));
            setVvalue((valY + adjustment.getY()) / (groupBounds.getHeight() - viewportBounds.getHeight()));
            evt.consume();
            
        });*/
         /*
        viewportBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // use vieport size, if not too small for zoomTarget
            pane.setPrefSize(newBounds.getWidth(), newBounds.getHeight());
        });
        setFitToHeight(true); //center
        setFitToWidth(true); //center
        
        zoom.addListener((ObservableValue<? extends Number> ov, Number t, Number newZoom) -> {
            System.out.println("Zoom=" + newZoom.doubleValue());
            //hacer mejor
            pane.setScaleX(newZoom.doubleValue());
            pane.setScaleY(newZoom.doubleValue());
        });

        addEventFilter(ScrollEvent.ANY, (ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
            event.consume();
        });
        
        
        
        this.viewportBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // keep it at least as large as the content
            //
        });
        
        this.setOnScroll((ScrollEvent event) -> {
            
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });
        // Moving the map view with middle mouse button
        setOnMousePressed(e -> {
            if ((e.getButton() == MouseButton.MIDDLE) || 
                (e.getButton() == MouseButton.SECONDARY)) setPannable(true);
        });
        setOnMouseReleased(e -> {
            if ((e.getButton() == MouseButton.MIDDLE) || 
                (e.getButton() == MouseButton.SECONDARY)) setPannable(false);
        });*/
    }
    
    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e -> {
            onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            e.consume();
        });
        return outerNode;
    }
    
    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }
    
    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());
        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }
    
    public void zoomIn() {
        
        double zoomValue = DEFAULT_ZOOM * Math.pow(zoomDelta.get(), deltaCount + 1);
        if (zoomValue > zoomMax.get()) {
            setZoom(zoomMax.get());
            return;
        }

        deltaCount++;
        setZoom(zoomValue);

    }

    public void zoomOut() {
        double zoomValue = DEFAULT_ZOOM * Math.pow(zoomDelta.get(), deltaCount - 1);
        if (zoomValue < zoomMin.get()) {
            setZoom(zoomMin.get());
            return;
        }

        deltaCount--;
        setZoom(zoomValue);
    }

    public void setZoom(double zoomValue) {
        zoom.set(zoomValue);
    }
    
    
}
