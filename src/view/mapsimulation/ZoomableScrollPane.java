package view.mapsimulation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;

public class ZoomableScrollPane extends ScrollPane {
    private int deltaCount = 0;
    private final double DEFAULT_ZOOM = 1.0;
    
    private final DoubleProperty zoomMax = new SimpleDoubleProperty(10.0);
    private final DoubleProperty zoomMin = new SimpleDoubleProperty(0.1);
    private final DoubleProperty zoomDelta = new SimpleDoubleProperty(1.2);
    private final DoubleProperty zoom = new SimpleDoubleProperty(DEFAULT_ZOOM);

    public ZoomableScrollPane(Node content) {
        super(content);
        
        setFitToHeight(true); //center
        setFitToWidth(true); //center
        
        zoom.addListener((ObservableValue<? extends Number> ov, Number t, Number newZoom) -> {
            System.out.println("Zoom=" + newZoom.doubleValue());
            content.setScaleX(newZoom.doubleValue());
            content.setScaleY(newZoom.doubleValue());
        });

        addEventFilter(ScrollEvent.ANY, (ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
            event.consume();
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
        });
    }
    
    public void zoomIn() {
        
        double zoomValue = DEFAULT_ZOOM * Math.pow(zoomDelta.get(), deltaCount + 1);
        System.out.println("Zoooom " + zoomValue);
        if (zoomValue > zoomMax.get()) {
            setZoom(zoomMax.get());
            return;
        }

        deltaCount++;
        setZoom(zoomValue);

    }

    public void zoomOut() {
        double zoomValue = DEFAULT_ZOOM * Math.pow(zoomDelta.get(), deltaCount - 1);
        System.out.println("Zoooom " + zoomValue);
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
