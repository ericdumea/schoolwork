#include <stdio.h>
#include <conio.h>
#include "Profiler.h"
#include <math.h>
#include <stdlib.h>

#define MAX_SIZE 10000

Profiler profiler("demo");

using namespace std;

int part(int a[], int p, int r, int n);
int rand_part(int a[], int p, int r);
int partW(int a[], int p, int r, int n);

void heapify(int a[], int n, int i, int c) {
	int l = 2 * i; //left
	int r = 2 * i + 1;//right
	int aux, largest;
	profiler.countOperation("Heap-Sort", c, 1);
	if (l <= n && a[l] > a[i]) {
		largest = l;
	}
	else
		largest = i;
	profiler.countOperation("Heap-Sort", c, 1);
	if (r <= n && a[r] > a[largest])
		largest = r;
	if (largest != i) {
		profiler.countOperation("Heap-Sort", c, 3);
		aux = a[i];
		a[i] = a[largest];
		a[largest] = aux;
		heapify(a, n, largest, c);
	}
}

void buildHeapBD(int a[], int n, int c) {
	for (int i = floor(n / 2); i >= 1; i--) {
		heapify(a, n, i, c);
	}
}


void heapSortBU(int a[], int n, int c) {
	buildHeapBD(a, n, c);
	int aux;
	for (int i = n; i >= 2; i--) {
		aux = a[1];
		a[1] = a[i];
		a[i] = aux;
		profiler.countOperation("Heap-Sort", c, 3);
		n--;
		heapify(a, n, 1, c);
	}
}

void quicksort(int a[], int p, int r, int n) {
	if (p < r) {
		int q = part(a, p, r,n);
		quicksort(a, p, q - 1,n);
		quicksort(a, q + 1, r,n);
	}
}

int part(int a[], int p, int r, int n) {
	int x = a[r];
	profiler.countOperation("Quick-Sort", n, 1);
	int i = p - 1;
	for (int j = p; j <= r - 1; j++) {
		profiler.countOperation("Quick-Sort", n, 1);
		if (a[j] <= x) {
			i++;
			profiler.countOperation("Quick-Sort", n, 3);
			int aux = a[i];
			a[i] = a[j];
			a[j] = aux;
		}
	}
	profiler.countOperation("Quick-Sort", n, 3);
	int aux = a[i + 1];
	a[i + 1] = a[r];
	a[r] = aux;
	return i + 1;
}

void quicksortW(int a[], int p, int r, int n) {
	if (p < r) {
		int q = partW(a, p, r, n);
		quicksortW(a, p, q - 1, n);
		quicksortW(a, q + 1, r, n);
	}
}

int partW(int a[], int p, int r, int n) {
	int x = a[r];
	profiler.countOperation("Quick-Sort-W", n, 1);
	int i = p - 1;
	for (int j = p; j <= r - 1; j++) {
		profiler.countOperation("Quick-Sort-W", n, 1);
		if (a[j] <= x) {
			i++;
			profiler.countOperation("Quick-Sort-W", n, 3);
			int aux = a[i];
			a[i] = a[j];
			a[j] = aux;
		}
	}
	profiler.countOperation("Quick-Sort-W", n, 3);
	int aux = a[i + 1];
	a[i + 1] = a[r];
	a[r] = aux;
	return i + 1;
}


void printv(int a[], int n) {
	for (int i = 0; i <= n; i++) {
		printf("%d ", a[i]);
	}
	printf("\n");
}

int rand_sel(int a[], int p, int r, int i) {
	if (p == r)
		return a[p];
	int q = rand_part(a, p, r);
	int k = q - p + 1;
	if (i == k)
		return a[q];
	else
		if (i < k)
			return rand_sel(a, p, q - 1, i);
		else
			return(a, q + 1, r, i - k);
}

int rand_part(int a[], int p, int r) {
	int i = (rand() % (r)) + p;
	int aux = a[i];
	a[i] = a[r];
	a[r] = aux;
	return part(a, p, r,r);
}

int main() {

	////int a[MAX_SIZE];
	////int n;

	//int b[] = { 33, 23, 14,56,78,12,1,4,3,2 };
	//int n1 = 9;
	//int x = rand_sel(b, 0, n1, 5);
	//quicksort(b, 0, n1,n1);

	//printv(b, n1);
	//printf("\n");
	//printf("%d", x);

				//chart part

	int a[MAX_SIZE], b[MAX_SIZE],c[MAX_SIZE];
	
	for (int n = 100; n <= 10000; n += 100) {
		for (int caz = 1; caz <= 5; caz++) {
			FillRandomArray(a, n, 1, 10000, true, 0);
			for (int j = 0; j < n; j++)
				b[j] = a[j];
			heapSortBU(a, n, n);
			quicksort(b, 0, n, n);
		}
		FillRandomArray(c, n, 1, 10000, true, 2);
		quicksortW(c, 0, n, n);
	}
	profiler.createGroup("Sorting","Heap-Sort","Quick-Sort");
	profiler.createGroup("Avg-W", "Quick-Sort-W", "Quick-Sort");
	profiler.showReport();

	//_getch();
	return 0;

}