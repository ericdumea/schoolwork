#include <iostream>
#include "Profiler.h"
#include <stdio.h>
#include <conio.h>
#define MAX_SIZE_V 10001

using namespace std;

Profiler profiler("demo");

void bsBest(int a[], int n) {
	int aux;
	for (int i = 1; i <= n; i++)
		for (int j = i; j <= n; j++) {
			profiler.countOperation("C-BS-B", n, 1);
			profiler.countOperation("AC-BS-B", n, 1);
			if (a[i] > a[j]) {
				aux = a[i];
				a[i] = a[j];
				a[j] = aux;
				profiler.countOperation("AC-BS-B", n, 3);
				profiler.countOperation("A-BS-B", n, 3);
			}
		}
}

void bsWorst(int a[], int n) {
	int aux;
	for (int i = 1; i <= n; i++)
		for (int j = i; j <= n; j++) {
			profiler.countOperation("C-BS-w", n, 1);
			profiler.countOperation("AC-BS-w", n, 1);
			if (a[i] > a[j]) {
				aux = a[i];
				a[i] = a[j];
				a[j] = aux;
				profiler.countOperation("AC-BS-w", n, 3);
				profiler.countOperation("A-BS-w", n, 3);
			}
		}
}

void showVector(int a[],int n) {
	for (int i = 1; i <= n; i++)
		cout << a[i] << " ";
	cout << endl;
}

void bs(int a[], int n) {
	int aux;
	for (int i = 1; i <= n; i++)
		for (int j = i; j <= n; j++) {
			profiler.countOperation("C-BS-avg", n, 1);
			profiler.countOperation("AC-BS-avg", n, 1);
			if (a[i] > a[j]) {
				aux = a[i];
				a[i] = a[j];
				a[j] = aux;
				profiler.countOperation("AC-BS-avg", n, 3);
				profiler.countOperation("A-BS-avg", n, 3);
			}
		}
}

void insertion(int a[], int n) {
	int k,aux;
	for (int i = 1; i <= n; i++) {
		aux = a[i];
		profiler.countOperation("AC-IS-avg", n, 1); 
		profiler.countOperation("A-IS-avg", n, 1);
		k = i - 1;
		while (aux < a[k] && k>=0) {
			profiler.countOperation("AC-IS-avg", n, 2);
			profiler.countOperation("C-IS-avg", n, 1);
			profiler.countOperation("A-IS-avg", n, 1);
			a[k + 1] = a[k];
			k--;
		}
		profiler.countOperation("AC-IS-avg", n, 1);
		profiler.countOperation("A-IS-avg", n, 1);
		a[k + 1] = aux;
	}
}

void insertionB(int a[], int n) {
	int k, aux;
	for (int i = 1; i <= n; i++) {
		aux = a[i];
		profiler.countOperation("AC-IS-B", n, 1);
		profiler.countOperation("A-IS-B", n, 1);
		k = i - 1;
		while (aux < a[k] && k >= 0) {
			profiler.countOperation("AC-IS-B", n, 2);
			profiler.countOperation("C-IS-B", n, 1);
			profiler.countOperation("A-IS-B", n, 1);
			a[k + 1] = a[k];
			k--;
		}
		profiler.countOperation("AC-IS-B", n, 1);
		profiler.countOperation("A-IS-B", n, 1);
		a[k + 1] = aux;
	}
}

void insertionW(int a[], int n) {
	int k, aux;
	for (int i = 1; i <= n; i++) {
		aux = a[i];
		profiler.countOperation("AC-IS-w", n, 1);
		profiler.countOperation("A-IS-w", n, 1);
		k = i - 1;
		while (aux < a[k] && k >= 0) {
			profiler.countOperation("AC-IS-w", n, 2);
			profiler.countOperation("C-IS-w", n, 1);
			profiler.countOperation("A-IS-w", n, 1);
			a[k + 1] = a[k];
			k--;
		}
		profiler.countOperation("AC-IS-w", n, 1);
		profiler.countOperation("A-IS-w", n, 1);
		a[k + 1] = aux;
	}
}



void selection(int a[], int n) {
	int min, aux;
	for (int i = 1; i < n; i++) {
		min = i;
		for (int j = i + 1; j <= n; j++) {
			profiler.countOperation("AC-SS-avg", n, 1);
			profiler.countOperation("C-SS-avg", n, 1);
			if (a[j] < a[min])
				min = j;
		}
		if (i != min) {
			aux = a[i];
			a[i] = a[min];
			a[min] = aux;
			profiler.countOperation("A-SS-avg", n, 3);
			profiler.countOperation("AC-SS-avg", n, 3);
		}
	}
}

