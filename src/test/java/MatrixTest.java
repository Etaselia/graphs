import net.eta.Matrix;
import net.eta.MatrixException;

public class MatrixTest {
    public static void main(String[] args) {
        Matrix m1 = new Matrix(5,5,true);
        try {
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

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
    }
}
