/*
 *     Copyright (C) <2014>  <Shane Christy>
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/** Line.java: Class that stores properties for all of the lines.
 * Includes x and y coordinates, as well as information used for operation of algorithm.
 * Code released under GNU GPLv3.
 * @author Shane Christy
 * 
 */
public class Line {
	Double lineP1[][] = new Double[1289][1289]; // 1289 // x value, y value of
												// first
	Double lineP2[][] = new Double[1289][1289]; // x value, y value of second
												// point
	boolean classified[] = new boolean[1289]; // whether or not belongs to
												// cluster
	int clusterid[] = new int[1289]; // cluster it belongs to

	boolean noise[] = new boolean[1289]; // whether or not it is noise

	boolean inProgress[] = new boolean[1289]; // if the calculation is currently
												// being determined

	boolean potentialCanidate[] = new boolean[1289]; // while each loop is being
														// run, each line is set
														// as
														// a temporary
														// "canidate" and is
														// decided at end
														// whether
														// or not it is a valid
														// clustre, if not it is
														// no longer
														// a canidate

	int trajectory[] = new int[1289];

	
	/** Information about each line in the Array, storing X and Y coordinates for both points as well as which trajectory part of
	 * and index in array
	 * @param xcoord1 First X coordinate of first line
	 * @param ycoord1 First Y coordinate of first line
	 * @param xcoord2 Second X coordinate of first line
	 * @param ycoord2 Second Y Coordinate of first line
	 * @param index The index of which part of array to search for line
	 * @param traj Which trajectory line belongs to
	 */
	public Line(double xcoord1, double ycoord1, double xcoord2,
			double ycoord2, int index, int traj) {

		/*
		 * 2D array For example, [0][0] and [0][1]
		 * would be point 1's x coords and y coords, same for point 2 array.
		 * [1][0] and [1][1] would be line 2, point 1's x coords and y coords,
		 * and so on...
		 */

		lineP1[index][index] = xcoord1;
		lineP1[index][index + 1] = ycoord1;

		lineP2[index][index] = xcoord2;
		lineP2[index][index + 1] = ycoord2;
		classified[index] = false; // initially set to false since line has been
									// created but not classified using
									// algorithm
		trajectory[index] = traj;

	}

	/** Gets x information about first line
	 * @param i Index in array
	 * @return X value of first point of line of i index
	 */
	public Double getP1X(int i) {
		return lineP1[i][i];
	}

	/** Gets y information about first line
	 * @param i Index in array
	 * @return Y value of first point of line of i index
	 */
	public Double getP1Y(int i) {
		return lineP1[i][i + 1];
	}

	/** Gets x information about second line
	 * @param i Index in array
	 * @return X value of second point of line of i index
	 */
	public Double getP2X(int i) {
		return lineP2[i][i];
	}

	/** Gets y information about second line
	 * @param i Index in array
	 * @return y value of second point of line of i index
	 */
	public Double getP2Y(int i) {
		return lineP2[i][i + 1];
	}

	/** Retrieve boolean value of if line is classified
	 * @param i Index in array to search for
	 * @return Is line already classified according to algorithm or not
	 */
	public boolean getClassified(int i) {
		return classified[i];
	}

	/** Non-permanent boolean value of if line is classified, not 100% certainty unlike getClassified() method
	 * @param i Index in array to search for
	 * @return Whether canidate line is true/false
	 */
	public boolean getCanidate(int i) {
		return potentialCanidate[i];
	}

	/** Set candidate line as true/false classified or not
	 * @param i Index in Array
	 * @param tf True/False boolean value
	 */
	public void setCanidate(int i, boolean tf) {
		potentialCanidate[i] = tf;
	}

	/** Assigns line to a trajectory based on algorithm information
	 * @param i Index in array
	 * @param tf Boolean value true/false line has found a proper trajectory
	 */
	public void setClassified(int i, boolean tf) {
		classified[i] = tf;
	}

	/** Sets line as belonging to a certain cluster according to clustering algorithm
	 * @param i Index in array
	 * @param id Integer value of which cluster line belongs to
	 */
	public void setCluster(int i, int id) {
		clusterid[i] = id;
	}

	/** Gets which cluster line belongs to
	 * @param i Index in array
	 * @return Integer value of which cluster line belongs to, which controls how it prints to screen
	 */
	public int getCluster(int i) {
		return clusterid[i];
	}

	/** Sets progress of line of in progress of finding cluster location, or already found
	 *  This is used to ensure proper operation of algorithm
	 * @param i Index in array
	 * @param tf True in progress, or false not in progress
	 */
	public void setProgress(int i, boolean tf) {
		inProgress[i] = tf;
	}

	/** Gets whether or not line is in progress of being calculated
	 * 	This is used to ensure proper operation of algorithm
	 * @param i
	 * @return line in progress or not
	 */
	public boolean getProgress(int i) {
		return inProgress[i];
	}

	/** Sets a line as noise, according to the algorithm it is useless information and will not be outputted to the screen for user to see
	 * @param i Index in array
	 * @param tf True is noise, false not noise
	 */
	public void setNoise(int i, boolean tf) {
		noise[i] = tf;
	}

	/** Gets whether a line is noise or not, meaning irrelevant to the algorithm's calculations and ignore in further passes of algorithm
	 * @param i Index in array
	 * @return Whether true, is noise, or false, is not noise
	 */
	public boolean getNoise(int i) {
		return noise[i];
	}

	/** Return integer array of IDs of clusters that each line belongs to
	 * @return Two-dimensional array of integers
	 */
	public int[] getClusterArray() {
		return clusterid;
	}

	/*
	 * Used to set values after constructor has been called for first time
	 */
	
	
	/** Set x value of first point of line
	 * @param i Index
	 * @param xcoord X coordinate as double
	 */
	public void setP1X(int i, double xcoord) {
		lineP1[i][i] = xcoord;
	}

	/** Sets x value of second point of line
	 * @param i Index
	 * @param xcoord X coordinate as double
	 */
	public void setP2X(int i, double xcoord) {
		lineP2[i][i] = xcoord;
	}

	/** Sets y value of first point of line
	 * @param i Index
	 * @param ycoord Y coordinate as double
	 */
	public void setP1Y(int i, double ycoord) {
		lineP1[i][i + 1] = ycoord;
	}

	/** Sets y value of second point of line
	 * @param i Index
	 * @param ycoord Y coordinate as double
	 */
	public void setP2Y(int i, double ycoord) {
		lineP2[i][i + 1] = ycoord;
	}

	/** Set which trajectory the line belongs to according to algorithm
	 * @param i Index
	 * @param traj Trajectory as integer
	 */
	public void setTrajectory(int i, int traj) {
		trajectory[i] = traj;
	}

	/** Get which trajectory the line belongs to according to algorithm
	 * @param i Index
	 * @return Trajectory as integer
	 */
	public int getTrajectory(int i) {
		return trajectory[i];
	}

}
