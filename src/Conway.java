import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.lang.Integer;

import static java.lang.Thread.sleep;

public class Conway
{
    public static Canvas myCanvas = null;
    public static BufferStrategy strategy = null;

    public static final int MAX_GAME_SIZE = 20;
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    public static boolean isRunning = false;
    public static int dimIn = 1;
    public static int seedIn = 0;
    public static boolean hasDim = false;
    public static boolean hasSeed = false;


    public static void main (String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public Conway(Canvas initCanvas)
    {
        myCanvas = initCanvas;
    }

    public static void repaint() {
        //Retrieve the canvas used for double buffering
        Graphics hiddenCanvas = strategy.getDrawGraphics();

        //Start with a black pen on a white background
        hiddenCanvas.setColor(Color.white);
        hiddenCanvas.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        hiddenCanvas.setColor(Color.black);

        //Draw the next frame in the game animation
        paint(hiddenCanvas);

        //Display the new canvas to the user
        strategy.show();
        try {
            Thread.sleep(1);
        } catch (InterruptedException ie) {
            //it's safe to ignore this
        }
    }

    public static void paint (Graphics canvas)
    {
        canvas.setColor(Color.black);
        canvas.drawRect(10, 10, 100, 100);
    }

    public static void setup()
    {
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Canvas newCanvas = new Canvas();
        frame.getContentPane().add(newCanvas);

        Conway newGame = new Conway(newCanvas);
        frame.setVisible(true);

        newGame.run();
    }

    public static void run ()
    {
        myCanvas.createBufferStrategy(2);
        strategy = myCanvas.getBufferStrategy();

        startGame();

        while (isRunning)
        {
            repaint();

        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

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
                setup();
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

        JTextField dimField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(dimField, c);

        JButton dimButton = new JButton("Set Dimensions");
        dimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getText = dimField.getText();
                if (getText.matches("\\d+"))
                {
                    int input = Integer.parseInt(getText);
                    if (input < 1 || input > MAX_GAME_SIZE)
                    {
                        displayErrorWindow("Please enter an integer from 1 to 20.");
                        return;
                    }

                    dimIn = input;
                    hasDim = true;
                    if (hasSeed)
                    {
                        startButton.setEnabled(true);
                    }
                }
                else
                {
                    displayErrorWindow("Please enter an integer from 1 to 20.");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(dimButton, c);

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
                    displayErrorWindow("Please enter an integer.");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(seedButton, c);
    }

    public static void displayErrorWindow (String message)
    {
        int messageType = JOptionPane.PLAIN_MESSAGE;
        JOptionPane.showMessageDialog(null, message, "Error", messageType);
    }

    /**
     * startGame - Initializes board with user input and starts game
     *
     */
    static private void startGame ()
    {
        Board board = new Board(5);

        // If user input a seed then call board.seed(inputSeed);

        // For now just do this
        board.seed(seedIn);
    }
}