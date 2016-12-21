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
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

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

    /* the number of times the simple ACO algorithm has to produce the same
     * tour for a given ant in a row to declare that the algorithm has
     * stagnated */
    private static final int STAGNATION_THRESHHOLD = 10;

    /**
     * This method uses a basic version of the Ant Colony Optimization
     * algorithm to find a solution the TSP. That is, it seeks a shortest-lenth
     * Hamiltonian circuit through a given set of locations by starting a virtual
     * "ant at a certain location, and choosing and moving to a location from
     * among the remaining locations based on a probability that depends on the
     * amount of virtual "pheromone" on that edge. As the ant moves along the
     * edge, it lays down its own pheromone, and the pheromone along all of the
     * edges evaporates by some proportion. This repeauts until an ant completes a
     * hamiltonian circuit, after which another ant is sent out to repeat the
     * process. This is continued until the ants reach a stagnating state -
     * that is, when subsequent ants all construct the same circuit a specified
     * number of times.
     *
     * @param locs the locations to use to find a solution
     *
     * @return an array representing the minimum-length Hamiltonian tour
     * through the graph, as determined using the simple ACO algorithm
     */
    public static Location[] sol_ACO_basic ( Location[] locs )
    {
        /* get all of the paths involving locs */
        Path[][] paths = get_all_paths( locs );

        /* initialize the last tour */
        ArrayList<Location> last_tour = new ArrayList<Location>();

        /* has a stagnating state been reached? */
        boolean stagnated = false;

        /* the number of repeated tours so far */
        int repeated_tours = 0;

        /* the weight to give pheromone */
        double pheromone_weight = 1;
        /* the weight to give length */
        double length_weight = 1;

        /* repeat until stagnating state is reached */
        while ( !stagnated )
        {
            /* to store the previous tour */
            ArrayList<Location> prev_tour = new ArrayList<Location>( last_tour );

            /* clear the last tour so this ant can start a new one */
            last_tour.clear();

            /* indices of remaining locations that can be visited */
            ArrayList<Integer> remaining_locs_inds = new ArrayList<Integer>() ; 

            /* go through each index in locs */
            for ( int locs_i = 0; locs_i < locs.length; locs_i++ )
            {
                /* set the next element of remaining_locs_inds to this index */
                remaining_locs_inds.add( locs_i );
            }

            /* the index of the current location */
            int current_loc_ind = 0;

            /* remove current_loc_ind from remaining_locs */
            remaining_locs_inds.remove( new Integer( 0 ) );

            /* add the current location to the end of the last tour */
            last_tour.add( locs[ current_loc_ind ] );

            /* continue until there are no remaining locations */
            while ( remaining_locs_inds.size() > 0 )
            {
                /* the index of the location the ant was on before moving */
                int prev_loc_ind = current_loc_ind;

                /* TODO replace with choosing location based on probability */
                int chosen_loc_ind = remaining_locs_inds.get( 0 );
                /**/

                /* the total value of the remaining path weights */
                double remaining_weights_total = 0;

                /* go through each of the possible paths */
                for ( int rem_loc_ind = 0; rem_loc_ind < 
                    remaining_locs_inds.size(); rem_loc_ind++ )
                {
                    /* add the weight of this path to the total */
                    remaining_weights_total
                        += paths[ current_loc_ind ][ 
                        remaining_locs_inds.get( rem_loc_ind  ) ].getWeight( 
                        pheromone_weight, length_weight );
                }

                /* to store the probabilities of each path */
                double[] path_probs = new double[ remaining_locs_inds.size() ];

                /* go through each of the possible paths */
                for ( int rem_loc_ind = 0; rem_loc_ind < 
                    remaining_locs_inds.size(); rem_loc_ind++ )
                {
                    /* set the probability of the ant choosing this path */
                    path_probs[ rem_loc_ind ] = paths[ current_loc_ind ][ 
                        remaining_locs_inds.get( rem_loc_ind ) ].getWeight( 
                        pheromone_weight, length_weight )
                        / remaining_weights_total;
                }

                /* to store remaining_locs_inds as an integer array */
                int[] remaining_locs_inds_ints = new int[ 
                    remaining_locs_inds.size() ];

                /* go through each of the elements in remaining_locs_inds */
                for ( int rem_ind = 0; rem_ind < remaining_locs_inds.size(); 
                    rem_ind++ )
                {
                    remaining_locs_inds_ints[ rem_ind ]
                        = remaining_locs_inds.get( rem_ind );
                }

                /* create a new enumerated random distribution using the paths
                 * and the path probabilites to determine the path the ant
                 * takes next */
                EnumeratedIntegerDistribution path_dist = 
                    new EnumeratedIntegerDistribution( 
                    remaining_locs_inds_ints, path_probs );
            
                chosen_loc_ind = path_dist.sample();

                /* move the ant to the chosen location */
                current_loc_ind = chosen_loc_ind;

                /* add the chosen location to the end of last_tour */
                last_tour.add( locs[ current_loc_ind ] );

                /* remove current_loc_ind from remaining_locs */
                remaining_locs_inds.remove( new Integer( current_loc_ind ) );

                /* go through each of the rows in paths */
                for ( int row = 0; row < paths.length; row++ )
                {
                    /* go through each of the columns in paths */
                    for ( int col = 0; col < paths.length; col++ )
                    {
                        /* the row is less than to the column, so this will not
                         * be entered twice for the same path */
                        if ( row < col )
                        {
                            /* evaporate the pheromone along this path */
                            paths[ row ][ col ].evaporatePheromone();
                        }
                    }
                }

                /* increase the pheromone along the path the ant just traversed */
                paths[ prev_loc_ind ][ current_loc_ind ].addPheromone();
            }

            /* this tour and the previous one are the same */
            if ( last_tour.equals( prev_tour ) )
            {
                /* there is another repeated tour */
                repeated_tours++;
            }
            /* this tour and the previous one are not the same */
            else
            {
                /* TODO */
                System.out.println( "Different!" );
                /**/
                /* there are no repeated tours */
                repeated_tours = 0;
            }

            /* the repeated tours is beyond the threshhold */
            if ( repeated_tours > STAGNATION_THRESHHOLD )
            {
                /* the algorithm has satagnated */
                stagnated = true;
            }
        }

        /* return the greedy tour */
        return last_tour.toArray(new Location[last_tour.size()]);
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

    /**
     * Returns a 2D array of paths that represent the set of all paths between
     * all of the locations in a given set of locations.
     *
     * @param locs the locs to use to construct the paths
     */
    public static Path[][] get_all_paths ( Location[] locs )
    {
        /* to store the paths to return */
        Path[][] paths = new Path[ locs.length ][ locs.length ];

        /* go through each of the rows in paths */
        for ( int row = 0; row < paths.length; row++ )
        {
            /* go through each of the columns in paths */
            for ( int col = 0; col < paths.length; col++ )
            {
                /* the row is not equal to the column, so a path is defined
                 * between the two locations because they are not the same
                 * locations */
                if ( row != col )
                {
                    /* construct the path from the location at index row to the
                     * location at index col */
                    paths[ row ][ col ] = paths[ col ][ row ]
                        = new Path( locs[ row ], locs[ col ] );
                }
            }
        }

        return paths;
    }
}
