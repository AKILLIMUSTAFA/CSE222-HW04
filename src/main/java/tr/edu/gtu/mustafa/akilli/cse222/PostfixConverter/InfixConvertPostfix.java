package tr.edu.gtu.mustafa.akilli.cse222.PostfixConverter;

import tr.edu.gtu.mustafa.akilli.cse222.Exception.MySyntaxErrorException;

import java.util.*;

/**
 * HW04_131044017_Mustafa_Akilli
 *
 * File:   InfixConvertPostfix
 *
 * Description:
 *
 * Infix expressions convert postfix form
 *
 * @author Mustafa_Akilli
 * @since Sunday 16 March 2016 by Mustafa_Akilli
 */
public class InfixConvertPostfix {

    private Stack<Character> stackForAdditionAndExtraction;/* Stack for For Addition And Extraction */
    private List<Character> postfixList;/* postfix form */
    private Stack<Character> stackForMultiplicationAndDivision;/* Stack for MULTIPLICATION and DIVISION*/
    private Stack<Character> stackAssignment;/* Stack for Assignment*/
    private static final Character MULTIPLICATION = '*';
    private static final Character DIVISION = '/';
    private static final Character ADDITION = '+';
    private static final Character EXTRACTION = '-';
    private static final Character ASSIGNMENT = '=';
    private static final Character EMPTYCHARACTER = ' ';

    /**
     * One Parameter Constructor
     *
     * @param InfixString Infix expressions
     */
    public InfixConvertPostfix(String InfixString) throws MySyntaxErrorException {
        setStackForAdditionAndExtraction();
        setPostfixList();
        setStackForMultiplicationAndDivision();
        setStackAssignment();
        try{
            infixExpressionsConvertPostfixForm(InfixString);
            controlPostfix();
        }catch (MySyntaxErrorException e){
            setPostfixList();
            throw new MySyntaxErrorException();
        }

    }//end One Parameter Constructor

    /**
     * Get Stack for Addition And Extraction
     *
     * @return Stack for Addition And Extraction
     */
    private Stack<Character> getStackForAdditionAndExtraction(){return stackForAdditionAndExtraction;}//end getStackForAdditionAndExtraction method

    /**
     * Set Stack For Addition And Extraction
     */
    private void setStackForAdditionAndExtraction(){
        stackForAdditionAndExtraction = new Stack<Character>();}//end setStackForAdditionAndExtraction method

    /**
     * Get Stack for MULTIPLICATION and DIVISION
     *
     * @return Stack for MULTIPLICATION and DIVISION
     */
    private Stack<Character> getStackForMultiplicationAndDivision(){return stackForMultiplicationAndDivision;}//end getStackForMultiplicationAndDivision method

    /**
     * Set Stack for MULTIPLICATION and DIVISION
     */
    private void setStackForMultiplicationAndDivision(){
        stackForMultiplicationAndDivision = new Stack<Character>();}//end setStackForMultiplicationAndDivision method

    /**
     * Get Stack Assignment for MULTIPLICATION and DIVISION
     *
     * @return Stack for Assignment
     */
    private Stack<Character> getStackAssignment(){return stackAssignment;}//end getStackAssignment method

    /**
     * Set Stack for Assignment
     */
    private void setStackAssignment(){stackAssignment = new Stack<Character>();}//end setStackAssignment method

    /**
     * Get Postfix String
     *
     * @return Postfix List
     */
    private List<Character>  getPostfixList(){return postfixList;}//end getPostfixString method

    /**
     * Set Postfix String
     */
    private void setPostfixList(){postfixList = new Stack<Character>();}//end setPostfixString method

    /**
     * Get Postfix To String
     *
     * @return Postfix To String
     */
    public String getPostfixToString(){
        StringBuilder postfix = new StringBuilder();
        for(int index =0; index < getPostfixList().size(); ++index)
            postfix.append(getPostfixList().get(index));
        return postfix.toString();
    }//end getPostfixToString method

