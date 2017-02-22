package botTraining;

public class ToolBlocks 
{	
	public ToolBlocks()
	{
		double[] testVector = new double[] { 1, 2, 3  };
		double[][] testMatrix = new double[][] { { 2, 3, 4 }, {4, 5, 6}, { 7, 8, 9} };
		double[][] testMatrix2 = new double[][] { { 1, 2 }, {3, 4} };
		try
		{
			double[][] m = subtract(testMatrix, testVector);
			
			for(int i = 0; i < m.length; i++)
			{
				for(int f = 0; f < m[0].length; f++)
				{
					//System.out.print(m[i][f] + " ");
				}
				
				//System.out.println();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/* Multiplies a matrix and a vector */
	public double[] multiply(double [][] matrix, double[] vector) throws Exception
	{
		if(vector.length != matrix[0].length)
			throw new Exception("Matrix length must == vector height");
		
		double[] result = new double[matrix.length];
		
		for(int i = 0; i < matrix.length; i++)
			for(int f = 0; f < matrix[i].length; f++)
				result[i] += matrix[i][f] * vector[f];
		
		return result;
	}
	
	public double[][] multiply (double[] vector, double[][] matrix) throws Exception
	{
		if(matrix.length != 1)
			throw new Exception("Matrix height must be == 1");
		
		double[][] result = new double[vector.length][matrix[0].length];
		for(int i = 0; i < result.length; i++)
			for(int f = 0; f < matrix[0].length; f++)
				result[i][f] = vector[i] * matrix[0][f];
		
		return result;
	}
	
	public double[][] multiply(double[][] matrix1, double[][] matrix2) throws Exception
	{
		if(matrix1[0].length != matrix2.length)
			throw new Exception("Matrix 1 length must == Matrix 2 height");
		
		double[][] result = new double[matrix1.length][matrix2[0].length];
		
		for(int i = 0; i < result.length; i++)
			for(int f = 0; f < result[0].length; f++)
				for(int x = 0; x < matrix1[0].length; x++)
					result[i][f] += matrix1[i][x] * matrix2[x][f];
		
		return result;
	}
	
	public double[][] multiply(double d, double[][] matrix)
	{
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for(int i = 0; i < result.length; i++)
			for(int f = 0; f < result[i].length; f++)
				result[i][f] = matrix[i][f] * d;
		
		return result;
	}
	
	public double[][] add(double[][] matrix1, double[][] matrix2) throws Exception
	{
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
			throw new Exception("Must be same length");
		
		double[][] result = new double[matrix1.length][matrix1[0].length];
		
		for(int i = 0; i < matrix1.length; i++)
			for(int f = 0; f < matrix1[0].length; f++)
				result[i][f] = matrix1[i][f] + matrix2[i][f];
		
		return result;
	}
	
	public double[] add(double[] vector1, double[] vector2) throws Exception
	{
		if(vector1.length != vector2.length)
			throw new Exception("Must be same length");
		
		double[] result = new double[vector1.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vector1[i] + vector2[i];
		
		return result;
	}
	
	public double[][] add(double[] vector, double[][] matrix) throws Exception
	{
		if(matrix.length != 1 && vector.length != matrix.length)
			throw new Exception("vector height must == matrix height");
		
		double[][] result = new double[vector.length][matrix[0].length];
		
		if(matrix.length == 1)
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] + matrix[0][f];
		}
		else
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] + matrix[i][f];
		}
		
		return result;
	}
	
	public double[][] add(double[][] matrix, double[] vector) throws Exception
	{
		return add(vector, matrix);
	}
	
	public double[][] subtract(double[][] matrix1, double[][] matrix2) throws Exception
	{
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
			throw new Exception("Must be same length");
		
		double[][] result = new double[matrix1.length][matrix1[0].length];
		
		for(int i = 0; i < matrix1.length; i++)
			for(int f = 0; f < matrix1[0].length; f++)
				result[i][f] = matrix1[i][f] - matrix2[i][f];
		
		return result;
	}
	
	public double[] subtract(double d, double[] vector)
	{
		double[] result = new double[vector.length];
		
		for(int i = 0; i < vector.length; i++)
			result[i] = d - vector[i];
		
		return result;
	}
	
