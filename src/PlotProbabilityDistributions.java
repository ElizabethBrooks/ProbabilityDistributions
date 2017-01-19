/*
	Name: Elizabeth Brooks
	Modified: 18 January 2017
	File: PlotProbabilityDistributions class
*/
//Imports
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Imports for graphing
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
//Class to set up and run the models then graph the results
public class PlotProbabilityDistributions extends Application{
	//Class fields for graphing
   	private static double xValues[];
   	private static double yValues[];
   	private static String graphTitle;
   	private static String xAxisTitle;
   	private static String yAxisTitle;
   	private static int graphSize = 0;
   	//Class fields for writing distribution files
   	private static String distributionChoice = "gaussian"; //Distribution choice of sigmoid (log odds) or gaussian
   	private static String randomChoice = "random"; //Random number quality choice of Random or SecureRandom
   	private static int numIterations = 0; //Number of iterations of random numbers to be generated
   	private static String resultsFilePath = "results.txt"; //Distribution results file path
	//The main method used to run the models
	public static void main(String[]args){
		//Verify the correct number of arguments have been input     
      		if(args.length == 3){
         		//Report if there is an error in recieving argument inputs
		 	try {
				//Parse the args input as "varName=value" to ensure the correct value is entered for each variable
			    	//Variables for reading in user input
			    	String argStr;
			    	String argVal;
			    	for(int i=0;i<args.length;i++){
				    	//Create a substrig of each input argument delimited by the equal sign
				       	String[] argSS = args[i].split("="); //Array to store input substrings
				       	argStr = argSS[0]; //Store the first arg in a string variable
				       	argVal = argSS[1]; //Store the second arg in a temp string variable
				       	//Retrieve each argument from its specified substring, order of input does not matter
				       	//Convert the input arguments from string to the appropriate data type (int or double)
				       	if((argStr.toLowerCase()).equals("iterations")){
						numIterations = Integer.parseInt(argVal); //Record the number of specified iterations
					  	graphSize = numIterations;
				       	}else if((argStr.toLowerCase()).equals("distributionchoice")){
					  	distributionChoice = argVal.toLowerCase(); //Record the distribution choice (gaussian or sigmoid)
					  	if(distributionChoice.equals("gaussian")){
					     		distributionChoice = "gaussian";
					     		resultsFilePath = "gaussianValues.txt";
					  	}else if(distributionChoice.equals("sigmoid")){ 
					     		distributionChoice = "sigmoid";
					     		resultsFilePath = "sigmoidValues.txt";
					  	}else{
					     		System.out.println("Incorrect distribution choice input, please enter 'gaussian' or 'sigmoid'.");
					  	}//End else
				       	}else if((argStr.toLowerCase()).equals("randomchoice")){
					  	randomChoice = argVal.toLowerCase(); //Record the distribution choice (gaussian or sigmoid)
					  	if(distributionChoice.equals("random")){
					     		randomChoice = "random";
					  	}else if(distributionChoice.equals("secure")){ 
					     		randomChoice = "secure";
					  	}else{
					     		System.out.println("Incorrect random choice input, please enter 'random', for low quality, or 'secure', for high quality random numbers.");
					  	}//End else
				       	}else{
					  	System.err.println("Incorrect argument string entered for arg[" + i + "]: " + argStr + ", with value of " + argVal + ". Program exited.");
					  	System.exit(0); //Do not run the models if incorrect input is recieved
				       	}//End if else if
				}//End for
			}catch(NumberFormatException e){
			    	System.err.println("Argument must be entered in the correct data type.");
			    	System.exit(1);
			}//End try, catch
	      	}//End if
      		//Generate specified random values and write to TXT file for plotting
      		writeDistributionFile();
      		readDistributionFile();
      		launch(args);//Launch the graphing application depending on user input
      		System.out.println("Program completed.");//Print end of program message
	}//End main
	//Method to write randomly distributed gaussian values to a TXT file
   	public static void writeDistributionFile(){
      	//Initialize variables for writing to file
      	String aOne;
		String bOne;
		//Catch exceptions and write to file in TXT format
		try {
         		//Determine which test number is being run for file naming
         		File distributionFile = new File(resultsFilePath);         
         		//Create distributionFile object file writer
		        FileWriter fw = new FileWriter(distributionFile.getAbsoluteFile()); 
			    distributionFile.createNewFile();
         		//Write to file the header
         		if(randomChoice.equals("random")){
            			ProbabilityDistribution distributionObject = new ProbabilityDistribution("random");
            			if(distributionChoice.equals("gaussian")){
               				fw.write("Gaussian Uniform\n");			
   			   		for(int i=0, k=1;i<numIterations;i++, k++){
			 			//Write to file "Gaussian Uniform" random values
			 			aOne = Double.toString(distributionObject.nextGaussian());//Transformed random values
			 			fw.append(aOne);
			 			fw.append(" ");
			 			bOne = Double.toString(distributionObject.getRandomUniformNum());//Uniform random values
			 			fw.append(bOne);
			 			fw.append("\n");
		   	         	}//End for
            			}else if(distributionChoice.equals("sigmoid")){ 
               				fw.write("Sigmoid Uniform\n");			
      					for(int i=0, k=1;i<numIterations;i++, k++){
			 			//Write to file "Sigmoid Uniform" random values
			 			aOne = Double.toString(distributionObject.nextQuantileSigmoid());//Transformed random values
			 			fw.append(aOne);
			 			fw.append(" ");
			 			bOne = Double.toString(distributionObject.getRandomUniformNum());//Uniform random values
			 			fw.append(bOne);
			 			fw.append("\n");
      	      				}//End for
            			}else{
               				System.out.println("Incorrect distribution choice input, please enter 'gaussian' or 'sigmoid'.");
            			}//End else
         		}else if(randomChoice.equals("secure")){
            			ProbabilityDistribution distributionObject = new ProbabilityDistribution("secure");
            			if(distributionChoice.equals("gaussian")){
               				fw.write("Gaussian Uniform\n");			
   			   		for(int i=0, k=1;i<numIterations;i++, k++){
			 			//Write to file "Gaussian Uniform" random values
			 			aOne = Double.toString(distributionObject.nextGaussian());//Transformed random values
			 			fw.append(aOne);
			 			fw.append(" ");
			 			bOne = Double.toString(k);//y-values?
			 			fw.append(bOne);
			 			fw.append("\n");
   	         			}//End for
            			}else if(distribution.equals("sigmoid")){ 
               				fw.write("Sigmoid Uniform\n");			
      					for(int i=0, k=1;i<numIterations;i++, k++){
			 			//Write to file "Sigmoid Uniform" random values
			 			aOne = Double.toString(distributionObject.nextQuantileSigmoid());//Transformed random values
			 			fw.append(aOne);
			 			fw.append(" ");
			 			bOne = Double.toString(distributionObject.getRandomUniformNum());//Uniform random values
			 			fw.append(bOne);
			 			fw.append("\n");
      	      				}//End for
            			}else{
               				System.out.println("Incorrect distribution choice input, please enter 'gaussian' or 'sigmoid'.");
            			}//End else
         		}else{
            			System.out.println("Incorrect random choice input, please enter 'random', for low quality, or 'secure', for high quality random numbers.");
         		}//End else			
				fw.close(); //Close the file
      		}catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}//End catch
	}//End writeDistributionFile
   	//Method for read from specified TXT file
   	public static void readDistributionFile(){
      		//Initialize variables for graphing and reading/writing to files
      		xValues = new double[graphSize];
      		yValues = new double[graphSize];
      		BufferedReader br = null;
      		graphTitle = distributionChoice + " distribution";
      		xAxisTitle = "uniform random values";
      		yAxisTitle = "transformed random values";
      		int j = 0; //Initialize counter
      		try{ //Handle errors
		   	//Read the distribution results from the selected csv file
         		br = new BufferedReader(new FileReader(resultsFilePath));
		   	String currentLine = br.readLine();
         		String[] txtLine = currentLine.split(" ");
         		//Advance the reader to the next line
         		currentLine = br.readLine();
         		//Loop through the selected TXT file
   			while (currentLine != null) {
            			//Print the current TXT line to the screen
            			txtLine = currentLine.split(" ");
   		   		System.out.println(currentLine);
            			//Save the x and y values for graphing
            			xValues[j] = Double.parseDouble(txtLine[0]);
            			yValues[j] = Double.parseDouble(txtLine[1]);
   				currentLine = br.readLine();
            			//Advance the reader to the next line
   				currentLine = br.readLine();
            			j++;
   			}//End while
		   	br.close(); //Close the file
		}catch (IOException e) {
		   	System.err.format("IOException: %s%n", e);
		}finally {
			try {
				if(br != null){
               				br.close();
            			}//End if
			}catch (IOException f) {
			   	System.err.format("IOException: %s%n", f);
			}//End catch
		}//End finally
   	}//End readResultsFile
   	//Overriding method for graphing a scatter plot of the model and simulated results
   	@Override public void start(Stage stage) {
	      stage.setTitle(graphTitle);
	      final NumberAxis xAxis = new NumberAxis(0, graphSize, 1);
	      final NumberAxis yAxis = new NumberAxis(0, graphSize, 1);        
	      final ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
	      xAxis.setLabel(xAxisTitle);                
	      yAxis.setLabel(yAxisTitle);
	      sc.setTitle(graphTitle);
	      //Graph selected model
	      XYChart.Series series1 = new XYChart.Series();
	      for(int i=0; i<xValues.length; i++){
		series1.getData().add(new XYChart.Data(xValues[i], yValues[i]));
	      }
	      sc.getData().addAll(series1);
	      Scene scene  = new Scene(sc, 500, 400);
	      stage.setScene(scene);
	      stage.show();
   	}//End start
}//End PlotProbabilityDistributions
