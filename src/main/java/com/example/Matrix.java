package com.example;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Matrix {
    // TODO: Testcases, Documentation
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
    public String getName(){
        return name;
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
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
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
                    if(distanceMatrix[i][j] == 0 && currentMatrix.getMatrix()[i][j] > 0){
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
        for (int i = 0; i < this.getSideLength(); i++) {
            distanceMatrix[i][i] = 0;
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
        int radius = radius().getMatrix()[0][0];
        int[][] eccentricity = eccentricity().getMatrix();
        int returnLenght = 0;
        for (int i = 0; i < eccentricity[0].length; i++) {
            if (eccentricity[0][i] == radius){
                returnLenght++;
            }
        }
        int[][] returnArray = new int[1][returnLenght];
        //reusing vars, cuz why not
        returnLenght = 0;
        for (int i = 0; i < eccentricity[0].length; i++) {
            if (eccentricity[0][i] == radius){
                returnArray[0][returnLenght] = i;
                returnLenght++;
            }
        }
        return new Matrix(returnArray,"Center");
    }
    public Matrix components() {
        boolean[] visited = new boolean[sideLength];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < sideLength; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                componentDFS(matrix, visited, i, component);
                components.add(component);
            }
        }
        int[][] returnMatrix = new int[components.size()][];
        for (int i = 0; i < components.size(); i++) {
            List<Integer> component = components.get(i);
            returnMatrix[i] = new int[component.size()];

            for (int j = 0; j < component.size(); j++) {
                returnMatrix[i][j] = component.get(j);
            }
        }

        return new Matrix(returnMatrix, "Components");
    }
    private void componentDFS(int[][] passedMatrix, boolean[] visited, int vertex, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);

        for (int i = 0; i < passedMatrix[vertex].length; i++) {
            if (passedMatrix[vertex][i] == 1 && !visited[i]) {
                componentDFS(passedMatrix, visited, i, component);
            }
        }
    }
    public Matrix articulationPoints() {
        List<Integer> articulationPoints = new ArrayList<>();
        int[] discoveryTime = new int[sideLength];
        int[] lowTime = new int[sideLength];
        boolean[] visited = new boolean[sideLength];
        int[] parent = new int[sideLength];
        Arrays.fill(parent, -1);
        int time = 0;

        for (int i = 0; i < sideLength; i++) {
            if (!visited[i]) {
                articulationPointsDFS(matrix, i, visited, discoveryTime, lowTime, parent, articulationPoints, time);
            }
        }

        int[][] returnMatrix = new int[1][articulationPoints.size()];
        for (int i = 0; i < articulationPoints.size(); i++) {
            returnMatrix[0][i] = articulationPoints.get(i);
        }
        return new Matrix(returnMatrix, "Artikulation Points");
    }
    private void articulationPointsDFS(int[][] passedMatrix, int vertex, boolean[] visited, int[] discoveryTime, int[] lowTime, int[] parent, List<Integer> articulationPoints, int time) {
        visited[vertex] = true;
        int childCount = 0;
        discoveryTime[vertex] = time;
        lowTime[vertex] = time;

        for (int i = 0; i < passedMatrix.length; i++) {
            if (passedMatrix[vertex][i] == 1) {
                if (!visited[i]) {
                    childCount++;
                    parent[i] = vertex;
                    articulationPointsDFS(passedMatrix, i, visited, discoveryTime, lowTime, parent, articulationPoints, time + 1);

                    lowTime[vertex] = Math.min(lowTime[vertex], lowTime[i]);

                    if (parent[vertex] == -1 && childCount > 1) {
                        articulationPoints.add(vertex);
                    }

                    if (parent[vertex] != -1 && lowTime[i] >= discoveryTime[vertex]) {
                        articulationPoints.add(vertex);
                    }
                } else if (i != parent[vertex]) {
                    lowTime[vertex] = Math.min(lowTime[vertex], discoveryTime[i]);
                }
            }
        }
    }
    public Matrix bridges(){
        Matrix adjacencyMatrix = this;
        int counter = 0;
        int amountComponents = components().sideLength;
        ArrayList<int[]> returnBridges = new ArrayList<>();
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < counter; j++) {
                if (adjacencyMatrix.getMatrix()[i][j] == 1){
                    int[][] workingMatrix = adjacencyMatrix.getMatrix();
                    workingMatrix[i][j] = 0;
                    workingMatrix[j][i] = 0;
                    adjacencyMatrix.setMatrix(workingMatrix);
                    if(adjacencyMatrix.components().getSideLength() > amountComponents){
                        //i guess this is at least readable, might need to rewrite this...
                        int[] values = new int[2];
                        values[0] = i;
                        values[1] = j;
                        returnBridges.add(values);
                    }
                    workingMatrix[i][j] = 1;
                    workingMatrix[j][i] = 1;
                    //this is terrible, OOP really doesn't help here
                    adjacencyMatrix.setMatrix(workingMatrix);
                }
            }
            counter++;
        }
        if (returnBridges.size() == 0){
            // prevents an error in toString
            return new Matrix(new int[1][0],"Bridges");
        }
        int[][] returnArray = new int[returnBridges.size()][2];

        for (int i = 0; i < returnBridges.size(); i++) {
            returnArray[i] = returnBridges.get(i);
        }
        return new Matrix(returnArray, "Bridges");
    }

    public Matrix eulerCycle() {
        if (!hasEulerCycle()){
            return new Matrix(new int[1][0], "EulerCycle");
        }
        // Note to self, = simply assigns the same memory, which means it messes with the data, creating a copy below
        int[][] adjMatrix = new int[getMatrix().length][];
        for (int i = 0; i < getMatrix().length; i++) {
            adjMatrix[i] = Arrays.copyOf(getMatrix()[i], getMatrix()[i].length);
        }

        List<Integer> cycle = new ArrayList<>();

        // won't get around recursion on this one
        findCycle(0, adjMatrix, cycle);

        int[][] returnArray = new int[1][cycle.size()];
        int count = 0;
        for (int value: cycle) {
            returnArray[0][count] = value;
            count++;
        }

        return new Matrix(returnArray,"EulerCycle");
    }
    private void findCycle(int vertex, int[][] adjMatrix, List<Integer> cycle) {
        for (int nextVertexIterator = 0; nextVertexIterator < adjMatrix.length; nextVertexIterator++) {
            if (adjMatrix[vertex][nextVertexIterator] > 0) {
                // remove checked vertexes by setting them to 0
                adjMatrix[vertex][nextVertexIterator]--;
                adjMatrix[nextVertexIterator][vertex]--;
                // recursion
                findCycle(nextVertexIterator, adjMatrix, cycle);
            }
        }
        cycle.add(vertex);
    }
    public Matrix eulerLine(){
        // reused code, this is just the cycle, only starting at a special point
        int[][] adjMatrix = new int[getMatrix().length][];
        for (int i = 0; i < getMatrix().length; i++) {
            adjMatrix[i] = Arrays.copyOf(getMatrix()[i], getMatrix()[i].length);
        }
        List<Integer> cycle = new ArrayList<>();
        try{
            // hasEulerPath returns a vertex of an unevenGrade
            findCycle(hasEulerPath(), adjMatrix, cycle);
        } catch (MatrixException e) {
            // or throws an error
            return new Matrix(new int[1][0], "EulerLine");
        }
        int[][] returnArray = new int[1][cycle.size()];
        int count = 0;
        for (int value: cycle) {
            returnArray[0][count] = value;
            count++;
        }
        return new Matrix(returnArray,"EulerLine");
    }
    private int hasEulerPath() throws MatrixException {
        // same as Cycle, can contain two uneven Grades
        int unevenGrades = 0;
        int unevenVertex = 0;
        if (components().sideLength == 1){
            for (int i = 0; i < matrix.length; i++)  {
                int sum = 0;
                for (int j = 0; j < matrix[i].length; j++) {
                    sum += matrix[i][j];
                }
                if (sum % 2 != 0){
                    unevenVertex = i;
                    unevenGrades += 1;
                }
            }
            if(unevenGrades == 2){
                return unevenVertex;
            }
        }
        throw new MatrixException("noEulerPath");
    }
    private Boolean hasEulerCycle(){
        //is connected
        if (components().sideLength == 1){
            for (int[] row : matrix) {
                int sum = 0;
                for (int value: row) {
                    sum+=value;
                }
                //all Grade Degrees are even
                if (sum % 2 != 0){
                    return false;
                }
            }
            return true;
        }
        return false;
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
    public String toString(){
        StringBuffer sb = new StringBuffer(64);
        sb.append(name + ":\n");
        if(this.getMatrix()[0].length == 0){
            sb.append("EMPTY\n");
            return  sb.toString();
        }
        for (int[] ints : this.getMatrix()) {
            for (int value : ints) {
                sb.append(value + "\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public void print(){
        System.out.println(toString());
    }
}
