import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SpecialArrayChecker {
    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Special Array Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel numsLabel = new JLabel("Enter nums (comma-separated):");
        JTextField numsField = new JTextField();

        JLabel queriesLabel = new JLabel("Enter queries (e.g., [0,2];[2,3]):");
        JTextField queriesField = new JTextField();

        inputPanel.add(numsLabel);
        inputPanel.add(numsField);
        inputPanel.add(queriesLabel);
        inputPanel.add(queriesField);

        // Result Area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton checkButton = new JButton("Check Special Array");
        buttonPanel.add(checkButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add Input Panel to Frame
        frame.add(inputPanel, BorderLayout.NORTH);

        // Button Action Listener
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse nums
                    String numsText = numsField.getText();
                    int[] nums = Arrays.stream(numsText.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    // Parse queries
                    String queriesText = queriesField.getText();
                    String[] queryPairs = queriesText.split(";");
                    int[][] queries = new int[queryPairs.length][2];

                    for (int i = 0; i < queryPairs.length; i++) {
                        String[] pair = queryPairs[i].replace("[", "").replace("]", "").split(",");
                        queries[i][0] = Integer.parseInt(pair[0]);
                        queries[i][1] = Integer.parseInt(pair[1]);
                    }

                    // Check if nums is special
                    boolean[] results = isArraySpecial(nums, queries);

                    // Display Results
                    StringBuilder resultText = new StringBuilder("Results:\n");
                    for (int i = 0; i < results.length; i++) {
                        resultText.append("Query ").append(Arrays.toString(queries[i])).append(": ")
                                .append(results[i] ? "Special" : "Not Special").append("\n");
                    }
                    resultArea.setText(resultText.toString());
                } catch (Exception ex) {
                    resultArea.setText("Error: Invalid input format.\n" +
                            "Nums: comma-separated integers (e.g., 1,2,3).\n" +
                            "Queries: semicolon-separated pairs (e.g., [0,2];[2,3]).");
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }

    // Function to check if the array is special
    public static boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] d = new int[n];
        d[0] = 0; // Initialize the first element of d
        for (int i = 1; i < n; i++) {
            if (nums[i] % 2 != nums[i - 1] % 2) {
                d[i] = d[i - 1];
            } else {
                d[i] = i;
            }
        }

        int m = queries.length;
        boolean[] ans = new boolean[m];
        for (int i = 0; i < m; i++) {
            ans[i] = d[queries[i][1]] <= queries[i][0];
        }
        return ans;
    }
}
