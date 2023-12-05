import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TE {
    private static int bestErr = Integer.MAX_VALUE;
    private static boolean[] bestAssignment;

	public static boolean findPartiion(int arr[], int n)
	{
		int sum = 0;
		int i, j;

		for (i = 0; i < n; i++)
			sum += arr[i];

		if (sum % 2 != 0)
			return false;

		boolean[] part = new boolean[sum / 2 + 1];
		for (i = 0; i <= sum / 2; i++) {
			part[i] = false;
		}

		for (i = 0; i < n; i++) {

			for (j = sum / 2; j >= arr[i]; j--) {

				if (part[j - arr[i]] == true || j == arr[i])
					part[j] = true;
			}
		}
		return part[sum / 2];
	}

    public static void getResultDP(int[] arr){
       
        findPartiion(arr, arr.length);
    }

    public static void getResultBnB(int[] values) {
        bestErr = Integer.MAX_VALUE;
        int totalValue = 0;
        for (int value : values) {
            totalValue += value;
        }
        boolean[] testAssignment = new boolean[values.length];
        partitionValuesFromIndex(values, 0, totalValue, totalValue, testAssignment, 0);
    }

    private static void partitionValuesFromIndex(int[] values, int startIndex, int totalValue, int unassignedValue, boolean[] testAssignment, int testValue) {
        if (bestErr == 0) return;
        if (startIndex >= values.length) {
            int testErr = Math.abs(2 * testValue - totalValue);
            if (testErr < bestErr) {
                bestErr = testErr;
                bestAssignment = testAssignment.clone();
                
            }
        } else {
            int testErr = Math.abs(2 * testValue - totalValue);
            if (testErr - unassignedValue < bestErr) {
                unassignedValue -= values[startIndex];

                testAssignment[startIndex] = true;
                partitionValuesFromIndex(values, startIndex + 1, totalValue, unassignedValue, testAssignment, testValue + values[startIndex]);

                testAssignment[startIndex] = false;
                partitionValuesFromIndex(values, startIndex + 1, totalValue, unassignedValue, testAssignment, testValue);
            }
        }
    }

    
	

    public static int[] readDatasetFromFile(String filename) {
        int size;
        if (filename.contains("small")) {
            size = 10;
        } else if (filename.contains("medium")) {
            size = 40;
        } else  {
            size = 80;
        } 
    
        int[] dataset = new int[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < size) {
                dataset[index++] = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public static void runAlgorithm(String alg, int[] arr, String arrType) {
        long startTime = System.nanoTime(); 

        
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ;

    
        if ("DP".equals(alg)) {
            System.out.println("Running DP for: " + arrType);
            getResultDP(arr);
        } else if ("BnB".equals(alg)) {
            System.out.println("Running BnB for: " + arrType);
            getResultBnB(arr);
        } else {
            System.out.println("Cleaning Memory");
        }
    
        long endTime = System.nanoTime();
    

        long afterUsedMem =Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ;

    
        long peakMemory = afterUsedMem - beforeUsedMem;
        System.out.println("Traced Memory " + peakMemory);
    
        System.out.println("Finished execution. Time: " + (endTime - startTime) /1000000.0 +" ms\n");
    }

    public static void main(String[] args)
	{
		int[] smallDataset = readDatasetFromFile("small_dataset.txt");
        int[] mediumDataset = readDatasetFromFile("medium_dataset.txt");
        int[] bigDataset = readDatasetFromFile("big_dataset.txt");

        runAlgorithm("", smallDataset, "small_dataset");
        runAlgorithm("DP", smallDataset, "small_dataset");
        runAlgorithm("BnB", smallDataset, "small_dataset");
        runAlgorithm("DP", mediumDataset, "medium_dataset");
        runAlgorithm("BnB", mediumDataset, "medium_dataset");
        runAlgorithm("DP", bigDataset, "big_dataset");
        runAlgorithm("BnB", bigDataset, "big_dataset");
	}
}
