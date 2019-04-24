public class Main {

    public static void main(String[] args) {
        int height = 11;
        int width = 11;
        int steps = 5;
        Simulation sim = new Simulation(height, width, steps);
        sim.run();
    }
}