    /**
     * Infix Expressions Convert Postfix Form
     *
     * @param InfixString Infix expressions
     */
    private void infixExpressionsConvertPostfixForm(String InfixString) throws MySyntaxErrorException {

        /*Check all elements in String */
        for(int index=0; index < InfixString.length(); ++index){

            /* İf char is ASSIGNMENT */
            if(InfixString.charAt(index) ==  ASSIGNMENT)
                getStackAssignment().push(InfixString.charAt(index));

            /* İf char is MULTIPLICATION or DIVISION */
            else if(InfixString.charAt(index) ==  MULTIPLICATION ||  InfixString.charAt(index) ==  DIVISION){

                getStackForMultiplicationAndDivision().push(InfixString.charAt(index));
            }

            /* İf char is ADDITION or EXTRACTION */
            else if(InfixString.charAt(index) ==  ADDITION ||  InfixString.charAt(index) ==  EXTRACTION){

                /* if stackForAdditionAndExtraction is empty then push the char Stack*/
                if(getStackForAdditionAndExtraction().empty())
                    getStackForAdditionAndExtraction().push(InfixString.charAt(index));

                /* if stackForAdditionAndExtraction is not empty */
                else{
                    getPostfixList().add(getStackForAdditionAndExtraction().pop());
                    getStackForAdditionAndExtraction().push(InfixString.charAt(index));
                }
            }

            /* if char is a operand then add in PostFix*/
            else{
                if(getPostfixList().size()>0) {
                    /* if last element in Postfix is not a space */
                    if (getPostfixList().get(getPostfixList().size() - 1) != ' '){
                        getPostfixList().add(InfixString.charAt(index));
                    }

                    /* if last element in Postfix is a space */
                    else{
                       if(InfixString.charAt(index)!= ' ') {
                           getPostfixList().add(InfixString.charAt(index));

                           while (!getStackForMultiplicationAndDivision().empty()) {
                               getPostfixList().add(' ');
                               getPostfixList().add(getStackForMultiplicationAndDivision().pop());
                           }
                       }
                    }
                }

                else{
                    getPostfixList().add(InfixString.charAt(index));
                }
            }
        }

        while (!getStackForMultiplicationAndDivision().empty()) {
            getPostfixList().add(' ');
            getPostfixList().add(getStackForMultiplicationAndDivision().pop());
        }

        /* Do Stack is Empty. All element in Stack add into PostfixList */
        while(!getStackForAdditionAndExtraction().empty()){
            getPostfixList().add(' ');
            getPostfixList().add(getStackForAdditionAndExtraction().pop());
        }

        /* if expressions not have a assignment then throw exception*/
        if(getStackAssignment().empty())
            throw new MySyntaxErrorException();

        /* add assignment operator into PostFixList*/
        else {
            while (!getStackAssignment().empty()) {
                getPostfixList().add(' ');
                getPostfixList().add(getStackAssignment().pop());
            }
        }
    }//end infixExpressionsConvertPostfixForm method

    /**
     * Control Postfix form is illegal or legal
     *
     * @return if postfix form is illegal then throw MySyntaxErrorException, otherwise return true.
     */
    public boolean controlPostfix() throws MySyntaxErrorException{

        /* İf left side is a number then throw MySyntaxErrorException */
        try {
            int foo = Integer.parseInt(String.valueOf(getPostfixList().get(0)));
            throw new MySyntaxErrorException();
        }catch (NumberFormatException e){}

        /* Second char isn't a assignment operator then throw MySyntaxErrorException */
        if(getPostfixToString().charAt(getPostfixToString().length()-1) != ASSIGNMENT)
            throw new MySyntaxErrorException();

        /* Check the right of assignment operator */
        Stack<Character> stack = new Stack<Character>();

        /* Check all elements in Postfix List if the wrong something then throw MySyntaxErrorException*/
        Iterator<Character> iter = (Iterator<Character>) getPostfixList().iterator();
        Character character;

        while(iter.hasNext()){

            character = iter.next();

            if(character != EMPTYCHARACTER) {
                /* if char a operand then push into stack */
                if (character != MULTIPLICATION && character != DIVISION && character != ADDITION &&
                        character != EXTRACTION && character != ASSIGNMENT) {
                    stack.push(character);
                }
                /* if char a operator then pop into stack 2 variables*/
                else {
                    try {
                        stack.pop();

                        if(character == ASSIGNMENT)
                            stack.pop();

                    } catch (EmptyStackException e) {
                        throw new MySyntaxErrorException();
                    }
                }
            }

        }



        return true;
    }
}
