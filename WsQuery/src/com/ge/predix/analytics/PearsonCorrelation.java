/**
 * 
 */
package com.ge.predix.analytics;

import java.text.DecimalFormat;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * @author Ravi_Shankar10
 *
 */
public class PearsonCorrelation {

	public static void main(String[] args) {

		PearsonsCorrelation correlation = new PearsonsCorrelation();

		/*
		 * Sample timeseries data for sensor EQUIP1-AI: This data will be feed
		 * from timeseries service of GE predix.
		 */
		double[][] data = { { 1, 1, 1, 2 }, { 2, 4, 0.5, 4 }, { 3, 9, 0.33, 6 }, { 4, 16, 0.25, 8 }, { 5, 25, 0.2, 10 },
				{ 6, 36, 0.16, 12 }, { 7, 49, 0.14, 14 }, { 8, 64, 0.125, 16 } };

		RealMatrix m = correlation.computeCorrelationMatrix(data);

		System.out.println("Correlation trace: " + m.getTrace());

		double[][] d = m.getData();
		DecimalFormat df = new DecimalFormat("##0.00");
		System.out.println("************ Matrix data *************** ");
		System.out.println("Number\tSquare\t 1/n\t *2");
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension(); j++) {
				System.out.print(df.format(d[i][j]) + "\t");
			}
			System.out.println("");
		}
		System.out.println("********** Matrix end **************");

	}

}
