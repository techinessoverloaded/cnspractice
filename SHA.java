import java.security.MessageDigest;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

public class SHA {

    public static String generateHash(byte[] msg) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");

            digest.update(msg);

            byte[] outputBytes = digest.digest();
            String output = bytesToHex(outputBytes);

            return output;
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return msg.toString();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Enter the text: ");
        input = scan.next();

        byte[] inputBytes = input.getBytes();

        String outputHash = generateHash(inputBytes);
        System.out.println("Hash: " + outputHash);

        scan.close();
    }
}
