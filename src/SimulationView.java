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
                returnColor = Color.decode("#e1f5fe");
                break;

            case 2:
                returnColor = Color.decode("#b3e5fc");
                break;

            case 3:
                returnColor = Color.decode("#81d4fa");
                break;

            case 4:
                returnColor = Color.decode("#4fc3f7");
                break;

            case 5:
                returnColor = Color.decode("#29b6f6");
                break;

            case 6:
                returnColor = Color.decode("#03a9f4");
                break;

            case 7:
                returnColor = Color.decode("#039be5");
                break;

            case 8:
                returnColor = Color.decode("#0288d1");
                break;

            case 9:
                returnColor = Color.decode("#0277bd");
                break;

            case 10:
                returnColor = Color.decode("#01579b");
                break;

            default:
                returnColor = Color.decode("#ffffff");
                break;
        }

        return returnColor;
    }
}
