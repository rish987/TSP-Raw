/* 
 * Filename:    TSPAlgorithms.java
 * Author:      Rish Vaishnav
 * Date:        12/16/2016
 *
 * Description:
 * This file contains the TSPAlgorithms class. See class header for more 
 * information.
 */
import java.util.ArrayList;

/**
 * This class is a utility class that contains various methods that can be used
 * to find solutions to the Traveling Salesman Problem (TSP); See method
 * headers for more information.
 */
public final class TSPAlgorithms
{
    /**
     * This method uses the greedy algorithm to find an (often unoptimal) 
     * solution the TSP. That is, it seeks a shortest-lenth Hamiltonian tour
     * through a given set of locations by starting at a certain location,
     * moving to the nearest location, and continuing to do so until all of the
     * locations have been visited. This is done using each of the given
     * locations as a starting location. After determining which starting
     * location produces the minimum-length tour, the greedy algorithm tour
     * corresponding to this location is returned (represented as an ordered
     * array of locations). This tour can be reversed and/or rotated to
     * produce other tours of equivalent length.
     *
     * @param locs the locations to use to find a solution
     *
     * @return an array representing the minimum-length Hamiltonian tour
     * through the graph, as determined using the greedy algorithm
     */
    public static Location[] sol_greedy ( Location[] locs )
    {
        /* minimum tour length so far */
        double min_length = Double.MAX_VALUE;
        
        /* minimum length tour so far */
        Location[] min_length_tour = null;

        /* iterate through every location */
        for ( int locs_i = 0; locs_i < locs.length; locs_i++ )
        {
            /* get at greedy tour starting at this location */
            Location[] this_tour = get_greedy_start( locs, locs_i );
            /* get the length of this tour */
            double this_tour_length = get_tour_length( this_tour );

            /* this tour's length is less than the minimum length so far */
            if ( this_tour_length < min_length )
            {
                /* reset the minimum length */
                min_length = this_tour_length;

                /* reset the minimum length tour */
                min_length_tour = this_tour;
            }
        }

        /* return the minimum length greedy tour */
        return min_length_tour;
    }

    /** 
     * This method uses the greedy algorithm to find an (often unoptimal)
     * solution the TSP, starting at a specified location. That is, it seeks a
     * shortest-lenth Hamiltonian tour through a given set of locations by
     * starting at a certain location, moving to the nearest location, and
     * continuing to do so until all of the locations have been visited.  This
     * tour can be reversed and/or rotated to produce other tours of equivalent
     * length.
     *
     * @param locs the locations to use to find a solution
     * @param start_ind the index of the locations at which to start the greedy
     * algorithm
     *
     * @return an array representing the minimum-length Hamiltonian tour
     * through the graph, as determined using the greedy algorithm starting at
     * the location with index start_ind
     */
    public static Location[] get_greedy_start( Location[] locs, int start_ind )
    {
        /* remaining locations that can be visited */
        ArrayList<Location> remaining_locs = new ArrayList<Location>( 
            java.util.Arrays.asList( locs ) ) ; 

        /* initialize the current location and the greedy tour with the
         * location at start_ind */
        Location current_loc = locs[ start_ind ];
        ArrayList<Location> greedy_tour = new ArrayList<Location>();
        greedy_tour.add( locs[ start_ind ] );

        /* remove this location from the remaining locations */
        remaining_locs.remove( start_ind );

        /* continue until there are no remaining locations */
        while ( remaining_locs.size() > 0 )
        {
            /* minimum distance to a location so far */
            double min_dist = get_distance_between( current_loc, 
                remaining_locs.get( 0 ) );
            Location min_dist_loc = remaining_locs.get( 0 );
            int min_dist_loc_ind = 0;
            /* TODO */
//            System.out.println( "--- Remaining Locations: "
//                + remaining_locs.size() + " ---" );
//
//            System.out.println( "Distance to (" 
//                    + remaining_locs.get( 0 ).getX()
//                    + ","
//                    + remaining_locs.get( 0 ).getY() 
//                    + "): " + min_dist );
            /**/

            for ( int i = 1; i < remaining_locs.size(); i++ )
            {
                /* get the distance to this locations */
                double this_dist = get_distance_between( current_loc, 
                    remaining_locs.get( i ) );

                /* this distance is less than the minimum distance so far */
                if ( this_dist < min_dist )
                {
                    /* this is the new minimum distance location */
                    min_dist = this_dist;
                    min_dist_loc = remaining_locs.get( i );
                    min_dist_loc_ind = i;
                }
                /* TODO */
//                System.out.println( "Distance to (" 
//                        + remaining_locs.get( i ).getX()
//                        + ","
//                        + remaining_locs.get( i ).getY() 
//                        + "): " + this_dist );
                /**/
            }
            /* TODO */
//            System.out.println( "Minimum distance to (" 
//                    + remaining_locs.get( min_dist_loc_ind ).getX()
//                    + ","
//                    + remaining_locs.get( min_dist_loc_ind ).getY() 
//                    + "): " + min_dist );
            /**/
            /* add this location to the greedy tour */
            greedy_tour.add( min_dist_loc );
            /* remove this location from the remaining locations */
            remaining_locs.remove( min_dist_loc_ind );
            /* this is the new current loc */
            current_loc = min_dist_loc;
        }

        /* return the greedy tour */
        return greedy_tour.toArray(new Location[greedy_tour.size()]);
    }

    /**
     * Returns the distance between two given locations.
     *
     * @param loc1 the first location
     * @param loc2 the second location
     *
     * @return the distance between loc1 and loc2
     */
    public static double get_distance_between ( Location loc1, Location loc2 )
    {
        /* the x- and y-distances between the two locations */
        double x_dis = loc1.getX() - loc2.getX();
        double y_dis = loc1.getY() - loc2.getY();

        /* return the distance between loc1 and loc2 */
        return Math.sqrt( Math.pow( x_dis, 2 ) + Math.pow( y_dis, 2 ) );
    }

    /**
     * Returns the total length of a given tour, including the distance from
     * the last location to the first location.
     *
     * @param tour the tour to find the length of
     *
     * @return the total length of the tour
     */
    public static double get_tour_length ( Location[] tour )
    {
        /* the total length of the tour */
        double total = 0.0;

        /* go through each location except the last one */
        for ( int tour_i = 0; tour_i < tour.length - 1; tour_i++ )
        {
            /* add the distance between this location and the next to the total
             * length of the tour so far */
            total += get_distance_between( tour[ tour_i ], tour[ tour_i + 1 ] );
        }
        /* add the distance between the last location and the first to the total
         * length of the tour */
        total += get_distance_between( tour[ tour.length - 1 ], tour[ 0 ] );

        /* return the total length of the tour */
        return total;
    }
}
