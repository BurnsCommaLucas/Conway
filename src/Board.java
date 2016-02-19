/**
 * Created by Lucas on 2/18/16.
 */
import java.util.Random;
import java.util.Date;

public class Board {

    // Class variables
    private int dim;
    private boolean seedComplete;
    private boolean[][] grid;
    private Random generator;
    private final int THRESHOLD = 10;

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
     *
     * @param seed String to input random number
     */
    public Board seed (int seed)
    {
        generator = new Random((long) seed);
        int start = (int) 2 * dim / 5;
        int stop = dim - start;

        for (int x = start; x < stop; x ++)
        {
            for (int y = start; y < stop; y++)
            {
                if (generator.nextInt(100) < THRESHOLD)
                {
                    this.grid[x][y] = true;
                }
            }
        }

        seedComplete = true;

        return this;
    }

    /**
     * getDim - Method to find the dimensions of the board
     *
     * @return Int dimension of square board
     */
    public int getDim ()
    {
        return this.dim;
    }

    public boolean isAlive (int x, int y)
    {
        return this.grid[x][y];
    }

    private int numAdj(int x, int y)
    {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                if (x + dx >= 0 && x + dx < this.getDim() &&
                        y + dy >= 0 && y + dy < this.getDim())
                {
                    if (this.isAlive(x + dx, y + dy))
                    {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public void advance ()
    {
        boolean[][] newGrid = this.grid;

        for (int x = 0; x < this.dim; x++)
        {
            for (int y = 0; y < this.dim; y++)
            {
                int adj = this.numAdj(x, y);

                if (this.isAlive(x, y))
                {
                    if (adj < 2 || adj > 3)
                    {
                        newGrid[x][y] = false;
                    }
                }
                else
                {
                    if (adj == 3)
                    {
                        newGrid[x][y] = true;
                    }
                }
            }
        }
    }

    public int totalLiving ()
    {
        int count = 0;

        for (int x = 0; x < this.dim; x++)
        {
            for (int y = 0; y < this.dim; y++)
            {
                if (this.isAlive(x, y))
                {
                    count++;
                }
            }
        }

        return count;
    }
}
