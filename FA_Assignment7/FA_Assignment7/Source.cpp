#include <iostream>
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#define MAX_SIZE 10001
#define MAX_CHILD 10

using namespace std;


typedef struct Node
{
	int key;
	struct Node *left;
	struct Node* right;
}Node;


typedef struct mwNode
{
	int childnr;
	int key;
	mwNode **children;
}mwNode;

Node* buildNode(int key);
mwNode* buildNodeMW(int key);
void pretty_print_mw(mwNode*root, int level);
mwNode* t1(int a[], int n);
void pretty_print(Node *root, int space);
Node* binary_build(mwNode *root, mwNode **chArr, int index);


Node* buildNode(int key)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->key = key;
	newNode->left = NULL;
	newNode->right = NULL;
	return newNode;
}


mwNode* buildNodeMW(int key)
{
	mwNode* newNode = (mwNode*)malloc(sizeof(mwNode));
	newNode->childnr = 0;
	newNode->key = key;
	newNode->children = (mwNode**)malloc(sizeof(mwNode*)*MAX_CHILD);

	for (int i = 0; i < MAX_CHILD; i++) {
		newNode->children[i] = NULL;
	}

	return newNode;
}

void pretty_print_mw(mwNode*root, int level)
{
	if (root != NULL) {
		for (int i = 0; i <= level; i++) {
			cout << "    ";
		}
		printf("%d", root->key);
		printf("\n");
		for (int i = 0; i < root->childnr; i++) {
			pretty_print_mw(root->children[i],level+1);
		}
	}
}

mwNode* t1(int a[], int n) {
	mwNode** Nodes = (mwNode**)malloc((n + 1) * sizeof(mwNode*));
	int root = -1;
	for (int i = 1; i <= n; i++) {
		Nodes[i] = buildNodeMW(i);
	}
	for (int i = 1; i <= n; i++) {
		if (a[i] != -1) {
			Nodes[a[i]]->children[Nodes[a[i]]->childnr] = Nodes[i];
			Nodes[a[i]]->childnr++;
		}
		else
			root = i;
	}
	return Nodes[root];
}

void pretty_print(Node *root, int space)
{
	if (root == NULL)
		return;
	space += 4;
	pretty_print(root->right, space);
	printf("\n");
	for (int i = 7; i < space; i++)
		printf(" ");
	printf("%d\n", root->key);
	pretty_print(root->left, space);
}

Node* binary_build(mwNode *root, mwNode **chArr, int index)
{

	Node *newNode = buildNode(root->key);
	Node *left = NULL;
	Node *right = NULL;
	mwNode *sibling = NULL;

	if (chArr != NULL)
		if (chArr[index + 1] != NULL)
			sibling = chArr[index + 1];

	if (sibling != NULL)
		right = binary_build(sibling, chArr, index + 1);

	if (root->children[0] != NULL)
		left = binary_build(root->children[0], root->children, 0);

	if (left != NULL) 
		newNode->left = left;
	if (right != NULL)
		newNode->right = right;

	return newNode;
}

int main() {
	int a[] = { NULL, 2, 7, 5, 2, 7, 7,-1, 5, 2 };
	int n = 9; //1->9

	mwNode* root = t1(a, n);

	//pretty_print_mw(root,0);

	Node* rootB = binary_build(root, NULL, 0);
	pretty_print(rootB, 2);
	_getch();
	return 0;
}