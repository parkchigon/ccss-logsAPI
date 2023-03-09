package com.lgu.common.util;




/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class TerminatingZeroNotFoundException extends  Exception
{
    public TerminatingZeroNotFoundException()
    {
        super("Terminating zero not found in buffer.");
    }
    
    public TerminatingZeroNotFoundException(String s)
    {
        super(s);
    }
    
    
}