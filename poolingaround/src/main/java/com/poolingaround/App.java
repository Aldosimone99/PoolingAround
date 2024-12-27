package com.poolingaround;

import com.poolingaround.controllers.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    static Controller controller = new Controller();
    public static void main( String[] args )
    {
        controller.startMenu();
    }
}
