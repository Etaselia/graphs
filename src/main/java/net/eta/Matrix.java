package net.eta;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.commons.lang3.ArrayUtils.toArray;

public class Matrix {
    private String name;
    private int sideLength;
    private int sideWidth;
    private int[][] matrix;
    public Matrix(int sideLength, int sideWidth,boolean random){
        matrix = new int[sideLength][sideWidth];
        this.sideLength = sideLength;
        this.sideWidth = sideWidth;
        if(random){
            matrix = fillRandArray(matrix);
        }
        this.name = "Custom";
    }
    public Matrix(int[][] matrix, String name){
        this.name = name;
        this.matrix = matrix;
        this.sideLength = matrix.length;
        this.sideWidth = matrix[0].length;
    }
    public int getSideLength() {
        return sideLength;
    }
    public int getSideWidth() {
        return sideWidth;
    }
    public int[][] getMatrix() {
        return matrix;
    }
    public void setName(String name){
        this.name = name;
    }
    private int[][] fillRandArray(int[][] matrixA){
        Random rand = new Random();
        for (int i = 0; i < sideLength;i++){
            for(int j = 0; j < sideWidth;j++){
                matrixA[i][j] = rand.nextInt(2);
                if(i == j){
                    matrixA[i][j] = 1;
                }
            }
        }
        return matrixA;
    }
    public static Matrix multiply(Matrix matrixA, Matrix matrixB) throws MatrixException {
        if(matrixA.getSideLength() != matrixB.getSideWidth()){
            System.out.println("Cannot Multiply Matrices");
            throw new MatrixException("Cannot Multiply matrix's due to length != width");
        }
        int[][] resultMatrix = new int[matrixA.getSideWidth()][matrixB.getSideLength()];
        for (int i = 0; i < matrixA.getSideWidth(); i++){
            for (int j = 0; j < matrixB.getSideLength();j++){
                for (int k = 0; k < matrixA.getSideLength(); k++) {
                    resultMatrix[i][j] += matrixA.getMatrix()[i][k] * matrixB.getMatrix()[k][j];
                }
            }
        }
        return new Matrix(resultMatrix, "Multiplied");
    }
    public Matrix power(int times) throws MatrixException {
        Matrix initialMatrix = this;
        Matrix workingMatrix = this;
        for (int i = 1; i < times; i++) {
            workingMatrix = multiply(initialMatrix,workingMatrix);
        }
        workingMatrix.setName("Power: " + times);
        return workingMatrix;
    }
    public Matrix distance() throws MatrixException {
        Matrix currentMatrix = this;
        int[][] distanceMatrix = new int[this.getSideLength()][this.getSideWidth()];
        int matrixLength = this.getSideLength();
        int powercount = 1;

        while(true){
            for (int i = 0; i < this.getSideLength(); i++) {
                for (int j = 0; j < this.getSideWidth(); j++) {
                    if(i != j && distanceMatrix[i][j] == 0 && currentMatrix.getMatrix()[i][j] > 0){
                        distanceMatrix[i][j] = powercount;
                    }
                }
            }
            if(powercount == matrixLength){
                break;
            }
            powercount++;
            currentMatrix = power(powercount);
        }
        return new Matrix(distanceMatrix, "Distance");
    }
    public Matrix eccentricity() throws MatrixException {
        Matrix distance = this.distance();
        int[][] eccentricityArray = new int[1][this.sideWidth];
        for (int i = 0; i < distance.getMatrix().length; i++) {
            eccentricityArray[0][i] = Collections.max(Arrays.asList(ArrayUtils.toObject(distance.getMatrix()[i])));
        }
        return new Matrix(eccentricityArray, "Eccentricity");
    }
    public Matrix radius() throws MatrixException {
        int[][] radius = new int[1][1];
        radius[0][0] = Collections.min(Arrays.asList(ArrayUtils.toObject(this.eccentricity().getMatrix()[0])));
        return new Matrix(radius, "Radius");
    }
    public Matrix diameter() throws MatrixException {
        int[][] diameter = new int[1][1];
        diameter[0][0] = Collections.max(Arrays.asList(ArrayUtils.toObject(this.eccentricity().getMatrix()[0])));
        return new Matrix(diameter, "Diameter");
    }
    public Matrix center() throws MatrixException {
        ArrayList<Integer> arrayListDiameter = new ArrayList<Integer>(sideLength);
        int[][] distanceArray = distance().getMatrix();
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideWidth; j++) {
                if (distanceArray[i][j] == diameter().getMatrix()[0][0]){
                    arrayListDiameter.add(i);
                }
            }
        }
        int[][] outputArray = new int[1][arrayListDiameter.size()];
        outputArray[0] = arrayListDiameter.stream().mapToInt(i -> i).toArray();
        return new Matrix(outputArray, "Center");
    }
    public Matrix components(){
        boolean[] visited = new boolean[sideLength];
        List<List<Integer>> components = new ArrayList<>();
        for (int i = 0; i < sideLength; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(matrix, visited, i, component);
                components.add(component);
            }
        }
        int[][] returnMatrix = new int[components.size()][0];
        for (int i = 0; i < components.size(); i++) {
            returnMatrix[i] = new int[components.get(i).size()];
            for (int j = 0; j < components.get(i).size(); j++) {
                returnMatrix[i][j] = components.get(i).get(j);
            }
        }
        return new Matrix(returnMatrix, "Components");
    }

    public Matrix findArticulationPoints() {
        boolean[] visited = new boolean[sideLength];
        List<Integer> articulationPoints = new ArrayList<>();
        for (int i = 0; i < sideLength; i++) {
            if (!visited[i]) {
                // TODO change DFS to check for Articulations, right now it checks for components
                dfs(matrix, visited, i, articulationPoints);
            }
        }
        int[][] returnMatrix = new int[1][articulationPoints.size()];
        for (int i = 0; i < returnMatrix[0].length; i++) {
            returnMatrix[0][i] = articulationPoints.get(i);
        }
        return new Matrix(returnMatrix, "Articulations");
    }
    private static void dfs(int[][] passedMatrix, boolean[] visited, int vertex, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int i = 0; i < passedMatrix.length; i++) {
            if (passedMatrix[vertex][i] == 1 && !visited[i]) {
                dfs(passedMatrix, visited, i, component);
            }
        }
    }
    public static Matrix loadCsv(String file) throws MatrixException {
        if (file == null) {
            throw new MatrixException("file cannot be null");
        }
        Path pathToFile = Paths.get(file);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)){
            String line = br.readLine();
            int side = line.split(";").length;
            int counter = 0;
            int[][] matrix = new int[side][side];

            while (line != null && line != "") {
                String[] widthString;
                widthString = line.split(";");
                int[] width = new int[side];
                for (int i = 0; i < widthString.length; i++) {
                    matrix[counter][i] = Integer.valueOf(widthString[i]);
                }
                counter++;
                line = br.readLine();
            }
            return new Matrix(matrix, file);
        }catch (Exception e){
            throw new MatrixException(e.getMessage());
        }
    }
    public void saveCsv(String file) throws MatrixException {
        if (file == null) {
            throw new MatrixException("file cannot be null");
        }
        Path pathToFile = Paths.get(file);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile.toString()))){
            for (int[] matrixWidth : this.getMatrix()) {
                for (int value : matrixWidth) {
                    writer.write(String.valueOf(value));
                    writer.write(";");
                }
                writer.newLine();
            }
        }catch (Exception e){
            throw new MatrixException(e.getMessage());
        }
    }
    public void print(){
        System.out.println("-------------------\n" + name + ":");
        for (int[] ints : this.getMatrix()) {
            for (int value : ints) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }
}
