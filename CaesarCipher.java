import java.util.Scanner;
public class CaesarCipher
{
    public static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static String correctCase(String reference,String target)
    {
        StringBuilder newRes = new StringBuilder();
        for(int i=0;i<reference.length();i++)
        {
            char refCur = reference.charAt(i);
            char tarCur = target.charAt(i);
            if(refCur>=65&&refCur<=90)
            {
                tarCur-=32;
            }
            newRes.append(tarCur);
        }
        return newRes.toString();
    }
    public static String encrypt(String plainText,int shiftKey)
    {
        StringBuilder builder = new StringBuilder();
        String lower = plainText.toLowerCase();
        char[] chars = lower.toCharArray();
        for(int i=0;i<plainText.length();i++)
        {
            int charPos = 0,keyVal = 0;
            char cur = chars[i];
            char newVal;
            if(cur==' ')
            {
                builder.append(" ");
                
            }
            else
            {
                charPos = ALPHABET.indexOf(cur);
                keyVal = (charPos+shiftKey)%26;
                newVal = ALPHABET.charAt(keyVal);
                builder.append(newVal);
            }
        }
        return correctCase(plainText,builder.toString());
    }
    public static String decrypt(String cipherText,int shiftKey)
    {
        StringBuilder builder = new StringBuilder();
        String lower = cipherText.toLowerCase();
        char[] chars = lower.toCharArray();
        for(int i=0;i<cipherText.length();i++)
        {
            char cur = chars[i];
            int keyVal = 0, charPos = 0;
            char newVal;
            if(cur==' ')
            {
                builder.append(" ");
            }
            else
            {
                charPos = ALPHABET.indexOf(cur);
                keyVal = (charPos - shiftKey)%26;
                if(keyVal<0)
                keyVal+=26;
                newVal = ALPHABET.charAt(keyVal);
                builder.append(newVal);
            }
        }
        return correctCase(cipherText,builder.toString());
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
