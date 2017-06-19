#include <iostream>
#include <stdio.h>
#include <conio.h>
#include <time.h>
#include "Profiler.h"

#define MAX_SIZE 10001


using namespace std;

Profiler p("demo");


typedef struct Node {
	int key;
	Node* p;
	int rank;
}Node;

typedef struct Edge {
	Node* u;
	Node* v;
	int w;
}Edge;

typedef struct G {
	Edge* edges[MAX_SIZE];
	Node* nodes[MAX_SIZE*4];
	int V = 0;
	int E = 0;
}G;

void sort_edges(G* g);
void printEdges(Edge* e[], int n);

Node* create_node(int key) {
	Node* x = (Node*)malloc(sizeof(Node));
	x->key = key;
	x->rank = 0;
	x->p = NULL;
	return x;
}

void make_set(Node* x, int n) {
	x->p = x;
	x->rank = 0;
	p.countOperation("Kruskal", n, 2);
}

void set_link(Node* x, Node* y, int n) {
	p.countOperation("Kruskal", n, 2);
	if (x->rank > y->rank) {
		p.countOperation("Kruskal", n, 1);
		y->p = x;
	}
	else {
		p.countOperation("Kruskal", n, 2);
		x->p = y;
		if (x->rank == y->rank) {
			p.countOperation("Kruskal", n, 1);
			y->rank = y->rank + 1;
		}
	}
}

Node* find_set(Node* x, int n) {
	p.countOperation("Kruskal", n, 1);
	if (x != x->p)
		x->p = find_set(x->p,n);
	return x->p;
}

void set_union(Node* x, Node* y, int n) {
	set_link(find_set(x,n), find_set(y,n),n);
}

int k = 0;

Edge** kruskal(G* g,int n) {
	Edge** A = (Edge**)malloc(sizeof(Edge*)*g->E);
	for (int i = 0; i < g->E; i++) {
		A[i] = (Edge*)malloc(sizeof(Edge));
	}
	k = 0;

	for (int i = 0; i < g->V; i++) {
		make_set(g->nodes[i],n);
	}
	sort_edges(g);
	for(int i=0;i<g->E;i++)
		if (find_set(g->edges[i]->u,n) != find_set(g->edges[i]->v,n)) {
			A[k++] = g->edges[i];
			set_union(g->edges[i]->u, g->edges[i]->v, n);
		}
	printEdges(A, k);
	return A;
}

G* create_graph(int V, int E) {
	G* graph = (G*)malloc(sizeof(G));
	graph->V = V;
	graph->E = E;
	
	for (int i = 0; i < V; i++) {
		graph->nodes[i]=create_node(i);
	}

	for (int i = 0; i < E; i++) {
		graph->edges[i] = (Edge*)malloc(sizeof(Edge));
	}
	return graph;
}

G* build_random_graph(int V) {

	G* g = create_graph(V, 4*V);
	srand(time(NULL));

	int edgenr = 0;

	for (int i = 0; i < V; i++) {
		g->edges[edgenr]->u = g->nodes[i];
		g->edges[edgenr]->v = g->nodes[i + 1];
		g->edges[edgenr]->w = rand() % 100 + 1;

		if (i == V - 1) {
			g->edges[edgenr]->u = g->nodes[i];
			g->edges[edgenr]->v = g->nodes[0];
			g->edges[edgenr]->w = rand() % 100 + 1;
		}
		edgenr++;
	}

	while (edgenr < 4 * V) {
		g->edges[edgenr]->u = g->nodes[rand() % V];
		g->edges[edgenr]->v = g->nodes[rand() % V];
		g->edges[edgenr]->w = rand() % 100 + 1;
		edgenr++;
	}

	return g;
}

void sort_edges(G* g) {
	Edge* aux;
	for (int i = 0; i < g->E-1; i++) {
		for(int j=i;j<g->E;j++)
			if (g->edges[i]->w > g->edges[j]->w) {
				aux = g->edges[i];
				g->edges[i] = g->edges[j];
				g->edges[j] = aux;
			}
	}
}

void printNode(Node* x) {
	printf("key: %d, parent: %d\n", x->key, x->p->key);
}



G* build_small_graph() {
	G* graph = create_graph(4,5);

	// add edge 0-1
	graph->edges[0]->u = graph->nodes[0];
	graph->edges[0]->v = graph->nodes[1];
	graph->edges[0]->w = 10;

	// add edge 0-2
	graph->edges[1]->u = graph->nodes[0];
	graph->edges[1]->v = graph->nodes[2];
	graph->edges[1]->w = 6;

	// add edge 0-3
	graph->edges[2]->u = graph->nodes[0];
	graph->edges[2]->v = graph->nodes[3];
	graph->edges[2]->w = 5;

	// add edge 1-3
	graph->edges[3]->u = graph->nodes[1];
	graph->edges[3]->v = graph->nodes[3];
	graph->edges[3]->w = 15;

	// add edge 2-3
	graph->edges[4]->u = graph->nodes[2];
	graph->edges[4]->v = graph->nodes[3];
	graph->edges[4]->w = 4;

	return graph;
}

void printg(G* g,int E) {
	for (int i = 0; i < E; i++) {
		printf("%d -- %d -> %d\n", g->edges[i]->u->key, g->edges[i]->v->key, g->edges[i]->w);
	}

}

void printEdges(Edge* e[], int n) {
	for (int i = 0; i < n; i++) {
		printf("%d -- %d -> %d\n", e[i]->u->key, e[i]->v->key, e[i]->w);
	}
}

int main() {
	int a[] = { 0,1,2,3,4,5,6,7 };
	
	Node* nodes[300];
	for (int i = 0; i < 300; i++) {
		nodes[i] = (Node*)malloc( sizeof(Node) );
	}

	for (int i = 0; i <= 7; i++) {
		nodes[i] = create_node(a[i]);
		make_set(nodes[i],0);
	}

	for (int i = 0; i < 7; i++) {
		printNode(nodes[i]);
	}

	set_union(nodes[1], nodes[4],0);
	set_union(nodes[5], nodes[3],0);
	cout << endl;
	for (int i = 0; i < 7; i++) {
		printNode(nodes[i]);
	}
	

	G *g1 = build_small_graph();

	cout << endl << endl << "SMALL INPUT\n\n";
	printg(g1, 5);
	cout << "Kruskal:\n";
	kruskal(g1,0);
	
	/*for (int n = 100; n <= 10000; n += 100) {
		G *g=build_random_graph(n);
		kruskal(g,n);
		free(g);
	}


	p.showReport();*/

	_getch();
	return 0;
}