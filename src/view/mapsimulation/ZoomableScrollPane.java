package view.mapsimulation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

public class ZoomableScrollPane extends ScrollPane {
    private int deltaCount = 0;
    private final double DEFAULT_ZOOM = 1.0;
    
    private DoubleProperty zoomMax = new SimpleDoubleProperty(10.0);
    private DoubleProperty zoomMin = new SimpleDoubleProperty(0.1);
    private DoubleProperty zoomDelta = new SimpleDoubleProperty(1.2);
    private DoubleProperty zoom = new SimpleDoubleProperty(DEFAULT_ZOOM);

    public ZoomableScrollPane(Node content) {
        super(new Group(content));
        this.setPannable(true);
        zoom.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number newZoom) {
                System.out.println("Zoom=" + newZoom.doubleValue());
                content.setScaleX(newZoom.doubleValue());
                content.setScaleY(newZoom.doubleValue());
            }

        });

        content.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomIn();
                } else {
                    zoomOut();
                }
            }
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
