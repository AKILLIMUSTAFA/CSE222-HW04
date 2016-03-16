package tr.edu.gtu.mustafa.akilli.cse222;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

    private Stack<Character> stack;/* Stack for operators */
    private List<Character> postfixList;/* postfix form */
    private Stack<Character> tempStack;/* Stack for MULTIPLICATION and DIVISION*/
    private Stack<Character> stackAssignment;/* Stack for Assignment*/
    private static final Character MULTIPLICATION = '*';
    private static final Character DIVISION = '/';
    private static final Character ADDITION = '+';
    private static final Character EXTRACTION = '-';
    private static final Character ASSIGNMENT = '=';


    /**
     * One Parameter Constructor
     *
     * @param InfixString Infix expressions
     */
    public InfixConvertPostfix(String InfixString){
        setStack();
        setPostfixList();
        setTempStack();
        setStackAssignment();
        try{
            infixExpressionsConvertPostfixForm(InfixString);
        }catch (Exception e){
            System.out.println("ERROR!! NOT FOUND ASSIGNMENT");
            setPostfixList();
        }

    }//end One Parameter Constructor

    /**
     * Get Stack for operators
     *
     * @return Stack for convert
     */
    private Stack<Character> getStack(){return stack;}//end getStack method

    /**
     * Set Stack for operators
     */
    private void setStack(){stack = new Stack<Character>();}//end setStack method

    /**
     * Get Temp Stack for MULTIPLICATION and DIVISION
     *
     * @return Temp Stack for MULTIPLICATION and DIVISION
     */
    private Stack<Character> getTempStack(){return tempStack;}//end getTempStack method

    /**
     * Set Temp for MULTIPLICATION and DIVISION
     */
    private void setTempStack(){tempStack = new Stack<Character>();}//end setTempStack method

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
    public List<Character>  getPostfixList(){return postfixList;}//end getPostfixString method

    /**
     * Set Postfix String
     */
    private void setPostfixList(){postfixList = new LinkedList<Character>();}//end setPostfixString method

    /**
     * Infix Expressions Convert Postfix Form
     *
     * @param InfixString Infix expressions
     */
    private void infixExpressionsConvertPostfixForm(String InfixString) throws Exception {

        /*Check all elements in String */
        for(int index=0; index < InfixString.length(); ++index){

            /* İf char is ASSIGNMENT */
            if(InfixString.charAt(index) ==  ASSIGNMENT)
                getStackAssignment().push(InfixString.charAt(index));

            /* İf char is MULTIPLICATION or DIVISION */
            else if(InfixString.charAt(index) ==  MULTIPLICATION ||  InfixString.charAt(index) ==  DIVISION){

                /* if stack is not empty then push the char Stack*/
                if(!getStack().empty())
                    getStack().push(InfixString.charAt(index));

                /* if stack is empty then push the char tempStack*/
                else
                    getTempStack().push(InfixString.charAt(index));
            }

            /* İf char is ADDITION or EXTRACTION */
            else if(InfixString.charAt(index) ==  ADDITION ||  InfixString.charAt(index) ==  EXTRACTION){

                /* if stack is empty then push the char Stack*/
                if(getStack().empty())
                    getStack().push(InfixString.charAt(index));

                /* if stack is not empty */
                else{
                    /* if last element in stack is MULTIPLICATION or DIVISION */
                    if(getStack().peek() ==  MULTIPLICATION ||  getStack().peek() ==  DIVISION){
                        /* Do Stack is Empty. All element in Stack add into PostfixList */
                        while(!getStack().empty()){
                            getPostfixList().add(getStack().pop());
                            if(!getStack().empty())
                                getPostfixList().add(' ');
                        }

                        /* add into char in stack */
                        getStack().push(InfixString.charAt(index));
                    }
                    /* if last element in stack is not MULTIPLICATION or DIVISION then push the char Stack*/
                    else
                        getStack().push(InfixString.charAt(index));
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

                           while (!getTempStack().empty()) {
                               getPostfixList().add(' ');
                               getPostfixList().add(getTempStack().pop());
                           }
                       }
                    }
                }

                else{
                    getPostfixList().add(InfixString.charAt(index));
                }

            }
        }

        /* Do Stack is Empty. All element in Stack add into PostfixList */
        while(!getStack().empty()){
            getPostfixList().add(' ');
            getPostfixList().add(getStack().pop());
        }

        /* if expressions not have a assignment then throw exception*/
        if(getStackAssignment().empty())
            throw new Exception();

        /* add assignment operator into PostFixList*/
        else {
            while (!getStackAssignment().empty()) {
                getPostfixList().add(' ');
                getPostfixList().add(getStackAssignment().pop());
            }
        }
    }


}
