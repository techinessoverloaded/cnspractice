public class HillCipher {
    public static int N = 3;

    // Function to get cofactor of A[p][q] in temp[][]. n is current
    // dimension of A[][]
    static void getCofactor(int A[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;

        // Looping for each element of the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];

                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    /*
     * Recursive function for finding determinant of matrix.
     * n is current dimension of A[][].
     */
    static int determinant(int A[][], int n) {
        int D = 0; // Initialize result

        // Base case : if matrix contains single element
        if (n == 1)
            return A[0][0];

        int[][] temp = new int[N][N]; // To store cofactors

        int sign = 1; // To store sign multiplier

        // Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            // Getting Cofactor of A[0][f]
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);

            // terms are to be added with alternate sign
            sign = -sign;
        }

        return D;
    }

    // Function to get adjoint of A[N][N] in adj[N][N].
    static void adjoint(int A[][], int[][] adj) {
        if (N == 1) {
            adj[0][0] = 1;
            return;
        }

        // temp is used to store cofactors of A[][]
        int sign = 1;
        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Get cofactor of A[i][j]
                getCofactor(A, temp, i, j, N);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0) ? 1 : -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = (sign) * (determinant(temp, N - 1));
            }
        }
    }

    static int getDivider(int det) {
        int val;
        int i = 1;
        while (true) {
            if ((i * det) % 26 == 1) {
                break;
            } else {
                i++;
            }
        }
        val = i;
        return val;
    }

    // Function to calculate and store inverse, returns false if
    // matrix is singular
    static boolean inverse(int A[][], int[][] inverse) {
        // Find determinant of A[][]
        int det = determinant(A, N);
        if (det == 0) {
            System.out.print("Singular matrix, can't find its inverse");
            return false;
        }
        System.out.println("Det: " + det);

        // Find adjoint
        int[][] adj = new int[N][N];
        adjoint(A, adj);

        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        det = det % 26;
        if (det < 26) {
            det += 26;
        }
        int oneByDetMod26 = getDivider(det);
        System.out.println("one: " + oneByDetMod26);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = adj[i][j] * oneByDetMod26;

        return true;
    }

    public static void getKeyMatrix(String key, int keyMatrix[][]) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = (key.charAt(k)) % 65;
                k++;
            }
        }
    }

    static void encrypt(int cipherMatrix[][],
            int keyMatrix[][],
            int messageVector[][]) {
        int x, i, j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 1; j++) {
                cipherMatrix[i][j] = 0;

                for (x = 0; x < 3; x++) {
                    cipherMatrix[i][j] += keyMatrix[i][x] * messageVector[x][j];
                }

                cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
            }
        }
    }

    static void decrypt(
            int keyMatrix[][],
            int cipherMatrix[][],
            int messageVector[][]) {
        int x, i, j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 1; j++) {
                messageVector[i][j] = 0;

                for (x = 0; x < 3; x++) {
                    messageVector[i][j] += keyMatrix[i][x] * cipherMatrix[x][j];
                }

                messageVector[i][j] = messageVector[i][j] % 26;
            }
        }
    }

    public static void main(String[] args) {
        String message = "ACT";

        String key = "RRFVSVCCT";

        int[][] keyMatrix = new int[3][3];
        getKeyMatrix(key, keyMatrix);

        int[][] messageVector = new int[3][1];

        for (int i = 0; i < 3; i++)
            messageVector[i][0] = (message.charAt(i)) % 65;

        int[][] cipherMatrix = new int[3][1];

        encrypt(cipherMatrix, keyMatrix, messageVector);

        String CipherText = "";

        // Generate the encrypted text from
        // the encrypted vector
        for (int i = 0; i < 3; i++)
            CipherText += (char) (cipherMatrix[i][0] + 65);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(keyMatrix[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
        // Finally print the ciphertext
        System.out.println(" Ciphertext:" + CipherText);

        int[][] inv = new int[N][N];
        if (inverse(keyMatrix, inv) == true) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    inv[i][j] = inv[i][j] % 26;
                    if (inv[i][j] < 0) {
                        inv[i][j] += 26;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("=======================");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(inv[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }

        int[][] resultMatrix = new int[N][1];
        decrypt(inv, cipherMatrix, resultMatrix);

        String PlainText = "";

        // Generate the encrypted text from
        // the encrypted vector
        for (int i = 0; i < 3; i++) {
            System.out.println(resultMatrix[i][0]);
        }
        for (int i = 0; i < 3; i++)
            PlainText += (char) (resultMatrix[i][0] + 65);

        System.out.println("Dec: " + PlainText);
    }
}
