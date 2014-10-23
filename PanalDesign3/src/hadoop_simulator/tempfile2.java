/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

/**
 *
 * @author Ankit
 */

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author hp
 */
public class tempfile2 {
    
     static int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
        
        
    public static void main(String[] args) throws IOException
	{
		long[] array1;
                
                DefaultCategoryDataset dataset  = new DefaultCategoryDataset();
		
		Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
                 String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		//newfilename = prop.getProperty("newFileName");
    		//System.out.println(newfilename);
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
	    	} catch (Exception ex)
	    		 {
	    		ex.printStackTrace();
	        	}
	        	
	        array1 = new long[NumofNodes];
	        int i;	
			for(i=1;i<=(NumofNodes);i++)
			{
//*************************//	
                            divide.dividefileintoblocks("D:\\aaaaaa\\AB.mp4","D:\\aaaaaa");
				
				failNodes.failNodes(i);
				
				long startTime = System.currentTimeMillis();
				boolean value;
				value = repairFailedNodes.repairFailedNodes();
				long endTime   = System.currentTimeMillis();
				long regenerateTime = endTime - startTime ;
				if(value==true)
				{				
				 
				System.out.println("regeneration node Time :: "+regenerateTime);	
				array1[i-1]=regenerateTime;
				}
				else{
					array1[i-1]=0;
				}
                                dataset.setValue(array1[i-1], "", ""+i);
                                
			}
		
		
		for(i=0;i<NumofNodes;i++)
		{
			System.out.println(array1[i]);
			//System.out.println("asdadsad");
		}
                
                 JFreeChart chart = ChartFactory.createBarChart("", "No of failed Nodes", "Time of repair (in miliseconds)", dataset, PlotOrientation.VERTICAL, true, true, false) ;
        
        
                CategoryPlot p = chart.getCategoryPlot() ;
                p.setRangeGridlinePaint(Color.BLACK);
                
                ChartFrame f = new ChartFrame("Repair Time Vs. Number of failed Nodes",chart);

                f.setVisible(true);

                f.setSize(450,300);
	
		
	
    
}
}
