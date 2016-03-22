package tr.edu.gtu.mustafa.akilli.cse222.AssemblyConverter;

import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyAlreadyDefinedVariableUsageException;
import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyDivisionByZeroException;
import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyOutOfRegisterLimitException;
import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyUndefinedVariableUsageException;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * HW04_131044017_Mustafa_Akilli
 *
 * File:   PostfixFileConvertAssemblyFile
 *
 * Description:
 *
 * Postfix Form File Convert Assembly Instructions File
 *
 * @author Mustafa_Akilli
 * @since Sunday 21 March 2016 by Mustafa_Akilli
 */
public class PostfixFileConvertAssemblyFile {

    private Stack<Character> stackOperands;/* Stack for Assignment*/
    private List<Character> definedVariables; /* Defined Variables */
    private List<Integer> variablesValue; /* Value of variables */
    private int numberOfRegistersUsed; /* how many register used by the user */
    private static final String ASSEMBLY_FILE_NAME = "output.asm";
    private static final int PRINT_FIRST_LETTER = 0;
    private static final int PRINT_LAST_LETTER = 5;
    private static final int STRING_EQUAL = 0;
    private static final int BEGINING_OF_THE_NUMBER_OF_REGISTERS_USED = 0;
    private static final int LIMIT_OF_REGISTERS = 9;
    private static final Character MULTIPLICATION = '*';
    private static final Character DIVISION = '/';
    private static final Character ADDITION = '+';
    private static final Character EXTRACTION = '-';
    private static final Character ASSIGNMENT = '=';
    private static final Character EMPTYCHARACTER = ' ';

    /**
     * One Parameter Constructor
     *
     * @param postfixFileName Postfix File Name
     */
    public PostfixFileConvertAssemblyFile(String postfixFileName){
        setStackOperands();
        setDefinedVariables();
        setNumberOfRegistersUsed(BEGINING_OF_THE_NUMBER_OF_REGISTERS_USED);
        PostfixFormFileConvertAssemblyInstructionsFile(postfixFileName);
    }//end One Parameter Constructor

    /**
     * Set Stack Operands
     */
    private void setStackOperands(){stackOperands = new Stack<Character>();}//end setStackOperands method

    /**
     * Get Stack Operands
     *
     * @return Stack of Operands
     */
    private Stack<Character> getStackOperands(){return stackOperands;}//end getStackOperands method

    /**
     *  Set Defined Variables
     */
    private void setDefinedVariables(){definedVariables = new Stack<Character>();}//end setDefinedVariables method

    /**
     * Get Defined Variables
     *
     * @return List of defined Variables
     */
    private List<Character> getDefinedVariables(){return definedVariables;}//end getDefinedVariables method

    /**
     * Set Variables Default Value
     */
    private void setVariablesDefaultValue(){
        variablesValue = new Stack<Integer>();

        for(int index=0; index < LIMIT_OF_REGISTERS; ++index){
            variablesValue.add(0);
        }// end for loop
    }//end setVariablesDefaultValue method

    /**
     * Set Variable Value
     *
     * @param registerName
     * @param newValue of register
     */
    private void setVariableValue(String registerName, int newValue) throws MyUndefinedVariableUsageException{
        boolean exist = true;

        switch (registerName.charAt(registerName.length())){
            case '0':getVariablesValueList().set(0,newValue);break;
            case '1':getVariablesValueList().set(1,newValue);break;
            case '2':getVariablesValueList().set(2,newValue);break;
            case '3':getVariablesValueList().set(3,newValue);break;
            case '4':getVariablesValueList().set(4,newValue);break;
            case '5':getVariablesValueList().set(5,newValue);break;
            case '6':getVariablesValueList().set(6,newValue);break;
            case '7':getVariablesValueList().set(7,newValue);break;
            case '8':getVariablesValueList().set(8,newValue);break;
            default: exist = false;
        }

        if(!exist)
            throw new MyUndefinedVariableUsageException();
    }

    /**
     * Get Variables Value List
     *
     * @return  List of Variables Value
     */
    private List<Integer> getVariablesValueList(){return variablesValue;}

    /**
     *
     * Get Variable Value of given register
     *
     * @param registerName
     * @return
     */
    private int getVariableValue(String registerName){
        int  valueOfRegister = 0;

        switch (registerName.charAt(registerName.length())){
            case '0':
                valueOfRegister = variablesValue.get(0);
                break;
            case '1':
                valueOfRegister = variablesValue.get(1);
                break;
            case '2':
                valueOfRegister = variablesValue.get(2);
                break;
            case '3':
                valueOfRegister = variablesValue.get(3);
                break;
            case '4':
                valueOfRegister = variablesValue.get(4);
                break;
            case '5':
                valueOfRegister = variablesValue.get(5);
                break;
            case '6':
                valueOfRegister = variablesValue.get(6);
                break;
            case '7':
                valueOfRegister = variablesValue.get(7);
                break;
            case '8':
                valueOfRegister = variablesValue.get(8);
                break;
        }

        return valueOfRegister;
    }// end getVariableValue method

