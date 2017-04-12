import static java.lang.System.out;

public class Interpolation {

    static void space(int p) {
        String s = "";
        for (int i = 0; i < p; i++) {
            out.print(" ");
        }
    }

    static void ch(){
        space(5);
        out.print("x");
        space(9);
        out.print("f");
        space(8);
        out.print('\u0394'+"f"+" ");
        for (int i=2;i<5;i++){
            space(6);
            out.print('\u0394'+"^"+i+"f");
        }
        out.println();
    }

    static double tx (double x1, double x0){
        double h=0.1;
        return (x1-x0)/h;
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
        int k=2;
        for (int column=2; column<length+1;column++) {
            if (column%2==0) {
                for (int i = column-1; i < 2*length - column + 1; i+=2) {
                    difference[i][column] = difference[i+1][k-1] - difference[i-1][k-1];
                }
                k++;
            }
            else{
                for (int i = column-1; i < 2*length - column + 1; i+=2) {
                    difference[i][column] = difference[i+1][k-1] - difference[i-1][k-1];
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
                }
                else {
                    difference[i][j]=0;
                    space(9);
                }
                out.print(" ");
            }
            out.println();
        }
        out.println();
        out.println();
        //for begin table

        double t=tx(0.064641,0);
        int i=0,j=1;
        double P1=difference[i++][j++]+t*difference[i++][j++]+t*(t-1)/2.0*difference[i++][j++]+t*(t-1)*(t-2)/6.0*difference[i++][j++]+t*(t-1)*(t-2)*(t-3)/24.0*difference[i][j];
        out.println("ИПН(интерполяционный полином Ньютона) для начала таблицы (x*=0.064641):");
        out.print("P(");
        out.printf("%1.6f", t);
        out.print(")=");
        out.printf("%1.6f", P1);

        //for end table
        t=tx(0.717422,0.8);
        i=difference.length-3;
        j=1;
        out.println();
        out.println('\n'+"ИПН для конца таблицы (x*=0.717422):");
        double P2=difference[i--][j++]+t*difference[i--][j++]+t*(t+1)/2.0*difference[i--][j++]+t*(t+1)*(t+2)/6.0*difference[i--][j++]+t*(t+1)*(t+2)*(t+3)/24.0*difference[i][j];
        out.print("P(");
        out.printf("%1.6f", t);
        out.print(")=");
        out.printf("%1.6f", P2);

        //for middle table
        out.println();
        t=tx(0.615524,0.6);
        i=12;
        j=1;
        out.println('\n'+"ИПН для середины таблицы (x*=0.615524):");
        double Pg=difference[i++][j++]+t*difference[i--][j++]+t*(t-1)/2.0*difference[i++][j++]+t*(t-1)*(t+1)/6.0*difference[i--][j++]+t*(t-1)*(t+1)*(t-2)/24.0*difference[i][j];
        out.print("Pg(");
        out.printf("%1.6f", t);
        out.print(")=");
        out.printf("%1.6f", Pg);


        i=12;
        j=1;
        t=tx(0.615524,0.6);
        P1=difference[i++][j++]+t*difference[i++][j++]+t*(t-1)/2.0*difference[i++][j++]+t*(t-1)*(t-2)/6.0*difference[i++][j++]+t*(t-1)*(t-2)*(t-3)/24.0*difference[i][j];
        out.println();
        out.print("Pbegin(");
        out.printf("%1.6f", t);
        out.print(")=");
        out.printf("%1.6f", P1);


        t=tx(0.615524,0.7);
        i=14;
        j=1;
        P2=difference[i--][j++]+t*difference[i--][j++]+t*(t+1)/2.0*difference[i--][j++]+t*(t+1)*(t+2)/6.0*difference[i--][j++]+t*(t+1)*(t+2)*(t+3)/24.0*difference[i][j];
        out.println();
        out.print("Pend(");
        out.printf("%1.6f", t);
        out.print(")=");
        out.printf("%1.6f", P2);

    }
}
