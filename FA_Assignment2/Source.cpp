/*
	Top-down method/strategy more inefficient, as the chart proves.
	build_max_heap, bottom-up, takes O(n), where build_max_heap', top-down, takes O(n lgn)
*/

#include <stdio.h>
#include<conio.h>
#include"Profiler.h"
#include <math.h>
#include <stdlib.h>

#define MAX_SIZE 10000

using namespace std;

Profiler profiler("demo");

void heapify(int a[], int n, int i,int c) {
	int l = 2 * i; //left
	int r = 2 * i + 1;//right
	int aux,largest;
	profiler.countOperation("Bottom-Up", n, 1);
	if (l<=n && a[l] > a[i]) {
		largest = l;
	}
	else
		largest = i;
	profiler.countOperation("Bottom-Up", n, 1);
	if (r <= n && a[r] > a[largest])
		largest = r;
	if (largest != i) {
		profiler.countOperation("Bottom-Up", n, 3);
		aux = a[i];
		a[i] = a[largest];
		a[largest] = aux;
		heapify(a, n, largest,c);
	}
}

void buildHeapBD(int a[], int n,int c) {
	for (int i = floor( n / 2); i >= 1; i--) {
		heapify(a, n, i,c);
	}
}


void printv(int a[], int n) {
	for (int i = 1; i <= n; i++) {
		printf("%d ", a[i]);
	}
	printf("\n");
}

void heapSortBU(int a[], int n, int c) {
	buildHeapBD(a, n,c);
	int aux;
	for (int i = n; i >= 2; i--) {
		aux = a[1];
		a[1] = a[i];
		a[i] = aux;
		n--;
		heapify(a, n, 1, c);
	}
}

int hsize;

void heapIncrease(int a[], int i, int n, int key) {
	profiler.countOperation("Top-Down", n, 1);
	a[i] = key;
	int aux;
	while (i > 1 && a[i / 2] < a[i]) {
		aux = a[i / 2];
		a[i / 2] = a[i];
		a[i] = aux;
		i = i / 2;
		profiler.countOperation("Top-Down", n, 4); // 3 assignments 1 c
	}
}

void insertMax(int a[], int key, int n) {
	hsize = hsize + 1;
	a[hsize] = -INFINITY;
	profiler.countOperation("Top-Down", n);
	heapIncrease(a, hsize,n, key);
}

void buildHeapTD(int a[], int n) {
	hsize = 0;
	for (int i = 1; i <= n;i++)
		insertMax(a, a[i], n);
}

void heapSortTD(int a[], int n, int hsize) {
	buildHeapTD(a,hsize);
	int aux;
	for (int i = hsize; i >= 2; i--) {
		aux = a[1];
		a[1] = a[i];
		a[i] = aux;
		hsize--;
		heapify(a, hsize, 1,n);
	}
}

int main() {
	//			testing part
	//int x[] = { NULL,12,45,6,8,3,21,9,1,76,10,2 };
	//int y[] = { NULL,12,45,6,8,3,21,9,1,76,10,2 };
	//int n1 = 11;
	//buildHeapTD(x, n1);
	//printv(x, n1);
	//heapSortTD(x,n1,n1);
	//printv(x, n1);

	//buildHeapBD(y, n1);
	//printv(y, n1);
	//heapSortBU(y,n1,n1);
	//printv(y, n1);

	//			chart part

	int a[MAX_SIZE], b[MAX_SIZE];
	
	for (int n = 100; n <= 10000; n += 500) {
		for (int caz = 1; caz <= 5; caz++) {
			FillRandomArray(a, n, 1, 10000, true, 0);
			for (int j = 0; j < n; j++)
				b[j] = a[j];
			buildHeapBD(a, n, n);
			buildHeapTD(b, n);
		}
	}
	profiler.createGroup("Heap-Sort","Top-Down","Bottom-Up");
	profiler.showReport();

	//_getch();
	return 0;
}