    /**
     * Add Variable InTo Defined Variable, if already defined then throw MyAlreadyDefinedVariableUsageException
     *
     * @param newVariable
     * @throws MyAlreadyDefinedVariableUsageException
     */
    private void addVariableInToDefinedVariable(Character newVariable) throws MyAlreadyDefinedVariableUsageException{
        try{
            registerNameVariable(newVariable);
            throw new MyAlreadyDefinedVariableUsageException();
        }catch (MyUndefinedVariableUsageException e){
            setNumberOfRegistersUsed(getNumberOfRegistersUsed()+1);
            getDefinedVariables().add(newVariable);
        }
    }//end addVariableInToDefinedVariable method

    /**
     * Set Number Of Registers Used
     *
     * @param newNumberOfRegistersUsed
     */
    private void setNumberOfRegistersUsed(int newNumberOfRegistersUsed){
        numberOfRegistersUsed = newNumberOfRegistersUsed;
    }//end setNumberOfRegistersUsed method

    /**
     * Get Number Of Registers Used
     *
     * @return number Of Registers Used
     */
    private int getNumberOfRegistersUsed(){return numberOfRegistersUsed;}//end getNumberOfRegistersUsed method

    /**
     * if variable didn't defined then throw MyUndefinedVariableUsageException, otherwise return registerName
     *
     * @param variableName
     * @return if variable defined then return registerName
     */
    private String registerNameVariable(Character variableName) throws MyUndefinedVariableUsageException{
        String registerName = new String();
        int index;
        boolean found = false;
        ListIterator<Character> iter = getDefinedVariables().listIterator();
        Character character;

        while (iter.hasNext()){
            character = iter.next();
            if(character == variableName){
                found = true;
                if(!iter.hasNext())
                    index = getDefinedVariables().size() - 1;
                else
                    index = iter.nextIndex()-1;

                switch (index){
                    case 0:registerName = "$t0";break;
                    case 1:registerName = "$t1";break;
                    case 2:registerName = "$t2";break;
                    case 3:registerName = "$t3";break;
                    case 4:registerName = "$t4";break;
                    case 5:registerName = "$t5";break;
                    case 6:registerName = "$t6";break;
                    case 7:registerName = "$t7";break;
                    case 8:registerName = "$t8";break;
                }
            }
        }

        if(!found)
            throw new MyUndefinedVariableUsageException();

        return registerName;
    }

    /**
     * if out Of Register Limit then throw MyOutOfRegisterLimitException, otherwise return false
     *
     * @return if not out Of Register Limit then return false
     */
    private boolean outOfRegisterLimit() throws MyOutOfRegisterLimitException{

        if(getNumberOfRegistersUsed()>9)
            throw new MyOutOfRegisterLimitException();

        return false;
    }


    /**
     *  Convert each operation to assembly instructions and registers.
     *  After all conversion save assembly code to .asm file
     *
     *  Check unconditional situations:
     *      - Undefined variable usage
     *      - Division by zero
     *      - Out of register limit
     *
     * @param postfixFileName
     */
    private void PostfixFormFileConvertAssemblyInstructionsFile(String postfixFileName){

        String fileName = ASSEMBLY_FILE_NAME;/* The name of the file to write. */
        String line = null; /* For the lines in file */

        try {
            /* FileWriter write text files */
            FileWriter fileWriter = new FileWriter(fileName);

            /* Always wrap FileWriter in BufferedWriter. */
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            try {
                /* FileReader reads text files */
                FileReader fileReader = new FileReader(postfixFileName);

                /* Always wrap FileReader in BufferedReader. */
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* First Line */
                line = bufferedReader.readLine();

                /* Do loop until line is null */
                while (line != null) {

                    /* if command is print */
                    if (line.substring(PRINT_FIRST_LETTER,PRINT_LAST_LETTER).compareTo("print") == STRING_EQUAL ||
                            line.substring(PRINT_FIRST_LETTER, PRINT_LAST_LETTER).compareTo("Print") == STRING_EQUAL) {

                        bufferedWriter.write("syscall\n");
                    } else {

                        /* Postfix Convert Postfix */

                    }

                }

                /* Close File */
                bufferedReader.close();

                /* Close File */
                bufferedWriter.close();

            }catch (FileNotFoundException ex) {
                System.out.println("File didn't open: " + postfixFileName);
            }catch (IOException ex) {
                System.out.println("File didn't reading: " + postfixFileName);
            }catch (MyOutOfRegisterLimitException e){
                System.out.println("ERROR OUT OF REGISTER LIMIT");
            }catch (MyUndefinedVariableUsageException e){
                System.out.println("ERROR UNDEFINED VARIABLE USAGE");
            }catch (MyDivisionByZeroException e){
                System.out.println("ERROR DIVISION BY ZERO");
            }finally {
                    /* Close File */
                    bufferedWriter.close();

                    /* FileWriter write text files */
                    FileWriter fileWriterEmpty = new FileWriter(fileName);

                    /* Always wrap FileWriter in BufferedWriter. */
                    BufferedWriter bufferedWriterEmpty = new BufferedWriter(fileWriterEmpty);

                    /* Close File */
                    bufferedWriterEmpty.close();
            }
        }catch (IOException e){System.out.println("Error writing to file: "+ fileName);}

    }
}
