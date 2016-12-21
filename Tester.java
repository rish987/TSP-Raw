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
    private final static int NUM_RAND_LOCS = 100;

    /* dimensions of map containin rand_locs */
    private final static double RAND_MAP_WIDTH = 500.0;
    private final static double RAND_MAP_HEIGHT = 500.0;

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
//        Location[] rand_locs = { new Location( 192.75876567011701, 299.31820789480315 ), new Location( 94.02963561766103, 134.74024340535567 ), new Location( 187.53670061764493, 253.26051914841148 ), new Location( 491.5091747605844, 329.44274825977845 ), new Location( 429.89423995266, 69.39729651327347 ), new Location( 248.03894787689688, 146.5372080604388 ), new Location( 375.9757713840731, 48.09653631681926 ), new Location( 394.68273386504023, 145.6765768775959 ), new Location( 208.9940719155569, 36.25769642380777 ), new Location( 432.7862344553367, 394.0091909133289 ) };
        /**/

        /* TODO */
//        System.out.print( "{ " );
//        /* go through all of the locations in rand_locs */
//        for ( int rand_locs_i = 0; rand_locs_i < NUM_RAND_LOCS - 1; 
//            rand_locs_i++ )
//        {
//            System.out.print( "new Location( " 
//                    + rand_locs[ rand_locs_i ].getX() 
//                    + ", " 
//                    + rand_locs[ rand_locs_i ].getY() 
//                    +  " ), " );
//        }
//        System.out.print( "new Location( " 
//                + rand_locs[ NUM_RAND_LOCS - 1 ].getX() 
//                + ", " 
//                + rand_locs[ NUM_RAND_LOCS - 1 ].getY() 
//                +  " )" );
//        System.out.println( " }" );
        /**/

        /* get the greedy tour through the random locs */
        Location[] greedy_tour = TSPAlgorithms.sol_greedy( rand_locs );

        System.out.println( "\nGREEDY SOLUTION: \n" );
//        /* go through each location in the greedy tour */
//        for ( int greedy_tour_ind = 0; greedy_tour_ind < greedy_tour.length; greedy_tour_ind++ )
//        {
//            /* print out this location */
//            System.out.println( "Location " + greedy_tour_ind + ": ( " 
//                    + greedy_tour[ greedy_tour_ind ].getX() 
//                    + ", " 
//                    + greedy_tour[ greedy_tour_ind ].getY() 
//                    +  " )" );
//        }
        System.out.println( "\nTour Length: " 
            + TSPAlgorithms.get_tour_length( greedy_tour ) );

        /* get the basic ACO tour through the random locs */
        Location[] ACO_basic_tour = TSPAlgorithms.sol_ACO_basic( rand_locs );

        System.out.println( "\nBASIC ACO SOLUTION: \n" );
//        /* go through each location in the basic ACO tour */
//        for ( int ACO_basic_tour_ind = 0; 
//            ACO_basic_tour_ind < ACO_basic_tour.length; ACO_basic_tour_ind++ )
//        {
//            /* print out this location */
//            System.out.println( "Location " + ACO_basic_tour_ind + ": ( " 
//                    + ACO_basic_tour[ ACO_basic_tour_ind ].getX() 
//                    + ", " 
//                    + ACO_basic_tour[ ACO_basic_tour_ind ].getY() 
//                    +  " )" );
//        }
        System.out.println( "\nTour Length: " 
            + TSPAlgorithms.get_tour_length( ACO_basic_tour ) );

        /* TODO */
//        EnumeratedIntegerDistribution test = 
//            new EnumeratedIntegerDistribution( new int[] { 1, 0 }, new double[] { 0.75, 0.25 } );
//        for ( int i = 0; i < 10; i++ )
//        {
//            System.out.println( test.sample() );
//        }
        /**/
    }
}
