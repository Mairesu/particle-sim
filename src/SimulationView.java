import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SimulationView extends JFrame {

    private Map<Integer, Color> colorMap;
    private SurfaceView surfaceView;

    public SimulationView(int height, int width) {
        setTitle("Particle Simulation");

        colorMap = new HashMap<>();
        setupColors();

        setLocation(100, 50);

        surfaceView = new SurfaceView(height, width);

        Container contents = getContentPane();
        contents.add(surfaceView, BorderLayout.CENTER);
        pack();
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showStatus(Surface surface)    {
        if (!isVisible()) {
            setVisible(true);
        }

        surfaceView.preparePaint();

        for (int row = 0; row < surface.getHeight(); row++) {
            for (int col = 0; col < surface.getWidth(); col++) {
                ParticleCluster p = (ParticleCluster) surface.getObjectAt(row, col);
                if (p != null) {
                    surfaceView.drawMark(col, row, getColor(p.getParticles()));
                } else {
                    surfaceView.drawMark(col, row, getColor(0));
                }
            }
        }
        surfaceView.repaint();
    }

    private void setupColors() {
        for(int i = 0; i <= 10; i++) {
            colorMap.put(i, getColor(i));
        }
    }

    private Color getColor(int value) {

        Color returnColor;

        switch(value) {
            case 0:
                returnColor = Color.decode("#ffffff");
                break;

            case 1:
                returnColor = Color.decode("#addafb");
                break;

            case 2:
                returnColor = Color.decode("#a1d5fa");
                break;

            case 3:
                returnColor = Color.decode("#96d0fa");
                break;

            case 4:
                returnColor = Color.decode("#8acbf9");
                break;

            case 5:
                returnColor = Color.decode("#7fc6f9");
                break;

            case 6:
                returnColor = Color.decode("#74b4e3");
                break;

            case 7:
                returnColor = Color.decode("#68a3cc");
                break;

            case 8:
                returnColor = Color.decode("#5d91b6");
                break;

            case 9:
                returnColor = Color.decode("#517f9f");
                break;

            case 10:
                returnColor = Color.decode("#466c88");
                break;

            default:
                returnColor = Color.decode("#ffffff");
                break;
        }

        return returnColor;
    }
}
