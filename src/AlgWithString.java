/**
 * Created by Yakovenko on 19.05.2016.
 */
public class AlgWithString {

    Selection[] selection;
    private int numPerson;
    private int numTour;
    private float specProbMut;
    private float specProbCross;

    private AlgWithString() {
        numPerson = 100;
        numTour = 20;
        specProbMut = 0.5f;
        specProbCross = 0.4f;

        selection = new Selection[numPerson];

        for (int i = 0; i < numPerson; i++) {
            float fX = (float) ((Math.random() * 2000));
            float fY = (float) ((Math.random() * 2000));
            selection[i] = new Selection(getBinary32(fX), getBinary32(fY));
            char c = '0';
            if (selection[i].getLengthPersonX() < 31)
                selection[i].personX = c + selection[i].personX;
            else
                selection[i].personX = selection[i].personX.substring(0, 31);
            if (selection[i].getLengthPersonY() < 31)
                selection[i].personY = c + selection[i].personY;
            else
                selection[i].personY = selection[i].personY.substring(0, 31);

        }
    }

    private AlgWithString(int numPerson, int numTour, float specProbMut, float specProbCross) {
        this.numPerson = numPerson;
        this.numTour = numTour;
        this.specProbMut = specProbMut;
        this.specProbCross = specProbCross;

        selection = new Selection[numPerson];

        for (int i = 0; i < numPerson; i++) {
            float fX = (float) ((Math.random() * 2000));
            float fY = (float) ((Math.random() * 2000));
            selection[i] = new Selection(getBinary32(fX), getBinary32(fY));
            char c = '0';
            if (selection[i].getLengthPersonX() < 31)
                selection[i].personX = c + selection[i].personX;
            else
                selection[i].personX = selection[i].personX.substring(0, 31);
            if (selection[i].getLengthPersonY() < 31)
                selection[i].personY = c + selection[i].personY;
            else
                selection[i].personY = selection[i].personY.substring(0, 31);

        }
    }

    private static float function(float x, float y) {
        return (float) (Math.pow((x - 3), 2) + Math.pow((y - 1), 2) * Math.abs(Math.cos(x)));
        //return Math.pow(x, 2) + Math.pow(y, 2);
    }

    // Convert the 32-bit binary into the decimal
    private static float getFloat32(String Binary) {
        int intBits = Integer.parseInt(Binary, 2);
        float myFloat = Float.intBitsToFloat(intBits);
        return myFloat;
    }

    // Get 32-bit IEEE 754 format of the decimal value
    private static String getBinary32(float value) {
        int intBits = Float.floatToIntBits(value);
        String binary = Integer.toBinaryString(intBits);
        return binary;
    }

    private void tournament() {
        for (int i = 0; i < numTour; i++) {
            int n = (int) (Math.random() * numPerson);
            int k = (int) (Math.random() * numPerson);
            if (n == k) {
                k = (int) (Math.random() * numPerson);
            }
            if (function(getFloat32(selection[n].personX), getFloat32(selection[n].personY)) > function(
                    getFloat32(selection[k].personX), getFloat32(selection[k].personY))) {
                selection[n].personX = selection[k].personX;
                selection[n].personY = selection[k].personY;
            } else {
                selection[k].personX = selection[n].personX;
                selection[k].personY = selection[n].personY;
            }
        }
    }

    private void mutation() {
        for (int i = 0; i < numPerson; i++) {
            double probMutation = Math.random();
            int num = selection[i].getLengthPersonX();
            if (probMutation > specProbMut) {
                int n = (int) (Math.random() * 22);
                int k = (int) (Math.random() * 22);
                if (n == k) {
                    k = (int) (Math.random() * 22);
                }
                if (n > k) {
                    int c = n;
                    n = k;
                    k = c;
                }
                StringBuffer buffer = new StringBuffer(selection[i].personX.substring(n, k));
                for (int j = 0; j < buffer.length(); j++) {
                    if (Character.getNumericValue((buffer.charAt(j))) == 1)
                        buffer.setCharAt(j, '0');
                    else
                        buffer.setCharAt(j, '1');
                }
                selection[i].personX = selection[i].personX.substring(0, n) + buffer.toString()
                        + selection[i].personX.substring(k, num);
//                probMutation = Math.random();
                num = selection[i].getLengthPersonY();
//                if (probMutation > specProbMut) {
                n = (int) (Math.random() * 22);
                k = (int) (Math.random() * 22);
                if (n == k) {
                    k = (int) (Math.random() * 22);
                }
                if (n > k) {
                    int c = n;
                    n = k;
                    k = c;
                }
                buffer = new StringBuffer(selection[i].personX.substring(n, k));
                for (int j = 0; j < buffer.length(); j++) {
                    if (Character.getNumericValue((buffer.charAt(j))) == 1)
                        buffer.setCharAt(j, '0');
                    else
                        buffer.setCharAt(j, '1');
                }
                selection[i].personY = selection[i].personY.substring(0, n) + buffer.toString()
                        + selection[i].personY.substring(k, num);
                //}
            }
        }
    }

