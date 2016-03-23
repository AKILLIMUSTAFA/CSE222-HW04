package tr.edu.gtu.mustafa.akilli.cse222.AssemblyConverter;

import tr.edu.gtu.mustafa.akilli.cse222.Exception.*;

import java.io.*;
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

    private Stack<String> stackOperands;/* Stack for Assignment*/
    private List<Registers> definedVariables; /* Defined Variables */
    private List<Boolean>  registerUsageList; /* Which register used */
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
        setVariablesDefaultValue();
        setRegisterUsageList();
        setNumberOfRegistersUsed(BEGINING_OF_THE_NUMBER_OF_REGISTERS_USED);
        PostfixFormFileConvertAssemblyInstructionsFile(postfixFileName);
    }//end One Parameter Constructor

    /**
     * Set Stack Operands
     */
    private void setStackOperands(){stackOperands = new Stack<String>();}//end setStackOperands method

    /**
     * Get Stack Operands
     *
     * @return Stack of Operands
     */
    private Stack<String> getStackOperands(){return stackOperands;}//end getStackOperands method

    /**
     * Set Register Usage List
     */
    private void setRegisterUsageList(){
        registerUsageList = new Stack<Boolean>();

        registerUsageList.add(false);/*$t0*/
        registerUsageList.add(false);/*$t1*/
        registerUsageList.add(false);/*$t2*/
        registerUsageList.add(false);/*$t3*/
        registerUsageList.add(false);/*$t4*/
        registerUsageList.add(false);/*$t5*/
        registerUsageList.add(false);/*$t6*/
        registerUsageList.add(false);/*$t7*/
        registerUsageList.add(false);/*$t8*/
    }

    /**
     * Set One Register Usage
     *
     * @param registerNameSequence
     * @param usage
     */
    private void setOneRegisterUsage(Integer registerNameSequence, boolean usage){
        registerUsageList.set(registerNameSequence, usage);
    }

    /**
     * Get Register Usage List
     *
     * @return
     */
    private List<Boolean> getRegisterUsageList(){return registerUsageList;}

    /**
     *  Set Defined Variables
     */
    private void setDefinedVariables(){definedVariables = new Stack<Registers>();}//end setDefinedVariables method

    /**
     * Get Defined Variables
     *
     * @return List of defined Variables
     */
    private List<Registers> getDefinedVariables(){return definedVariables;}//end getDefinedVariables method

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

        switch (registerName.charAt(registerName.length()-1)){
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

        switch (registerName.charAt(registerName.length()-1)){
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
    private void addVariableInToDefinedVariable(String newVariable) throws MyAlreadyDefinedVariableUsageException,MyOutOfRegisterLimitException{
        try{
            registeredNameVariable(newVariable);/* Check Already Defined Variable */
            throw new MyAlreadyDefinedVariableUsageException();
        }catch (MyUndefinedVariableUsageException e){

            /* Check Register Limit */
            setNumberOfRegistersUsed(getNumberOfRegistersUsed()+1); /* increase the Number Of Registers Used */
            outOfRegisterLimit();

            /* Add new Variable into defined variable list */
            for(int index=0; index<9 ;++index){
                if(getRegisterUsageList().get(index) == false){
                    getDefinedVariables().add(new Registers(index,newVariable));
                    setOneRegisterUsage(index,true);
                    break;
                }
            }
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
    private String registeredNameVariable(String variableName) throws MyUndefinedVariableUsageException{
        String registerName = new String();
        int index;
        boolean found = false;
        ListIterator<Registers> iter = getDefinedVariables().listIterator();
        Registers registers;

        while (iter.hasNext()){
            registers = iter.next();
            if(registers.getVariableName().compareTo(variableName) == 0){
                found = true;

                switch (registers.getRegisterNameSequence()){
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
     * Is It Only Assignment Operator
     *
     * @param line
     * @return if is It Only Assignment Operator then return true, otherwise return false
     */
    private boolean isItOnlyAssignmentOperator(String line){

        int index=2;
        boolean biggerThenOneNumber = false;

        try {
            while(true) {
                if(biggerThenOneNumber){
                    if(line.charAt(index) == '=')
                        return true;
                }

                if(line.charAt(index) != ' ') {
                    int foo = Integer.parseInt(String.valueOf(line.charAt(index)));
                    ++index;
                }else{
                    biggerThenOneNumber = true;
                    ++index;
                }
            }
        }catch (NumberFormatException q){return false;}
    }

    /**
     * Find the number value in the line
     *
     * @param line
     * @param index start of number
     * @return number value
     */
    private int giveTheNumber(String line, int index){

        int number = 0;

        try {
            while(true){
                int foo = Integer.parseInt(String.valueOf(line.charAt(index)));
                number = number*10;
                number += foo;
                ++index;
            }

        }catch (NumberFormatException e){}
        catch (StringIndexOutOfBoundsException t){}
        finally {
            --index;
            return number;
        }
    }

    /**
     * Digit Number Of Given Number
     *
     * @return digit Number Of Given Number
     */
    private int digitNumberOfGivenNumber(int number){
        int index = 0;

        if(number%10>0){
            number = number%10;
            ++index;
        }

        return index++;
    }

    /**
     * Remove the variable and free register
     *
     * @param variableName
     */
    private void freeRegister(String variableName){

        ListIterator<Registers> iter = getDefinedVariables().listIterator();
        Registers registers;

        while (iter.hasNext()) {
            registers = iter.next();
            if (registers.getVariableName() == variableName) {
                setOneRegisterUsage(registers.getRegisterNameSequence(),false);
                getDefinedVariables().remove(registers);
                setNumberOfRegistersUsed(getNumberOfRegistersUsed()-1);
                break;
            }
        }
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
        boolean foundError = false;
        List<String> tempVariables = new Stack<String>();
        int tempNameIndex = 0;

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

                    if(isItOnlyAssignmentOperator(line)){

                        try {
                            addVariableInToDefinedVariable(String.valueOf(line.charAt(0))); /* Add Variable */
                        } catch (MyAlreadyDefinedVariableUsageException e) {}

                        int foo = giveTheNumber(line, 2);
                        setVariableValue(registeredNameVariable(String.valueOf(line.charAt(0))), foo);
                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(line.charAt(0))) + ", " + foo);
                        bufferedWriter.newLine();

                    }

                    else{
                        /* if command is print */
                        if (line.substring(PRINT_FIRST_LETTER, PRINT_LAST_LETTER).compareTo("print") == STRING_EQUAL ||
                                line.substring(PRINT_FIRST_LETTER, PRINT_LAST_LETTER).compareTo("Print") == STRING_EQUAL) {
                            String ch = registeredNameVariable(String.valueOf(line.charAt(line.length() - 1)));
                            bufferedWriter.write("move \t$a0, " + ch);
                            bufferedWriter.newLine();
                            bufferedWriter.write("li \t$v0, " + getVariableValue(ch));
                            bufferedWriter.newLine();
                            bufferedWriter.write("syscall");
                            bufferedWriter.newLine();
                        } else {




                            /*Check all elements in String */
                            for (int index = 0; index < line.length(); ++index) {

                                /* For the left hand of the assigned */
                                if (index == 0) {
                                    try {
                                        getStackOperands().push(String.valueOf(line.charAt(index)));
                                        addVariableInToDefinedVariable(String.valueOf(line.charAt(index))); /* Add Variable */
                                    } catch (MyAlreadyDefinedVariableUsageException e) {}
                                }

                                // if char a assignment */
                                else if (line.charAt(index) == ASSIGNMENT) {
                                    String rightHand = getStackOperands().pop();
                                    String leftHand = getStackOperands().pop();

                                    /* Check This Variables already register */
                                    registeredNameVariable(leftHand);

                                    try {
                                        registeredNameVariable(rightHand);
                                    } catch (MyUndefinedVariableUsageException e) {
                                        /* İf left side is a number then register, otherwise throw MyUndefinedVariableUsageException*/
                                        try {
                                            int foo = Integer.parseInt(String.valueOf(rightHand));
                                            setVariableValue(registeredNameVariable(leftHand), foo);
                                            bufferedWriter.write("li \t" + registeredNameVariable(leftHand) + ", " + foo);
                                            bufferedWriter.newLine();
                                        } catch (NumberFormatException q) {
                                            throw new MyUndefinedVariableUsageException();
                                        }
                                    }

                                    bufferedWriter.newLine();
                                    bufferedWriter.write("move \t" + registeredNameVariable(leftHand) + "," + registeredNameVariable(rightHand));
                                    bufferedWriter.newLine();
                                    setVariableValue(registeredNameVariable(leftHand), getVariableValue(registeredNameVariable(rightHand)));

                                }

                                // if char a MULTIPLICATION */
                                else if (line.charAt(index) == MULTIPLICATION) {
                                    String rightHand = getStackOperands().pop();
                                    String leftHand = getStackOperands().pop();
                                    String newVariable;

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(String.valueOf(leftHand));
                                        tempVariables.add(leftHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(leftHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(leftHand);
                                    }

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(String.valueOf(rightHand));
                                        tempVariables.add(rightHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(rightHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(rightHand);
                                    }

                                    String stringFooTwo = String.valueOf("temp" + (++tempNameIndex));
                                    tempVariables.add(stringFooTwo);
                                    addVariableInToDefinedVariable(stringFooTwo);

                                    bufferedWriter.write("mult \t" + registeredNameVariable(leftHand) + "," + registeredNameVariable(rightHand));
                                    bufferedWriter.newLine();
                                    bufferedWriter.write("mflo \t" + registeredNameVariable(stringFooTwo));
                                    bufferedWriter.newLine();
                                    setVariableValue(registeredNameVariable(stringFooTwo),
                                            getVariableValue(registeredNameVariable(rightHand))*getVariableValue(registeredNameVariable(leftHand)));
                                    getStackOperands().push(stringFooTwo);

                                }

                                // if char a DIVISION */
                                else if (line.charAt(index) == DIVISION) {

                                    String rightHand = getStackOperands().pop();
                                    String leftHand = getStackOperands().pop();
                                    String newVariable;

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(leftHand);
                                        tempVariables.add(leftHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(leftHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(leftHand);
                                    }

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(rightHand);
                                        tempVariables.add(rightHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(rightHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(rightHand);
                                    }

                                    String stringFooTwo = String.valueOf("temp" + (++tempNameIndex));
                                    tempVariables.add(stringFooTwo);
                                    addVariableInToDefinedVariable(stringFooTwo);

                                    bufferedWriter.write("div \t" + registeredNameVariable(leftHand) + "," + registeredNameVariable(rightHand));
                                    bufferedWriter.newLine();
                                    bufferedWriter.write("mflo \t" + registeredNameVariable(stringFooTwo));
                                    bufferedWriter.newLine();
                                    setVariableValue(registeredNameVariable(stringFooTwo),
                                            getVariableValue(registeredNameVariable(leftHand))/getVariableValue(registeredNameVariable(rightHand)));
                                    getStackOperands().push(stringFooTwo);

                                }

                                // if char a ADDITION */
                                else if (line.charAt(index) == ADDITION) {

                                    String rightHand = getStackOperands().pop();
                                    String leftHand = getStackOperands().pop();
                                    String newVariable;
                                    boolean isNecesserynewVariable = true; /* if left and hand side both of them a variable then
                                    make a new variable and assign the result */

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(leftHand);
                                        tempVariables.add(leftHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(leftHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(leftHand);
                                    }

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(rightHand);
                                        tempVariables.add(rightHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(rightHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(rightHand);
                                    }


                                    String stringFoo = String.valueOf("temp" + (++tempNameIndex));
                                    tempVariables.add(stringFoo);
                                    addVariableInToDefinedVariable(stringFoo);

                                    bufferedWriter.write("add \t" + registeredNameVariable(stringFoo) + "," +
                                                registeredNameVariable(leftHand) + "," +
                                                registeredNameVariable(rightHand));
                                    bufferedWriter.newLine();

                                    setVariableValue(registeredNameVariable(stringFoo),
                                            getVariableValue(registeredNameVariable(leftHand))+getVariableValue(registeredNameVariable(rightHand)));
                                    getStackOperands().push(stringFoo);


                                }

                                // if char a EXTRACTION */
                                else if (line.charAt(index) == EXTRACTION) {
                                    String rightHand = getStackOperands().pop();
                                    String leftHand = getStackOperands().pop();
                                    String newVariable;
                                    boolean isNecesserynewVariable = true; /* if left and hand side both of them a variable then
                                    make a new variable and assign the result */

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(leftHand);
                                        tempVariables.add(leftHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(leftHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(leftHand);
                                    }

                                    try{
                                        /* Check This Variables is it integer */
                                        int foo = Integer.parseInt(rightHand);
                                        tempVariables.add(rightHand);
                                        bufferedWriter.write("li \t" + registeredNameVariable(String.valueOf(rightHand)) + ", " + foo);
                                        bufferedWriter.newLine();

                                    }catch (NumberFormatException e){
                                        /* Check This Variables already register */
                                        registeredNameVariable(rightHand);
                                    }


                                    String stringFoo = String.valueOf("temp" + (++tempNameIndex));
                                    tempVariables.add(stringFoo);
                                    addVariableInToDefinedVariable(stringFoo);

                                    bufferedWriter.write("sub \t" + registeredNameVariable(stringFoo) + "," +
                                            registeredNameVariable(leftHand) + "," +
                                            registeredNameVariable(rightHand));
                                    bufferedWriter.newLine();

                                    setVariableValue(registeredNameVariable(stringFoo),
                                            getVariableValue(registeredNameVariable(leftHand))-getVariableValue(registeredNameVariable(rightHand)));
                                    getStackOperands().push(stringFoo);

                                }

                                else {
                                    if (line.charAt(index) != EMPTYCHARACTER) {

                                        try{
                                            int foo = Integer.parseInt(String.valueOf(line.charAt(index)));
                                            try{
                                                addVariableInToDefinedVariable(String.valueOf(giveTheNumber(line,index)));
                                            }catch (MyAlreadyDefinedVariableUsageException a){}
                                            setVariableValue(registeredNameVariable(String.valueOf(giveTheNumber(line,index))), giveTheNumber(line,index));
                                            getStackOperands().push(String.valueOf(giveTheNumber(line,index)));
                                            index += digitNumberOfGivenNumber(giveTheNumber(line,index));
                                        }catch (NumberFormatException e){
                                            registeredNameVariable(String.valueOf(line.charAt(index)));
                                            getStackOperands().push(String.valueOf(line.charAt(index)));
                                        }


                                    }
                                }

                            }


                        }
                    }


                    /*clear temp values */
                    for(int i =0; i<tempVariables.size();++i){
                        freeRegister(tempVariables.get(i));
                    }
                    tempVariables.clear();

                    /* Next Line */
                    line = bufferedReader.readLine();

                    /* Add New Line in File */
                    if(line != null){
                        bufferedWriter.newLine();
                    }

                    /* Check Variable */
                    outOfRegisterLimit();

                }

                /* Close File */
                bufferedReader.close();

                /* Close File */
                bufferedWriter.close();

            }catch (FileNotFoundException ex) {
                System.out.println("File didn't open: " + postfixFileName);

            }catch (IOException ex) {
                System.out.println("File didn't reading: " + postfixFileName);
                foundError = true;
            }catch (MyOutOfRegisterLimitException e){
                System.out.println("ERROR OUT OF REGISTER LIMIT");
                foundError = true;
            }catch (MyUndefinedVariableUsageException e){
                System.out.println("ERROR UNDEFINED VARIABLE USAGE");
                foundError = true;
            }catch (MyDivisionByZeroException e){
                System.out.println("ERROR DIVISION BY ZERO");
                foundError = true;
            }finally {
                    /* Close File */
                    bufferedWriter.close();

                    if(foundError) {
                        /* FileWriter write text files */
                        FileWriter fileWriterEmpty = new FileWriter(fileName);

                        /* Always wrap FileWriter in BufferedWriter. */
                        BufferedWriter bufferedWriterEmpty = new BufferedWriter(fileWriterEmpty);

                        /* Close File */
                        bufferedWriterEmpty.close();
                    }
            }
        }catch (IOException e){System.out.println("Error writing to file: "+ fileName);}

    }
}