	public double[] subtract(double[] vector1, double[] vector2) throws Exception
	{
		if(vector1.length != vector2.length)
			throw new Exception("Must be same length");
		
		double[] result = new double[vector1.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vector1[i] - vector2[i];
		
		return result;
	}
	
	public double[][] subtract(double[] vector, double[][] matrix) throws Exception
	{
		if(matrix.length != 1 && vector.length != matrix.length)
			throw new Exception("vector height must == matrix height");
		
		double[][] result = new double[vector.length][matrix[0].length];
		
		if(matrix.length == 1)
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] - matrix[0][f];
		}
		else
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] - matrix[i][f];
		}
		
		return result;
	}
	
	public double[][] subtract(double[][] matrix, double[] vector) throws Exception
	{
		double[] vCopy = new double[vector.length];
		
		for(int i = 0; i < vCopy.length; i++)
			vCopy[i] = vector[i] * -1;
		
		return add(vCopy, matrix);
	}

	public double[][] dotProduct(double[][] matrix1, double[][] matrix2) throws Exception
	{
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
			throw new Exception("Must be same length");
		
		double[][] result = new double[matrix1.length][matrix1[0].length];
		
		for(int i = 0; i < matrix1.length; i++)
			for(int f = 0; f < matrix1[0].length; f++)
				result[i][f] = matrix1[i][f] * matrix2[i][f];
		
		return result;
	}
	
	public double[] dotProduct(double[] vector1, double[] vector2) throws Exception
	{
		if(vector1.length != vector2.length)
			throw new Exception("Must be same length");
		
		double[] result = new double[vector1.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vector1[i] * vector2[i];
		
		return result;
	}
	
	public double[][] dotProduct(double[] vector, double[][] matrix) throws Exception
	{
		if(matrix.length != 1 && vector.length != matrix.length)
			throw new Exception("vector height must == matrix height");
		
		double[][] result = new double[vector.length][matrix[0].length];
		
		if(matrix.length == 1)
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] * matrix[0][f];
		}
		else
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] * matrix[i][f];
		}
		
		return result;
	}
	
	public double[][] dotProduct(double[][] matrix, double[] vector) throws Exception
	{		
		return dotProduct(vector, matrix);
	}
	
	public double[][] dotDivide(double[][] matrix1, double[][] matrix2) throws Exception
	{
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
			throw new Exception("Must be same length");
		
		double[][] result = new double[matrix1.length][matrix1[0].length];
		
		for(int i = 0; i < matrix1.length; i++)
			for(int f = 0; f < matrix1[0].length; f++)
				result[i][f] = matrix1[i][f] / matrix2[i][f];
		
		return result;
	}
	
	public double[] dotDivide(double[] vector1, double[] vector2) throws Exception
	{
		if(vector1.length != vector2.length)
			throw new Exception("Must be same length");
		
		double[] result = new double[vector1.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vector1[i] / vector2[i];
		
		return result;
	}
	
	public double[][] dotDivide(double[] vector, double[][] matrix) throws Exception
	{
		if(matrix.length != 1 && vector.length != matrix.length)
			throw new Exception("vector height must == matrix height");
		
		double[][] result = new double[vector.length][matrix[0].length];
		
		if(matrix.length == 1)
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] / matrix[0][f];
		}
		else
		{
			for(int i = 0; i < result.length; i++)
				for(int f = 0; f < result[0].length; f++)
					result[i][f] = vector[i] / matrix[i][f];
		}
		
		return result;
	}
	
	public double[][] dotDivide(double[][] matrix, double[] vector) throws Exception
	{		
		return dotProduct(vector, matrix);
	}
	
	public double[] transposeSingleRow(double[][] matrix)
	{
		double[] vector = new double[matrix[0].length];
		
		for(int i = 0; i < vector.length; i++)
			vector[i] = matrix[0][i];
		
		return vector;
	}
	
	public double[][] transposeMultiRow(double[][] matrix)
	{
		double[][] result = new double[matrix[0].length][matrix.length];
		
		for(int i = 0; i < result.length; i++)
			for(int f = 0; f < result[0].length; f++)
				result[i][f] = matrix[f][i];
		
		return result;
	}
	
	public double[][] transposeVector(double[] vector)
	{
		double[][] result = new double[1][vector.length];
		
		for(int i = 0; i < vector.length; i++)
			result[0][i] = vector[i];
		
		return result;
	}
	
	public double sigmoid(double num)
	{
		return 1.0 / (1.0 + Math.exp(num * -1));
	}
	
	public double[] sigmoid(double[] vector)
	{
		double[] result = new double[vector.length];
		
		for(int i = 0; i < vector.length; i++)
			result[i] = sigmoid(vector[i]);
		
		return result;
	}
	
	public double[][] sigmoid(double[][] matrix)
	{
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for(int i = 0; i < result.length; i++)
			for(int f = 0; f < result[i].length; f++)
				result[i][f] = sigmoid(matrix[i][f]);
		
		return matrix;
	}
	
	public double [][] zeroes(int rows, int columns)
	{
		double[][] result = new double[rows][columns];
		return result;
	}
	
	public double[] zeroes(int rows)
	{
		double[] result = new double[rows];
		return result;
	}
	
	public double [][] rand(int rows, int columns)
	{
		final double INIT_EPSILON = 0.12;
		
		double[][] result = new double[rows][columns];
		
		for(int i = 0; i < rows; i++)
			for(int f = 0; f < columns; f++)
				result[i][f] = Utility.random.nextDouble() * (2 * INIT_EPSILON) - INIT_EPSILON;
		
		return result;
	}
	
	public double [] rand(int rows)
	{
		final double INIT_EPSILON = 0.12;
		
		double [] result = new double[rows];
		
		for(int i = 0; i < rows; i++)
			result[i] = Math.random() * (2 * INIT_EPSILON) - INIT_EPSILON;
		
		return result;
	}
	
	public double[] subArray(int start, double[] vector)
	{
		double[] result = new double[vector.length - start];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vector[i + start];
		
		return result;
	}
	
	public double[] insertAtBeginning(double val, double[] vector)
	{
		double[] result = new double[vector.length + 1];
		result[0] = val;
		for(int i = 1; i < result.length; i++)
			result[i] = vector[i - 1];
		
		return result;
	}
}
