#include <mpi.h>
#include <iostream>
#include <string>
#include <vector>
#include <string.h>
#include <stdio.h>
using namespace std;

void run_mpi_hello_world(){

    //get rank process
    int my_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    //get total nr of processes
    int num_processes;
    MPI_Comm_size(MPI_COMM_WORLD, &num_processes);

    //print hello world message
    if(my_rank == 0) {

        //process 0 - recieve message from all other processes
        string result= "";
        char buffer[256];

        for(int i=1; i<num_processes; i++){
            MPI_Recv(buffer, 256, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            result += buffer;
            if(i<num_processes-1){
                result += "\n";
            }
        }

        // display the final cconcatenated string
        cout<<" Process 0 recieved all messages :\n"<<result<<endl;
    }
    else{
        //other proceses: Send message to process 0
        char message[256];
        sprintf(message, "Hello from id %d", my_rank);
        MPI_Send(message, strlen(message)+1, MPI_CHAR,0,0, MPI_COMM_WORLD);
    }
}

void run_api_hello_world_async(){

    //get rank process
    int my_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    //get total nr of processes
    int num_processes;
    MPI_Comm_size(MPI_COMM_WORLD, &num_processes);

    if(my_rank ==0){

        //process 0: recieve message async from all other processes
        int num_other_processes = num_processes-1;

        vector<char[256]> buffers(num_other_processes);
        vector<MPI_Request> requests(num_other_processes);
        vector<int> flag(num_other_processes, 0);

        for(int i=1; i<=num_other_processes; i++){
            MPI_Irecv(buffers[i-1], 256, MPI_CHAR, i, 0, MPI_COMM_WORLD, &requests[i-1]);
        }

        int recieve_count =0;
        while(recieve_count< num_other_processes){
            for(int i=0;i<num_other_processes;i++){

                //yet not recieved
                if(flag[i]==0){
                    MPI_Test(&requests[i], &flag[i], MPI_STATUS_IGNORE);
                    if(flag[i]){

                        //message recieved
                        recieve_count++;
                    }
                }
            }
        }

        //build the final string
        string result="";
        for(int i=0;i<num_other_processes;i++){
            result+=buffers[i];
            if(i<num_other_processes-1){
                result+="\n";
            }
        }
        cout<<"Process 0 recevied all messages async: \n"<<result<<endl;
    }
    else{
        char message[256];
        sprintf(message,"Hello from id %id", my_rank);

        MPI_Request request;
        MPI_Isend(message, strlen(message)+1, MPI_CHAR, 0,0, MPI_COMM_WORLD,&request );

        int flag=0;
        while(!flag){
            MPI_Test(&request, &flag, MPI_STATUS_IGNORE);
        }
    }
}

void run_vector_addition(){

    //get rank process
    int my_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    //get total nr of processes
    int num_processes;
    MPI_Comm_size(MPI_COMM_WORLD, &num_processes);

    const int VECTOR_SIZE =100;
    int segment_size = VECTOR_SIZE/num_processes;

    if(my_rank==0){
        vector<int> A(VECTOR_SIZE);
        vector<int> B(VECTOR_SIZE);
        vector<int> C(VECTOR_SIZE);

        for(int i=0;i<VECTOR_SIZE;i++){
            A[i]=i;
            B[i]=i*2;
        }

        //send segments to other processes
        for(int i=1;i<num_processes;i++){
            int start_index = i*segment_size;
            MPI_Send(&A[start_index], segment_size, MPI_INT, i,0,MPI_COMM_WORLD);
            MPI_Send(&B[start_index], segment_size, MPI_INT, i,1,MPI_COMM_WORLD);
        }

        //process 0 computes its own segment
        for(int i=0;i<segment_size;i++){
            C[i]= A[i]+ B[i];
        }

        //receive computed segments from other processes
        for(int i=1;i<num_processes;i++){
            int start_index = i*segment_size;
            MPI_Recv(&C[start_index], segment_size, MPI_INT, i,2,MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        //display result
        cout<<"Vector addition completed: First 10 elements of result vector C: ";
        for(int i=0;i<10;i++){
            cout<<C[i]<<" ";
        }
        cout<<"...\n";
        cout<<"Last 10 elements of result vector C: ";
        for(int i=VECTOR_SIZE-10;i<VECTOR_SIZE;i++){
            cout<<C[i]<<" ";
        }
    }
    else{
        //other processes recieve segments, compute and send back
        vector<int> A_segment(segment_size);
        vector<int> B_segment(segment_size);
        vector<int> C_segment(segment_size);

        // receive segments from process 0
        MPI_Recv(A_segment.data(), segment_size, MPI_INT, 0,0,MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(B_segment.data(), segment_size, MPI_INT, 0,1,MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        //compute C=A+B for the segment
        for(int i=0;i<segment_size;i++){
            C_segment[i]= A_segment[i]+ B_segment[i];
        }

        //send computed segment back to process 0
        MPI_Send(C_segment.data(), segment_size, MPI_INT, 0,2,MPI_COMM_WORLD);
    }
}

void run_vector_addition_collective() {
    // Get the rank of the process
    int my_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    // Get the total number of processes
    int num_processes;
    MPI_Comm_size(MPI_COMM_WORLD, &num_processes);

    const int VECTOR_SIZE = 100;
    int segment_size = VECTOR_SIZE / num_processes;

    // All processes need their own segment buffers
    std::vector<int> segment_A(segment_size);
    std::vector<int> segment_B(segment_size);
    std::vector<int> segment_C(segment_size);

    std::vector<int> A, B, C;

    if (my_rank == 0) {
        // Process 0: Initialize vectors A and B
        A.resize(VECTOR_SIZE);
        B.resize(VECTOR_SIZE);
        C.resize(VECTOR_SIZE);

        // Initialize vectors with some values
        for (int i = 0; i < VECTOR_SIZE; i++) {
            A[i] = i;
            B[i] = i * 2;
        }
    }

    // Scatter A and B from process 0 to all processes
    MPI_Scatter(my_rank == 0 ? A.data() : nullptr, segment_size, MPI_INT,
                segment_A.data(), segment_size, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(my_rank == 0 ? B.data() : nullptr, segment_size, MPI_INT,
                segment_B.data(), segment_size, MPI_INT, 0, MPI_COMM_WORLD);

    // All processes compute their segment: C = A + B
    for (int i = 0; i < segment_size; i++) {
        segment_C[i] = segment_A[i] + segment_B[i];
    }

    // Gather all computed segments back to process 0
    MPI_Gather(segment_C.data(), segment_size, MPI_INT,
               my_rank == 0 ? C.data() : nullptr, segment_size, MPI_INT, 0, MPI_COMM_WORLD);

    if (my_rank == 0) {
        // Display the result
        std::cout << "Vector addition (collective) completed. First 10 elements of C:\n";
        for (int i = 0; i < 10; i++) {
            std::cout << "C[" << i << "] = " << C[i] << " (A[" << i << "] + B[" << i << "] = " << A[i] << " + " << B[i] << ")\n";
        }
        std::cout << "...\n";
        std::cout << "Last 10 elements of C:\n";
        for (int i = VECTOR_SIZE - 10; i < VECTOR_SIZE; i++) {
            std::cout << "C[" << i << "] = " << C[i] << " (A[" << i << "] + B[" << i << "] = " << A[i] << " + " << B[i] << ")\n";
        }
    }
}



int main(int argc, char** argv) {
    MPI_Init(NULL,NULL);

//    run_mpi_hello_world();
//    run_api_hello_world_async();
//    run_vector_addition();
    run_vector_addition_collective();
    MPI_Finalize();
}