    private void cross() {
        for (int i = 0; i < numPerson; i++) {
            double probCross = Math.random();
            int numX0 = selection[i].getLengthPersonX();

            if (probCross > specProbCross) {

                int j = (int) (Math.random() * numPerson);

                int numX1 = selection[j].getLengthPersonX();

               /* if (numX1>numX0) {
                    int c = numX1;
                    numX1 = numX0;
                    numX0 = c;
                }*/

                int n = (int) (Math.random() * numX1);
                int k = (int) (Math.random() * numX1);

                if (n == k) {
                    k = (int) (Math.random() * numX1);
                }

                if (n > k) {
                    int c = n;
                    n = k;
                    k = c;
                }

                StringBuffer buffer0 = new StringBuffer(selection[i].personX.substring(n, k));
                StringBuffer buffer1 = new StringBuffer(selection[j].personX.substring(n, k));

                selection[i].personX = selection[i].personX.substring(0, n) + buffer1.toString()
                        + selection[i].personX.substring(k, numX0);
                selection[j].personX = selection[j].personX.substring(0, n) + buffer0.toString()
                        + selection[j].personX.substring(k, numX0);

                buffer0 = new StringBuffer(selection[i].personY.substring(n, k));
                buffer1 = new StringBuffer(selection[j].personY.substring(n, k));

//                numX0 = selection[i].getLengthPersonY();
                numX1 = selection[j].getLengthPersonY();

                /*if (numX1>numX0) {
                    int c = numX1;
                    numX1 = numX0;
                    numX0 = c;
                }*/

                selection[i].personY = selection[i].personY.substring(0, n) + buffer1.toString()
                        + selection[i].personY.substring(k, numX1);
                selection[j].personY = selection[j].personY.substring(0, n) + buffer0.toString()
                        + selection[j].personY.substring(k, numX1);
            }
        }
    }

    private Selection minSelection() {
        Selection min = new Selection(selection[0].personX, selection[0].personY);

        for (int i = 1; i < numPerson; i++) {
            if (function(getFloat32(min.personX), getFloat32(min.personY)) > function(getFloat32(selection[i].personX),
                    getFloat32(selection[i].personY))) {
                min.personX = selection[i].personX;
                min.personY = selection[i].personY;
            }
        }
        return min;
    }

    public void printSelection() {
        for (int i = 0; i < numPerson; i++) {
            System.out.println("PersonX " + selection[i].personX);
            System.out.println("PersonY " + selection[i].personY);
        }
        //System.out.println();
    }

    public void printSelectionFloat() {
        for (int i = 0; i < numPerson; i++) {
            System.out.println("PersonX " + getFloat32(selection[i].personX));
            System.out.println("PersonY " + getFloat32(selection[i].personY));
        }
        //System.out.println();
    }

    public static void main(String[] args) {
        float eps = 0.0001f;
        int maxIter = 100000;
        int i = 0;
        int counter = 0;

        int numPerson = 100;
        int numTour = 20;
        float specProbMut = 0.1f;
        float specProbCross = 0.5f;

        AlgWithString algWithString = new AlgWithString(numPerson, numTour, specProbMut, specProbCross);

        Selection sel0 = algWithString.selection[0];
        Selection sel1;

        while (i < maxIter) {
            sel1 = algWithString.minSelection();

            System.out.println("Эпоха №" + i);
            System.out.println("Лучшая особь");
            System.out.println(
                    "X= " + AlgWithString.getFloat32(sel1.personX) +
                            " Y= " + AlgWithString.getFloat32(sel1.personY) +
                            " F = " + AlgWithString.function(AlgWithString.getFloat32(sel1.personX),
                            AlgWithString.getFloat32(sel1.personY)));

            if (Math.abs(AlgWithString.function(AlgWithString.getFloat32(sel1.personX),
                    AlgWithString.getFloat32(sel1.personY))
                   /* - AlgWithString.function(AlgWithString.getFloat32(sel0.personX),
                    AlgWithString.getFloat32(sel0.personY))*/) < eps) {
                counter++;
            } else
                counter = 0;

            if (counter == 5) {
                break;
            }

            sel0.personX = sel1.personX;
            sel0.personY = sel1.personY;

            algWithString.cross();
            algWithString.mutation();
            algWithString.tournament();
            i++;
        }
    }
}
