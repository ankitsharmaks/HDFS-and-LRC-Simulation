/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author Ankit
 */


class MyPanel extends JPanel{
    
    
     private JButton Button_Open_File;
    private JButton Button_Encode;
    private JButton Button_Project_Location;
    private JTextField File_Name;
    private JTextField Project_Location_Button_Value;
    private JTextArea Encode_Report;
    private JTextArea Decode_Report;
    private JTextArea File_Regeneration_Report;
    private JLabel No_Failed_Nodes;
    private JLabel No_Of_Times;
    private JTextField No_Of_Times_Value;
    
    //private JTextField No_Failed_Nodes_Value;
    private JCheckBox Check_Filednodes_one;
    private JCheckBox Check_Filednodes_morethan_one;
    private JCheckBox Check_HDFS_Decode;
    private JCheckBox Check_RAID_Decode;
    private JCheckBox Check_LRC_Decode;
   // private JCheckBox Check_CORE_Decode;
    private JLabel Failed_Nodes;
    private JLabel Decoding_Type;
    private JTextField Failed_Nodes_Value;
    
    private JButton Button_Final_Report;
    private JLabel Encoding_Type;
    private JCheckBox Check_HDFS_ENCODE;
    private JCheckBox Check_RAID_Encode;
    private JCheckBox Check_LRC_Encode;
    private JLabel Regeneration_Type;
    private JCheckBox Check_HDFS_Regeneration;
    private JCheckBox Check_RAID_Regeneration;
    private JButton Button_Decode;
    private JButton Button_Regenerate_File;
    private JButton Button_Regeneration_Report;
    private JComboBox Select;
    
    private JScrollPane spaneEncode;
    private JScrollPane spaneDecode;
    private JScrollPane spaneReg;
    
    
    public JFileChooser File_Chooser = new JFileChooser();
    public JFileChooser Dir_Chooser = new JFileChooser();
    public String filepath,dirpath,filename;
    
    
    static	int NumofNodes;
    static	int BlockSize;
    static	int replicationFactor;
    
    
    public MyPanel() {
        
        
        Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
                String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		//System.out.println(newfilename);
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex)
    	 {
    		ex.printStackTrace();
        }
        
        
        
        String[] SelectItems = {"One", "More than one(Automatically)","More than one(Manually)"};
    //construct components
       Button_Open_File = new JButton ("Upload a File");
        Button_Encode = new JButton ("Encode");
        Encoding_Type = new JLabel ("Encoding Type:");
        Decoding_Type = new JLabel("Decoding Type");
        Check_HDFS_ENCODE = new JCheckBox ("HDFS");
        Check_RAID_Encode = new JCheckBox ("HDFS-RAID");
        Check_LRC_Encode = new JCheckBox ("LRC");
        Button_Project_Location = new JButton ("Choose a Directory..");
        File_Name = new JTextField (5);
        Project_Location_Button_Value = new JTextField (5);
        Encode_Report = new JTextArea (5, 5);
        Decode_Report = new JTextArea (5, 5);
        File_Regeneration_Report = new JTextArea (5,5);
        No_Failed_Nodes = new JLabel ("No. of Failed Nodes :");
        No_Of_Times = new JLabel("No. of times:");
        No_Of_Times_Value = new JTextField(3);

        //No_Failed_Nodes_Value = new JTextField (5);
        Check_Filednodes_one = new JCheckBox("One");
        Check_Filednodes_morethan_one = new JCheckBox("More than one");
        Check_HDFS_Decode = new JCheckBox ("HDFS");
        Check_RAID_Decode = new JCheckBox ("HDFS-RAID");
        Check_LRC_Decode = new JCheckBox ("LRC");
      //  Check_CORE_Decode = new JCheckBox ("OTHER");
        Failed_Nodes = new JLabel ("Specify No. of Failed Nodes:");
        Failed_Nodes_Value = new JTextField (5);
        Button_Final_Report = new JButton ("Generate Report");
        Regeneration_Type = new JLabel ("File Regeneration Type:");
        Check_HDFS_Regeneration = new JCheckBox ("HDFS");
        Check_RAID_Regeneration = new JCheckBox ("RAID");
        Button_Decode = new JButton ("Decode");
        Button_Regenerate_File = new JButton ("Regenerate File");
        Button_Regeneration_Report = new JButton ("Generate Report");
        Select = new JComboBox (SelectItems);

        
        
