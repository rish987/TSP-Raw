/* 
 * Filename:    Tester.java
 * Author:      Rish Vaishnav
 * Date:        12/15/2016
 *
 * Description:
 * This file contains the Tester class. See class header for more information.
 */

/**
 * This class contains a main method that can be used to test the various TSP
 * algorithms found in the class TSPAlgorithms.
 */
class Tester
{
    /* the number of random locations to create */
    private final static int NUM_RAND_LOCS = 10;

    /* dimensions of map containin rand_locs */
    private final static double RAND_MAP_WIDTH = 100.0;
    private final static double RAND_MAP_HEIGHT = 100.0;

    /**
     * Main method, used to test the various TSP algorithms found in the class 
     * TSPAlgorithms.
     *
     * @param args Strings input by user prior to running program
     */ 
    public static void main( String[] args )
    {
        /* random locations */
        Location[] rand_locs = new Location[ NUM_RAND_LOCS ];

        /* go through all of the locations in rand_locs */
        for ( int rand_locs_i = 0; rand_locs_i < NUM_RAND_LOCS; rand_locs_i++ )
        {
            /* set this random location to a new randomized location */
            rand_locs[ rand_locs_i ] = new Location( 
                Math.random() * RAND_MAP_WIDTH,
                Math.random() * RAND_MAP_HEIGHT );
        }

        /* TODO */
        System.out.print( "{ " );
        /* go through all of the locations in rand_locs */
        for ( int rand_locs_i = 0; rand_locs_i < NUM_RAND_LOCS - 1; 
            rand_locs_i++ )
        {
            System.out.print( "new Location( " 
                    + rand_locs[ rand_locs_i ].getX() 
                    + ", " 
                    + rand_locs[ rand_locs_i ].getY() 
                    +  " ), " );
        }
        System.out.print( "new Location( " 
                + rand_locs[ NUM_RAND_LOCS - 1 ].getX() 
                + ", " 
                + rand_locs[ NUM_RAND_LOCS - 1 ].getY() 
                +  " )" );
        System.out.println( " }" );
        /**/
    }
}
