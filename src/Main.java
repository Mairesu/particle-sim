public class Main {

    public static void main(String[] args) {
        int height = 51;
        int width = 51;
        int steps = 10000;
        boolean delay = false;
        boolean addMoreParticles = true;
        Simulation sim = new Simulation(height, width, steps, delay, addMoreParticles);
        sim.run();
    }
}
