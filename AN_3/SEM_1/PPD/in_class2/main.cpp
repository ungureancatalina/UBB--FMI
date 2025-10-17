#include <iostream>
#include <chrono>
#include <thread>
#include <vector>

using namespace  std;

void generate(vector<int> v1, int n, int maxi){
    for(int i=0; i<n; i++)
        v1[i] = rand() % maxi;
}

long long sum(const vector<int> v1, const vector<int> v2, vector<int> v3,  int n){
    auto t0=chrono::high_resolution_clock::now();
    for(int i = 0; i < n; i++)
        v3[i] = v1[i] + v2[i];
    auto t1= chrono::high_resolution_clock::now();
    return chrono::duration_cast<chrono::nanoseconds>(t1-t0).count();
}

long long runCyclic(const vector<int>& A, const vector<int>& B, vector<int>& C, int nrThreads){
    vector<thread> threads;
    auto t0=chrono::high_resolution_clock::now();
    for(int id=0; id<A.size(); id++){
        threads.emplace_back([id, nrThreads, &A, &B, &C](){
            for(size_t i=id; i<A.size(); i+=nrThreads)
                C[i]= A[i]+B[i];
        });
    }

    for(auto &th : threads)
        th.join();

    auto t1=chrono::high_resolution_clock::now();
    return chrono::duration_cast<chrono::nanoseconds>(t1-t0).count();
}

long long runBlocks(const vector<int>& A, const vector<int> & B, vector<int>& C, int nrThreads){
    vector<thread> threads;
    auto t0=chrono::high_resolution_clock::now();
    int n = A.size();
    int blockSize = n / nrThreads;
    for(int id=0; id<nrThreads; id++){
        int start = id * blockSize;
        int end = (id == nrThreads - 1) ? n : start + blockSize;
        threads.emplace_back([start, end, &A, &B, &C](){
            for(int i=start; i<end; i++)
                C[i]= A[i]+B[i];
        });
    }

    for(auto &t : threads)
        t.join();

    auto t1=chrono::high_resolution_clock::now();
    return chrono::duration_cast<chrono::nanoseconds>(t1-t0).count();
}

bool verifyEqual(const vector<int>& X, const vector<int>& Y) {
    if (X.size() != Y.size()) return false;
    for (size_t i = 0; i < X.size(); i++) {
        if (X[i] != Y[i]) return false;
    }
    return true;
}

int main() {
    int n = 1000000, maxi = 50000, p=3;

    vector<int> v1, v2, v3, v4, v5;

    generate(v1,n,maxi);
    generate(v2,n,maxi);

    cout<< "Secvential: " << sum(v1, v2, v3, n) << " ns" << endl;
    cout<< "Cyclic: " << runCyclic(v1, v2, v4, p) << " ns" << endl;
    cout<< "Blocks: " << runBlocks(v1, v2, v5, p) << " ns" << endl;

    cout<< "Verify Cyclic: " << (verifyEqual(v3, v4) ? "Success" : "Failure") << endl;
    cout<< "Verify Blocks: " << (verifyEqual(v4, v5) ? "Success" : "Failure") << endl;

    return 0;
}
