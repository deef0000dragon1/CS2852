/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 2
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab3;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.msoe.se1010.winPlotter.WinPlotter;

/**
 * clas in charge of starting and mantaining the program. initalizes the gui and
 * all buttons. Waits for input. from user after.
 */
public class DotViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	public List<Dot> original = new ArrayList<Dot>();
	public List<Dot> changed = new ArrayList<Dot>();
	private String numberOfPoints;
	private WinPlotter plotter;

	public void loadImage() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "pnt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this); // gets the file using a
														// file chooser
		// lists must be initalized once for addition of points to work.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			original = CreatePoints.ReadFromFile(new File(chooser.getSelectedFile().getPath())); 
			//creates the list of original points. 
		}

		plotter = new WinPlotter();
		//sould erase the plotter and re draw it. IT does not. 
		
		plotter.setPenColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
		plotter.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
		//sets the color of the pen background. 
		plotter.setWindowSize(600, 600);// sets the window size
		
		// plotter.setPlotBoundaries(getMinX()-1, getMinY()-1, getMaxX()+1,
		// getMaxY()+1);
		plotter.setPlotBoundaries(0, 0, 1, 1); 
		//fudge the plot boundries to account for above code not working. 
		plotter.moveTo(original.get(original.size() - 1).getX(), original.get(original.size() - 1).getY()); 
		//moves the plotter to the last point. 
		
		for (int i = 0; i < original.size(); i++) {
			plotter.drawTo(original.get(i).getX(), original.get(i).getY()); 
			//draw loop that draws from last point through all points to end point. 
		}
		numberOfPoints = original.size() + ""; // sets the number of points
												// string to the number of
												// points currently.
	}

	/**
	 * creates the pannel.
	 */
	public static void main(String args[]) {
		new DotViewer();
	}

	/**
	 * builds the gui and creates initalizes changed and original. Unknown why
	 * extra initalization code is necessary.
	 */
	public DotViewer() {
		super();
		buildGUI();

	}

	/**
	 * 
	 * builds and constructs the GUI that the user interacts with. uses gridbag
	 * layout and internal lambda expressions for action listeners as the
	 * provided the easiest method of implementation
	 */
	private void buildGUI() {
		GridBagLayout layout = new GridBagLayout();

		// create the sui objects for manipulation
		JButton SelectButton = new JButton();
		JLabel numberOfPointsLabel = new JLabel();
		JLabel InputLabel = new JLabel();
		JTextField InputText = new JTextField();
		JButton DotButton = new JButton();
		JButton LineButton = new JButton();
		JButton BothButton = new JButton();
		JButton Benchmark = new JButton();

		// set text, add listener, set layout, add item.
		SelectButton.setText("Select File");
		SelectButton.addActionListener(e -> {
			plotter = null; // sets the plotter to null. unknown why this is
							// necessary.
			loadImage();// load image function
			numberOfPointsLabel.setText("Points In Original File: " + numberOfPoints); 
			//change the text of the set text label. 
		});
		layout.setConstraints(SelectButton, makeGridBagLayoutConstraints(0, 0, 5, 1, GridBagConstraints.HORIZONTAL));
		add(SelectButton);

		numberOfPointsLabel.setText("Points In Original File: " + numberOfPoints);
		layout.setConstraints(numberOfPointsLabel,
				makeGridBagLayoutConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL));
		add(numberOfPointsLabel);

		InputLabel.setText("Input Number of desired Dots:");
		layout.setConstraints(InputLabel, makeGridBagLayoutConstraints(0, 2, 3, 1, GridBagConstraints.HORIZONTAL));
		add(InputLabel);

		InputText.setText("100");
		layout.setConstraints(InputText, makeGridBagLayoutConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL));
		add(InputText);

		try {
			DotButton.setText("Dots!");
			DotButton.addActionListener(e -> {
				if (plotter != null) {// if the plotter exists
					CreatePoints.getDesiredDots(original, changed, Integer.parseInt(InputText.getText())); 
					//update the dots to the number wanted. 
					plotter.erase();// clear the plotter

					for (int i = 0; i < changed.size(); i++) {
						plotter.drawPoint(changed.get(i).getX(), changed.get(i).getY());
						// draw the points for each dot in the changed points.

					}
				}
			});
			layout.setConstraints(DotButton, makeGridBagLayoutConstraints(0, 4, 2, 1, GridBagConstraints.HORIZONTAL));
			add(DotButton);

			LineButton.setText("Lines!");
			LineButton.addActionListener(e -> {
				if (plotter != null) {
					CreatePoints.getDesiredDots(original, changed, Integer.parseInt(InputText.getText()));
					plotter.erase();

					plotter.moveTo(changed.get(changed.size() - 1).getX(), changed.get(changed.size() - 1).getY());
					// move the plotter to the last dots position
					for (int i = 0; i < changed.size(); i++) {
						plotter.drawTo(changed.get(i).getX(), changed.get(i).getY());
						// move to each point on the list and draw from that
						// point to the next.
					}

				}
			});
			layout.setConstraints(LineButton, makeGridBagLayoutConstraints(1, 4, 2, 1, GridBagConstraints.HORIZONTAL));
			add(LineButton);

			BothButton.setText("Both!");
			BothButton.addActionListener(e -> {
				if (plotter != null) {// does oth loops from points and from
										// lines
					plotter.erase();
					CreatePoints.getDesiredDots(original, changed, Integer.parseInt(InputText.getText()));

					plotter.moveTo(changed.get(changed.size() - 1).getX(), changed.get(changed.size() - 1).getY());
					// move the plotter to the last dots position
					for (int i = 0; i < changed.size(); i++) {
						plotter.drawTo(changed.get(i).getX(), changed.get(i).getY());
						// move to each point on the list and draw
						// the lines from that point to the next. 
					}

					for (int i = 0; i < changed.size(); i++) {
						plotter.drawPoint(changed.get(i).getX(), changed.get(i).getY());
					}

				}
			});
			layout.setConstraints(BothButton, makeGridBagLayoutConstraints(0, 5, 2, 1, GridBagConstraints.HORIZONTAL));
			add(BothButton);

			Benchmark.setText("Benchmark!");
			Benchmark.addActionListener(e -> {

				// TODO write benchmarking code.
				long time;
				String result;
				ArrayList<Dot> arrayListOriginal = (ArrayList<Dot>) original;
				LinkedList<Dot> linkedListOriginal = new LinkedList(original);
				ArrayList<Dot> arrayListChanged = (ArrayList<Dot>) changed;
				LinkedList<Dot> linkedListChanged = new LinkedList(changed);

				time = (CreatePoints.getDesiredDots(arrayListOriginal, arrayListChanged,
						Integer.parseInt(InputText.getText())));
				System.out.println("Indexed ArrayList: " + time);

				time = (CreatePoints.getDesiredDots(linkedListOriginal, linkedListChanged,
						Integer.parseInt(InputText.getText())));
				System.out.println("Indexed Linked List: " + time);

				time = (CreatePoints.getDesiredDots2(arrayListOriginal, arrayListChanged,
						Integer.parseInt(InputText.getText())));
				System.out.println("Itterated ArrayList: " + time);

				time = (CreatePoints.getDesiredDots2(linkedListOriginal, linkedListChanged,
						Integer.parseInt(InputText.getText())));
				System.out.println("Itterated Linked List: " + time);

			});
			layout.setConstraints(Benchmark, makeGridBagLayoutConstraints(1, 5, 2, 1, GridBagConstraints.HORIZONTAL));
			add(Benchmark);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "");
		}
		setLayout(layout);
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * method for easier costruction of gridbag items.
	 * 
	 * @param gridx,gridy,weightx,weighty,fill(-1
	 *            for none)
	 */
	private GridBagConstraints makeGridBagLayoutConstraints(int gx, int gy, int wx, int wy, int fill) {

		GridBagConstraints Constraints = new GridBagConstraints();
		Constraints.gridx = gx;
		Constraints.gridy = gy;
		Constraints.weightx = wx;
		Constraints.weighty = wy;
		if (fill != -1) {
			Constraints.fill = fill;
		}

		return Constraints;

	}

	/**
	 * retruns the smallest x value. one of four methods that are not currently
	 * in use because of unnatural code errors.
	 * plotter.setPlotBoundaries(getMinX()-1, getMinY()-1, getMaxX()+1,
	 * getMaxY()+1); did not retrn a plot size that allowed for the image to be
	 * shown.
	 */
	// returns the smallest of the points. References Canged
	private double getMinX() {
		double smallest = Double.MAX_VALUE;

		for (Dot i : original) {
			if (i.getX() < smallest) {
				smallest = i.getX();
			}
		}
		return smallest;
	}

	/**
	 * retruns the smallest y value. one of four methods that are not currently
	 * in use because of unnatural code errors.
	 * plotter.setPlotBoundaries(getMinX()-1, getMinY()-1, getMaxX()+1,
	 * getMaxY()+1); did not retrn a plot size that allowed for the image to be
	 * shown.
	 */
	private double getMinY() {
		double smallest = Double.MAX_VALUE;

		for (Dot i : original) {
			if (i.getY() < smallest) {
				smallest = i.getY();
			}
		}
		return smallest;
	}

	/**
	 * retruns the largest x value. one of four methods that are not currently
	 * in use because of unnatural code errors.
	 * plotter.setPlotBoundaries(getMinX()-1, getMinY()-1, getMaxX()+1,
	 * getMaxY()+1); did not retrn a plot size that allowed for the image to be
	 * shown.
	 */
	private double getMaxX() {
		double smallest = Double.MAX_VALUE;

		for (Dot i : original) {
			if (i.getX() > smallest) {
				smallest = i.getX();
			}
		}
		return smallest;
	}

	/**
	 * retruns the largest y value. one of four methods that are not currently
	 * in use because of unnatural code errors.
	 * plotter.setPlotBoundaries(getMinX()-1, getMinY()-1, getMaxX()+1,
	 * getMaxY()+1); did not retrn a plot size that allowed for the image to be
	 * shown.
	 */
	private double getMaxY() {
		double smallest = Double.MAX_VALUE;

		for (Dot i : original) {
			if (i.getY() > smallest) {
				smallest = i.getY();
			}
		}
		return smallest;
	}
	// end of min/max X and Y getting

}
