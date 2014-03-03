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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;
import java.util.Scanner;
import java.lang.Math;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** ClusteringAlgorithm.java
 *  This program uses trajectory clustering to group lines intelligently and output lines generated to a GUI.
 *  Lines are taken from text file named trajectorypoints.txt.
 *  Lines that belong to cluster are printed to screen as set color, noise lines are ignored entirely and not printed.
 *  Each line has two x values and two y values.
 *  Algorithm has two modifiable constants.
 *  First Constant: MINLNS, the minimum number of lines that constitute a proper cluster (default: 8).
 *  Second Constant: E, used to calculate distance between two lines (default and recommended: 1).
 *  
 *  Algorithm based on academic paper titled: "Trajectory Clustering: A Partition-and-Group Framework
 *  Jae-Gil Lee, Jiawei Han and Kyu-Young Whang
 *  
 *  Code released under GNU GPLv3.
 * @author Shane Christy
 * 
 * 
 */

public class ClusteringAlgorithm extends JPanel {
	public static Scanner sc;
	public static int i = 0;
	public static int i2 = 0;
	public static Line ln;
	public static boolean newuser = true;
	public static boolean newusertemp = true;
	public static double xValue;
	public static double xValue2;
	public static double yValue;
	public static double yValue2;
	public static int tempindexX;
	public static int tempindexY;
	public static int tempIndexUser;
	public static String tempStringX;
	public static String tempStringY;
	public static String temp;
	public static int clusterid = 1;
	public static int classifycount = 0;
	public static int check;
	public static int trajectorycount = 0;
	public static FileWriter fw;
	public static int k[] = new int[12];
	public static int traj[] = new int[12];
	public Image backgroundImage;
	public Color tempcolor;
	public static int MINLNS = 8; //minimum number of lines to constitute a cluster 
	public static double E = 1; //e value used for distance algorithm (1 is default and recommended)

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		int j = 0;
		try {
			backgroundImage = ImageIO.read(new File("learningfactory.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(backgroundImage, 0, 0, null); // draw background image of
													// Learning Factory
		while (j <= ln.classified.length - 3) {
			j++;
			if (ln.getCluster(j) == 1) { // assign color based on cluster ID, colors are changeable based on user preference
				tempcolor = Color.blue;
			}

			else if (ln.getCluster(j) == 2) {
				tempcolor = Color.CYAN;
			}

			else if (ln.getCluster(j) == 3) {
				tempcolor = Color.red;
			}

			else if (ln.getCluster(j) == 4) {
				tempcolor = Color.green;
			}

			else if (ln.getCluster(j) == 5) {
				tempcolor = Color.ORANGE;
			}

			else if (ln.getCluster(j) == 6) {
				tempcolor = Color.magenta;
			}

			else if (ln.getCluster(j) == 7) {
				tempcolor = Color.white;
			}

			else if (ln.getCluster(j) == 8) {
				tempcolor = Color.white;
			}

			else if (ln.getCluster(j) == 9) {
				tempcolor = Color.red;
			}

			else if (ln.getCluster(j) == 10) {
				tempcolor = Color.green;
			}

			else if (ln.getCluster(j) == 11) {
				tempcolor = Color.blue;
			}

			if (ln.getNoise(j) != true && ln.getCluster(j) != 0) { // only print
																	// if not
																	// noise
				// g.setStroke(new BasicStroke(3));
				((Graphics2D) g).setStroke(new BasicStroke(2)); // makes lines
																// slightly
																// thicker
				g.drawLine(38 * (ln.getP1X(j).intValue()), //multiply by 38 to scale to background image
						38 * (ln.getP1Y(j).intValue()),
						38 * (ln.getP2X(j)).intValue(),
						38 * (ln.getP2Y(j).intValue()));
				g.setColor(tempcolor); //set to temporary color
			}
		}

		repaint();
	}

	/** Main method for ClusteringAlgorithm trajectory clustering application, initiates GUI, constructors
	 *  Also reads file and sets array values while filtering out useless data
	 * @param args Command line string argument not used
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Clustering Algorithm");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1490, 1000); // the size of the background image, too large
									// for some monitors, resize source image as needed but not tested

		ClusteringAlgorithm panel = new ClusteringAlgorithm();
		frame.setContentPane(panel);
		frame.setVisible(true);

		/*
		 * Read in each line of the file, separating out pertinent data and
		 * useless data and storing x coords, y coords into Line.java
		 */
		try {
			sc = new Scanner(new FileReader("trajectorypoints.txt")); // file
																		// containing
																		// all
																		// the
																		// coords
																		// relies
																		// on a
																		// certain
																		// format
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (sc.hasNext()) {

			temp = sc.nextLine();
			// move to line with actual data
			if (temp.contains("Trajectory")) { // don't attempt to read lines
												// which contain no useful data
				trajectorycount++;
				temp = sc.nextLine();
				newuser = true;
			}
			if (newuser == false) {
				xValue = xValue2;
				yValue = yValue2;
				i++;
			} else if (newuser == true) {
				tempindexX = temp.indexOf("x="); // find index of x value in
													// line
				tempindexY = temp.indexOf("y="); // find index of y value in
													// line
				tempIndexUser = temp.indexOf("user="); // used to determine end
														// of y value in file
														// no other purpose

				String tempStringX = temp.substring(tempindexX + 2,
						tempindexY - 1);
				xValue = Double.parseDouble(tempStringX); // find x value,
															// convert to
															// double

				String tempStringY = temp.substring(tempindexY + 2,
						tempIndexUser - 1);
				// find y value, convert to double
				yValue = Double.parseDouble(tempStringY);

				i++;
			}
			/*
			 * The code gets rather confusing here I'm basically repeating the
			 * same process again for second point After the same process is
			 * done again I create my first line object, then it is repeated
			 * until the end of the file is reached and loop ends after loop
			 * ends, all of the line objects should be created see Line.java for
			 * details about what constitutes a line
			 */
			if (newuser == true) {
				temp = sc.nextLine();
			}
			if (temp.contains("Trajectory")) { // handle the case where the
												// user is switched in file, the
												// first x value should be
												// replaced
												// instead of using the x/y
												// values from the previous
												// user.
												// copy-pasted from above. After
												// executed, moves to finding
												// second point
				temp = sc.nextLine();

				tempindexX = temp.indexOf("x=");
				tempindexY = temp.indexOf("y=");
				tempIndexUser = temp.indexOf("user=");

				tempStringX = temp.substring(tempindexX + 2, tempindexY - 1);
				xValue = Double.parseDouble(tempStringX);

				tempStringY = temp.substring(tempindexY + 2, tempIndexUser - 1);
				yValue = Double.parseDouble(tempStringY);

				temp = sc.nextLine();
				if (newusertemp == true) {
					newuser = true;
				} else
					newuser = false;
				// newusertemp = false;

				// else continue as usual to next point in file
			}
			int tempindexX2 = temp.indexOf("x="); // find index of x value in
													// line
			int tempindexY2 = temp.indexOf("y="); // find index of y value in
													// line
			int tempIndexUser2 = temp.indexOf("user="); // used to determine
														// end
														// of y value in
														// file
														// no other purpose

			String tempStringX2 = temp.substring(tempindexX2 + 2,
					tempindexY2 - 1);
			xValue2 = Double.parseDouble(tempStringX2); // find x value,
														// convert to
														// double

			String tempStringY2 = temp.substring(tempindexY2 + 2,
					tempIndexUser2 - 1);
			// find y value, convert to double
			yValue2 = Double.parseDouble(tempStringY2);

			i2++;
			if (i == 1) { // only create if first iteration of loop
				ln = new Line(xValue, yValue, xValue2, yValue2, i,
						trajectorycount);
			} else if (i != 1) {
				ln.setP1X(i, xValue);
				ln.setP1Y(i, yValue);
				ln.setP2X(i, xValue2);
				ln.setP2Y(i, yValue2);
				ln.setTrajectory(i, trajectorycount + 74);
			}
/*
			 // purely for testing purposes to ensure all of my logic is sound, I
			// print out each line and the two points, uncomment if visual feedback desired
			System.out.println("Line " + i);
			System.out.println();
			System.out.println("P1: " + ln.getP1X(i) + ", " + ln.getP1Y(i));
			System.out.println("P2: " + ln.getP2X(i) + ", " + ln.getP2Y(i));
			System.out.println();
*/
			newuser = false;
		}
		sc.close();

		while (check != 1287) {
			check = checkLength();
			queue(determineStarting());
		}
		System.out.println("Results stored in output.txt");
		System.out.println();
		System.out.println("Cluster 0 is noise line and not outputted to screen");
		System.out.println();
		results();

	}

	// return distance between two lines based on two line numbers sent as
	// integer values

	/** Calculate distance between two lines using complex TRACULUS formula as detailed in "Trajectory Clustering:
	 *  A Parition-and-Group Framework" by Jae-gil Lee, Jiawei Han, Kyu-Young Whang
	 *  This algorithm is what whole application is based on, and is used to intelligently group lines
	 * @param li The first line to compare (l is line and i is index)
	 * @param lj The second line to compare (l is line and j is index)
	 * @return The distance between li first line and lj second line
	 */
	public static double calcDistance(int li, int lj) {
		// PERPINDICULAR DISTANCE FORMULA FOR TWO LINES

		double u1 = (((ln.getP1X(lj)) - ln.getP1X(li)) * (ln.getP2X(li) - ln
				.getP1X(li)))
				+ (((ln.getP1Y(lj)) - ln.getP1Y(li)) * (ln.getP2Y(li) - ln
						.getP1Y(li)));
		u1 = u1
				/ ((ln.getP2X(li) - ln.getP1X(li))
						* (ln.getP2X(li) - ln.getP1X(li)) + (ln.getP2Y(li) - ln
						.getP1Y(li)) * (ln.getP2Y(li) - ln.getP1Y(li)));

		double u2 = (((ln.getP2X(lj)) - ln.getP1X(li)) * (ln.getP2X(li) - ln
				.getP1X(li)))
				+ (((ln.getP2Y(lj)) - ln.getP1Y(li)) * (ln.getP2Y(li) - ln
						.getP1Y(li)));
		u2 = u2
				/ ((ln.getP2X(li) - ln.getP1X(li))
						* (ln.getP2X(li) - ln.getP1X(li)) + (ln.getP2Y(li) - ln
						.getP1Y(li)) * (ln.getP2Y(li) - ln.getP1Y(li)));
		double psx = ln.getP1X(li) + u1 * (ln.getP2X(li) - ln.getP1X(li));
		double psy = ln.getP1Y(li) + u1 * (ln.getP2Y(li) - ln.getP1Y(li));

		double pex = ln.getP1X(li) + u2 * (ln.getP2X(li) - ln.getP1X(li));
		double pey = ln.getP1Y(li) + u2 * (ln.getP2Y(li) - ln.getP1Y(li));

		double li1 = (ln.getP1X(lj) - psx) * (ln.getP1X(lj) - psx)
				+ (ln.getP1Y(lj) - psy) * (ln.getP1Y(lj) - psy);
		li1 = Math.sqrt(li1);

		double li2 = (ln.getP2X(lj) - pex) * (ln.getP2X(lj) - pex)
				+ (ln.getP2Y(lj) - pey) * (ln.getP2Y(lj) - pey);
		li2 = Math.sqrt(li2);

		double perpindistance = (li1 * li1) + (li2 * li2);
		perpindistance = (perpindistance) / (li1 + li2);
		// PARALLEL DISTANCE FORMULA FOR TWO LINES

		double di1 = (ln.getP1X(li) - psx) * (ln.getP1X(li) - psx)
				+ (ln.getP1Y(li) - psy) * (ln.getP1Y(li) - psy);
		di1 = Math.sqrt(di1);
		double di2 = (ln.getP2X(li) - pex) * (ln.getP2X(li) - pex)
				+ (ln.getP2Y(li) - pey) * (ln.getP2Y(li) - pey);
		di2 = Math.sqrt(di2);
		double distparallel = Math.min(di1, di2);

		// ANGULAR DISTANCE FORMULA FOR TWO LINES

		double ang1 = (ln.getP2X(li) - ln.getP1X(li))
				* (ln.getP2X(lj) - ln.getP1X(lj));

		double ang2 = (ln.getP2Y(li) - ln.getP1Y(li))
				* (ln.getP2Y(lj) - ln.getP1Y(lj));

		double len1 = (ln.getP2X(li) - ln.getP1X(li))
				* (ln.getP2X(li) - ln.getP1X(li))
				+ (ln.getP2Y(li) - ln.getP1Y(li))
				* (ln.getP2Y(li) - ln.getP1Y(li));
		len1 = Math.sqrt(len1);

		double len2 = (ln.getP2X(lj) - ln.getP1X(lj))
				* (ln.getP2X(lj) - ln.getP1X(lj))
				+ (ln.getP2Y(lj) - ln.getP1Y(lj))
				* (ln.getP2Y(lj) - ln.getP1Y(lj));
		len2 = Math.sqrt(len2);

		double cosang = (ang1 + ang2) / (len1 * len2);
		double angdistance = len2 * (Math.sqrt(1 - cosang * cosang));
		return distparallel + angdistance + perpindistance; //Adds together the three values for accurate, intelligent trajectory clustering and returns to user
	}

	
	/** This method is used to assign each line a cluster, comparing each line to other lines and finding the most suitable cluster
	 *  Algorithm is fast, calculates instantly for this amount of data
	 *  Efficiency yet unknown for larger amounts of data
	 * @param starting
	 */
	public static void queue(int starting) {
		int j = 1;
		int k = 1;
		int y = 0;
		int canidatecount = 0; 
		int startingspot = starting;

		// determine comparison line

		while (ln.clusterid.length - 3 >= y) {
			y++;
			int next = determineNext();
			double distv = calcDistance(startingspot, next);
			if (distv <= E && ln.getClassified(y) != true) {

				ln.setCanidate(y, true);
				ln.setProgress(y, true);
			} else if (ln.getClassified(y) != true && distv > E) {
				ln.setProgress(next, true);
			}
		}
		while (k <= ln.clusterid.length - 3) {

			boolean tempprog = ln.getCanidate(k);
			if (tempprog == true && ln.getClassified(k) != true) {
				canidatecount++;
				k++;

			} else {
				k++;

			}
		}

		k = 0;
		// cluster is valid because there are 8 or more lines in it
		if (canidatecount >= MINLNS) {
			clusterid++;
			while (k <= ln.clusterid.length - 3) {
				k++;
				if (ln.getCanidate(k) == true && ln.getClassified(k) == false) {
					ln.setClassified(k, true);
					// classifycount++;
					ln.setCluster(k, clusterid - 1);
					ln.setProgress(k, false);
					ln.setCanidate(k, false);
					// System.out.println(ln.clusterid.length);
				}

			}
			// cluster is not valid because there are less than 8 lines in it
		} else if (canidatecount < MINLNS) {
			// k = 1;
			while (k < ln.clusterid.length - 3) {
				if (ln.getProgress(k) == true) {
					ln.setProgress(k, false);
					ln.setCanidate(k, false);
					k++;
				}
				break;
			}

			ln.setClassified(startingspot, true);
			// classifycount++;
			ln.setNoise(startingspot, true);

		}
	}

	
	/** Determine the starting line to be used for comparison, this is run once every time
	//  queue() is called
	 * @return Comparison line
	 */
	public static int determineStarting() {
		int j = 0;
		int startingspot = 1;
		while (ln.clusterid.length - 3 >= j) {
			j++;
			boolean tempCheck = ln.getClassified(j);
			if (tempCheck == false) {
				startingspot = j;
				break;
			}

		}
		ln.setProgress(startingspot, true);
		ln.setCanidate(startingspot, true);
		return startingspot;
	}

	
	/**  Determine next line in queue, this method is accessed constantly during
	// queue() method
	 * This skips past noise lines and lines that have already been classified for efficiency
	 * and accuracy
	 * @return Next line in queue
	 */
	public static int determineNext() {

		int j = 1;
		int next;
		while (ln.clusterid.length - 3 >= j) {
			j++;
			boolean tempCheck = ln.getClassified(j);
			boolean progCheck = ln.getProgress(j);

			if (tempCheck == false && progCheck == false) {
				break;

			}

			else {

				j++;
			}
		}
		return j;
	}

	
	/** Checks whether every line is classified or if algorithm needs another pass
	 * @return Number of classified lines (does every line have a cluster?)
	 */
	public static int checkLength() {
		int cc = 0; // count total number of classified
		int j = 0; // used for loops
		while (j <= ln.clusterid.length - 3) {
			j++;
			if (ln.getClassified(j) == true) {
				cc++;
			}

		}
		return cc; // return how many classified as integer
	}

	//TODO Improve this method
	/** Prints results of algorithm to text file named output.txt
	 * This can be used for testing purposes or analysis purposes
	 * Modify output syntax as needed for your goal
	 * My format is listing each cluster (e.g. C1 equals Cluster 1) and how many lines each consists of
	 */
	public static void results() {
		int j = 0;
		int highestid = 0; // determine highest cluster id
		int highestid2 = 0; // temporary variable used for comparison
		int noisecount = 0; // count number of "noise" values in case this info
							// is needed

		while (j <= ln.clusterid.length - 3) {
			j++;
			highestid2 = ln.getCluster(j);
			if (highestid2 > highestid) {
				highestid = highestid2;
			}
			System.out.println("Line: " + j + " (Cluster " + ln.getCluster(j)
					+ ") (Trajectory " + ln.getTrajectory(j) + ")");
		}
		System.out.println();
		System.out.println("Clustering Complete:");
		// System.out.println();
		System.out
				.println("Total number of qualified clusters is " + highestid);
		System.out.println();

		j = 0;
		while (j <= ln.clusterid.length - 3) {
			j++;
			if (ln.getNoise(j) == true) {
				noisecount++;
			}

		}
		System.out.println("Total number of noise lines is " + noisecount);
		j = 0;

		while (j <= ln.clusterid.length - 3) {
			j++;
			k[ln.getCluster(j)]++;
		}
		j = 0;
		while (j <= ln.clusterid.length - 3) {
			j++;

		}

		try {
			fw = new FileWriter("output.txt"); // write select information to
												// file titled output.txt
		} catch (IOException e) {
			e.printStackTrace();
		}
		j = 0;
		//TODO Fix writing to file, clusters not properly retrieved and written
		PrintWriter printWriter = new PrintWriter(fw);
		while (j <= 10) {
			j++;
			printWriter.println("C" + j + ": " + k[j] + " line segments");
		}
		try {
			fw.close();
			System.out.println();
			System.out.println("File written and closed with no errors");
		} catch (IOException e) {
			e.printStackTrace();
		}
		j = 0;
	}

}