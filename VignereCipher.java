import java.util.Scanner;
public class VignereCipher
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
    public static String encrypt(String plainText,String keyword)
    {
        StringBuilder builder = new StringBuilder();
        String lower = plainText.toLowerCase();
        char[] chars = lower.toCharArray();
        keyword = keyword.toLowerCase();
        int[] keyNos = new int[keyword.length()];
        for(int i=0;i<keyword.length();i++)
        {
            keyNos[i]=ALPHABET.indexOf(keyword.charAt(i));
        }
        int j=0;
        for(int i=0;i<plainText.length();i++)
        {
            if(chars[i]==' ')
            builder.append(" ");
            else
            {
                int index = ALPHABET.indexOf(chars[i]);
                int sum = (index+keyNos[j])%26;
                char res = ALPHABET.charAt(sum);
                builder.append(res);
                j++;
                if(j==keyNos.length)
                j=0;
            }
        }
        return correctCase(plainText,builder.toString());
    }
    public static String decrypt(String cipherText,String keyword)
    {
        StringBuilder builder = new StringBuilder();
        String lower = cipherText.toLowerCase();
        char[] chars = lower.toCharArray();
        keyword = keyword.toLowerCase();
        int[] keyNos = new int[keyword.length()];
        for(int i=0;i<keyword.length();i++)
        {
            keyNos[i]=ALPHABET.indexOf(keyword.charAt(i));
        }
        int j=0;
        for(int i=0;i<cipherText.length();i++)
        {
            if(chars[i]==' ')
            builder.append(" ");
            else
            {
                int index = ALPHABET.indexOf(chars[i]);
                int diff = (index-keyNos[j])%26;
                if(diff<0)
                diff+=26;
                char res = ALPHABET.charAt(diff);
                builder.append(res);
                j++;
                if(j==keyNos.length)
                j=0;
            }
        }
        return correctCase(cipherText,builder.toString());
    }
	public static void main(String[] args) 
	{
	    Scanner scan = new Scanner(System.in);
	    System.out.println("Enter the plain text: ");
	    String input = scan.nextLine();
	    System.out.println("Enter the keyword: ");
	    String keyword = scan.nextLine();
	    String cipherText = encrypt(input,keyword);
	    System.out.println("cipherText: "+cipherText);
	    String plainText = decrypt(cipherText,keyword);
	    System.out.println("plainText: "+plainText);
	}
}
