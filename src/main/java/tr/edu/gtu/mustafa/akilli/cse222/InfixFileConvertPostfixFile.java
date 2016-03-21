package tr.edu.gtu.mustafa.akilli.cse222;

import java.io.*;

/**
 * HW04_131044017_Mustafa_Akilli
 *
 * File:   InfixFileConvertPostfixFile
 *
 * Description:
 *
 * Infix File convert Postfix File
 *
 * @author Mustafa_Akilli
 * @since Sunday 20 March 2016 by Mustafa_Akilli
 */
public class InfixFileConvertPostfixFile {

    private InfixConvertPostfix infixConvertPostfix;/* Infix expressions convert postfix form */
    private static final int FOUND_SYNTAX_ERROR = 1;
    private static final int NO_FOUND_SYNTAX_ERROR = 0;
    private static final int PRINT_FIRST_LETTER = 0;
    private static final int PRINT_LAST_LETTER = 5;
    private static final int STRING_EQUAL = 0;

    /**
     * One Parameter Constructor
     *
     * @param infixFileName Infix File Name
     */
    public InfixFileConvertPostfixFile(String infixFileName){

        postfixConverter(infixFileName);

    }//end One Parameter Constructor

    /**
     * Set Infix Convert Postfix
     *
     * @param InfixString Infix String
     */
    private void setInfixConvertPostfix(String InfixString){infixConvertPostfix = new InfixConvertPostfix(InfixString);}
    //end setInfixConvertPostfix method

    /**
     * Get Infix Convert Postfix
     *
     * @return infixConvertPostfix
     */
    private InfixConvertPostfix getInfixConvertPostfix(){return infixConvertPostfix;}//end getInfixConvertPostfix method

    /**
     * Infix File convert Postfix File
     *
     * @param infixFileName
     */
    private void postfixConverter(String infixFileName){

        String fileName = "postfix.txt";/* The name of the file to open. */
        String line = null; /* For the lines in file */
        int foundSyntacError = NO_FOUND_SYNTAX_ERROR;

        try {
            /* FileWriter write text files */
            FileWriter fileWriter = new FileWriter(fileName);

            /* Always wrap FileWriter in BufferedWriter. */
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            try {
                /* FileReader reads text files */
                FileReader fileReader = new FileReader(infixFileName);

                /* Always wrap FileReader in BufferedReader. */
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* First Line */
                line = bufferedReader.readLine();

                /* Do loop until line is null */
                while (line != null) {

                    /* if command is print */
                    if (line.substring(PRINT_FIRST_LETTER,PRINT_LAST_LETTER).compareTo("print") == STRING_EQUAL ||
                            line.substring(PRINT_FIRST_LETTER, PRINT_LAST_LETTER).compareTo("Print") == STRING_EQUAL) {
                        bufferedWriter.write(line);
                    } else {

                        /* Infix Convert Postfix */
                        try{
                            setInfixConvertPostfix(line);
                        }catch (MySyntaxErrorException e){
                            System.out.println("SYNTAX ERROR: " + line);
                            foundSyntacError = FOUND_SYNTAX_ERROR;
                        }

                        /* Write postfix form into file */
                        try{
                            bufferedWriter.write(getInfixConvertPostfix().getPostfixToString());
                        }catch (NullPointerException e){}

                    }

                    /* Next Line */
                    line = bufferedReader.readLine();

                    /* Add New Line in File */
                    if(line != null)
                        bufferedWriter.newLine();
                }

                /* Close File */
                bufferedReader.close();

            } catch (FileNotFoundException ex) {
                System.out.println("File didn't open: " + infixFileName);
            } catch (IOException ex) {
                System.out.println("File didn't reading " + infixFileName);
            }

            /* Close File */
            bufferedWriter.close();

            if(foundSyntacError == FOUND_SYNTAX_ERROR){
                /* FileWriter write text files */
                FileWriter fileWriterEmpty = new FileWriter(fileName);

                /* Always wrap FileWriter in BufferedWriter. */
                BufferedWriter bufferedWriterEmpty = new BufferedWriter(fileWriterEmpty);

                /* Close File */
                bufferedWriter.close();
            }

        }catch(IOException ex) {System.out.println("Error writing to file: "+ fileName);}

    }//end postfixConverter method
}