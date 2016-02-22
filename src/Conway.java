import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.lang.Integer;

/**
 * Conway - A small version of Conway's Game of Life.
 *          http://www.conwaylife.com/wiki/Conway's_Game_of_Life
 *          Code Excerpts from: Andrew Nuxoll
 *                              &
 *                              https://docs.oracle.com/javase/tutorial/uiswing/
 *                                  examples/layout/GridBagLayoutDemoProject/
 *
 * @author Lucas Burns
 *
 * @version 19 Feb 2016
 */
public class Conway
{
    // ==========================================
    // Constants
    // ==========================================
    public static final int MAX_BOARD_SIZE = 1000;
    public static final int BUFFER_DISTANCE = 15;
    public static final int CELL_SIZE = 4;
    public static final int BOARD_OFFSET = (int) Math.floor(0.4 * MAX_BOARD_SIZE);
    public static final int WINDOW_SIZE = (BOARD_OFFSET/2 * CELL_SIZE) + (2 * BUFFER_DISTANCE);

    // ==========================================
    // Class parameters
    // ==========================================
    public static int seedIn = 0;

    public static boolean hasDim = true;
    public static boolean hasSeed = false;
    public static boolean isRunning = false;

    public static Canvas myCanvas = null;
    public static BufferStrategy strategy = null;
    public static Board myBoard = null;

    /**
     * main - Method to kick the whole thing off
     * @param args Command line input parameters
     */
    public static void main (String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * startGame - Initializes board with user input
     */
    private static void startGame ()
    {
        // Create a new board with the given dimensions
        Board board = new Board(MAX_BOARD_SIZE);
        // Randomly seed the board with the given seed
        myBoard = board.seed(seedIn);
    }

    /**
     * run - Opens a new window for the game to play in, sets up canvas/buffer,
     */
    public static void run()
    {
        // Create the JFrame that the game will be in
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        frame.setSize(WINDOW_SIZE, WINDOW_SIZE + 27);

        Canvas newCanvas = new Canvas();
        frame.getContentPane().add(newCanvas);

        myCanvas = newCanvas;
        frame.setVisible(true);

        isRunning = true;

        myCanvas.createBufferStrategy(2);
        strategy = myCanvas.getBufferStrategy();

        startGame();

        while (isRunning && myBoard.totalLiving() > 0)
        {
            myBoard.advance();
            repaint();
            try
            {
                Thread.sleep(50);
            }
            catch(InterruptedException ie)
            {
                //it's safe to ignore this
            }
        }

        popup("Game Over", "Your colony survived for " + myBoard.getGeneration() + " generations.");
    }

    /**
     * createAndShowGUI - Creates the start window
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     *
     * @param pane Area to add content
     */
    public static void addComponentsToPane(Container pane)
    {
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton startButton = new JButton("Start Game");
        startButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(startButton, c);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning)
                {
                    pauseButton.setText("Resume");
                    isRunning = false;
                }
                else
                {
                    pauseButton.setText("Pause");
                    isRunning = true;
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(pauseButton, c);

//        JTextField dimField = new JTextField();
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 1;
//        pane.add(dimField, c);

//        JButton dimButton = new JButton("Set Dimensions");
//        dimButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String getText = dimField.getText();
//                if (getText.matches("\\d+"))
//                {
//                    int input = Integer.parseInt(getText);
//                    if (input < 1 || input > MAX_GAME_SIZE)
//                    {
//                        popup("Please enter an integer from 1 to " + MAX_GAME_SIZE + '.');
//                        return;
//                    }
//
//                    MAX_BOARD_SIZE = input;
//                    hasDim = true;
//                    if (hasSeed)
//                    {
//                        startButton.setEnabled(true);
//                    }
//                }
//                else
//                {
//                    popup("Please enter an integer from 1 to ");
//                }
//            }
//        });
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 1;
//        pane.add(dimButton, c);

        JTextField seedField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(seedField, c);

        JButton seedButton = new JButton("Set Seed");
        seedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getText = seedField.getText();
                if (getText.matches("\\d+"))
                {
                    seedIn = Integer.parseInt(getText);
                    hasSeed = true;
                    if (hasDim)
                    {
                        startButton.setEnabled(true);
                    }
                }
                else
                {
                    popup("Error", "Please enter an integer.");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(seedButton, c);
    }

    public static void popup (String name, String message)
    {
        int messageType = JOptionPane.PLAIN_MESSAGE;
        JOptionPane.showMessageDialog(null, message, name, messageType);
    }

    public static void repaint()
    {
        //Retrieve the canvas used for double buffering
        Graphics hiddenGraphics = strategy.getDrawGraphics();

        //Start with a black pen on a white background
        hiddenGraphics.setColor(Color.white);
        hiddenGraphics.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        hiddenGraphics.setColor(Color.black);

        //Draw the next frame in the game animation
        paint(hiddenGraphics);

        //Display the new canvas to the user
        strategy.show();
        try {
            Thread.sleep(50);
        } catch (InterruptedException ie) {
            //it's safe to ignore this
        }
    }

    /**
     * paint -
     * @param canvas Graphics object that will be drawn on
     */
    public static void paint (Graphics canvas)
    {
        int currX = BUFFER_DISTANCE;
        int currY = BUFFER_DISTANCE;
        canvas.setColor(Color.black);
        for (int x = BOARD_OFFSET; x < MAX_BOARD_SIZE - BOARD_OFFSET; x++)
        {
            for (int y = BOARD_OFFSET; y < MAX_BOARD_SIZE - BOARD_OFFSET; y++)
            {
                if (myBoard.isAlive(x, y))
                {
                    canvas.fillRect(currX, currY, CELL_SIZE, CELL_SIZE);
                }
                else
                {
                    canvas.drawRect(currX, currY, CELL_SIZE, CELL_SIZE);
                }
                currY = currY + CELL_SIZE;
            }
            currY = BUFFER_DISTANCE;
            currX = currX + CELL_SIZE;
        }
    }
}