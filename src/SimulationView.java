import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SimulationView extends JFrame {

    private SurfaceView surfaceView;
    private HashMap<Integer, Color> colourMap;
    private int colours;

    public SimulationView(int height, int width) {
        colourMap = new HashMap<>();
        fillColourHashMap();
        colours = colourMap.size()-1; //Amount of colours, excluding white

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
                if (p != null && p.getParticles() > 0)  {
                    surfaceView.drawMark(col, row, getColor(p.getParticles(), highestParticleCount));
                } else {
                    surfaceView.drawMark(col, row, getColor(0, 0));
                }
            }
        }
        surfaceView.repaint();
    }

    private void fillColourHashMap()    {

        try(Stream<String> s = Files.lines(Paths.get("src/resources/colours.txt"), Charset.defaultCharset()))    {
            AtomicInteger number = new AtomicInteger();
            s.forEachOrdered(line -> {
                this.colourMap.put(number.get(), Color.decode(line));
                number.getAndIncrement();
            });
        }
        catch(IOException e) {
            System.out.println("Colour file read failed");
            e.printStackTrace();
        }
        //Add to colour map
    }

    private Color getColor(int value, int highestParticleCount) {

        double slope = (double)this.colours/(double)highestParticleCount;
        int switchValue = (int)Math.ceil(slope*(value));

        return colourMap.get(switchValue);
    }
}
