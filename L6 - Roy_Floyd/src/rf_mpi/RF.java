package rf_mpi;

import mpi.*;


public class RF {

    public static final int NR = 5;
    public static final int INF = 99999;


    public static void main(String[] args) {
       
        int cost_matrix[][] = {
                {0, 4, INF, 6, INF},
                {4, 0, 3, INF, INF},
                {INF, 3, 0, 1, 8},
                {6, INF, 1, 0, INF},
                {INF, INF, 8, INF, 0}
        };
             
        int optim_path[][] = new int[NR][NR];

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();


        for (int k = 0; k < NR; k++) {
            for (int i = 0; i < NR; i += size) {
                for (int j = 0; j < NR; j++) {
                    if (cost_matrix[i][j] > cost_matrix[i][k] + cost_matrix[k][j]) {
                        cost_matrix[i][j] = cost_matrix[i][k] + cost_matrix[k][j];
                    }                   
                }
            }
            MPI.COMM_WORLD.Gather(cost_matrix, rank * NR, NR, MPI.INT, optim_path, rank * NR, NR, MPI.INT, 0);
            MPI.COMM_WORLD.Barrier();
            
            if (rank == 0) {
                for (int i = 0; i < NR; i++) {
                    for (int j = 0; j < NR; j++) {
                        cost_matrix[i][j] = optim_path[i][j];
                    }
                }
                MPI.COMM_WORLD.Bcast(cost_matrix, 0, NR * NR, MPI.INT, 0);
            }
            MPI.COMM_WORLD.Barrier();
        }

        if (rank == 0){
            for (int i = 0; i < NR; i++) {
                for (int j = 0; j < NR; j++) {
                    System.out.print(optim_path[i][j] + " ");
                }
                System.out.println();
            }
        }

        MPI.Finalize();
    }
}