import java.util.Scanner;
public class CaesarCipher
{
    public static String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static String UPPER = LOWER.toUpperCase();
    public static String encrypt(String plainText,int shiftKey)
    {
        StringBuilder builder = new StringBuilder();
        char[] chars = plainText.toCharArray();
        for(int i=0;i<plainText.length();i++)
        {
            int charPos = 0,keyVal = 0;
            char cur = chars[i];
            char newVal;
            if(cur>=65&&cur<=90)
            {
                charPos = UPPER.indexOf(cur);
                keyVal = (charPos+shiftKey)%26;
                newVal = UPPER.charAt(keyVal);
                builder.append(newVal);
            }
            else if(cur>=97&&cur<=122)
            {
                charPos = LOWER.indexOf(cur);
                keyVal = (charPos+shiftKey)%26;
                newVal = LOWER.charAt(keyVal);
                builder.append(newVal);
            }
            else
            {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
    public static String decrypt(String cipherText,int shiftKey)
    {
        StringBuilder builder = new StringBuilder();
        char[] chars = cipherText.toCharArray();
        for(int i=0;i<cipherText.length();i++)
        {
            char cur = chars[i];
            int keyVal = 0, charPos = 0;
            char newVal;
            if(cur>=65&&cur<=90)
            {
                charPos = UPPER.indexOf(cur);
                keyVal = (charPos - shiftKey)%26;
                if(keyVal<0)
                keyVal+=26;
                newVal = UPPER.charAt(keyVal);
                builder.append(newVal);
            }
            else if(cur>=97&&cur<=122)
            {
                charPos = LOWER.indexOf(cur);
                keyVal = (charPos - shiftKey)%26;
                if(keyVal<0)
                keyVal+=26;
                newVal = LOWER.charAt(keyVal);
                builder.append(newVal);
            }
            else
            {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
	public static void main(String[] args) 
	{
	    Scanner scan = new Scanner(System.in);
	    String input = scan.nextLine();
	    String cipherText = encrypt(input,3);
	    System.out.println("cipherText: "+cipherText);
	    String plainText = decrypt(cipherText,3);
	    System.out.println("plainText: "+plainText);
	}
}
