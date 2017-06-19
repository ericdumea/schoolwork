#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "a2_helper.h"
#include <stdlib.h>
#include <pthread.h>



void* f2(void* arg){
	int i=*(int*)arg;
	//i++;
	
	info(BEGIN, 2, i);

	info(END, 2, i);
	//printf("\n%d\n", i );
	return NULL;
}

void* f6(void* arg){
	int i=*(int*)arg;
	//i++;
	
	info(BEGIN, 6, i);

	info(END, 6, i);
	//printf("\n%d\n", i );
	return NULL;
}

int main(){
    init();
    int pid[9];
    //int* x = (int*) malloc (sizeof(int));
    //int i;
    for(int i=0;i<9;i++){
    	pid[i]=-1;
    }

    info(BEGIN, 1, 0);
	
    if((pid[2]=fork())==0){ 
    	//suntem in P2
    	info(BEGIN,2,0);
    	pthread_t t[5];
    	int th_nr[]={1,2,3,4};
  		for(int i=4;i>=1;i--){
  			if(pthread_create(&t[i],NULL,f2,&th_nr[i-1])!=0){
    			perror("Cannot create threads");
   		 		exit(1);
   		 	}
  		}			

    	//for(int i=1;i<=4;i++){
    	//	pthread_join(*t,NULL);
    	//}

    	if((pid[3]=fork())==0){
    		//suntem in p3
    		info(BEGIN,3,0);

    		info(END,3,0);
    	}
    	else{
    		//suntem in p2
    		if((pid[4]=fork())==0){//p4
    			info(BEGIN,4,0);

    			info(END,4,0);
    		}
    		else{
    			//suntem in p2
    			if((pid[6]=fork())==0){
    				info(BEGIN,6,0);

    				int th6_nr[37];
    				pthread_t t6[37];
    				for(int i=1;i<=36;i++){
    					th6_nr[i]=i;
    				}
  					for(int i=36;i>=1;i--){
	  					if(pthread_create(&t6[i],NULL,f6,&th6_nr[i])!=0){
	    					perror("Cannot create threads");
	   		 				exit(1);
	   		 			}
  				}
  					for(int i=1;i<=36;i++)
    					 pthread_join(t6[i],NULL);

    				info(END,6,0);
    			}
    			else{
    				if((pid[7]=fork())==0){
    					info(BEGIN,7,0);

    					info(END,7,0);
    				}
    				else{
    					waitpid(pid[3],NULL,0);
    					waitpid(pid[4],NULL,0);
    					waitpid(pid[7],NULL,0);
    					waitpid(pid[6],NULL,0);
    					for(int i=1;i<=4;i++)
    					 pthread_join(t[i],NULL);
    					//pthread_join(t[1],NULL);
    					info(END,2,0);
    				}
    			}
    		}
    	}
    	
    }
    else{
    	//suntem in P1
    	//wait(NULL);
    	if((pid[5]=fork())==0){
    		//suntem in 5
    		info(BEGIN,5,0);

    		//exit(5);
    		info(END,5,0);
    	}
    	else{
    		//suntem iar in p1
    		waitpid(pid[2],NULL,0);
    		waitpid(pid[5],NULL,0);
    		info(END,1,0);
    	}
    }

  	//info(END, 1, 0);
    return 0;
}
