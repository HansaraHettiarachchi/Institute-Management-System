/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hansara
 */
public class User {

    private static int id;
    private static int wTypeID = 3;
    private static boolean bDone = true;

    /**
     * @return the id
     */
    public static int getId() {
        return id;
    }

    /**
     * @param aId the id to set
     */
    public static void setId(int aId) {
        id = aId;
    }

    /**
     * @return the bDone
     */
    public static boolean isbDone() {
        return bDone;
    }

    /**
     * @param abDone the bDone to set
     */
    public static void setbDone(boolean abDone) {
        bDone = abDone;
    }

    /**
     * @return the wTypeID
     */
    public static int getwTypeID() {
        return wTypeID;
    }

    /**
     * @param awTypeID the wTypeID to set
     */
    public static void setwTypeID(int awTypeID) {
        wTypeID = awTypeID;
    }

}
