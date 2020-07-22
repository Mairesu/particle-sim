import javax.swing.*;
import java.awt.*;

public class SurfaceView extends JPanel {
    private static final int GRID_VIEW_SCALING_FACTOR = 5;

    private int gridWidth, gridHeight;
    private int xScale, yScale;
    Dimension size;
    private Graphics g;
    private Image surfaceImage;

    public SurfaceView(int height, int width)   {
        gridHeight = height;
        gridWidth = width;
        size = new Dimension(0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                             gridHeight * GRID_VIEW_SCALING_FACTOR);
    }

    public void preparePaint() {
        if (!size.equals(getSize())) {  // if the size has changed...
            size = getSize();
            surfaceImage = createImage(size.width, size.height);
            g = surfaceImage.getGraphics();

            xScale = size.width / gridWidth;
            if (xScale < 1) {
                xScale = GRID_VIEW_SCALING_FACTOR;
            }
            yScale = size.height / gridHeight;
            if (yScale < 1) {
                yScale = GRID_VIEW_SCALING_FACTOR;
            }
        }
    }

    public void drawMark(int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * xScale, y * yScale, xScale, yScale);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (surfaceImage != null) {
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(surfaceImage, 0, 0, null);
            } else {
                // Rescale the previous image.
                g.drawImage(surfaceImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    }

}
