public class Main {

    public static void main(String[] args) {
        int height = 99;
        int width = 99;
        int steps = Integer.MAX_VALUE;
        boolean delay = false;
        boolean addMoreParticles = true;
        Simulation sim = new Simulation(height, width, steps, delay, addMoreParticles);
        sim.run();
    }
}
