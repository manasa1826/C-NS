import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ColumnarTranspositionCipher {

    // Encrypt the plaintext using the columnar transposition technique
    public static String encrypt(String plaintext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / columns);
        char[][] grid = new char[rows][columns];

        // Fill the grid with plaintext
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (index < plaintext.length()) {
                    grid[r][c] = plaintext.charAt(index++);
                } else {
                    grid[r][c] = 'X'; // Padding
                }
            }
        }

        // Determine column order based on the key
        ArrayList<Integer> order = getColumnOrder(key);

        // Read the grid column by column in the determined order
        StringBuilder ciphertext = new StringBuilder();
        for (int col : order) {
            for (int r = 0; r < rows; r++) {
                ciphertext.append(grid[r][col]);
            }
        }

        return ciphertext.toString();
    }

    // Decrypt the ciphertext using the columnar transposition technique
    public static String decrypt(String ciphertext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / columns);
        char[][] grid = new char[rows][columns];

        // Determine column order based on the key
        ArrayList<Integer> order = getColumnOrder(key);

        // Fill the grid column by column in the determined order
        int index = 0;
        for (int col : order) {
            for (int r = 0; r < rows; r++) {
                if (index < ciphertext.length()) {
                    grid[r][col] = ciphertext.charAt(index++);
                }
            }
        }

        // Read the grid row by row to recover the plaintext
        StringBuilder plaintext = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                plaintext.append(grid[r][c]);
            }
        }

        // Remove padding (X) if present
        while (plaintext.charAt(plaintext.length() - 1) == 'X') {
            plaintext.deleteCharAt(plaintext.length() - 1);
        }

        return plaintext.toString();
    }

    // Get column order based on the key
    private static ArrayList<Integer> getColumnOrder(String key) {
        ArrayList<Character> sortedKey = new ArrayList<>();
        for (char c : key.toCharArray()) {
            sortedKey.add(c);
        }
        Collections.sort(sortedKey);

        ArrayList<Integer> order = new ArrayList<>();
        for (char c : key.toCharArray()) {
            order.add(sortedKey.indexOf(c));
            sortedKey.set(order.get(order.size() - 1), null); // Avoid duplicates
        }

        return order;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine().replaceAll("\\s", ""); // Remove spaces

        System.out.println("Enter the key:");
        String key = scanner.nextLine();

        // Encrypt
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + ciphertext);

        // Decrypt
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted text: " + decryptedText);

        scanner.close();
    }
}
