/*
You are required to implement correctly and efficiently an O(nlogk) method for merging k
sorted sequences?, where n is the total number of elements. (Hint: use a heap, see seminar no. 2
notes).
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <conio.h>
#include "Profiler.h"

#define MAX_SIZE 10001
#define VECT_SIZE 1024

Profiler profiler("demo");



typedef struct node {
	int list_index;
	int key;
	node* next;
}node;

typedef struct sll {
	node* first;
	node* last;
	sll() {
		first = NULL;
		last = NULL;
	}
}sll;

typedef struct Heap {
	int key, list_index;
}Heap;

void insertBeforeFirst(node* pNode, sll& lista);
void addNode(int i, int key, sll& l);
void generate_lists(int n, int k, int a[]);

using namespace std;

void lshow(sll lista)
{
	node* pWalker = lista.first;
	while (pWalker != NULL) {
		printf("%d ", pWalker->key);
		pWalker = pWalker->next;
	}
	cout << endl;
}

void addNode(int i, int key, sll& l) {
	node* pNode = (node*)malloc(sizeof(node));
	pNode->list_index = i;
	pNode->key = key;
	insertBeforeFirst(pNode, l);
}

void insertBeforeFirst(node* pNode, sll& lista)
{
	pNode->next = lista.first;
	lista.first = pNode;
}

void generate_lists(int n, int k, sll x[]) {

	int sizes[MAX_SIZE];
	int sum = 0;
	for (int i = 0; i < k - 1; i++) {
		sizes[i] = rand() % (n / k);
		if (sizes[i] == 0)
			sizes[i] = rand() % (n / k);
		sum += sizes[i];
	}
	sizes[k - 1] = n - sum;

	for (int i = 0; i < k; i++) {
		int a[MAX_SIZE];
		FillRandomArray(a, sizes[i], 0, 50000, true, 2);
		for (int j = 0; j <sizes[i]; j++) {
			addNode(i, a[j], x[i]);
		}
	}

	/*for (int i = 0; i < k; i++) {
		lshow(x[i]);
		cout << endl;
	}*/

}

void heapify(Heap h[], int n, int i, int no) {
	int l, r, smallest;
	Heap aux;
	l = 2 * i + 1;
	r = 2 * i + 2;
	smallest = i;
	if (l < n && h[l].key < h[smallest].key) {
		smallest = l;
	}
	else
		smallest = i;
	if (r < n && h[r].key < h[smallest].key) {
		smallest = r;
	}

	if (smallest != i) {
		aux = h[i];
		h[i] = h[smallest];
		h[smallest] = aux;
		heapify(h, n, smallest, no);
	}
}

void buildHeap(Heap h[], int k, int n) {
	for (int i = floor(k / 2) ; i >= 0; i--) {
		heapify(h, k, i, n);
	}
}


bool isEmpty(sll x) {
	if (x.first == NULL && x.last == NULL)
		return true;
	return false;
}

sll firsts(sll x[], int k) {
	sll firsts;
	for (int i = 0; i < k; i++) {
		if (x[i].first != NULL) {
			
			addNode(i, x[i].first->key, firsts);
			x[i].first = x[i].first->next;
		}
		else
			continue;
	}
	//cout << "Listele: \n";
	lshow(firsts);
	return firsts;
}

void pv(int a[], int n) {
	for (int i = 0; i < n; i++) {
		cout << a[i] << " ";
	}
	cout << endl;
}

 
void merge(sll x[],int n,int k,Heap h[]) {
	int a[MAX_SIZE];
	int m = 0;
	//sll f = firsts(x, k);
	int hs;
	for (int i = 0; i < k; i++) {
		profiler.countOperation("k=10", n, 2);
		h[i].key = x[i].first->key;
		h[i].list_index = i;
	}
	
	buildHeap(h,k, n);
	hs = n;
	while (k > 0) {
		//h[0].key = x[h[0].list_index].first->key;
		a[m++] = h[0].key;
		profiler.countOperation("k=10", n, 1);
		x[h[0].list_index].first = x[h[0].list_index].first->next;
		profiler.countOperation("k=10", n, 2);
		if(x[h[0].list_index].first==NULL){
			profiler.countOperation("k=10", n,n-1);
			for (int i = 0; i < k - 1; i++) {
				h[i] = h[i + 1];
			}
			k--;
		}
		else {
			profiler.countOperation("k=10", n, 1);
			h[0].key = x[h[0].list_index].first->key;
		}
		
		buildHeap(h, k, n);
		profiler.countOperation("k=10", n, 1);
		
	}
	m--;
	//pv(a, m);
}

