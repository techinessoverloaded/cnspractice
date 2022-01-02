import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.Iterator;
public class PlayfairCipher
{
    public static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static char[][] keyTable = new char[5][5];
    public static int rowPos = -1,colPos=-1;
    public static String correctCase(String reference,String target) //optional
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
            if(refCur==' ')
            tarCur=' ';
            newRes.append(tarCur);
        }
        return newRes.toString();
    }
    public static String removeDuplicateLetters(String source)
    {
        StringBuilder builder = new StringBuilder();
        char[] chars = source.toLowerCase().toCharArray();
        LinkedHashSet<Character> uniq = new LinkedHashSet<Character>();
        for(int i=0;i<source.length();i++)
        {
            if(chars[i]==' ')
            continue;
            uniq.add(chars[i]);
        }
        Iterator<Character> it = uniq.iterator();
        while(it.hasNext())
        {
            builder.append(it.next());
        }
        return builder.toString();
    }
    public static void genKey(String keyword)
    {
        String uniqKey = removeDuplicateLetters(keyword.toLowerCase());
        if(uniqKey.indexOf('i')!=-1&&uniqKey.indexOf('j')!=-1)
        {
            uniqKey = uniqKey.replace("j","");
        }
        StringBuilder rem = new StringBuilder();
        for(int i=0;i<26;i++)
        {
            char cur = ALPHABET.charAt(i);
            if(uniqKey.indexOf(cur)!=-1||cur==' '||cur=='j')
            continue;
            else
            {
                rem.append(cur);
            }
        }
        String remaining = rem.toString();
        uniqKey+=remaining;
        int k=0;
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                keyTable[i][j]=uniqKey.charAt(k++);
            }
        }
        System.out.println("Playfair Cipher Key Matrix:");
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                System.out.print(keyTable[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static int[] getCharPos(char ch)
    {
        int pos[] = new int[2];
        for (int i = 0; i < 5; i++) 
        {
        for (int j = 0; j < 5; j++) 
        {
            if (keyTable[i][j] == ch) 
            {
                pos[0]=i;
                pos[1]=j;
            }
        }
        }
        return pos;
    }
    public static String makePairs(String plainText)
    {
       String s = "";
       char c = 'a';
       plainText = plainText.replaceAll("j","i").toLowerCase();
       for (int i=0;i<plainText.length();i++) 
       {
            if (plainText.charAt(i) == ' ')
                continue;
            else 
            {
                c = plainText.charAt(i);
                s += plainText.charAt(i);
            }
            if(i<plainText.length()-1)
                if (plainText.charAt(i)==plainText.charAt(i+1))
                    s += "x";
        }
        if(s.length()%2!=0)
            s += "x";
        return s;
    }
    public static String encrypt(String plainText,String keyword)
    {
        String pairs = makePairs(plainText);
        genKey(keyword);
        String encText = "";
        for (int i=0;i<pairs.length();i+=2) 
        {
            char ch1 = pairs.charAt(i);
            char ch2 = pairs.charAt(i+1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);
            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) 
            {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }
            // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }
            // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            // get the corresponding cipher characters from
            // the key matrix
            encText = encText + keyTable[ch1Pos[0]][ch1Pos[1]]
                      + keyTable[ch2Pos[0]][ch2Pos[1]];
        }
        return encText;
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
	}
}
