import java.util.*;

public class Simulator {
    private ArrayList<Car> cars;
    private HashMap<Pair, Tile> grid;
    private int time = 0;
    private ArrayList<BoardEntry> leaderBoard;

    public Simulator(ArrayList<Car> cars, HashMap<Pair, Tile> grid) {
        this.cars = cars;
        this.grid = grid;
        this.leaderBoard = new ArrayList<>();
    }

    /** Written by Saif and edited by Alex
     * This is the method to update our race. Here all cars make a move and we update their position in grid.
     */
    public void step() {
        // Remove all cars from the map (so they can update their position.)
        time+=1;
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            //if (car.hasFinished()) continue; // This prevents adding the same car multiple times.
            int x = car.getX();
            int y = car.getY();
            grid.put(new Pair(x, y), Tile.EMPTY_TILE);          // Replace the old location with empty tile.
            // make the cars move:
            car.drive();
            int newX = car.getX();
            int newY = car.getY();
            if(!car.hasFinished())
                grid.put(new Pair(newX, newY), car);                //Show the car image on the next tile
            if(car.hasFinished()) {
                cars.remove(car);
                leaderBoard.add(new BoardEntry(car, time));
            }
        }
    }

    public boolean raceFinished(){ return cars.size() == 0;}

    /** Written by Saif edited by Alex
     * We use this instead of a constructor for now.
     * (This is just temporary, but shows how the map is made.)
     * @return
     */
    public final static Simulator generateRace(HashMap<Pair, Tile> gameBoard, GameCreator gameCreator) {
        gameCreator.unoccupiedTiles(gameCreator.getTilesMap());
        //There will always be four checkpoints to pick from
        ArrayList<Pair> checkpoints = gameCreator.getCheckpoints();

        //Generating the list of cars
        ArrayList<Car> cars = new ArrayList<>(Arrays.asList(
                new Car(0, 0, new ArrayList<>(Arrays.asList(checkpoints.get(0), checkpoints.get(1), checkpoints.get(2), checkpoints.get(3))), gameBoard, "Red Car", "red", 2),
                new Car(0, 4, new ArrayList(Arrays.asList(checkpoints.get(1), checkpoints.get(2), checkpoints.get(3), checkpoints.get(0))), gameBoard, "Blue Car", "blue", 1),
                new Car(0, 8, new ArrayList(Arrays.asList(checkpoints.get(3), checkpoints.get(0), checkpoints.get(1), checkpoints.get(2))), gameBoard, "Green Car","green", 1)
        ));

        return generateHelper(cars, gameBoard);
    }

    //Written by Saif and edited by Alex
    public final static Simulator generateHelper(ArrayList<Car> cars, HashMap<Pair, Tile> map) {
        // Add cars
        for (Car car : cars)
            map.put(new Pair(car.getX(), car.getY()), car);

        return new Simulator(
                cars,
                map
        );
    }


    public ArrayList<BoardEntry> getLeaderBoard() {
        return leaderBoard;
    }


}
