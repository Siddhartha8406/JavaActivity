import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroTimer {

    private JFrame frame;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox<String> modeSelector;

    private Timer timer;
    private int timeLeft;

    private final int WORK_TIME = 25 * 60; // 25 minutes
    private final int SHORT_BREAK_TIME = 5 * 60; // 5 minutes
    private final int LONG_BREAK_TIME = 15 * 60; // 15 minutes

    public PomodoroTimer() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Pomodoro Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        timerLabel = new JLabel("25:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        frame.add(timerLabel, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        resetButton = new JButton("Reset");

        String[] modes = {"Work", "Short Break", "Long Break"};
        modeSelector = new JComboBox<>(modes);

        controlsPanel.add(modeSelector);
        controlsPanel.add(startButton);
        controlsPanel.add(resetButton);

        frame.add(controlsPanel, BorderLayout.SOUTH);

        addEventListeners();

        frame.setVisible(true);
    }

    private void addEventListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        modeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeBasedOnMode();
            }
        });
    }

    private void startTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            startButton.setText("Start");
        } else {
            startButton.setText("Pause");
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (timeLeft > 0) {
                        timeLeft--;
                        updateTimerLabel();
                    } else {
                        timer.stop();
                        JOptionPane.showMessageDialog(frame, "Time's up!");
                        resetTimer();
                    }
                }
            });
            timer.start();
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        startButton.setText("Start");
        updateTimeBasedOnMode();
    }

    private void updateTimeBasedOnMode() {
        String selectedMode = (String) modeSelector.getSelectedItem();
        if ("Work".equals(selectedMode)) {
            timeLeft = WORK_TIME;
        } else if ("Short Break".equals(selectedMode)) {
            timeLeft = SHORT_BREAK_TIME;
        } else if ("Long Break".equals(selectedMode)) {
            timeLeft = LONG_BREAK_TIME;
        }
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PomodoroTimer());
    }
}

