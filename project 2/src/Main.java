import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){
        //Setting up worlds and putting them in an array for easy access
        World[] worlds = new World[10];
        World worldA5x5 = new World(5);
        World worldB5x5 = new World(5);
        worlds[0] = worldA5x5;
        worlds[1] = worldB5x5;

        World worldA10x10 = new World(10);
        World worldB10x10 = new World(10);
        worlds[2] = worldA10x10;
        worlds[3] = worldB10x10;

        World worldA15x15 = new World(15);
        World worldB15x15 = new World(15);
        worlds[4] = worldA15x15;
        worlds[5] = worldB15x15;

        World worldA20x20 = new World(20);
        World worldB20x20 = new World(20);
        worlds[6] = worldA20x20;
        worlds[7] = worldB20x20;

        World worldA25x25 = new World(25);
        World worldB25x25 = new World(25);
        worlds[8] = worldA25x25;
        worlds[9] = worldB25x25;

        //creating rules and game
        String rules = "Rule1, Rule2, Rule3";
        ResolutionBased game = new ResolutionBased();

        //running the game on each world
        for(World world : worlds){
            game.runGame(world, rules);
        }
    }
}
