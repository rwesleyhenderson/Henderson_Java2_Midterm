import java.io.*;
import java.nio.file.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Robert Henderson
 * 02/28/2021
 * Midterm Assignment
 */

public class Henderson_Temperature_File
{
	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);
		
		Path file = Paths.get("DailyTemperatures.txt");
		OutputStream output = null;
		String day, tempFile, high = null, low = null, fileOut = null, delimiter = ",";
		String [] fileArray = new String[3];
		String nLine = System.lineSeparator();
		String checkFile = "";
		char quit = 'r';
		int count = 0;
		int dayHigh, dayLow, totalHigh = 0, totalLow = 0;
		final String HEADER_FORMAT = "%-15s%-15s%-15s%n";
		final String TEMP_FORMAT = "%-15s%-15s%-15s%n";
		
		
		//****Write to file****
	    try
	    {
	    	try
	    	{
		    	file.getFileSystem().provider().checkAccess(file);
		        System.out.print("*****************");
		        System.out.print("   File does exist and can be read.  ");
		        System.out.print("*****************\n\n");
		        checkFile = "good";	        
	    	}
	        catch(Exception e)
	        {
	        	  System.out.println("Message: " + e); 
	        	  checkFile = "bad";	
	        }
	    	
	    	if (checkFile == "good")
	        {
	        	output = new BufferedOutputStream(Files.newOutputStream
	     			  (file, StandardOpenOption.APPEND)); 
	        	
	        }
	        else
	        {
	    	   	output = new BufferedOutputStream(Files.newOutputStream
	  			  (file, StandardOpenOption.CREATE)); 
	    	    System.out.print("The file \'DailyTemperatures.txt\' has been created\n\n");
	        }
		    
	        do
	        {
		        System.out.println("Enter the day of the week: ");
		        day = kb.next();
		        try
		        {
			        System.out.println("Enter the high for " + day);
			        high = kb.next();
			        System.out.println("Enter the low for " + day);
			        low = kb.next();

		        }
		        catch(InputMismatchException ime)
		        {
		        	System.out.println("ERROR: Enter a numeric value");
		        }
			    System.out.println("Enter another day? y/n ");
			    quit = kb.next().toLowerCase().charAt(0);
		        fileOut = day + delimiter + high + delimiter + low + nLine;
		        output.write(fileOut.getBytes());
	        }
	        while(quit != 'n');
	        {
	        	output.flush();
	        }
	        output.close();
	    }
	    catch(IOException ioe)
	    {
	    	System.out.print("File cannot be used.");
	    }
	    kb.close();  
	    
	    //****Read from file****
	    try
	    {
	    	InputStream fileIn = new BufferedInputStream(Files.newInputStream(file));
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));
	    	tempFile = reader.readLine();
	    	System.out.printf(HEADER_FORMAT, "Weekday", "High Temp", "Low Temp");
	    	while (tempFile != null)
	    	{
	    		fileArray = tempFile.split(delimiter);
	    		day = fileArray[0];
	    		dayHigh = Integer.parseInt(fileArray[1]);
	    		dayLow = Integer.parseInt(fileArray[2]);
	    		totalHigh += dayHigh;
	    		totalLow += dayLow;
	    		count++;
	    		
	    		System.out.printf(TEMP_FORMAT, day, dayHigh, dayLow);
	    		tempFile = reader.readLine();
	    	}
	    	reader.close();
	    	System.out.println("High Temperature Average: " + calculateAverage(totalHigh,count));
	    	System.out.println("Low Temperature Average: " + calculateAverage(totalLow,count));
	    }
	    catch(IOException ioe)
	    {
	    	System.out.println("ERROR: File Cannot Be Read");
	    }
	}

	public static int calculateAverage(int total, int count)
	{
		return total / count;
	}
}
