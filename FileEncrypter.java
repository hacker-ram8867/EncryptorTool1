import java.io.*;
import java.security.*;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FileEncrypter {

    // Generate AES key from password using SHA-256
    private static SecretKey getKeyFromPassword(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes("UTF-8"));
        return new SecretKeySpec(key, "AES");
    }

    // Encrypt file
    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, read);
            }
        }
    }

    // Decrypt file
    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        }
    }

    // Corrupt file by overwriting with random data
    public static void corruptFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] garbage = new byte[1024];
            new SecureRandom().nextBytes(garbage);
            for (int i = 0; i < 10; i++) {
                fos.write(garbage);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Failed to corrupt file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("🔒 Enter 'E' to encrypt or 'D' to decrypt: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter path of file: ");
            String inputPath = scanner.nextLine();

            System.out.print("Enter output file path: ");
            String outputPath = scanner.nextLine();

            File inputFile = new File(inputPath);
            File outputFile = new File(outputPath);

            if (!inputFile.exists()) {
                System.out.println("❌ Input file does not exist.");
                return;
            }

            if (choice.equals("E")) {
                System.out.print("Enter password to encrypt: ");
                String password = scanner.nextLine();
                SecretKey key = getKeyFromPassword(password);
                encryptFile(inputFile, outputFile, key);
                System.out.println("✅ File encrypted successfully: " + outputPath);
            } else if (choice.equals("D")) {
                boolean success = false;
                int attempts = 0;

                while (attempts < 5 && !success) {
                    System.out.print("Enter password to decrypt (Attempt " + (attempts + 1) + "/5): ");
                    String password = scanner.nextLine();

                    SecretKey key = getKeyFromPassword(password);
                    try {
                        decryptFile(inputFile, outputFile, key);
                        System.out.println("✅ File decrypted successfully: " + outputPath);
                        success = true;
                    } catch (Exception e) {
                        System.out.println("❌ Incorrect password or corrupted file.");
                        attempts++;
                    }
                }

                if (!success) {
                    System.out.println("💀 Too many failed attempts. The file will be corrupted.");
                    corruptFile(inputFile);
                    System.out.println("☠️ Your file was corrupted.");
                }
            } else {
                System.out.println("❌ Invalid option. Choose 'E' or 'D'.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error occurred: " + e.getMessage());
        }

        scanner.close();
    }
}
