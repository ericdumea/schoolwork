#include <iostream>
#include <list>
#include <vector>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "Profiler.h"

#define WHITE 0
#define GRAY 1
#define BLACK 2


using namespace std;

Profiler profiler = Profiler("DFS");

int ops = 0;

typedef struct Node {
	int key;
	Node *next;
}Node;

typedef struct Vertex {
	int key;
	int color;
	int d;
	int f;
	Node *first;
	Vertex *pred;
}Vertex;

//typedef struct Q {
//	std::list<Vertex*> list;
//}Q;

//void push(Q *q, Vertex *v)
//{
//	q->list.push_back(v);
//}
//
//Vertex* pop(Q *q)
//{
//	Vertex *v = q->list.back;
//	q->list.pop_back;
//	return v;
//}

std::list<Vertex*> l;

void printGraph(std::vector<Vertex*> g)
{
	int size = g.size();
	for (int i = 0; i < size; i++) {
		printf("%d", g[i]->key);
		Node *p = g[i]->first;
		while (p != NULL) {
			printf("->%d", p->key);
			p = p->next;
		}
		printf("\n");
	}
}
std::vector<Vertex*> createG(int V, int E);

std::vector<Vertex*> genRandomG(int V, int E) {
	std::vector<Vertex*> g =createG(V, E);
	int **m = (int**)calloc(V, sizeof(int*));
	for (int i = 0; i < V; i++) {
		m[i] = (int*)calloc(V, sizeof(int));
	}

	int row = rand() % V;
	int col = rand() % V;
	for (int i = 0; i <	E; i++) {
		while ((m[row][col] == 1) || (row == col)) {
			row = rand() % V;
			col = rand() % V;
		}
		m[row][col] = 1;
		if (g[row]->first == NULL) {
			g[row]->first = (Node*)malloc(sizeof(Node));
			g[row]->first->key = g[col]->key;
			g[row]->first->next = NULL;
		}
		else {
			Node *p = g[row]->first;
			while (p->next != NULL) {
				p = p->next;
			}
			p->next = (Node*)malloc(sizeof(Node));
			p->next->key = g[col]->key;
			p->next->next = NULL;
		}
	}
	return g;
}

std::vector<Vertex*> createG(int V, int E) {
	std::vector<Vertex*> g;
	if (E > V * (V - 1)) {
		return g;
	}
	for (int i = 0; i < V; i++) {
		Vertex *u = (Vertex*)malloc(sizeof(Vertex));
		u->key = i;
		u->first = NULL;
		u->pred = NULL;
		u->color = WHITE;
		g.push_back(u);
	}
	return g;
}

int tiempo = 0;

void dfs_visit(std::vector<Vertex*> g, Vertex* u) {
	tiempo++;
	u->d = tiempo;
	u->color = GRAY;
	ops += 2;
	Node* p = NULL;
	p = u->first;
	printf("%d ", u->key);
	ops += 2;
	while (p != NULL) {
		//printf("%d ", p->key);
		Vertex *v = g[p->key];
		ops += 2;
		if (v->color == WHITE) {
			v->pred = u;
			ops++;
		//	printf("%d ",v->key);
			dfs_visit(g, v);
		}
		p = p->next;
	}
	u->color = BLACK;
	//printf("%d ", u->key);
	tiempo++;
	u->f = tiempo;
	l.push_front(u);
	ops += 2;
}

void dfs(std::vector<Vertex*> g) {
	for (size_t i = 0; i < g.size(); i++) {
		if(g[i]->color == WHITE)
			dfs_visit(g, g[i]);
	}
}

void topo(std::vector<Vertex*> g) {
	dfs(g);
	cout << endl << endl;
	cout <<endl<< "Topological:" << endl;
	int size = l.size();
	for (int i = 0; i < size; i++) {
		printf("%d ", l.front()->key);
		l.pop_front();
	}
}

void prettyPrint(std::vector<Vertex*> g, Vertex *p, int parents) {
	int size = g.size();
	for (int i = 0; i < size; i++) {
		if (g[i]->pred == p) {
			printf("%*s%d\n", parents * 8, " ", g[i]->key);
			prettyPrint(g, g[i], parents + 1);
		}
	}
}

int main() {
	// small sample: 

	srand(time(NULL));
	std::vector<Vertex*> g = genRandomG(6, 6);
	printGraph(g);
	printf("\n");
	int o;
	//dfs(g);
	topo(g);
	
	//prettyPrint(g,NULL, 0);
	getchar();


	//demo



	/*int V = 100;
	for (int E = 1000; E <= 5000; E += 100) {
		ops = 0;
		for (int i = 0; i < 5; i++) {
			std::vector<Vertex*> g = genRandomG(V,E);
			dfs(g);
		}
		profiler.countOperation("E-variable", E, ops / 5);
	}
	int E = 9000;
	for (int V = 100; V <= 200; V += 10) {
		 ops = 0;
		for (int i = 0; i < 5; i++) {
			std::vector<Vertex*> g = genRandomG(V, E);
			dfs(g);
		}
		profiler.countOperation("V-variable", V, ops / 5);
	}
	profiler.showReport();*/
	return 0;
}