import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private int height;
    private int width;
    private int step = 1;
    private int maxStep;
    private Surface surface;
    private List<ParticleCluster> clusters;
    private Random rng = new Random();

    private static final int STARTING_PARTICLES = 5;
    private static final int MAX_PARTICLES = 10;

    public Simulation(int height, int width, int maxStep) {
        this.surface = new Surface(height, width);
        this.maxStep = maxStep;
        this.height = height;
        this.width = width;
        clusters = new ArrayList<>();
    }

    public void run()   {
        populate();
        while(step <= maxStep)  {
            simulateStep();
        }
    }

    private void simulateStep() {
        System.out.println("Step: " + step);

            ArrayList<ParticleCluster> newClusters = new ArrayList<>();

            for(ParticleCluster p : clusters)   {
                int randomNumber = rng.nextInt(100) + 1; //Random number between 1 and 100;

                //TODO Testing movement. change to independent rolls for height and width.
                if(randomNumber <= 10)  {
                    int newHeight = p.getLocation().getRow();
                    int newWidth = p.getLocation().getCol();
                    if(newHeight != height && newHeight > 0) {
                        if(rng.nextBoolean())   {
                            newHeight++;
                        }
                        else {
                            newHeight--;
                        }
                    }
                    if(newWidth != width && newWidth > 0) {
                        if(rng.nextBoolean())   {
                            newWidth++;
                        }
                        else {
                            newWidth--;
                        }
                    }

                    //TODO properly implement "exhange" of particles between clusters
                    //TODO add a check if "p" has 0 particles
                    Location newLocation = new Location(newHeight, newWidth);
                    if(null != surface.getObjectAt(newLocation))    {
                        p.decreaseParticles();
                        if(surface.getObjectAt(newLocation) instanceof ParticleCluster) {
                            ((ParticleCluster) surface.getObjectAt(newLocation)).increaseParticles();
                        }
                    }
                    else {
                        p.decreaseParticles();
                        ParticleCluster newCluster = new ParticleCluster(0, MAX_PARTICLES, newLocation);
                        newClusters.add(newCluster);
                        surface.place(newCluster, newLocation);
                        newCluster.increaseParticles();
                    }
                }
                System.out.println("Col: " + p.getLocation().getCol() + ", Row:" + p.getLocation().getRow() + " - Particles:" + p.getParticles() + "/" + p.getParticleMax());
            }

            clusters.addAll(newClusters);
        step ++;
    }

    private void populate() {
        int middleHeight = height/2;
        int middleWidth = width/2;
        Location startingLocation = new Location(middleHeight, middleWidth);
        ParticleCluster startingCluster = new ParticleCluster(STARTING_PARTICLES, MAX_PARTICLES, startingLocation);
        clusters.add(startingCluster);
        surface.place(startingCluster, startingLocation);
    }
}
