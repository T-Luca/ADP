package proceslider;

import java.util.Random;
import mpi.*;

public class ProcesLider {
    public static void main(String[] args) {
        final int Master = 0;		
        Random rand = new Random();		
        
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int array[] = new int[1];
        
        if(rank != Master) {
            int nr = rand.nextInt(20);
            array[0]= nr;
            System.out.println("Rank:" + rank + " nr:" + array[0]);
            MPI.COMM_WORLD.Send(array, 0, array.length, MPI.INT, 0, 0);
            
        } else {
            
            int nr = rand.nextInt(20);
            int maxNr = nr;
            int maxRank = Master;
            for(int id = 1; id < size; id++) {
                MPI.COMM_WORLD.Recv(array, 0, array.length, MPI.INT, id, 0);
                if(array[0] > maxNr) {
                    maxNr = array[0];
                    maxRank = id;
                } else if (array[0] == maxNr && id > maxRank) {					
                    maxRank = id;
                    maxNr = array[0];
                }
            }
            System.out.println("Proces lider: " + maxRank + " valoare maxima: " + maxNr);
        }
        MPI.Finalize();		
    }
}