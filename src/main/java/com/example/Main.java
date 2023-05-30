package com.example;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        while (true){
            Scanner userInput = new Scanner(System.in);
            System.out.println("Please select Testcase:");
            System.out.println("1: Random 5x5 Arrays");
            System.out.println("2: Predefined Arrays");
            System.out.println("q: Quit");
            String userInputString = userInput.nextLine();
            if (userInputString.equals("1")){
                try {
                    Matrix matrixA = new Matrix(5,5,true);
                    Matrix matrixB = new Matrix(5,5,true);

                    System.out.println("A:");
                    matrixA.print();

                    System.out.println("B:");
                    matrixB.print();

                    System.out.println("A²");
                    matrixA.power(2).print();

                    System.out.println("A³");
                    matrixA.power(3).print();

                    System.out.println("Distance");
                    matrixA.distance().print();
                } catch (MatrixException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (userInputString.equals("2")){
                try {
                    int[][] a = {{4,1,7,3},{1,2,1,5},{7,1,1,4},{3,5,4,3}};
                    int[][] b = {{1,4,3,8},{5,7,4,2},{3,4,1,3},{8,2,5,1}};
                    Matrix matrixA = new Matrix(a, "A");
                    Matrix matrixB = new Matrix(b, "B");


                    System.out.println("A:");
                    matrixA.print();
                    System.out.println("B:");
                    matrixB.print();
                    System.out.println("A*B");
                    Matrix.multiply(matrixA,matrixB).print();
                    System.out.println("A³");
                    matrixA.power(3).print();
                    System.out.println("Distance A");
                    matrixA.distance().print();
                    System.out.println("Distance B");
                    matrixB.distance().print();
                } catch (MatrixException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (userInputString.equals("q")){
                break;
            }
            System.out.println("Selection Complete, please press any button to restart");
            userInput.nextLine();
        }
    }
}