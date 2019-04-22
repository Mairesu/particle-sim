public class Main {

    public static void main(String[] args) {
        int height = 20;
        int width = 10;
        int steps = 100;
        Simulation sim = new Simulation(height, width, steps);
        sim.run();
    }
}
