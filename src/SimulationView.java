import javax.swing.*;
import java.awt.*;

public class SimulationView extends JFrame {

    private SurfaceView surfaceView;

    public SimulationView(int height, int width) {
        setTitle("Particle Simulation");

        setLocation(100, 50);

        surfaceView = new SurfaceView(height, width);

        Container contents = getContentPane();
        contents.add(surfaceView, BorderLayout.CENTER);
        pack();
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showStatus(Surface surface, int highestParticleCount)    {
        if (!isVisible()) {
            setVisible(true);
        }

        surfaceView.preparePaint();

        for (int row = 0; row < surface.getHeight(); row++) {
            for (int col = 0; col < surface.getWidth(); col++) {
                ParticleCluster p = (ParticleCluster) surface.getObjectAt(row, col);
                if (p != null) {
                    surfaceView.drawMark(col, row, getColor(p.getParticles(), highestParticleCount));
                } else {
                    surfaceView.drawMark(col, row, getColor(0, 0));
                }
            }
        }
        surfaceView.repaint();
    }

    private Color getColor(int value, int highestParticleCount) {

        Color returnColor;
        int switchValue = value;

        if(value > highestParticleCount)    {
            switchValue = 10;
        }
        else if(highestParticleCount > 10)   {
            double slope = 10d/(double)highestParticleCount;
            switchValue = (int)Math.ceil(slope*(value));
        }

        switch(switchValue) {
            case 0:
                returnColor = Color.decode("#ffffff");
                break;

            case 1:
                returnColor = Color.decode("#b4ff99");
                break;

            case 2:
                returnColor = Color.decode("#86ef7f");
                break;

            case 3:
                returnColor = Color.decode("#68e07b");
                break;

            case 4:
                returnColor = Color.decode("#53d180");
                break;

            case 5:
                returnColor = Color.decode("#40c18a");
                break;

            case 6:
                returnColor = Color.decode("#2fb295");
                break;

            case 7:
                returnColor = Color.decode("#20a3a1");
                break;

            case 8:
                returnColor = Color.decode("#137a93");
                break;

            case 9:
                returnColor = Color.decode("#085284");
                break;

            case 10:
                returnColor = Color.decode("#002d75");
                break;

            default:
                returnColor = Color.decode("#ffffff");
                break;
        }

        return returnColor;
    }
}
