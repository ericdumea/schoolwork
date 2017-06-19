#include <conio.h>
#include <stdlib.h>
#include <stdio.h>
#include <queue>
#include <iostream>
#include "Profiler.h"
#include <time.h>
#define MAX_SIZE 10001
#define MAX_SIZE_C 10001
#define WHITE 0
#define GREY 1
#define BLACK 2

using namespace std;

Profiler profiler("profiler");

typedef struct Node {
	int key;
	int color; // 0 white, 1 grey, 2 black
	int cnr;
	int d;
	Node** c;
	Node* next;
}Node;

int size = 0;

//typedef struct Vertex {
//	Node* head;
//}Vertex;

typedef struct Graph {
	Node** v;
	int V,E;
}Graph;


typedef struct QNode
{
	struct Node* key;
	struct QNode *next;
}QNode;

typedef struct Q {
	QNode* head;
	QNode* tail;
}Q;


QNode* newNode(struct Node* key)
{
	QNode *temp = (QNode*)malloc(sizeof(QNode));
	temp->key = key;
	temp->next = NULL;
	return temp;
}

Node* createNode(int key);
Graph* createGraph(int V, int E);

bool isEmpty(Q* q, int size) {
	return (q->head == NULL);
}

void enqueue(Q* q, Node* k, int size) {
	QNode* x = newNode(k);
	profiler.countOperation("BFS", size, 1);
	if (q->tail == NULL) {
		profiler.countOperation("BFS", size, 1);
		q->head = q->tail = x;
		return;

	}
	profiler.countOperation("BFS", size, 1);
	q->tail->next = x;
	q->tail = x;
}
QNode* dequeue(Q* q, int size) {
	profiler.countOperation("BFS", size, 1);
	if (q->head == NULL) {
		return NULL;
	}
	QNode* t = q->head;
	profiler.countOperation("BFS", size, 1);
	q->head = q->head->next;
	profiler.countOperation("BFS", size, 1);
	if (q->head == NULL) {
		q->tail = NULL;
	}
	return t;
}

Graph* generateGraph(int v, int e) {
	Graph* g = createGraph(v,e);
	for (int i = 0; i < v; i++) {
			g->v[i] = createNode(i+1);
	}

	for (int i = 0; i < e; i++) {
		int y = rand() % v;
		int x = rand() % v;
		while (x == y) { // if there are equal, no duplicates.
			y = rand() % v;
			x = rand() % v;
		}

		if (g->v[x]->cnr == 0) {
			g->v[x]->c[g->v[x]->cnr] = g->v[y];
			g->v[x]->cnr++;
		}
		else {
			int ok;
			do {
				ok = 0; //ok==1 if found
				for (int j = 0; j < g->v[x]->cnr; j++) {
					if (g->v[x]->c[j]->key == y + 1) {
						ok = 1;
					}
				}
				if (ok == 0) {
					g->v[x]->c[g->v[x]->cnr] = g->v[y];
					g->v[x]->cnr++;	
				}
				else {
					y = rand() % v;
					x = rand() % v;
					while (x == y) {
						y = rand() % v;
						x = rand() % v;
					}
				}
			} while (ok == 1);
		}

	}
	return g;
}

Graph* createGraph(int V, int E) {
	Graph* g = (Graph*)malloc(sizeof(Graph));
	g->V = V;
	g->E = E;
	g->v = (Node**)malloc(V*sizeof(Node*));
	for (int i = 0; i < V; i++) {
		g->v[i] = (Node*)malloc(sizeof(Node));
	}
	return g;
}


Node* createNode(int key) {
	Node* temp = (Node*)malloc(sizeof(Node));
	temp->color = 0;
	temp->key = key;
	temp->color = WHITE;
	temp->d = INT_MAX;
	temp->cnr = 0;
	temp->c = (Node**)malloc(sizeof(Node*)*MAX_SIZE_C);
	return temp;
}

Q* createQ(int size) {
	Q* q = (Q*)malloc(sizeof(Q));
	profiler.countOperation("BFS", size, 1);
	q->tail = NULL;
	q->head = NULL;
	return q;
}

//void BFS(Graph* g, Node* s, Q* q) {
//	s->color = GREY;
//	s->d = 0;
//	s->pred = NULL;
//	Node* u;
//	Node* v;
//	queue<int> qq;
//	qq.push(s->key);
//	cout << s->key << "-> ";
//	//enqueue(q, s); //g[s]->head
//	while(!qq.empty()){
//		int x = qq.front();
//		qq.pop();
//		//cout << "asta e x: " << x << endl;
//		u = g->v[x]->head;
//		cout << u->key << " ";
//		//v = u->head;
//		while (u->next != NULL) {
//			v = u->next;
//			if (v->color == WHITE) {
//				v->color = GREY;
//				v->d = u->d + 1;
//				v->pred = u;
//				/*if (g->v[v->key]->head->next != NULL) {
//					cout << g->v[v->key]->head->next->key << endl;
//				}*/
//				qq.push(v->key);
//				//cout << v->key << " ";
//				//enqueue(q, g->v[v->key]->head);
//			}
//			u = u->next;
//		}
//		u->color = BLACK;
//	}
//}

void bfs( Node* start, Q* q, int size)
{
	QNode*u;
	start->d = 0;
	start->color = GREY;
	enqueue(q, start, size);
	while (isEmpty(q, size) != 1) {
		u = dequeue(q, size);
		printf("%d\t", u->key->key);
		int i = 0;
		while (i< u->key->cnr) {
			Node* temp = u->key->c[i];
			if (temp->color == WHITE) {
				profiler.countOperation("BFS", size, 2);
				temp->color = GREY;
				enqueue(q, temp, size);	
			}
			i++;
		}
		u->key->color = BLACK;
	}

}



int main() {
	srand(time(NULL));
	Graph* graph = generateGraph(6,10);
	Q* q = createQ(0);
	for (int i = 0; i <6; i++) {
		printf("%d->", i + 1);
		for (int j = 0; j < graph->v[i]->cnr; j++) {
			printf("%d ", graph->v[i]->c[j]->key);
		}
		printf("\n");
	}
	cout << endl << endl;
	printf("BFS\n");
	bfs(graph->v[0], q, 0);

	/*int v = 100;
	for (int i = 1000; i < 5000; i = i + 100) {
		struct	Q* q = createQ(size);
		struct Graph* graph = generateGraph(v, i);
		printf("%d  %d\n", v, i);
		bfs(graph->v[0], q, i);
	}*/
	
	//profiler.createGroup("Operations", "BFS");
	//profiler.showReport();

	_getch();
	return 0;
}
