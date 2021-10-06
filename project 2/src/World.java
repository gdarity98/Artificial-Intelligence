public class World {
    private String[][] filledWorld;
    private int size = 0;
    //more attributes including position of explorer and such

    public World(int size){
        this.size = size;
        // create world in filledWorld based on size
    }

    public String[][] getFilledWorld() {
        return filledWorld;
    }

    // More get methods
}
