package botTraining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LearningSystem 
{
	private double[][] inputMatrix;
	public int INPUT_SIZE = 4;
	public int MAX_ROWS = 2000;
	public int HIDDEN_LAYER_SIZE = 50;
	public int MAX_BP_ITERATIONS = 600;
	public int NUM_OUTPUTS = 6;
	public double lambda = 0.5;
	private int numRows;
	private ToolBlocks toolbox;
	public double[][] theta1, theta2;
	
	public LearningSystem()
	{
		inputMatrix = new double[MAX_ROWS][INPUT_SIZE + 1];
		numRows = 0;
		toolbox = new ToolBlocks();
		loadTableData("trainingData.txt");
		
		try
		{
			learn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int classify(double[] input) throws Exception
	{
		input = toolbox.insertAtBeginning(1, input);
				
		double[] hiddenLayer = toolbox.sigmoid(toolbox.multiply(theta1, input));
		hiddenLayer = toolbox.insertAtBeginning(1, hiddenLayer);
				
		double[] outputLayer = toolbox.sigmoid(toolbox.multiply(theta2, hiddenLayer));
				
		int maxInd = 0;
		for(int i = 0; i < outputLayer.length; i++)
		{
			System.out.println(outputLayer[i]);
			if(outputLayer[i] > outputLayer[maxInd])
			{
				maxInd = i;
			}
		}
		
		System.out.println();
		
		return maxInd;
	}
	
	public void learn() throws Exception
	{
		theta1 = toolbox.rand(HIDDEN_LAYER_SIZE, INPUT_SIZE + 1);
		theta2 = toolbox.rand(NUM_OUTPUTS, HIDDEN_LAYER_SIZE + 1);
		
		for(int iterations = 0; iterations < MAX_BP_ITERATIONS; iterations++)
		{
			double[][] delta1 = toolbox.zeroes(HIDDEN_LAYER_SIZE, INPUT_SIZE + 1);
			double[][] delta2 = toolbox.zeroes(NUM_OUTPUTS, HIDDEN_LAYER_SIZE + 1);
			
			for(int m = 0; m < numRows; m++)
			{
				/* Retrieve the mth training input -> input vector */
				double[] x = new double[INPUT_SIZE];
				for(int copyIndex = 0; copyIndex < INPUT_SIZE; copyIndex++)
					x[copyIndex] = inputMatrix[m][copyIndex];
				/* Retrieve the mth training output -> output vector */
				double[] y = toolbox.zeroes(NUM_OUTPUTS);
				y[(int) inputMatrix[m][INPUT_SIZE]] = 1;

				x = toolbox.insertAtBeginning(1, x);
				
				double[] hiddenLayer = toolbox.sigmoid(toolbox.multiply(theta1, x));
				hiddenLayer = toolbox.insertAtBeginning(1, hiddenLayer);
				
				double[] outputLayer = toolbox.sigmoid(toolbox.multiply(theta2, hiddenLayer));
				
				double[] error3 = toolbox.subtract(outputLayer, y);
				double[] error2 = toolbox.dotProduct(toolbox.multiply(toolbox.transposeMultiRow(theta2), error3), toolbox.dotProduct(hiddenLayer, toolbox.subtract(1, hiddenLayer)));
				error2 = toolbox.subArray(1, error2);
				
				delta1 = toolbox.add(delta1, toolbox.multiply(error2, toolbox.transposeVector(x)));
				delta2 = toolbox.add(delta2, toolbox.multiply(error3, toolbox.transposeVector(hiddenLayer)));
			}
			
			double[][] partial1 = toolbox.add(toolbox.multiply(1.0 / (double)numRows, delta1), toolbox.multiply(lambda, theta1));
			double[][] partial2 = toolbox.add(toolbox.multiply(1.0 / (double)numRows, delta2), toolbox.multiply(lambda, theta2));
			
			for(int i = 0; i < partial1.length; i++)
				partial1[i][0] = (1.0 / (double)numRows) * delta1[i][0];
			
			for(int i = 0; i < partial2.length; i++)
				partial2[i][0] = (1.0 / (double)numRows) * delta2[i][0];
			
			theta1 = toolbox.subtract(theta1, delta1);
			theta2 = toolbox.subtract(theta2, delta2);
		}
	}
	
	private void loadTableData(String file)
	{
		ArrayList<ArrayList<Double>> tableData = new ArrayList<ArrayList<Double>>();
		
		try
		{
			FileReader fr = new FileReader(new File(file));
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
					
			while(line != null)
			{
				tableData.add(getTokens(line));
				line = br.readLine();
				numRows++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		for(int i = 0; i < tableData.size(); i++)
			for(int f = 0; f < tableData.get(i).size(); f++)
				inputMatrix[i][f] = tableData.get(i).get(f);
	}
	
	private ArrayList<Double> getTokens(String l)
	{
		ArrayList<Double> tokens = new ArrayList<Double>();
		
		String currToken = "";
		for(int i = 0; i < l.length(); i++)
		{
			if(i == l.length() - 1 || l.charAt(i) == ' ')
			{
				if(currToken != "")
					tokens.add(Double.parseDouble(currToken));
				currToken = "";
			}
			else
			{
				currToken += l.charAt(i);
			}
		}
		
		return tokens;
	}
	
	public void saveTableData(String file)
	{		
		try
		{
			FileWriter fw = new FileWriter(new File(file));
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter outFile = new PrintWriter(bw);
			
			for(int i = 0; i < numRows; i++)
			{
				for(int f = 0; f <= INPUT_SIZE; f++)
				{
					outFile.print(inputMatrix[i][f] + " ");
				}
				
				outFile.println();
			}
			
			outFile.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void appendInput(double[][] newInput, int rows)
	{
		System.out.println("Appending " + rows + " rows. numRows should become " + (numRows + rows));
		for(int i = 0; i < rows; i++)
		{
			for(int f = 0; f < newInput[i].length; f++)
			{
				inputMatrix[numRows][f] = newInput[i][f];
			}
			
			numRows++;
		}
		System.out.println("Num Rows: " + numRows);
		
		saveTableData("trainingData.txt");
	}
}
