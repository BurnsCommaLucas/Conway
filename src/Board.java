/**
 * Created by Lucas on 2/18/16.
 */
import java.util.Random;

public class Board {

    // Class variables
    private int dim;
    private boolean seedComplete;
    private boolean[][] grid;
    Random generator;

    /**
     * Board - Default constructor for Board class
     */
    public Board()
    {
        dim = 10;
        seedComplete = false;
        grid = new boolean[dim][dim];

        for (int i = 0; i < dim; i ++)
        {
            for (int j = 0; j < dim; j++)
            {
                grid[i][j] = false;
            }
        }
    }

    /**
     * Board - Specialized constructor for Board class
     *
     * @param initDim Dimension to initialize board with
     */
    public Board(int initDim)
    {
        dim = initDim;
        seedComplete = false;
        grid = new boolean[dim][dim];

        for (int i = 0; i < dim; i ++)
        {
            for (int j = 0; j < dim; j++)
            {
                grid[i][j] = false;
            }
        }
    }

    /**
     * isSeeded - Method to check if board has been seeded/initialized
     *
     * @return bool
     */
    public boolean isSeeded ()
    {
        return this.seedComplete;
    }

    /**
     * seed - Method to seed the board with a random number
     */
    public void seed ()
    {
        generator = new Random();
    }

    /**
     * seed - Method to seed the board with a random number
     *
     * @param seed String to input random number
     */
    public void seed (String seed)
    {

    }
}