void selectionB(int a[], int n) {
	int min, aux;
	for (int i = 1; i < n; i++) {
		min = i;
		for (int j = i + 1; j <= n; j++) {
			profiler.countOperation("AC-SS-B", n, 1);
			profiler.countOperation("C-SS-B", n, 1);
			if (a[j] < a[min])
				min = j;
		}
		if (i != min) {
			aux = a[i];
			a[i] = a[min];
			a[min] = aux;
			profiler.countOperation("A-SS-B", n, 3);
			profiler.countOperation("AC-SS-B", n, 3);
		}
	}
}

void selectionW(int a[], int n) {
	int min, aux;
	for (int i = 1; i < n; i++) {
		min = i;
		for (int j = i + 1; j <= n; j++) {
			profiler.countOperation("AC-SS-w", n, 1);
			profiler.countOperation("C-SS-w", n, 1);
			if (a[j] < a[min])
				min = j;
		}
		if (i != min) {
			aux = a[i];
			a[i] = a[min];
			a[min] = aux;
			profiler.countOperation("A-SS-w", n, 3);
			profiler.countOperation("AC-SS-w", n, 3);
		}
	}
}

int main() {

	int n;
	int a[MAX_SIZE_V];
	int b[] = { NULL,15,23,12,11,6,3,16,15,87,99 };
	int c[] = { NULL,15,23,12,11,6,3,16,15,87,99 };
	int d[] = { NULL,15,23,12,11,6,3,16,15,87,99 };
	int aw[MAX_SIZE_V];
	insertion(b, 10);
	showVector(b, 10);
	selection(c, 10);
	showVector(b, 10);
	bs(d, 10);
	showVector(b, 10);


	//for (int c = 1; c <= 3; c++) { //c=1 -  best case,  c=2 worst case,  c=3 avg
	//	for (n = 100; n <= 10000; n += 100) {
	//		if (c == 1) { //bestCase;
	//			FillRandomArray(a, n, 1, 50000, false, 1);
	//			bsBest(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 1);
	//			selectionB(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 1);
	//			insertionB(a, n);
	//		}
	//		if (c == 2) {
	//			FillRandomArray(aw, n, 1, 50000, false, 2);
	//			bsWorst(aw, n);
	//			FillRandomArray(aw, n, 1, 50000, false, 2);
	//			selectionW(aw, n);
	//			FillRandomArray(aw, n, 1, 50000, false, 2);
	//			insertionW(aw, n);
	//		}
	//		if (c == 3) {
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			bs(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			bs(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			bs(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			bs(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			bs(a, n);

	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			insertion(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			insertion(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			insertion(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			insertion(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			insertion(a, n);

	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			selection(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			selection(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			selection(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			selection(a, n);
	//			FillRandomArray(a, n, 1, 50000, false, 0);
	//			selection(a, n);
	//			
	//		}
	//	}
	//}

	////Average-Worst-Best BubbleSort Assignments+Comparisons
	//profiler.createGroup("A-W-B-BS-AC","AC-BS-B","AC-BS-w","AC-BS-avg" );
	////Average-Worst-Best BubbleSort Assignments
	//profiler.createGroup("A-W-B-BS-A", "A-BS-B", "A-BS-w", "A-BS-avg");
	////Average-Worst-Best BubbleSort Comparisons
	//profiler.createGroup("A-W-B-BS-C", "C-BS-B", "C-BS-w", "C-BS-avg");

	////Average-Worst-Best InsertionSort Assignments+Comparisons
	//profiler.createGroup("A-W-B-IS-AC", "AC-IS-B", "AC-IS-w", "AC-IS-avg");
	////Average-Worst-Best InsertionSort Assignments
	//profiler.createGroup("A-W-B-IS-A", "A-IS-B", "A-IS-w", "A-IS-avg");
	////Average-Worst-Best InsertionSort Comparisons
	//profiler.createGroup("A-W-B-IS-C", "C-IS-B", "C-IS-w", "C-IS-avg");
	//
	////Average-Worst-Best SelectionSort Assignments+Comparisons
	//profiler.createGroup("A-W-B-SS-AC", "AC-SS-B", "AC-SS-w", "AC-SS-avg");
	////Average-Worst-Best SelectionSort Assignments
	//profiler.createGroup("A-W-B-SS-A", "A-SS-B", "A-SS-w", "A-SS-avg");
	////Average-Worst-Best SelectionSort Comparisons
	//profiler.createGroup("A-W-B-SS-C", "C-SS-B", "C-SS-w", "C-SS-avg");

	//profiler.showReport();
	std::getchar();
	return 0;
}