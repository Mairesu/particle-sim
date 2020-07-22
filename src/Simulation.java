import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private int height;
    private int width;
    private int step = 0;
    private int maxStep;
    private Surface surface;
    private List<ParticleCluster> clusters;
    private Random rng = new Random();
    private Location startingLocation;

    private boolean simulateWithDelay;
    private boolean addMoreParticles;

    /*
    ##### CHANGE THESE VALUES TO CHANGE THE BEHAVIOUR OF THE SIMULATOR
    */
    private static final int STARTING_PARTICLES = 512;
    private static final int MAX_PARTICLES = 512;
    //Total chance for moving in any diagonal direction. the chance is distributed between the available slots in those directions
    private static final int DIAGONAL_CHANCE = 10; // out of 100, any diagonal
    //Total chance for up/down/left/right. the chance is distributed between the available slots in those directions
    private static final int CARDINAL_CHANCE = 20; // out of 100, any cardinal
    /*
    #####
    */

    private SimulationView simView;

    public Simulation(int height, int width, int maxStep, boolean simulateWithDelay, boolean addMoreParticles) {
        this.surface = new Surface(height, width);
        this.maxStep = maxStep;
        this.height = height;
        this.width = width;
        this.simulateWithDelay = simulateWithDelay;
        this.addMoreParticles = addMoreParticles;
        this.simView = new SimulationView(this.height, this.width);
        this.startingLocation = new Location(height / 2,  width / 2);
        clusters = new ArrayList<>();
    }

    public void run() {
        populate();
        while(step <= maxStep) {
            simulateStep();
        }
    }

    @SuppressWarnings("Duplicates")
    private void simulateStep() {
        System.out.println("\n- Step: " + step + " -");

        ArrayList<ParticleCluster> newClusters = new ArrayList<>();

        int highestParticleCount = 0;

        for(ParticleCluster p : clusters) {
            //Check if the cluster has more than 0 particles in it, otherwise skip it
            if(p.getParticles() > 0) {

                int initialParticles = p.getParticles();
                int particlesChecked = 0;

                while(initialParticles > particlesChecked) {
                    int randomNumber = rng.nextInt(100) + 1; //Random number between 1 and 100

                    //Figure out if we want to move the particle, and where
                    if(randomNumber <= DIAGONAL_CHANCE) { //Move diagonally
                        //Find new diagonal location
                        Location newLocation = findDiagonalLocation(p);

                        //Check if the new location contains a cluster, if not create one
                        if(null != surface.getObjectAt(newLocation)) {
                            if(((ParticleCluster) surface.getObjectAt(newLocation)).getParticles() < MAX_PARTICLES)    {
                                p.decreaseParticles();
                                ((ParticleCluster) surface.getObjectAt(newLocation)).increaseParticles();
                            }
                        }
                        else {
                            ParticleCluster newCluster = new ParticleCluster(0, MAX_PARTICLES, newLocation);
                            newClusters.add(newCluster);
                            surface.place(newCluster, newLocation);
                            p.decreaseParticles();
                            newCluster.increaseParticles();
                        }
                    }
                    else if(randomNumber <= DIAGONAL_CHANCE + CARDINAL_CHANCE) { //Move up or down
                        //Find new cardinal location
                        Location newLocation = findCardinalLocation(p);

                        //Check if the new location contains a cluster, if not create one
                        if(null != surface.getObjectAt(newLocation)) {
                            if(((ParticleCluster) surface.getObjectAt(newLocation)).getParticles() < MAX_PARTICLES)    {
                                p.decreaseParticles();
                                ((ParticleCluster) surface.getObjectAt(newLocation)).increaseParticles();
                            }
                        }
                        else {
                            ParticleCluster newCluster = new ParticleCluster(0, MAX_PARTICLES, newLocation);
                            newClusters.add(newCluster);
                            surface.place(newCluster, newLocation);
                            p.decreaseParticles();
                            newCluster.increaseParticles();
                        }
                    }
                    //If none of the checks above happen, do nothing (Particle doesn't move)
                    particlesChecked++;
                }
            }
        }

        clusters.addAll(newClusters);

        //Use this in case you want particles to continuously appear in the middle
        if(addMoreParticles) {
            increaseParticles(STARTING_PARTICLES, startingLocation);
        }

        for(ParticleCluster p : clusters) {
            if(p.getParticles() > 0) {
                //System.out.println("> " + p.getLocation().toString() + " | " + p.getParticles() + "/" + p.getParticleMax());
                if(!addMoreParticles && p.getParticles() > highestParticleCount)    {
                    highestParticleCount = p.getParticles();
                }
            }
        }

        if(addMoreParticles) {
            simView.showStatus(surface, MAX_PARTICLES);
        }
        else {
            simView.showStatus(surface, highestParticleCount);
            System.out.println("Highest Particle count: " + highestParticleCount);
        }

        step++;

        if(simulateWithDelay) {
            delay(25);
        }
    }

    @SuppressWarnings("Duplicates")
    private void populate() {
        System.out.println("\n- Step: " + step + " -");
        ParticleCluster startingCluster = new ParticleCluster(STARTING_PARTICLES, MAX_PARTICLES, startingLocation);
        clusters.add(startingCluster);
        surface.place(startingCluster, startingLocation);

        for(ParticleCluster p : clusters) {
            if(p.getParticles() > 0) {
                System.out.println("> " + p.getLocation().toString() + " | " + p.getParticles() + "/" + p.getParticleMax());
            }
        }

        simView.showStatus(surface, MAX_PARTICLES);
        step++;

        if(simulateWithDelay) {
            delay(25);
        }
    }

    @SuppressWarnings("Duplicates")
    private Location findDiagonalLocation(ParticleCluster cluster) {
        Location oldLocation = cluster.getLocation();
        //Figure out which corner to move the particle to

        //Dirty solution, change later?
        List<Location> possibleLocations = new ArrayList<>();
        possibleLocations.add(new Location(oldLocation.getRow() + 1, oldLocation.getCol() + 1));
        possibleLocations.add(new Location(oldLocation.getRow() + 1, oldLocation.getCol() - 1));
        possibleLocations.add(new Location(oldLocation.getRow() - 1, oldLocation.getCol() + 1));
        possibleLocations.add(new Location(oldLocation.getRow() - 1, oldLocation.getCol() - 1));

        List<Location> invalidLocations = new ArrayList<>();

        //find invalid locations and remove them
        for(Location l : possibleLocations) {
            if(l.getRow() < 0 || l.getRow() > height - 1 || l.getCol() < 0 || l.getCol() > width - 1) {
                invalidLocations.add(l);
            }
        }

        possibleLocations.removeAll(invalidLocations);

        int randomNumber = rng.nextInt(possibleLocations.size());

        return possibleLocations.get(randomNumber);
    }

    @SuppressWarnings("Duplicates")
    private Location findCardinalLocation(ParticleCluster cluster) {
        Location oldLocation = cluster.getLocation();
        //Figure out which corner to move the particle to

        //Dirty solution, change later?
        List<Location> possibleLocations = new ArrayList<>();
        possibleLocations.add(new Location(oldLocation.getRow() + 1, oldLocation.getCol()));
        possibleLocations.add(new Location(oldLocation.getRow() - 1, oldLocation.getCol()));
        possibleLocations.add(new Location(oldLocation.getRow(), oldLocation.getCol() + 1));
        possibleLocations.add(new Location(oldLocation.getRow(), oldLocation.getCol() - 1));

        List<Location> invalidLocations = new ArrayList<>();

        //find invalid locations and remove them
        for(Location l : possibleLocations) {
            if(l.getRow() < 0 || l.getRow() > height - 1 || l.getCol() < 0 || l.getCol() > width - 1) {
                invalidLocations.add(l);
            }
        }

        possibleLocations.removeAll(invalidLocations);

        int randomNumber = rng.nextInt(possibleLocations.size());

        return possibleLocations.get(randomNumber);
    }

    private void increaseParticles(int amount, Location location)    {
        ((ParticleCluster) surface.getObjectAt(location)).increaseParticlesBy(amount);
    }

    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch(InterruptedException ie) {
            // wake up
        }
    }
}
