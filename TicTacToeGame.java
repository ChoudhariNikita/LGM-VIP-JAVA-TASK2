import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons;
    private JLabel currentPlayerLabel;
    private boolean isPlayerX;
    private boolean gameEnded;
    private JButton resetbtn;

    public TicTacToeGame() {
        super("Tic-Tac-Toe");

        // Create the main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // Create the game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(Color.WHITE);
        buttons = new JButton[3][3];
        isPlayerX = true;
        gameEnded = false;
        resetbtn = new JButton("Start Over");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = createButton();
                boardPanel.add(buttons[row][col]);
            }
        }

        // Create the current player label
        currentPlayerLabel = new JLabel("Current Player: X");
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add components to the main panel
        panel.add(boardPanel, BorderLayout.CENTER);
        panel.add(currentPlayerLabel, BorderLayout.PAGE_END);
        panel.add(resetbtn, BorderLayout.PAGE_START);

        resetbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TicTacToeGame();
            }
        });

        // Set the panel as the content pane of the frame
        setContentPane(panel);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Set the frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createButton() {
        JButton button = new JButton();

        button.setBackground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 48));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameEnded) {
                    return;
                }

                button.setText(isPlayerX ? "X" : "O");
                button.setEnabled(false);
                button.setForeground(isPlayerX ? Color.BLUE : Color.RED);

                if (checkGameResult()) {
                    currentPlayerLabel.setText("Player " + (isPlayerX ? "X" : "O") + " wins!");
                    gameEnded = true;
                } else if (checkGameDraw()) {
                    currentPlayerLabel.setText("It's a draw!");
                    gameEnded = true;
                } else {
                    isPlayerX = !isPlayerX;
                    currentPlayerLabel.setText("Current Player: " + (isPlayerX ? "X" : "O"));
                }
            }
        });
        return button;
    }

    private boolean checkGameResult() {
        String[][] boardValues = new String[3][3];

        // Get the values from the buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardValues[row][col] = buttons[row][col].getText();
            }
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (!boardValues[row][0].isEmpty() &&
                    boardValues[row][0].equals(boardValues[row][1]) &&
                    boardValues[row][0].equals(boardValues[row][2])) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (!boardValues[0][col].isEmpty() &&
                    boardValues[0][col].equals(boardValues[1][col]) &&
                    boardValues[0][col].equals(boardValues[2][col])) {
                return true;
            }
        }

        // Check diagonals
        if (!boardValues[0][0].isEmpty() &&
                boardValues[0][0].equals(boardValues[1][1]) &&
                boardValues[0][0].equals(boardValues[2][2])) {
            return true;
        }

        if (!boardValues[0][2].isEmpty() &&
                boardValues[0][2].equals(boardValues[1][1]) &&
                boardValues[0][2].equals(boardValues[2][0])) {
            return true;
        }

        return false;
    }

    private boolean checkGameDraw() {
        // Check if all buttons are disabled (i.e., the game board is full)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGame();
            }
        });
    }
}
