package arraysum;

import mpi.*;

public class ArraySum {

    public static void main(String[] args) {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        
        final int Master = 0;
        int local_sum = 0; 
        int global_sum = 0;
        int vector[] = {4, 6, 10, 15, 5, 9, 1, 7};
        int master_vect[] = new int[2];
        int slave_vect[] = new int [1];

        if (rank == Master) {          
            int length;           
            length = vector.length / size;

            for(int i = 0; i < length; i++) {               
                global_sum += vector[i];
            }
            System.out.println("Process " + rank + " local sum is: " + global_sum);                       
            for (int j = 1; j < size; j++) {               
                int k=0;
                for (int iterator = j * length; iterator < j * length + length; iterator++) {
                    master_vect[k++] = vector[iterator];                  
                }
                MPI.COMM_WORLD.Send(master_vect, 0, length, MPI.INT, j, 0);
            }
        }
        
        if (rank != Master) {
            MPI.COMM_WORLD.Recv(master_vect, 0, 2, MPI.INT, 0, 0);           
            for (int j = 0; j < 2; j++) {
                local_sum += master_vect[j];
            }
            System.out.println("Process " + rank + " local sum is: " + local_sum);
            slave_vect[0]=local_sum;
            MPI.COMM_WORLD.Send(slave_vect, 0, 1, MPI.INT, 0, 0);
        }
        
        if (rank == Master) {               
            for(int i = 1; i < size; i++){
                MPI.COMM_WORLD.Recv(slave_vect, 0, 1, MPI.INT, i, 0);
                global_sum+=slave_vect[0];
            }
            System.out.println("Global sum is: " + global_sum);
        }
        MPI.Finalize();
    }
}