public class Main {

    public static void main(String[] args) {
        int height = 10;
        int width = 10;
        int steps = 10;
        Simulation sim = new Simulation(height, width, steps);
        sim.run();
    }
}
