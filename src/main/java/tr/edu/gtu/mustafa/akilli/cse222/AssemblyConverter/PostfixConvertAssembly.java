package tr.edu.gtu.mustafa.akilli.cse222.AssemblyConverter;

import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyOutOfRegisterLimitException;
import tr.edu.gtu.mustafa.akilli.cse222.Exception.MyUndefinedVariableUsageException;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * HW04_131044017_Mustafa_Akilli
 *
 * File:   PostfixConvertAssembly
 *
 * Description:
 *
 * Postfix Form Convert Assembly Instructions
 *
 * @author Mustafa_Akilli
 * @since Sunday 21 March 2016 by Mustafa_Akilli
 */
public class PostfixConvertAssembly {

    private Stack<Character> stackOperands;/* Stack for Assignment*/
    private List<Character> definedVariables; /* Defined Variables */
    private int numberOfRegistersUsed; /* how many register used by the user */
    private static final Character MULTIPLICATION = '*';
    private static final Character DIVISION = '/';
    private static final Character ADDITION = '+';
    private static final Character EXTRACTION = '-';
    private static final Character ASSIGNMENT = '=';
    private static final Character EMPTYCHARACTER = ' ';

    /**
     * One Parameter Constructor
     *
     * @param PostfixString Postfix form
     */
    public PostfixConvertAssembly(String PostfixString){



    }//end One Parameter Constructor

    /**
     * Set Stack Operands
     */
    private void setStackOperands(){stackOperands = new Stack<Character>();}//end setStackOperands method

    /**
     * Get Stack Operand
     */

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
     * Add Variable InTo Defined Variable
     *
     * @param newVariable
     */
    private void addVariableInToDefinedVariable(Character newVariable){getDefinedVariables().add(newVariable);}
    //end addVariableInToDefinedVariable method

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
     * if variable didn't defined then throw MyUndefinedVariableUsageException, otherwise return false
     *
     * @param variableName
     * @return if variable defined then return false
     */
    private boolean undefinedVariableUsed(Character variableName){
        boolean found = false;
        Iterator<Character> iter = getDefinedVariables().iterator();
        Character character;

        while (iter.hasNext()){
            character = iter.next();
            if(character == variableName)
                found = true;
        }

        if(!found)
            throw new MyUndefinedVariableUsageException();

        return false;
    }

    /**
     * if out Of Register Limit then throw MyOutOfRegisterLimitException, otherwise return false
     *
     * @return if not out Of Register Limit then return false
     */
    private boolean outOfRegisterLimit(){

        if(getNumberOfRegistersUsed()>9)
            throw new MyOutOfRegisterLimitException();

        return false;
    }

    private void PostfixFormConvertAssemblyInstructions(){


    }

}
