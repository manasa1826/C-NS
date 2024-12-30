import java.util.Scanner;

public class HillCipher {
    // Modulo inverse of a number (for mod 26)
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // Inverse doesn't exist
    }

    // Find the determinant of a 2x2 matrix
    private static int determinant(int[][] matrix) {
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
    }

    // Find the inverse of a 2x2 matrix mod 26
    private static int[][] inverseMatrix(int[][] matrix) {
        int det = determinant(matrix);
        int detInverse = modInverse(det, 26);
        if (detInverse == -1) {
            throw new IllegalArgumentException("Key matrix is not invertible.");
        }

        // Swap elements and adjust signs for the inverse
        int[][] inverse = new int[2][2];
        inverse[0][0] = matrix[1][1] * detInverse % 26;
        inverse[0][1] = -matrix[0][1] * detInverse % 26;
        inverse[1][0] = -matrix[1][0] * detInverse % 26;
        inverse[1][1] = matrix[0][0] * detInverse % 26;

        // Ensure all elements are positive mod 26
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }
        return inverse;
    }

    // Encrypt a plaintext message
    public static String encrypt(String plaintext, int[][] key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        if (plaintext.length() % 2 != 0) {
            plaintext += "X"; // Padding for odd length
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] pair = {plaintext.charAt(i) - 'A', plaintext.charAt(i + 1) - 'A'};
            int[] encryptedPair = new int[2];
            encryptedPair[0] = (key[0][0] * pair[0] + key[0][1] * pair[1]) % 26;
            encryptedPair[1] = (key[1][0] * pair[0] + key[1][1] * pair[1]) % 26;

            ciphertext.append((char) (encryptedPair[0] + 'A'));
            ciphertext.append((char) (encryptedPair[1] + 'A'));
        }
        return ciphertext.toString();
    }

    // Decrypt a ciphertext message
    public static String decrypt(String ciphertext, int[][] key) {
        int[][] inverseKey = inverseMatrix(key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int[] pair = {ciphertext.charAt(i) - 'A', ciphertext.charAt(i + 1) - 'A'};
            int[] decryptedPair = new int[2];
            decryptedPair[0] = (inverseKey[0][0] * pair[0] + inverseKey[0][1] * pair[1]) % 26;
            decryptedPair[1] = (inverseKey[1][0] * pair[0] + inverseKey[1][1] * pair[1]) % 26;

            plaintext.append((char) (decryptedPair[0] + 'A'));
            plaintext.append((char) (decryptedPair[1] + 'A'));
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Key matrix
        int[][] key = {
            {3, 3},
            {2, 5}
        };

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();

        // Encrypt
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + ciphertext);

        // Decrypt
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted text: " + decryptedText);

        scanner.close();
    }
}
