import java.util.Random;

/**
 * Created by Lucas on 2/18/16.
 */
public class Board
{
    // ==========================================
    // Constants
    // ==========================================
    private final int THRESHOLD = 50;

    // ==========================================
    // Class parameters
    // ==========================================
    private int dim;
    private int genNum;
    private boolean seedComplete;
    private boolean[][] grid;
    private boolean[][] emptyGrid;
    private Random generator;

    /**
     * Board - Default constructor for Board class
     */
    public Board()
    {
        dim = 10;
        genNum = 0;
        seedComplete = false;
        grid = new boolean[dim][dim];

        for (int i = 0; i < dim; i ++)
        {
            for (int j = 0; j < dim; j++)
            {
                emptyGrid[i][j] = false;
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
        genNum = 0;
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

    /**
     * numAdj - Counts the number of living cells adjacent to the one passed in
     *
     * @param x X coordinate of cell to be checked
     * @param y Y coordinate of cell to be checked
     * @return int number of living adjacent cells
     */
    private int numAdj(int x, int y)
    {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                if (dx == 0 && dy == 0) continue;

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

    /**
     * advance - Advances the game to the next generation
     */
    public void advance ()
    {
        this.genNum++;
        boolean[][] newGrid = emptyGrid;

        for (int x = 0; x < this.dim; x++)
        {
            for (int y = 0; y < this.dim; y++)
            {
                int adj = this.numAdj(x, y);

                if (this.isAlive(x, y))
                {
                    if (adj == 2 || adj == 3)
                    {
                        newGrid[x][y] = true;
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

    /**
     * totalLiving - Counts the number of living cells on the board
     * @return int number of living cells
     */
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

    public int getGeneration ()
    {
        return this.genNum;
    }
}
