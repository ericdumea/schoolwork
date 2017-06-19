#include <iostream>
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include "Profiler.h"

#define MAX_SIZE 10001

using namespace std;

Profiler p("demo");

typedef struct Node {
	int key;
	int size;
	Node* left;
	Node* right;
}Node;

struct Node* build_node(int key);
Node* build_tree(int a[], int l, int r, int n);
void printBST(struct Node* node);
Node* os_select(Node* root, int i, int n);

Node* build_tree(int a[],int l, int r, int n) {
	int m = (l + r )/ 2;
	if (l > r) {
		return NULL;
	}
	Node *root = build_node(a[m]);

	
	root->left = build_tree(a, l, m - 1,n);
	root->right = build_tree(a, m+ 1, r,n);
	root->size = r - l +1;

	return root;
	
}

Node* os_select(Node* root, int i, int n) {
	int r = 0;
	p.countOperation("os-select", n, 2);
	if (root->left != NULL)
		r = root->left->size + 1;
	else
		r = 1;
	//cout << endl << "root size: " << root->size << "root key: " << root->key << "R: " << r;
	if (r==i )
		return root;
	else
		if (i < r)
			if (root->left != NULL) {
				p.countOperation("os-select", n, 1);
				return os_select(root->left, i, n);
			}
			else;
		else
			//if(i > r)
			if (root->right != NULL) {
				p.countOperation("os-select", n, 1);
				return os_select(root->right, i - r, n);
			}
	//return NULL;
}

void printBST(struct Node* node) //preorder print
{
	if (node == NULL)
		return;
	printf("%d ", node->key);
	printBST(node->left);
	printBST(node->right);
}

struct Node* build_node(int key)
{
	Node* node = (Node*)malloc(sizeof(Node));
	node->key = key;
	node->left = NULL;
	node->right = NULL;

	return node;
}

Node* FindMin(Node* x, int n) {
	p.countOperation("os-delete", n, 1);
	if (x->left == NULL)
		return x;
	else
		return FindMin(x->left,n);
}

Node* Delete( Node *root, int key, int n) {
	p.countOperation("os-delete", n, 1);
	if (root == NULL) {
		return NULL;
	}
	if (key < root->key) {  
		p.countOperation("os-delete", n, 3);
		root->size--;
		root->left = Delete(root->left, key,n);
	}
	else if (key > root->key) { 
		p.countOperation("os-delete", n, 3);
		root->size--;
		root->right = Delete(root->right, key,n);
	}
	else {
		if (root->left == NULL && root->right == NULL) {
			p.countOperation("os-delete", n, 3);
			free(root); 
			root = NULL;
		}
		else if (root->left == NULL) {
			 Node *temp = root; 
			 p.countOperation("os-delete", n, 2);
			root = root->right;
			free (temp);
		}
		else if (root->right == NULL) {
			Node *temp = root;
			p.countOperation("os-delete", n, 2);
			root = root->left;
			free (temp);
		}
		else {
			struct Node *temp = FindMin(root->right,n); 
			p.countOperation("os-delete", n, 3);
			root->key = temp->key; 
			root->size--;
			root->right = Delete(root->right, temp->key,n);
		}
	}
	return root; 
}

void pretty_print(Node *root, int space)
{
	if (root == NULL)
		return;
	space += 7;
	pretty_print(root->right, space);
	printf("\n");
	for (int i = 7; i < space; i++)
		printf(" ");
	printf("%d,%d\n", root->key, root->size);
	pretty_print(root->left, space);
}

int main() {
	int a[MAX_SIZE];
	int idx[MAX_SIZE];
	int b[] = { 1,2,3,4,5,6,7,8,9,10,11 };
	int n1 = 11;

	Node* root = build_tree(b, 0, n1-1, 0);
	cout << "The tree:" << endl;

	//pretty_print(root, 3);

	/*printBST(root);
	cout << endl;
	for (int i = 1; i <= n1; i++)
	{
		Node *s = os_select(root, i,0);
		cout << " " << s->key;
	}*/

	Delete(root, 6,0);
	//Delete(root, 6, 0);
	cout << endl;

	//printBST(root);
	
	pretty_print(root, 3);

	Node *s = os_select(root, 5,0);
	cout << endl << endl << endl;
	cout << endl << s->key;

	//for (int n = 100; n <= 10000; n += 500) {
	//	for (int i = 0; i < 5; i++) {
	//		FillRandomArray(a, n, 1, 50000, true, 1);
	//		FillRandomArray(idx, n,1, n, true, 0);
	//		Node* root = build_tree(a, 1, n - 1, n);
	//		for (int k = 0; k < n; k++) {
	//			if (idx[k] != -1) {
	//				os_select(root, idx[k], n);
	//				Delete(root, idx[k], n);
	//				idx[k] = -1;
	//			}
	//		}
	//	}
	//}


	//p.showReport();
	_getch();
	return 0;
}