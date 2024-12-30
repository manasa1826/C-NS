import java.util.Scanner;

public class AutoKeyCipher {
    // Encrypt a plaintext message using the AutoKey Cipher
    public static String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        StringBuilder extendedKey = new StringBuilder(key);
        extendedKey.append(plaintext); // Append plaintext to the key for AutoKey

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char encryptedChar = (char) ((plainChar + keyChar - 2 * 'A') % 26 + 'A');
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    // Decrypt a ciphertext message using the AutoKey Cipher
    public static String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");

        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(key);

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char decryptedChar = (char) ((cipherChar - keyChar + 26) % 26 + 'A');
            plaintext.append(decryptedChar);
            extendedKey.append(decryptedChar); // Add decrypted character to the extended key
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();

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
