import static java.lang.System.out;
import static java.lang.Math.abs;

public class InverseInterpolation {
    public static double y = 1.782125;
    public static double epsilon = 0.00001;

    static void space(int p) {
        String s = "";
        for (int i = 0; i < p; i++) {
            out.print(" ");
        }
    }

    static void ch() {
        space(5);
        out.print("x");
        space(9);
        out.print("f");
        space(8);
        out.print('\u0394' + "f" + " ");
        for (int i = 2; i < 5; i++) {
            space(6);
            out.print('\u0394' + "^" + i + "f");
        }
        out.println();
    }

    static double tx(double x1, double x0) {
        double h = 0.1;
        return (x1 - x0) / h;
    }

    static double phi(double t, double[][] difference) {
        int i = 8, j = 1;
        return (y - (difference[i++][j++] +0*difference[i++][j++]+ t * (t - 1) / 2.0 * difference[i++][j++] + t * (t - 1) * (t - 2) / 6.0 * difference[i++][j++] + t * (t - 1) * (t - 2) * (t - 3) / 24.0 * difference[i][j])) / difference[9][2];
    }

    public static void main(String[] args) {
        final int length = 10;
        double[] x = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
        double[] f = {1.614419, 1.656832, 1.694888, 1.728606, 1.758030, 1.783225, 1.804279, 1.821299, 1.834414, 1.843768};
        double[][] difference = new double[2 * length - 1][length + 1];
        for (int i = 0; i < 2 * length - 1; i++) {
            for (int j = 0; j < length + 1; j++) {
                difference[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < 2 * length - 1; i += 2) {
            difference[i][0] = x[i / 2];
            difference[i][1] = f[i / 2];
        }
        int k = 2;
        for (int column = 2; column < length + 1; column++) {
            if (column % 2 == 0) {
                for (int i = column - 1; i < 2 * length - column + 1; i += 2) {
                    difference[i][column] = difference[i + 1][k - 1] - difference[i - 1][k - 1];
                }
                k++;
            } else {
                for (int i = column - 1; i < 2 * length - column + 1; i += 2) {
                    difference[i][column] = difference[i + 1][k - 1] - difference[i - 1][k - 1];
                }
                k++;
            }
        }
        out.println("Частичные разности:");
        ch();
        for (int i = 0; i < 2 * length - 1; i++) {
            for (int j = 0; j < length - 4; j++) {
                if (difference[i][j] != Double.POSITIVE_INFINITY) {
                    if (difference[i][j] >= 0) {
                        out.print(" ");
                    }
                    out.printf("%1.6f", difference[i][j]);
                } else {
                    difference[i][j] = 0;
                    space(9);
                }
                out.print(" ");
            }
            out.println();
        }
        out.println();
        out.println();
        //begin inverse intepolation
        k=0;
        double t0 = 0, t1 = phi(t0, difference);
        out.println("Поиск t:");
        out.print(" k");
        space(5);
        out.print("t_k");
        space(5);
        out.println('\u03c6'+"(t_k)");
        out.print(" "+k+"  ");
        out.printf("%1.6f",t0);
        out.print("  ");
        out.printf("%1.6f",t1);
        out.println();
        while (abs(t0-t1)>epsilon) {
            k++;
            t0=t1;
            t1=phi(t0,difference);
            if (k>9) {
                out.print(k + "  ");
            }
            else {
                out.print(" "+k+"  ");
            }
            out.printf("%1.6f",t0);
            out.print("  ");
            out.printf("%1.6f",t1);
            out.println();
        }
        double h=0.1;
        double xk=difference[8][0]+t1*h;
        out.println();
        out.print("x*=");
        out.printf("%1.6f",xk);
        out.println();

        int i=8,j=1;
        double P1=difference[i++][j++]+t1*difference[i++][j++]+t1*(t1-1)/2.0*difference[i++][j++]+t1*(t1-1)*(t1-2)/6.0*difference[i++][j++]+t1*(t1-1)*(t1-2)*(t1-3)/24.0*difference[i][j];
        out.println();
        out.println("Проверка:");
        out.print(y+"=y*=Pbegin(");
        out.printf("%1.6f",t1);
        out.print(")"+"=");
        out.printf("%1.6f",P1);
    }
}
