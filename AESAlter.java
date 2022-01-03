import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class ImpAes {
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");

        return generator.generateKey();
    }

    public static byte[] encrypt(byte[] input, SecretKey key) throws Exception {
        Cipher desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, key);

        return desCipher.doFinal(input);
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static byte[] decrypt(byte[] input, SecretKey key) throws Exception {
        Cipher desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        desCipher.init(Cipher.DECRYPT_MODE, key);

        return desCipher.doFinal(input);
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Enter the text: ");
        input = scan.nextLine();

        SecretKey key = generateSecretKey();

        byte[] inputBytes = input.getBytes();

        byte[] outputBytes = encrypt(inputBytes, key);

        System.out.println("Encrypted: " + bytesToHex(outputBytes));

        byte[] resultBytes = decrypt(outputBytes, key);
        System.out.println("Descrypted: " + new String(resultBytes));

        scan.close();
    }
}
