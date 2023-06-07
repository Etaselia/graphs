package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testConstructorWithRandom() {
        Matrix matrix = new Matrix(3, 3, true);
        assertNotNull(matrix);
        assertEquals("Custom", matrix.getName());
        assertEquals(3, matrix.getSideLength());
        assertEquals(3, matrix.getSideWidth());
        assertNotNull(matrix.getMatrix());
    }

    @Test
    void testConstructorWithMatrix() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}};
        Matrix matrix = new Matrix(array, "Matrix1");
        assertNotNull(matrix);
        assertEquals("Matrix1", matrix.getName());
        assertEquals(2, matrix.getSideLength());
        assertEquals(3, matrix.getSideWidth());
        assertArrayEquals(array, matrix.getMatrix());
    }

    @Test
    void testMultiply() throws MatrixException {
        //not same dimensions
        int[][] array1 = {{1, 2, 3}, {4, 5, 6}};
        int[][] array2 = {{7, 8}, {9, 10}, {11, 12}};
        int[][] expected = {{58, 64}, {139, 154}};
        Matrix matrix1 = new Matrix(array1, "Matrix1");
        Matrix matrix2 = new Matrix(array2, "Matrix2");
        Matrix result = Matrix.multiply(matrix1, matrix2);
        assertNotNull(result);
        assertEquals("Multiplied", result.getName());
        assertEquals(2, result.getSideLength());
        assertEquals(2, result.getSideWidth());
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    void testMultiplySame() throws MatrixException {
        int[][] array1 = {{1, 2}, {4, 5}};
        int[][] expected = {{9, 12}, {24, 33}};
        Matrix matrix1 = new Matrix(array1, "Matrix1");
        Matrix matrix2 = new Matrix(array1, "Matrix2");
        Matrix result = Matrix.multiply(matrix1, matrix2);
        assertNotNull(result);
        assertEquals("Multiplied", result.getName());
        assertEquals(2, result.getSideLength());
        assertEquals(2, result.getSideWidth());
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    void testMultiplyWithIncompatibleMatrices() throws MatrixException {
        int[][] array1 = {{1, 2, 3}, {4, 5, 6}};
        int[][] array2 = {{7, 8, 9}, {10, 11, 12}};
        Matrix matrix1 = new Matrix(array1, "Matrix1");
        Matrix matrix2 = new Matrix(array2, "Matrix2");
        assertThrows(MatrixException.class, () -> Matrix.multiply(matrix1, matrix2));
    }

    @Test
    void testPower() throws MatrixException {
        int[][] array = {{1, 2}, {3, 4}};
        int[][] expected = {{37, 54}, {81, 118}};
        Matrix matrix = new Matrix(array, "Matrix1");
        Matrix result = matrix.power(3);
        assertNotNull(result);
        assertEquals("Power: 3", result.getName());
        assertEquals(2, result.getSideLength());
        assertEquals(2, result.getSideWidth());
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    void testDistance() throws MatrixException {
        int[][] array = {{0, 1, 1}, {1, 0, 0}, {1, 0, 0}};
        int[][] expected = {{0, 1, 1}, {1, 0, 2}, {1, 2, 0}};
        Matrix matrix = new Matrix(array, "Matrix1");
        Matrix result = matrix.distance();
        assertNotNull(result);
        assertEquals("Distance", result.getName());
        assertEquals(3, result.getSideLength());
        assertEquals(3, result.getSideWidth());
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    public void testEccentricity() throws MatrixException {
        // Create a test matrix
        int[][] matrixData = Matrix.loadCsv("./24n_01.csv").getMatrix();
        Matrix matrix = new Matrix(matrixData, "TestMatrix");

        // Calculate the eccentricity
        Matrix eccentricity = matrix.eccentricity();

        // Verify the expected eccentricity values
        // Note that this is dumb
        String expected_outcome = "Eccentricity:\n9\t8\t7\t7\t6\t7\t7\t5\t6\t5\t5\t8\t9\t6\t7\t7\t6\t8\t8\t7\t7\t8\t9\t9\t\n";
        assertEquals(expected_outcome, eccentricity.toString());

        // Verify the name of the resulting matrix
        assertEquals("Eccentricity", eccentricity.getName());
    }

    public void testRadius() throws MatrixException {
        // Create a test matrix
        int[][] matrixData = {
                {0, 1, 3},
                {1, 0, 2},
                {3, 2, 0}
        };
        Matrix matrix = new Matrix(matrixData, "TestMatrix");

        // Calculate the radius
        Matrix radius = matrix.radius();

        // Verify the expected radius value
        int[][] expectedRadiusData = {
                {2}
        };
        assertArrayEquals(expectedRadiusData, radius.getMatrix());

        // Verify the name of the resulting matrix
        assertEquals("Radius", radius.getName());
    }

    @Test
    public void testDiameter() throws MatrixException {
        // Create a test matrix
        int[][] matrixData = Matrix.loadCsv("./eulerCycle.csv").getMatrix();
        Matrix matrix = new Matrix(matrixData, "TestMatrix");

        // Calculate the diameter
        Matrix diameter = matrix.diameter();

        // Verify the expected diameter value
        int[][] expectedDiameterData = {
                {3}
        };
        assertArrayEquals(expectedDiameterData, diameter.getMatrix());

        // Verify the name of the resulting matrix
        assertEquals("Diameter", diameter.getName());
    }

    @Test
    void center() {
    }

    @Test
    void components() {
    }

    @Test
    void articulationPoints() {
    }

    @Test
    void bridges() {
    }

    @Test
    public void testFindCycle() {
        int[][] adjMatrix = {
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {0, 1, 1, 0}
        };
        List<Integer> expectedCycle = Arrays.asList(0, 2, 3, 1, 0); // this has multiple correct answers, which makes this testcase somewhat useless

        List<Integer> cycle = new ArrayList<>();
        Matrix matrix = new Matrix(adjMatrix, "TestMatrix");
        matrix.findCycle(0, adjMatrix, cycle);

        assertEquals(expectedCycle, cycle);
    }

    @Test
    public void testEulerLine() throws MatrixException {
        int[][] matrixData = Matrix.loadCsv("./eulerLine.csv").getMatrix();
        int[][] expectedEulerLineData = {
                {0,1,2,3,4,5,6,7,8,9}
        };

        Matrix matrix = new Matrix(matrixData, "TestMatrix");
        Matrix eulerLine = matrix.eulerLine();

        assertArrayEquals(expectedEulerLineData, eulerLine.getMatrix());
        assertEquals("EulerLine", eulerLine.getName());
    }

    @Test
    public void testEulerLineNoPath() {
        int[][] matrixData = {
                {0, 1, 0},
                {1, 0, 0},
                {0, 0, 0}
        };
        int[][] expectedEmptyEulerLineData = {
                {}
        };

        Matrix matrix = new Matrix(matrixData, "TestMatrix");
        Matrix eulerLine = matrix.eulerLine();

        assertArrayEquals(expectedEmptyEulerLineData, eulerLine.getMatrix());
        assertEquals("EulerLine", eulerLine.getName());
    }

    @Test
    void loadCsv() throws MatrixException {
        int[][] matrixData = {{0,0,0},{1,1,0}};
        assertArrayEquals(Matrix.loadCsv("./loadTest.csv").getMatrix(), matrixData);
    }
}