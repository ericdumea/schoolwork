#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/mman.h>


#define MAX_SECTION 3072
#define MAX_BUF 1000

typedef struct section{
	int s_off;
	int s_size;
	int start_sec;
	int end_sec;
}section;

int main(){

	char buf[1000];
	int fd[2];

	int n=0;

	if(mkfifo("RESP_PIPE_12906",0640)==-1){
		perror("ERROR\ncannot create the response pipe");
		exit(0);
	}


	fd[1] = open("REQ_PIPE_12906",O_RDWR);
	
	if(fd[1]<0){
		perror("ERROR\ncannot open the request pipe");
		exit(0);
	}

	fd[0] = open("RESP_PIPE_12906",O_RDWR);

	int len_size = 7;
	write(fd[0],&len_size,1);
	char a[10] = "CONNECT";
	write(fd[0],&a,strlen(a));

	printf("SUCCESS\n");
	char buf_send[100];
	unsigned int buf_int =0;

	int bufint=0;
	int mem_id=0;
	char *my_mem = NULL;
	int off_shm=0;
	int val_shm=0;
	int size_shm=0;
	char map_path[MAX_BUF];
	int mapped_fd = 0;
	char* map_addr = NULL;
	unsigned int off_val=0;
	int no_of_bytes =0;
	int sizeof_map_file = 0;
	int section_nr = 0;
	int offset_val = 0;
	int hsize =66;
	int no_of_sections = 0;
	int logical_offset = 0;
	int y=0;
	int z=0;
	int logical_offset_sec = 0;

	section s[100];

	while(1){
		if((n=read( fd[1], &bufint, 1)) > 0){
			read(fd[1],buf,bufint);
			buf[bufint]='\0';
			printf("Received: %s\n", buf);
	 		if(strcmp(buf,"PING")==0){
	 			len_size = 4;
	 			write(fd[0],&len_size,1);
	 			strcpy(buf_send,"PING");
	 			write(fd[0],buf_send,strlen(buf_send));
	 			write(fd[0],&len_size,1);
	 			strcpy(buf_send,"PONG");
	 			write(fd[0],buf_send,strlen(buf_send));
	 			len_size=5;
	 			strcpy(buf_send,"12906");
	 			buf_int=12906;
	 			write(fd[0],&buf_int,4);
	 		}
	 		else if(strcmp(buf,"EXIT")==0){
	 			close(fd[1]);
	 			close(fd[0]);
	 			shmdt(my_mem);
				shmctl(mem_id, IPC_RMID, 0);
	 			exit(0);
	 		}
	 		else if(strcmp(buf,"CREATE_SHM")==0){
	 			read(fd[1],&buf_int,4);
	 			size_shm = buf_int;
	 			key_t key_of_mem = 10003;
	 			mem_id = shmget(key_of_mem,size_shm,IPC_CREAT | 0777);

	 			if(mem_id==-1){
	 				strcpy(buf_send,"CREATE_SHM");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"ERROR");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			perror("Why, oh why?");
	 			}
	 			else{
		 			my_mem = shmat(mem_id, (void*)NULL,0);
		 			if(my_mem == (char*)(-1)){
		 				perror("problem at accessing memory");
		 				exit(0);
		 			}
		 			strcpy(buf_send,"CREATE_SHM");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"SUCCESS");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			
		 		}
	 		}
	 		else if(strcmp(buf,"WRITE_TO_SHM")==0){
	 			read(fd[1],&off_shm,4);
	 			if(off_shm>size_shm||off_shm<0||(off_shm+4)>size_shm){
	 				read(fd[1],&val_shm,4);
	 				strcpy(buf_send,"WRITE_TO_SHM");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"ERROR");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));

	 			}
	 			else{
	 				read(fd[1],&val_shm,4);
	 				memcpy((my_mem+off_shm),&val_shm,4);
	 				printf("%d\n",my_mem[off_shm]);
	 				strcpy(buf_send,"WRITE_TO_SHM");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"SUCCESS");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
	 			}
	 		}
	 		else if(strcmp(buf,"MAP_FILE")==0){
	 			read( fd[1], &bufint, 1);
	 			read(fd[1],buf,bufint);
				buf[bufint]='\0';
				strcpy(map_path, buf); 
				printf("Received:@map %s\n", map_path);	
	 			mapped_fd = open(map_path, O_RDONLY);
	 			if(mapped_fd<0){
	 				strcpy(buf_send,"MAP_FILE");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"ERROR");
	 				len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
	 				perror("cannot map!");

	 			}else{
	 				sizeof_map_file = lseek(mapped_fd,0,SEEK_END);
	 				lseek(mapped_fd,0,SEEK_SET);
	 				printf("%d",sizeof_map_file);
					if((map_addr=mmap(NULL,sizeof_map_file,PROT_READ|PROT_WRITE,MAP_PRIVATE,mapped_fd,0)) == MAP_FAILED)
        				perror("Error in mmap");
        			else{
        				strcpy(buf_send,"MAP_FILE");
		 				len_size=strlen(buf_send);
		 				write(fd[0],&len_size,1);
			 			write(fd[0],buf_send,strlen(buf_send));
			 			strcpy(buf_send,"SUCCESS");
		 				len_size=strlen(buf_send);
		 				write(fd[0],&len_size,1);
			 			write(fd[0],buf_send,strlen(buf_send));
        			}
	 			}
	 		}
	 		else if(strcmp(buf,"READ_FROM_FILE_OFFSET")==0){
	 			read(fd[1],&off_val,4);
	 			read(fd[1],&no_of_bytes,4);
	 			if(off_val+no_of_bytes<sizeof_map_file){
	 				memcpy(my_mem,(map_addr+off_val),no_of_bytes);
	 				strcpy(buf_send,"READ_FROM_FILE_OFFSET");
		 			len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"SUCCESS");
		 			len_size=strlen(buf_send);
		 			write(fd[0],&len_size,1);
			 		write(fd[0],buf_send,strlen(buf_send));
	 			}else{
	 				strcpy(buf_send,"READ_FROM_FILE_OFFSET");
		 			len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"ERROR");
		 			len_size=strlen(buf_send);
		 			write(fd[0],&len_size,1);
			 		write(fd[0],buf_send,strlen(buf_send));
	 			}
	 		}
	 		else if(strcmp(buf,"READ_FROM_LOGICAL_SPACE_OFFSET")==0){

	 			read(fd[1],&logical_offset,4);
	 			read(fd[1],&no_of_bytes,4);
	 			memcpy(&hsize,(map_addr+sizeof_map_file -3),2);
	 			int x=sizeof_map_file-hsize+3;
	 			memcpy(&no_of_sections,(map_addr+sizeof_map_file-hsize+2),1);
	 			perror("");
	 			for(int i=1;i<=no_of_sections;i++){
						x+=16;
						memcpy(&s[i].s_off,(map_addr+x),4);
						x+=4;
						memcpy(&s[i].s_size,(map_addr+x),4);
						x+=4;
				}

				y=0;
				for(int i=1;i<=no_of_sections;i++){
					s[i].start_sec = y;
					if(s[i].s_size>3072){
						z=(int)(s[i].s_size)/3072+1;
						s[i].end_sec = y+z;
						y+=z;
					}
					else{
						y++;
					}
					s[i].end_sec = y;

				}
				logical_offset_sec = (int)logical_offset /3072;
			


				for(int i=1;i<=no_of_sections;i++){
					if(s[i].start_sec>logical_offset_sec){
						y=logical_offset-(s[i-1].start_sec*3072);
						memcpy(my_mem,(map_addr+s[i-1].s_off+y),no_of_bytes);

	 					strcpy(buf_send,"READ_FROM_LOGICAL_SPACE_OFFSET");
		 				len_size=strlen(buf_send);
	 					write(fd[0],&len_size,1);
	 					write(fd[0],buf_send,strlen(buf_send));
			 			strcpy(buf_send,"SUCCESS");
				 		len_size=strlen(buf_send);
						write(fd[0],&len_size,1);
						write(fd[0],buf_send,strlen(buf_send));
						break;	
					}else if(s[i].start_sec==logical_offset_sec){
						y=logical_offset-(s[i].start_sec*3072);
						memcpy(my_mem,(map_addr+s[i].s_off+y),no_of_bytes);

						strcpy(buf_send,"READ_FROM_LOGICAL_SPACE_OFFSET");
		 				len_size=strlen(buf_send);
	 					write(fd[0],&len_size,1);
	 					write(fd[0],buf_send,strlen(buf_send));
			 			strcpy(buf_send,"SUCCESS");
				 		len_size=strlen(buf_send);
						write(fd[0],&len_size,1);
						write(fd[0],buf_send,strlen(buf_send));
						break;
					}
				}

	 		}
	 		else if(strcmp(buf,"READ_FROM_FILE_SECTION")==0){
	 			read(fd[1],&section_nr,4);
	 			read(fd[1],&offset_val,4);
	 			read(fd[1],&no_of_bytes,4);
	 			memcpy(&hsize,(map_addr+sizeof_map_file -3),2);
				memcpy(&no_of_sections,(map_addr+sizeof_map_file-hsize+2),1);
				int sect_headers = 24* no_of_sections;
				int x=sizeof_map_file-hsize+3;

				if(sect_headers>hsize || no_of_sections<section_nr){
					strcpy(buf_send,"READ_FROM_FILE_SECTION");
		 			len_size=strlen(buf_send);
	 				write(fd[0],&len_size,1);
		 			write(fd[0],buf_send,strlen(buf_send));
		 			strcpy(buf_send,"ERROR");
		 			len_size=strlen(buf_send);
		 			write(fd[0],&len_size,1);
			 		write(fd[0],buf_send,strlen(buf_send));
			 		perror("cam mare");
				}else{
					for(int i=1;i<=no_of_sections;i++){
						x+=16;
						memcpy(&s[i].s_off,(map_addr+x),4);
						x+=4;
						memcpy(&s[i].s_size,(map_addr+x),4);
						x+=4;
					}

					printf("\n\n\n%d\n%d\n\n\n\n",s[section_nr].s_size,no_of_bytes);
					if(no_of_bytes>s[section_nr].s_size){
						strcpy(buf_send,"READ_FROM_FILE_SECTION");
		 				len_size=strlen(buf_send);
	 					write(fd[0],&len_size,1);
		 				write(fd[0],buf_send,strlen(buf_send));
		 				strcpy(buf_send,"ERROR");
		 				len_size=strlen(buf_send);
		 				write(fd[0],&len_size,1);
			 			write(fd[0],buf_send,strlen(buf_send));
			 			perror("cam mare");
					}else{
						memcpy(my_mem,(map_addr+s[section_nr].s_off+offset_val),no_of_bytes);
						strcpy(buf_send,"READ_FROM_FILE_SECTION");
		 				len_size=strlen(buf_send);
	 					write(fd[0],&len_size,1);
		 				write(fd[0],buf_send,strlen(buf_send));
		 				strcpy(buf_send,"SUCCESS");
		 				len_size=strlen(buf_send);
		 				write(fd[0],&len_size,1);
			 			write(fd[0],buf_send,strlen(buf_send));
					}
				}
	 		}
 		}
	}

	return 0;
}