        //set components properties
        Button_Open_File.setToolTipText ("Click to select File");
        File_Chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        
        
        // Action to perform when "Open a File" button is clicked
        
        Button_Open_File.addActionListener(  new ActionListener(){

            
                    public void actionPerformed(ActionEvent e) {

                   int returnVal ;
                   returnVal = File_Chooser.showOpenDialog(MyPanel.this);

                   if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = File_Chooser.getSelectedFile();
                        filename = file.getName();
                        filepath = file.getPath();
                        File_Name.setText(filepath);

                        //This is where a real application would open the file.
                        //log.append("Opening: " + file.getName() + "." + newline);
                    } 

                   
                   
                        //log.setCaretPosition(log.getDocument().getLength());
                    if(filepath!=null && dirpath!=null && (Check_HDFS_ENCODE.isSelected() || Check_RAID_Encode.isSelected()) ) {
                        Button_Encode.setEnabled(true);
                     }
                         //Handle save button action.
            
            
            }
        
     
        }
        
        );     
        
        
        Button_Project_Location.setToolTipText ("Click to select Project Directory");
        Dir_Chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Button_Project_Location.addActionListener(new OpenAction());
        
        
        File_Name.setEditable (false);
        File_Name.setToolTipText ("Uploaded File");
        
        
        Project_Location_Button_Value.setEditable (false);
        Project_Location_Button_Value.setToolTipText ("Project Directory");
        
        
        Check_HDFS_ENCODE.addActionListener(new OpenAction());
        Check_RAID_Encode.addActionListener(new OpenAction());       
        Check_LRC_Encode.addActionListener(new OpenAction());   
        
        
        Button_Encode.setEnabled(true);
        Button_Encode.addActionListener(new OpenAction());
        
        
        
        spaneEncode = new JScrollPane(Encode_Report);
        
        Encode_Report.setEditable(false);
        Border Encode_Report_Border = BorderFactory.createLineBorder(Color.gray);
        Encode_Report.setBorder(Encode_Report_Border);
        Encode_Report.setText("   \t    ENCODE REPORT");                
        
        
        No_Failed_Nodes.setEnabled(false);

        
        
        
        //No_Failed_Nodes_Value.setEnabled(false);
        Check_Filednodes_one.setEnabled(false);
        Check_Filednodes_morethan_one.setEnabled(false);
        Failed_Nodes.setEnabled(false);
        Failed_Nodes.setVisible(false);
        Failed_Nodes_Value.setEnabled (false);
        Failed_Nodes_Value.setEditable (false);
        Failed_Nodes_Value.setVisible (false);

        
        Select.setEnabled(false);
        Select.addActionListener(new OpenAction());
                
        
        Check_HDFS_Decode.setEnabled (false);
        Check_RAID_Decode.setEnabled (false);
        Check_LRC_Decode.setEnabled (false);
        //Check_CORE_Decode.setEnabled (false);
        
        
        Button_Final_Report.setEnabled (false);
        
        
        Button_Decode.setEnabled(false);
        Button_Decode.addActionListener(new OpenAction());
        
        
        Regeneration_Type.setEnabled(false);
        Check_HDFS_Regeneration.setEnabled(false);
        Check_RAID_Regeneration.setEnabled(false);
        Button_Regenerate_File.setEnabled(true);
        Button_Regenerate_File.addActionListener(new OpenAction());
        Button_Regeneration_Report.setEnabled(false);
        
        No_Of_Times.setVisible(false);
        No_Of_Times_Value.setVisible(false);
        No_Of_Times_Value.setText("1");
        
        
        spaneDecode = new JScrollPane(Decode_Report);
        Decode_Report.setText("   \t    DECODE REPORT"); 
        
        
        spaneReg = new JScrollPane(File_Regeneration_Report);
        File_Regeneration_Report.setText("   \tFile Regeneration Report");   
        
        
        //adjust size and set layout
        setPreferredSize (new Dimension (560, 618));
        setLayout (null);
        
        
        
        
        
        /*File_Name.setText("D:\\aaaaaa\\AB.mp4");
        Project_Location_Button_Value.setText("D:\\aaaaaa");*/
        Button_Encode.setEnabled(true);

        
        
        
       //add components
        add (Button_Open_File);
        add (Encoding_Type);
        add (Decoding_Type);
        add (Check_HDFS_ENCODE);
        add (Check_RAID_Encode);
        add (Check_LRC_Encode);
        add (Button_Encode);
        add (Button_Project_Location);
        add (File_Name);
        add (Project_Location_Button_Value);
        /*add (Encode_Report);
        add (Decode_Report);
        add(File_Regeneration_Report);*/
        add (spaneEncode);
        add (spaneDecode);
        add(spaneReg);
        add (No_Failed_Nodes);

        //add (No_Failed_Nodes_Value);
        //add(Check_Filednodes_morethan_one);
        //add(Check_Filednodes_one);
        add (Check_HDFS_Decode);
        add (Check_RAID_Decode);
        add (Check_LRC_Decode);
     //   add (Check_CORE_Decode);
        add (No_Of_Times);
        add (No_Of_Times_Value);
        add (Failed_Nodes);
        add (Failed_Nodes_Value);
       // add (Button_Final_Report);
        add (Regeneration_Type);
        add (Check_HDFS_Regeneration);
        add (Check_RAID_Regeneration);
        add (Button_Decode);
        add (Button_Regenerate_File);
       // add (Button_Regeneration_Report);
        add (Select);

        
        
        
        //set component bounds (only needed by Absolute Positioning)
        Button_Open_File.setBounds (15, 10, 160, 30);
        Button_Encode.setBounds (100, 215, 110, 35);
        Button_Project_Location.setBounds (15, 55, 160, 30);
        File_Name.setBounds (220, 10, 305, 30);
        Project_Location_Button_Value.setBounds (220, 55,305, 30);
        //Encode_Report.setBounds (220, 115, 305, 135);
        spaneEncode.setBounds (220, 115, 305, 135);
        No_Failed_Nodes.setBounds (15, 275, 160, 30);
        
        //No_Failed_Nodes_Value.setBounds (195, 320, 185, 30);
        //Check_Filednodes_one.setBounds (195, 320, 185, 30);
        //Check_Filednodes_morethan_one.setBounds (300, 320, 185, 30);
        Check_HDFS_Decode.setBounds (120, 355, 100, 25);
        Check_RAID_Decode.setBounds (120, 380, 100, 25);
        Check_LRC_Decode.setBounds (120, 405, 100, 25);
        //Decode_Report.setBounds (220, 355, 305, 120);
        spaneDecode.setBounds (220, 355, 305, 120);
        //Check_CORE_Decode.setBounds (345, 395, 100, 25);
        Failed_Nodes.setBounds (15, 315, 160, 30);
        Failed_Nodes_Value.setBounds (220, 315, 185, 30);
        No_Of_Times.setBounds(415, 275, 160, 30);
        No_Of_Times_Value.setBounds(500, 275, 40, 30);
        Button_Final_Report.setBounds (240, 440, 150, 40);
        Encoding_Type.setBounds (15, 105, 160, 30);
        Decoding_Type.setBounds (15, 355,160,30);
        Check_HDFS_ENCODE.setBounds (120, 110, 65, 25);
        Check_RAID_Encode.setBounds (120, 140, 100, 25);
        Check_LRC_Encode.setBounds (120, 170, 65, 25);
        Regeneration_Type.setBounds (20, 500, 160, 30);
        Check_HDFS_Regeneration.setBounds (155, 505, 60, 25);
        Check_RAID_Regeneration.setBounds (155, 535, 60, 25);
        Button_Decode.setBounds (100, 440, 110, 35);
        Button_Regenerate_File.setBounds (60, 565, 150, 40);
        Button_Regeneration_Report.setBounds (240, 565, 150, 40);
        //File_Regeneration_Report.setBounds(220, 505, 305,80);
        spaneReg.setBounds(220, 505, 305,80);
        Select.setBounds (220, 275, 185, 30);
        
        
        //Add Separator
        
        JSeparator Separator1 = new JSeparator(SwingConstants.HORIZONTAL);  
        Separator1.setPreferredSize(new Dimension(50,3));
        Separator1.setBounds(0, 260, 575, 10);
        add(Separator1);
        
        
        
        //Second Separator
        
        JSeparator Separator2 = new JSeparator(SwingConstants.HORIZONTAL);  
        Separator2.setPreferredSize(new Dimension(50,3));
        Separator2.setBounds(0, 490, 600, 10);
        add(Separator2);
        
    }
    
    
    
    public String Get_File_Path(){
        
        
        String File_Path = new String();
        File_Path = File_Name.toString();
        return File_Path;
        
    }
          
    
    
    public String Get_Project_Path(){
        
        
        String Project_Path = new String();
        Project_Path = Project_Location_Button_Value.toString();
        return Project_Path;
        
    }
    
    
    
    
 class OpenAction implements ActionListener {
 
 public void actionPerformed(ActionEvent e) {
     
       int returnVal;
       //Handle open button action.
       //if (e.getSource() == Button_Open_File) {
       //}
     
       
       if (e.getSource() == Button_Project_Location){
                
           returnVal = Dir_Chooser.showOpenDialog(MyPanel.this);
 
           if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file2 = Dir_Chooser.getSelectedFile();
                dirpath = file2.getPath();
                Project_Location_Button_Value.setText(dirpath);
                //log.append("Open command cancelled by user." + newline);
           }
           
           if(filepath!=null && dirpath!=null && (Check_HDFS_ENCODE.isSelected() || 
                   Check_RAID_Encode.isSelected() || Check_LRC_Encode.isSelected())) 
           {
                Button_Encode.setEnabled(true);
                No_Failed_Nodes.setEnabled(true);
                //No_Failed_Nodes_Value.setEnabled(true);
                //Check_Filednodes_one.setEnabled(true);
                //Check_Filednodes_morethan_one.setEnabled(true);
                Select.setEnabled(true);
                Failed_Nodes.setEnabled(true);
                Failed_Nodes_Value.setEnabled (true); 
                Check_HDFS_Decode.setEnabled (true);
                Check_RAID_Decode.setEnabled (true);
                Check_LRC_Decode.setEnabled (true);
                
                Button_Final_Report.setEnabled (true);
                Button_Decode.setEnabled(true);
                Regeneration_Type.setEnabled(true);
                Check_HDFS_Regeneration.setEnabled(true);
                Check_RAID_Regeneration.setEnabled(true);
                Button_Regenerate_File.setEnabled(true);
                Button_Regeneration_Report.setEnabled(true);
           }
           
           
        }
       
       
       
        else if (e.getSource() == Button_Encode) {
            
            
            No_Failed_Nodes.setEnabled(true);
            //No_Failed_Nodes_Value.setEnabled(true);
            //Check_Filednodes_one.setEnabled(true);
            //Check_Filednodes_morethan_one.setEnabled(true);
            Select.setEnabled(true);
            Failed_Nodes.setEnabled(true);
            Failed_Nodes_Value.setEnabled (true); 
            Check_HDFS_Decode.setEnabled (true);
            Check_RAID_Decode.setEnabled (true);
            Check_LRC_Decode.setEnabled (true);
        
            Button_Final_Report.setEnabled (true);
            Button_Decode.setEnabled(true);
            Regeneration_Type.setEnabled(true);
            Check_HDFS_Regeneration.setEnabled(true);
            Check_RAID_Regeneration.setEnabled(true);
            Button_Regenerate_File.setEnabled(true);
            Button_Regeneration_Report.setEnabled(true);
            
            
            
           // String File_Path = new String();
           // String File_Path = File_Name.toString();
            System.out.println("File Path:" + filepath);
            //String Project_Path = new String();
            //String Project_Path = Project_Location_Button_Value.toString();
            System.out.println("Project Path:" + dirpath);
            
            
            File OriginalFile = new File(filepath);
            int Block_Size=1000*1024;
            long File_Size = (int)OriginalFile.length();
            int no_of_blocks = (int)File_Size/Block_Size;
            
                
            int no_of_nodes = no_of_blocks/16;
            if(no_of_nodes%16!=0)
                    no_of_nodes++;
            
            
            //HDFS ENCODER
            
             if(Check_HDFS_ENCODE.isSelected())
            {
                long startTimeDivide = System.currentTimeMillis();
                try {
                    divide.dividefileintoblocks(filepath,dirpath);
                } catch (IOException ex) {
                    Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                long endTimeDivide   = System.currentTimeMillis();
                long DivideandStoreTime = endTimeDivide - startTimeDivide ; 
                System.out.println("DivideandStoreTime  "+DivideandStoreTime);
                
                
                Encode_Report.append("\n-----HDFS Encoder----- ");
                
                Encode_Report.append("\nNumber of Nodes: "+NumofNodes);
                Encode_Report.append("\nBlock Size: "+BlockSize);
                Encode_Report.append("\nReplication Factor:" + replicationFactor);
                Encode_Report.append("\nTotal Encoding Time: " + DivideandStoreTime);
                
               // Encode_Report.append("\n---------------------- ");

            }    
            
            
            
            //RAID ENCODER
             
            if(Check_RAID_Encode.isSelected())
            {
                long File_RAID_Encode_StartTime = System.currentTimeMillis();
                RAIDdivide newdivide = new RAIDdivide (filepath,dirpath);
               


            
                System.out.println("\n RAID encode Enabled");

                Galois1 newRAIDEncode = new Galois1();
                newRAIDEncode.callEncoder(filepath,dirpath);

                
                 long File_RAID_Encode_EndTime = System.currentTimeMillis();
                long Total_RAID_Encode_Time = File_RAID_Encode_EndTime - File_RAID_Encode_StartTime;
                System.out.println("Start Time:"+File_RAID_Encode_StartTime+"    End Time: "+File_RAID_Encode_EndTime+"    Total Encode Time: " + Total_RAID_Encode_Time);
                
                Encode_Report.append("\n\n-----HDFS-RAID Encoder----- ");
                Encode_Report.append("\nCode used: (26,16)RS Code");
                Encode_Report.append("\nTotal Encoding Time: " + Total_RAID_Encode_Time);

                //Encode_Report.append("\n--------------------------- ");

          
            }
            
            
            //LRC ENCODER
            
            if(Check_LRC_Encode.isSelected())
            {    
                long File_LRC_Encode_StartTime = System.currentTimeMillis();
                
                System.out.println("\n LRC Encoding Enabled");

                System.out.println("\nblock:"+no_of_blocks+"\tnodes:"+no_of_nodes);

                LRCEncode newlrcencode = new LRCEncode(filepath,dirpath,no_of_blocks);
                
                long File_LRC_Encode_EndTime = System.currentTimeMillis();
                
                long Total_LRC_Encode_Time = File_LRC_Encode_EndTime - File_LRC_Encode_StartTime;
                
                System.out.println("Start Time:"+File_LRC_Encode_StartTime+"    End Time: "+File_LRC_Encode_EndTime+"    Total Encode Time: " + Total_LRC_Encode_Time);
                
                int no_of_lrc_parity = ((no_of_blocks)/8)+1;
                Encode_Report.append("\n\n-----LRC Encoder----- ");
                Encode_Report.append("\nTotal LRC Parities Generated: "+ no_of_lrc_parity);
                Encode_Report.append("\nTotal LRC Encode Time: " + Total_LRC_Encode_Time);
                
                //Encode_Report.append("\n--------------------- ");


                
            }
            
            
           
          
            
        }
        
        
        
        else if (e.getSource() == Button_Decode){
        
            System.out.println("Button has been pressed.");
            
            int no_of_times,avg_HDFS = 0 ,avg_RAID = 0, avg_LRC = 0, rtime_HDFS = 0 ,rtime_RAID = 0, rtime_LRC = 0;
            
            DefaultCategoryDataset dataset  = new DefaultCategoryDataset();
            
            no_of_times = Integer.parseInt(No_Of_Times_Value.getText());
            
            for(int i = 0 ; i<no_of_times ; i++)
            {   
                
                int Random_failed_nodes = 1;
                
                // int Random_failed_nodes = i;
                 
                
                if(Select.getModel().getSelectedItem().toString() == "More than one(Automatically)")
                {    
                        Random randomGenerator = new Random();
                
                        Random_failed_nodes = 2 + randomGenerator.nextInt(4);
                        
                }
                
                else if(Select.getModel().getSelectedItem().toString() == "More than one(Manually)")
                {
                        Random_failed_nodes = Integer.parseInt(Failed_Nodes_Value.getText());
                        
                }    
            
                Decode_Report.append("\n\n"+(i+1)+"> Number of failed Nodes:" + Random_failed_nodes);
            
            
            
            
             if(Check_HDFS_Decode.isSelected())
             {
                 
                 System.out.println("\n HDFS decode Enabled");
                
           
                
                //for(int i=1;i<6;i++)
                //{

                      Decode_Report.append("\n\n-----HDFS Decoder-----");      
                      
                      long startTimeNodeRegenerate = System.currentTimeMillis();
                    //System.out.println("no of failed nodes"+str);
                    if(Random_failed_nodes > NumofNodes)
                    {
                            Decode_Report.append("\nEnter some number (< total nodes) in Node to Fail  field");
                    }
                    else
                    {
                    System.out.println("node to fail"+Random_failed_nodes);
                    failNodes.failNodes(Random_failed_nodes);
                    String[] failednodeslist;
                    failednodeslist = getFailedNodelist.getFailedNodelist();
                    int lnth =failednodeslist.length;
                    StringBuilder builder = new StringBuilder();


                    int c=0;
                    for (String s : failednodeslist) {
                        if (builder.length() > 0) {
                            builder.append(" ");
                        }
                        if(c%5==0 && c!=0)
                        {
                            builder.append("\n");
                        }
                        
                        int temp = s.indexOf("hdfs\\node");
                        String temp1 = s.substring(temp+4);
                        builder.append(temp1);
                        c++;
                    }

                    String stringnodes = builder.toString();

                    //String stringnodes = StringUtils.join(str,' ');
                    //Decode_Report.setText("Total: " +lnth+" nodes failed\n");
                    
                    Decode_Report.append("\nFailed Nodes: "+stringnodes);
                    

                    


                        }
                    
                    
                    
                    //regeneration starts from hre
                    
                    
                    
                    

                    boolean b;
                    b = repairFailedNodes.repairFailedNodes();

                    long endTimeNodeRegenerate = System.currentTimeMillis();
                    long timediff = 0;
                    if(b==false)
                    {
                            Decode_Report.append("\n\nNode could not be regenerated");
                    }

                    else
                    {
                            timediff = endTimeNodeRegenerate - startTimeNodeRegenerate;
                            Decode_Report.append("\nTime to repair nodes :"+ timediff);
                    }
                    
                    rtime_HDFS += timediff ;
                    
                    //Decode_Report.append("\n----------------------");   
                 
             }  
            
            
            
            if(Check_RAID_Decode.isSelected())
            {
                
                        
                
                System.out.println("\n RAID decode Enabled");
               
           
                Decode_Report.append("\n\n-----HDFS-RAID Decoder-----");           

                //for(int i=1;i<6;i++)
                //{
                    
                 long start_time = System.currentTimeMillis();
                 
                 System.out.println("Calender - Time in milliseconds :" + start_time);
     
                int Random_failed_nodes_new = Random_failed_nodes;
                if(Random_failed_nodes > 5)
                {            
                        Random_failed_nodes_new = 5;   
                
                        Decode_Report.append("\nRaid decoder cannot decode more than 5 nodes. No of failed nodes = 5");
                        
                }
                System.out.println("No of failed blocks: " + Random_failed_nodes_new);
                Galois1 newRAIDDecode = new Galois1();
                newRAIDDecode.callDecoder(filepath,dirpath,Random_failed_nodes_new);
                
                
                long end_time = System.currentTimeMillis();
                 System.out.println("Calender - Time in milliseconds :" + end_time);
                 
                 int diff =(int) (end_time - start_time);
                 
        
                 Decode_Report.append("\nTime to repair nodes :"+ diff);
               // }
                
                 rtime_RAID += diff; 
                 //Decode_Report.append("\n---------------------------");           

            } 

            }
            
            avg_HDFS = rtime_HDFS/no_of_times;
            avg_RAID = rtime_RAID/no_of_times;
            
            if(Check_HDFS_Decode.isSelected() && (no_of_times>1))
            {
                Decode_Report.append("\n\nAverage HDFS Repair Time :"+ avg_HDFS);
                dataset.setValue(avg_HDFS, "", "HDFS");
            }
            
            if(Check_RAID_Decode.isSelected() && (no_of_times>1))
            {
                Decode_Report.append("\n\nAverage RAID Repair Time :"+ avg_RAID);
                dataset.setValue(avg_RAID, "", "HDFS-RAID");
            }
            
            if(Check_LRC_Decode.isSelected()&&Check_LRC_Decode.isEnabled())
            {    
                
                
                Decode_Report.append("\n\n-----LRC Decoder-----");
                
                
                File OriginalFile = new File(filepath);
                int Block_Size=1000*1024;
                long File_Size = (int)OriginalFile.length();
                int no_of_blocks = (int)File_Size/Block_Size;


                int no_of_nodes = no_of_blocks/16;
                if(no_of_nodes%16!=0)
                        no_of_nodes++;

                System.out.println("\nblock:"+no_of_blocks+"\tnodes:"+no_of_nodes);
                 Calendar LRCstart_time = Calendar.getInstance();
                 System.out.println("Calender - Time in milliseconds :" + LRCstart_time.getTimeInMillis());

                 
                 
                LRCDecode newlrcdecode = new LRCDecode(filepath,dirpath,no_of_blocks);
                
                
                Calendar LRCend_time = Calendar.getInstance();
                 System.out.println("Calender - Time in milliseconds :" + LRCend_time.getTimeInMillis());
                 
                 int diff =(int) (LRCend_time.getTimeInMillis() - LRCstart_time.getTimeInMillis());
                 Decode_Report.append("\nTime to repair nodes :"+ diff);
                // dataset.setValue(diff, "LRC", ""+1);
                 
                 //Decode_Report.append("\n---------------------");
                 
            }
            
            
             if ((no_of_times>1))
             {    
                JFreeChart chart = ChartFactory.createBarChart("", "Decoding Technique", "Avg. Time of repair (in miliseconds)", dataset, PlotOrientation.VERTICAL, true, true, false) ;
        
        
                CategoryPlot p = chart.getCategoryPlot() ;
                p.setRangeGridlinePaint(Color.BLACK);
                
                ChartFrame f = new ChartFrame("Comparison of Average Repair Time",chart);

                f.setVisible(true);

                f.setSize(450,300);
             }
            
        }
        
        
        
        else if (e.getSource() == Button_Regenerate_File){
            
            if(Check_HDFS_Regeneration.isSelected())
            {
                
                File_Regeneration_Report.append("\n\n-----HDFS Regeneration-----");
                long startTimeFileRegenerate = System.currentTimeMillis();
                //String[] fldnodes = getFailedNodelist.getFailedNodelist();
                //if(fldnodes.length==0)
                //{
                
                File f;
                f= fileRegenerate.regenerateFilefromNodes(dirpath);
                long endTimeFileRegenerate   = System.currentTimeMillis();
                	 
                //System.out.println(f);
                
                File originalfile = new File(filepath);
                if(f.length()!=originalfile.length())
                {
                    File_Regeneration_Report.append("\nRegenerated File is corrupted");
                }  
                else
                {
                    File_Regeneration_Report.append("\nFile Regenerated Successfully!");
                    long RegenerateTime = endTimeFileRegenerate - startTimeFileRegenerate ; 
                    System.out.println(f+"\nRegenerateFileTime  "+ RegenerateTime);
                    File_Regeneration_Report.append("\nRegeneration Time: " + RegenerateTime);
                }    
                
                
                    
                //System.out.println(endTimeFileRegenerate);
                //Status_File_Regeneration.setText("regeneration Time: "+RegenerateTime);
             }
            
            
            if(Check_RAID_Regeneration.isSelected())
            {        
                File_Regeneration_Report.append("\n\n-----HDFS-RAID Regeneration-----");
                long startTimeFileRegenerate = System.currentTimeMillis();
                RAIDcombiner newcombine = new RAIDcombiner (filepath,dirpath);
                long endTimeFileRegenerate = System.currentTimeMillis();
                
                
                long RegenerateTime = endTimeFileRegenerate - startTimeFileRegenerate ; 
                System.out.println("\nRegenerateFileTime  "+ RegenerateTime);
                File_Regeneration_Report.append("\nFile Regenerated Successfully!");
                File_Regeneration_Report.append("\nRegeneration Time: " + RegenerateTime);
                    
            }
        }
       
        
        
        else if (e.getSource() == Check_HDFS_ENCODE) {
            
            if(filepath!=null && dirpath!=null)
                Button_Encode.setEnabled(true);    
        
        }
       
        
        
        else if (e.getSource() == Check_RAID_Encode) {
            
            if(filepath!=null && dirpath!=null)
                Button_Encode.setEnabled(true);        
            
        }
        
        else if (e.getSource() == Check_LRC_Encode) {
            
            if(filepath!=null && dirpath!=null)
                Button_Encode.setEnabled(true);    
        
        }
       
        /*else if (e.getSource() == No_Failed_Nodes ){
             if(Integer.parseInt(No_Failed_Nodes.getText()) == 1)
             {
                  Check_LRC_Decode.setEnabled (true);
             }    
             else
             {
                  Check_LRC_Decode.setEnabled (false);
             }    
        }*/
        
       
       
        else if (e.getSource() == Select ){
            
            
             if(Select.getModel().getSelectedItem().toString() == "One")
             {
                Check_LRC_Decode.setEnabled (true);
                Failed_Nodes.setEnabled(false);
                Failed_Nodes.setVisible(false); 
                Failed_Nodes_Value.setEnabled (false);
                Failed_Nodes_Value.setEditable (false);
                Failed_Nodes_Value.setVisible (false);
                No_Of_Times.setVisible(false);
                No_Of_Times_Value.setVisible(false);
             }
             
             
             else if(Select.getModel().getSelectedItem().toString() == "More than one(Automatically)")
             {
                Check_LRC_Decode.setEnabled (false);
                Failed_Nodes.setEnabled(false);
                Failed_Nodes.setVisible(false); 
                Failed_Nodes_Value.setEnabled (false);
                Failed_Nodes_Value.setEditable (false);
                Failed_Nodes_Value.setVisible (false);
                No_Of_Times.setVisible(true);
                No_Of_Times_Value.setVisible(true);
             }   
             else if(Select.getModel().getSelectedItem().toString() == "More than one(Manually)")
             {
                Check_LRC_Decode.setEnabled (false);
                Failed_Nodes.setEnabled(true);
                Failed_Nodes.setVisible(true); 
                Failed_Nodes_Value.setEnabled (true);
                Failed_Nodes_Value.setEditable (true);
                Failed_Nodes_Value.setVisible (true);
                No_Of_Times.setVisible(false);
                No_Of_Times_Value.setVisible(false);
             }   
             
            
       
        }
        
    }
 
   
}
} 