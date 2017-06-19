#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include "Profiler.h"

Profiler profiler("demo");

#define MAX_SIZE 10007
#define MAX_V_SIZE 3000
FILE *f;


using namespace std;
int ops = 0;
float fill_f[] = { 0.8f, 0.85f, 0.9f, 0.95f, 0.99f };
int op = 0;

int h(int k, int i, int n) {
	int h = (k%n  + i +i*i) % n;
	return h;
}


void initialize_h(int* t, int n) {
	for (int i = 0; i < n; i++) {
		*(t + i) = NULL;
	}
}

int h_search(int* t, int k, int n) { // t- hash table
	int i = 0;						 // k- searched elem
	int j;							 // n- length of vector
	do {
		j = h(k, i,n);
		op++;
		if (*(t + j) == k)
			return j;
		i++;
	} while (*(t + j) != NULL || i < n); 
	return NULL;
}

int h_insert(int *t, int k, int n) {
	int i = 0;
	int j;
	do {
		j = h(k, i,n);
		if (*(t + j) == NULL) {
			*(t + j) = k;
			return j;
		}
		else i++;
	} while (i != n);
	return NULL;
}

void printv(int a[], int n) {
	for (int i = 0; i < n; i++) {
		cout << a[i] << " ";
	}
	cout << endl;
}

int main() {

	f = fopen("asdf.csv", "w");
	int* ht = (int*)malloc((MAX_SIZE+1501)*(sizeof(int)));
	int n = MAX_SIZE;
	int idx[1501];
	int v[MAX_SIZE + 1501];
	int op_ex = 0, op_nonex=0, ops_nonex=0, ops_ex=0;
	//cout << n << " sdsadssd" << endl;
	initialize_h(ht, n);

	fprintf(f,"%s, %s, %s, %s, %s", "Filling ", "Avg Effort", "Max Effort", "Avg. Effort", "Max Effort");
	fprintf(f,"\n");
	fprintf(f,"%s, %s, %s, %s, %s", "factor ", "found", "found", "not found", "not found");
	fprintf(f,"\n");


	for (int cases = 0; cases < 5; cases++) {
		n = (int) (fill_f[cases] * MAX_SIZE);
		//cout << n << " sdsadssd" << endl;
		FillRandomArray(v,(int) n, 0, 1000000, true, 0);
		initialize_h(ht, MAX_SIZE);
		for (int i = 0; i < n; i++) {
			h_insert(ht, v[i], MAX_SIZE);
		}
		ops = 0;
		op_nonex = 0;
		op_ex = 0;
		ops_nonex = 0;
		ops_ex = 0;
		//printv(ht, n);
		for (int i = n-1500; i < n; i++) {
			h_search(ht, v[i],MAX_SIZE);
			if (op_nonex < op) {
				op_nonex = op;
			}
			ops += op;
			op = 0;
		}
		ops_nonex = ops;
		ops = 0;

		FillRandomArray(idx, 1500, 0, (int)(n - 1501),true, 0);

		for (int i = 0; i < 1500; i++) {
			h_search(ht, v[idx[i]],MAX_SIZE);
			if (op_ex < op) {
				op_ex = op;
			}
			ops += op;
			op = 0;
			
		}
		ops_ex = ops;
		ops = 0;

		fprintf(f,"%3.2f, %3.2f, %d, %3.2f, %d", fill_f[cases],(float) ops_ex / 1500, op_ex,(float) ops_nonex / 1500, op_nonex);
		fprintf(f,"\n");
	}

	//int a[] = { 23,45,22,56,76,21,7,4,2,1,3,27,87 };

	//for (int i = 0; i < n; i++) {
		//h_insert(ht, a[i], n);
	//}

	//cout<<h_search(ht, 76, n)<<" <-value searched"<<endl;
	//cout << h_search(ht, 754, n) << " <-value searched" << endl;

	free(ht);
	_getch();
	return 0;
}