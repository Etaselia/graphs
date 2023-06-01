package com.example;

public class MatrixTest {
    public static void main(String[] args) {
        try {
            Matrix m1 = Matrix.loadCsv("./24n_01.csv");
            /* m1.saveCsv("./output/file.csv");
            m1.print();
            Matrix m2 = Matrix.loadCsv("./output/file.csv");
            m2.print();*/
            /* m1.distance().print();
            m1.eccentricity().print();*/
            m1.distance().print();
            m1.radius().print();
            m1.diameter().print();
            m1.center().print();
            m1.components().print();
            m1.bridges().print();
            m1.eulerCycle().print();
            m1.eulerLine().print();

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
    }
}