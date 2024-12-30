import java.util.Scanner;

public class RailFenceCipher {

    // Encrypts the plaintext using the Rail Fence technique
    public static String encrypt(String plaintext, int rails) {
        char[][] rail = new char[rails][plaintext.length()];
        boolean down = true; // Direction flag
        int row = 0;

        // Fill the rail matrix with characters
        for (int col = 0; col < plaintext.length(); col++) {
            rail[row][col] = plaintext.charAt(col);

            if (down) {
                if (row == rails - 1) down = false; // Reverse direction
                else row++;
            } else {
                if (row == 0) down = true; // Reverse direction
                else row--;
            }
        }

        // Read characters row by row to form the ciphertext
        StringBuilder ciphertext = new StringBuilder();
        for (char[] r : rail) {
            for (char c : r) {
                if (c != 0) ciphertext.append(c);
            }
        }

        return ciphertext.toString();
    }

    // Decrypts the ciphertext using the Rail Fence technique
    public static String decrypt(String ciphertext, int rails) {
        char[][] rail = new char[rails][ciphertext.length()];
        boolean down = true; // Direction flag
        int row = 0;

        // Mark positions in the rail matrix
        for (int col = 0; col < ciphertext.length(); col++) {
            rail[row][col] = '*';

            if (down) {
                if (row == rails - 1) down = false; // Reverse direction
                else row++;
            } else {
                if (row == 0) down = true; // Reverse direction
                else row--;
            }
        }

        // Fill the rail matrix with ciphertext characters
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < ciphertext.length(); c++) {
                if (rail[r][c] == '*' && index < ciphertext.length()) {
                    rail[r][c] = ciphertext.charAt(index++);
                }
            }
        }

        // Read characters in zigzag order to form the plaintext
        StringBuilder plaintext = new StringBuilder();
        row = 0;
        down = true;
        for (int col = 0; col < ciphertext.length(); col++) {
            plaintext.append(rail[row][col]);

            if (down) {
                if (row == rails - 1) down = false; // Reverse direction
                else row++;
            } else {
                if (row == 0) down = true; // Reverse direction
                else row--;
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();

        System.out.println("Enter the number of rails:");
        int rails = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Encrypt
        String ciphertext = encrypt(plaintext, rails);
        System.out.println("Encrypted text: " + ciphertext);

        // Decrypt
        String decryptedText = decrypt(ciphertext, rails);
        System.out.println("Decrypted text: " + decryptedText);

        scanner.close();
    }
}
