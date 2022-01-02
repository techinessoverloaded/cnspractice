import java.util.Scanner;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import javax.xml.bind.DatatypeConverter;

public class DSS {
    public static KeyPair generateKeyPair() throws Exception {
        SecureRandom secRandom = new SecureRandom();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
        generator.initialize(2048, secRandom);
        return generator.generateKeyPair();
    }

    public static byte[] digitalSignatureSign(byte[] input, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initSign(privateKey);
        signature.update(input);
        return signature.sign();
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static boolean verifySignature(byte[] output, byte[] input, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initVerify(publicKey);
        signature.update(input);
        return signature.verify(output);
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Enter the text: ");
        input = scan.next();

        KeyPair keyPair = generateKeyPair();

        byte[] inputBytes = input.getBytes();

        byte[] outputBytes = digitalSignatureSign(inputBytes, keyPair.getPrivate());

        System.out.println("Signature: " + bytesToHex(outputBytes));

        System.out
                .println("Verification: " + verifySignature(outputBytes, inputBytes, keyPair.getPublic()));
        scan.close();
    }
}
