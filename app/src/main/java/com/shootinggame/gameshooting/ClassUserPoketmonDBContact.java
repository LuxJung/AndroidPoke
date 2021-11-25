package com.shootinggame.gameshooting;

public class ClassUserPoketmonDBContact {

    private ClassUserPoketmonDBContact() {
    }

    public static final String TBL_CONTACT = "CONTACT_T";

    public static final String COL_NAME = "NAME";
    public static final String COL_IMAGE = "IMAGE";
    public static final String COL_PROFILE = "PROFILE";
    public static final String COL_PIKENUM = "POKENUM";
    public static final String COL_SPEED = "SPEED";
    public static final String COL_HP = "HP";
    public static final String COL_ATK = "ATK";

    // CREATE TABLE IF NOT EXISTS CONTACT_T (NO INTEGER NOT NULL, NAME TEXT, PHONE TEXT, OVER20 INTEGER)
    public static final String SQL_CREATE_TBL =
            "CREATE TABLE IF NOT EXISTS"+ TBL_CONTACT  +
                    "(" + COL_NAME + "TEXT" + "," +
                    COL_IMAGE + "TEXT" + "," +
                    COL_PROFILE + "TEXT" + ", " +
                    COL_PIKENUM + "TEXT" + ", " +
                    COL_SPEED + "TEXT" + ", " +
                    COL_HP + "TEXT" + ", " +
                    COL_ATK + "TEXT" + ")";

    // DROP TABLE IF EXISTS CONTACT_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT;

    // SELECT * FROM CONTACT_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT;

    // INSERT OR REPLACE INTO CONTACT_T
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO" + TBL_CONTACT +
            "(" + COL_NAME + "TEXT" + "," +
            COL_IMAGE + "TEXT" + "," +
            COL_PROFILE + "TEXT" + ", " +
            COL_PIKENUM + "TEXT" + ", " +
            COL_SPEED + "TEXT" + ", " +
            COL_HP + "TEXT" + ", " +
            COL_ATK + "TEXT" + ")" + " VALUES ";

    // DELETE FROM CONTACT_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_CONTACT;

}




