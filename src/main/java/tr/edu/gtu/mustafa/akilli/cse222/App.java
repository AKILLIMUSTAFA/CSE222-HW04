package tr.edu.gtu.mustafa.akilli.cse222;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        InfixConvertPostfix infix = new InfixConvertPostfix("A - B * 3");

        for(int index =0; index < infix.getPostfixList().size(); ++index)
                System.out.print(infix.getPostfixList().get(index));

        System.out.println( "" );
    }
}