void merge100(sll x[], int n, int k, Heap h[]) {
	int a[MAX_SIZE];
	int m = 0;
	//sll f = firsts(x, k);
	int hs;
	for (int i = 0; i < k; i++) {
		profiler.countOperation("k=100", n, 2);
		h[i].key = x[i].first->key;
		h[i].list_index = i;
	}

	buildHeap(h, k, n);
	hs = n;
	while (k > 0) {
		//h[0].key = x[h[0].list_index].first->key;
		a[m++] = h[0].key;
		profiler.countOperation("k=100", n, 1);
		x[h[0].list_index].first = x[h[0].list_index].first->next;
		profiler.countOperation("k=100", n, 2);
		if (x[h[0].list_index].first == NULL) {
			profiler.countOperation("k=100", n, n - 1);
			for (int i = 0; i < k - 1; i++) {
				h[i] = h[i + 1];
			}
			k--;
		}
		else {
			profiler.countOperation("k=100", n, 1);
			h[0].key = x[h[0].list_index].first->key;
		}

		buildHeap(h, k, n);
		profiler.countOperation("k=100", n, 1);

	}
	m--;
	//pv(a, m);
}

void merge5(sll x[], int n, int k, Heap h[]) {
	int a[MAX_SIZE];
	int m = 0;
	//sll f = firsts(x, k);
	int hs;
	for (int i = 0; i < k; i++) {
		profiler.countOperation("k=5", n, 2);
		h[i].key = x[i].first->key;
		h[i].list_index = i;
	}

	buildHeap(h, k, n);
	hs = n;
	while (k > 0) {
		//h[0].key = x[h[0].list_index].first->key;
		a[m++] = h[0].key;
		profiler.countOperation("k=5", n, 1);
		x[h[0].list_index].first = x[h[0].list_index].first->next;
		profiler.countOperation("k=5", n, 2);
		if (x[h[0].list_index].first == NULL) {
			profiler.countOperation("k=5", n, n - 1);
			for (int i = 0; i < k - 1; i++) {
				h[i] = h[i + 1];
			}
			k--;
		}
		else {
			profiler.countOperation("k=5", n, 1);
			h[0].key = x[h[0].list_index].first->key;
		}

		buildHeap(h, k, n);
		profiler.countOperation("k=5", n, 1);

	}
	m--;
	//pv(a, m);
}

void mergek(sll x[], int n, int k, Heap h[]) {
	int a[MAX_SIZE];
	int m = 0;
	//sll f = firsts(x, k);
	int hs;
	for (int i = 0; i < k; i++) {
		profiler.countOperation("n=10000", n, 2);
		h[i].key = x[i].first->key;
		h[i].list_index = i;
	}

	buildHeap(h, k, n);
	hs = n;
	while (k > 0) {
		//h[0].key = x[h[0].list_index].first->key;
		a[m++] = h[0].key;
		profiler.countOperation("n=10000", n, 1);
		x[h[0].list_index].first = x[h[0].list_index].first->next;
		profiler.countOperation("n=10000", n, 2);
		if (x[h[0].list_index].first == NULL) {
			profiler.countOperation("n=10000", n, n - 1);
			for (int i = 0; i < k - 1; i++) {
				h[i] = h[i + 1];
			}
			k--;
		}
		else {
			profiler.countOperation("n=10000", n, 1);
			h[0].key = x[h[0].list_index].first->key;
		}

		buildHeap(h, k, n);
		profiler.countOperation("k=5", n, 1);

	}
	m--;
	//pv(a, m);
}

int main() {
	sll x[MAX_SIZE];
	sll f;
	sll y[2];
	int n, k;
	int a[] = { 12,34,55,73,12,3,2,5,6,7,1,4,6,1,2,5,2,22,5,3,22,12,53,51,32,44,22,33,5,2,4,5,6,8,5,3,2,5,7,9,9,6,5,4,3,2,5,4,3,66,4,7 };
	n = 200;
	k = 10;
	//generate_lists(n, k, x);

	for (int n = 100; n <= 10000; n += 100) {
		k = 10;
		generate_lists(n, k, x);
		Heap h1[MAX_SIZE*5];
		merge(x, n, k, h1);
		//k = 100;
		//generate_lists(n, k, x);
		//merge100(x, n, k, h);
		k = 5;
		Heap h2[MAX_SIZE*5];
		generate_lists(n, k, x);
		merge5(x, n, k, h2);


	}

	/*n = 1000;
	for (k = 10; k <= 250; k += 10) {
		generate_lists(n, k, x);
		n = 1000;
		Heap h3[100000];
		mergek(x, n, k, h3);
	}*/

	//Heap h1[1000];
	//merge(x, n, k,h1);
	profiler.createGroup("n-variabil", "k=10", "k=5");
	profiler.showReport();
	_getch();

	return 0;

}