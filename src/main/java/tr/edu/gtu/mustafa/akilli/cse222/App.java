package tr.edu.gtu.mustafa.akilli.cse222;


import tr.edu.gtu.mustafa.akilli.cse222.AssemblyConverter.PostfixFileConvertAssemblyFile;
import tr.edu.gtu.mustafa.akilli.cse222.PostfixConverter.InfixFileConvertPostfixFile;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String infixFileName = "input.txt";
        InfixFileConvertPostfixFile postfix = new InfixFileConvertPostfixFile(infixFileName);
        PostfixFileConvertAssemblyFile assembly = new PostfixFileConvertAssemblyFile(postfix.getPostfixFileName());

    }
}
