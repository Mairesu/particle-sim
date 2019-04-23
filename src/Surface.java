public class Surface {

    private Object[][] surface;
    private int height;
    private int width;

    public Surface(int height, int width)   {
        this.height = height;
        this.width = width;
        this.surface = new Object[height][width];
    }

    public void clear() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                surface[row][col] = null;
            }
        }
    }

    public void place(Object cluster, Location location) {
        surface[location.getRow()][location.getCol()] = cluster;
    }

    public Object getObjectAt(int row, int col) {
        return surface[row][col];
    }

    public Object getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getCol());
    }

    public int getHeight()  {
        return this.height;
    }

    public int getWidth()   {
        return this.width;
    }

}
