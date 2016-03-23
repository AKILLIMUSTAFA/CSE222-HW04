package tr.edu.gtu.mustafa.akilli.cse222;

/**
 * HW04_131044017_Mustafa_Akilli
 *
 * File:   Registers
 *
 * Description:
 *
 * Registers
 *
 * @author Mustafa_Akilli
 * @since Sunday 23 March 2016 by Mustafa_Akilli
 */
public class Registers {
    private Integer registerNameSequence;
    private String registerName;
    private String variableName;

    /**
     * One parameter Constructor
     *
     * @param newRegisterNameSequence
     * @param newVariableName
     */
    public Registers(Integer newRegisterNameSequence, String newVariableName){

        setVariableName(newVariableName);
        setRegisterNameSequence(newRegisterNameSequence);
        setRegisterName();
    }

    /**
     * Set Register Name
     *
     */
    public void setRegisterName(){

        switch (getRegisterNameSequence()){
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

    /**
     * Get Register Name
     *
     * @return
     */
    public String getRegisterName(){return registerName;}

    /**
     * Set Register Name Sequence
     *
     * @param newRegisterNameSequence
     */
    public void setRegisterNameSequence(Integer newRegisterNameSequence){registerNameSequence = newRegisterNameSequence;}

    /**
     * Get Register Name Sequence
     *
     * @return
     */
    public Integer getRegisterNameSequence(){return registerNameSequence;}

    /**
     * Set Variable Name
     *
     * @param newVariableName
     */
    public void setVariableName(String newVariableName){variableName = newVariableName;}

    /**
     * Get Variable Name
     *
     * @return
     */
    public String getVariableName(){return variableName;}
}
