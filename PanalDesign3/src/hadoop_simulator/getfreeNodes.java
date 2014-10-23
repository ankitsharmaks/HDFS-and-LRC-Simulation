/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

/**
 *
 * @author Ankit
 */

//package outer ;


import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.*;


public class getfreeNodes  

{
	static	int BlockSize;
	static	int replicationFactor;

	public String[] getfreenodes(String[] nodes)//, int replicfactor)
	{	
		
			Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
            //String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		//newfilename = prop.getProperty("newFileName");
    		//System.out.println(newfilename);
    		//NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		
		
		int numofNodes=nodes.length; 
		// counts total num of nodes
		int replicfactor = replicationFactor ; 
		ArrayList<Integer> numFilesinNode = new ArrayList<Integer>();
		//int[] nodeElems;
		//nodeElems = new int[numofNodes];
		//  to store number of files stored in that node
		
		String[] retrnNodes;
		retrnNodes = new String[replicfactor];
		// to return the nodes where files blocks will be saved
		
		int i;
		int j;
		
		for(i=0;i<numofNodes;i++)
			{
				int temp;
				File dirr =new File(nodes[i]);
				temp =	dirr.listFiles().length ;
				numFilesinNode.add(temp);
			}
			
		int[] minFileNodenumber= new int[replicfactor];
		//int minFileNode=0;
		for(i=0;i<replicfactor;i++)
			{
				int k=0; //=numFilesinNode.get(0);
				int minIndex; 
			
				while(numFilesinNode.get(k)== null && k < numofNodes)	
				{
					k++;
				}
				
				minIndex=k;					
				int smallest=k;
				
				for(j=0;j<numFilesinNode.size();j++)
				{
					
					if(numFilesinNode.get(j)!= null && numFilesinNode.get(smallest) > numFilesinNode.get(j) )
					{
					smallest = j;
					//numFilesinNode.set(j,null);					
					}
				}
				
				minIndex=smallest;
				numFilesinNode.set(smallest,null);
				minFileNodenumber[i]=minIndex;	
		
			}		
		
		
		for(i=0;i<replicfactor;i++)
			{
				int temp = minFileNodenumber[i];
				retrnNodes[i]=nodes[temp];
			}
		
				
		return retrnNodes;
	}
	
	
	
	public void sortArray(int[] array)
	{
		int a, b;
		int temp;
		int length =9;//= array.length();

		for (a = 0; a < length ; ++a) {
    			for (b = 0; b < length; ++b) {
        			if (array[b] < array[b + 1]) {
            		temp = array[b];
            		array[b] = array[b + 1];
            		array[b + 1] = temp;
        }
    }
}
	}
	
	
	public static void main(String[] args)
	{
		
	}
}