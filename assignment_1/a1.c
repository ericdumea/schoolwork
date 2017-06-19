#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>

#define MAX_BUF 1024
#define TEXT_BUF 1500000


void reclist(char* dpath, int ss, char* endsw);
void reclist1(char* dpath, int ss, char* endsw);
int isin(int a[],int v,int n);

typedef struct section{
	char section_name[50];
	int section_type;
	int section_offset;
	int section_size;
}section;


void listdirs(char* dpath, int ss, char* endsw){

	DIR* dir;
	struct dirent* f;
	struct stat inode;
	char pathf[1024];
	dir=opendir(dpath);
	if(dir==0){
		perror("Error opening file");
	}
	else
		printf("SUCCESS\n");

	while((f=readdir(dir))!=0){
		if(strcmp(f->d_name, ".") !=0 && strcmp(f->d_name, "..") != 0){
			strcpy(pathf,dpath);
			strcat(pathf,"/");
			strcat(pathf,f->d_name);
			lstat(pathf,&inode);
			if(ss!=-1){
				if((S_ISREG(inode.st_mode)))
					if(inode.st_size<=ss)
						printf("%s\n",pathf);
			}
			else 
				if(strcmp(endsw,"")!=0){
					size_t len1=strlen(f->d_name);
					size_t len2=strlen(endsw);
					if(len2<=len1)
						if(strncmp(f->d_name+len1-len2,endsw,len2)==0)
							printf("%s\n",pathf);
				}
				else
					printf("%s\n",pathf);

		}
	}
	closedir(dir);
}

void reclist1(char* dpath, int ss,char* endsw){
	DIR* dir;
	struct dirent* f;
	struct stat inode;
	char pathf[1024];
	dir=opendir(dpath);

	if(dir==0){
		return;
	}

	while((f=readdir(dir))!=0){
		
		if(strcmp(f->d_name, ".") !=0 && strcmp(f->d_name, "..") != 0){
		strcpy(pathf,dpath);
		strcat(pathf,"/");
		strcat(pathf,f->d_name);
		lstat (pathf, &inode);
		//printf("%s\n",pathf);

		if((S_ISDIR(inode.st_mode))){
			reclist1(pathf,ss,endsw);}
		if(ss!=-1){
				if((S_ISREG(inode.st_mode)))
					if(inode.st_size<=ss)
						printf("%s\n",pathf);
			}
			else 
				if(strcmp(endsw,"")!=0){
					size_t len1=strlen(f->d_name);
					size_t len2=strlen(endsw);
					if(len2<=len1)
						if(strncmp(f->d_name+len1-len2,endsw,len2)==0)
							printf("%s\n",pathf);
				}
				else
					printf("%s\n",pathf);
		}
	}
	closedir(dir);

}

void reclist(char* dpath, int ss,char* endsw){
	DIR* dir;
	struct dirent* f;
	struct stat inode;
	char pathf[1024];
	dir=opendir(dpath);
	if(dir==0){
		perror("Error opening file");
	}
	else
		printf("SUCCESS\n");

	while((f=readdir(dir))!=0){
		
		if(strcmp(f->d_name, ".") !=0 && strcmp(f->d_name, "..") != 0){
		strcpy(pathf,dpath);
		strcat(pathf,"/");
		strcat(pathf,f->d_name);
		lstat (pathf, &inode);
		if((S_ISDIR(inode.st_mode))){
			reclist1(pathf,ss,endsw);}
		if(ss!=-1){
				if((S_ISREG(inode.st_mode)))
					if(inode.st_size<=ss)
						printf("%s\n",pathf);
			}
			else 
				if(strcmp(endsw,"")!=0){
					size_t len1=strlen(f->d_name);
					size_t len2=strlen(endsw);
					if(len2<=len1)
						if(strncmp(f->d_name+len1-len2,endsw,len2)==0)
							printf("%s\n",pathf);
				}
				else
					printf("%s\n",pathf);
		}
	}
	closedir(dir);

}

int issf(char* dpath){
	int fd;
	int hsize;
	int* bufint;
	bufint=(int*)malloc(sizeof(int)*MAX_BUF);
	fd=open(dpath,O_RDONLY);
	if(fd<0){
		printf("ERROR\ninvalid file\n");
	}

	char buf[MAX_BUF];
	strcpy(buf,"");

	lseek(fd,-1,SEEK_END);
	read(fd,buf,1);

	if(buf[0]!='m'){
		//printf("ERROR\ninvalid file\n");
		free(bufint);
		return 0;
	}
	*bufint=0;
	lseek(fd, -3,SEEK_END);
	read(fd, bufint,2);
	hsize=*bufint;
	*bufint=0;

	lseek(fd,-(hsize),SEEK_END);
	read(fd, bufint, 2);

	int version=*bufint;
	*bufint=0;

	if(version<77||version>191){
		//printf("ERROR\ninvalid file\n");
		free(bufint);
		return 0;
	}

	int nr_section;
	read(fd,bufint,1);
	nr_section=*bufint;

	if(nr_section<8||nr_section>10){
		//printf("ERROR\ninvalid file\n");
		free(bufint);
		return 0;
	}

	section s[nr_section+1];

	for(int i=1;i<=nr_section;i++){
		read(fd,buf,14);
		strcpy(s[i].section_name,buf);
		strcpy(buf,"");
		read(fd,bufint,2);
		s[i].section_type=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_offset=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_size=*bufint;
		*bufint=0;
	}
	int type[]={50,55,28,71,10,99};
	
	for(int i=1;i<=nr_section;i++)
		if(isin(type,s[i].section_type,6)==0){
			
			free(bufint);
			return 0;
		}


	int types[]={71};
	int ok=0;
	for(int i=1;i<=nr_section;i++)
		if(isin(types,s[i].section_type,1)==1){
			ok=1;
		}
	if(ok==0){
		//printf("ERROR\ninvalid file\n");
		free(bufint);
		return 0;
	}
	free(bufint);
	return 1;
}



int isin(int a[],int v,int n){
	for(int i=0;i<n;i++)
		if(a[i]==v)
			return 1;
	return 0;
}

void parse(char* dpath){
	int fd;
	int hsize;
	int* bufint;
	bufint=(int*)malloc(sizeof(int)*MAX_BUF);
	

	fd=open(dpath,O_RDONLY);

	char buf[MAX_BUF];
	strcpy(buf,"");

	lseek(fd,-1,SEEK_END);
	read(fd,buf,1);

	if(buf[0]!='m'){
		printf("ERROR\nwrong magic\n");
		free(bufint);
		return;
	}
	*bufint=0;
	lseek(fd, -3,SEEK_END);
	read(fd, bufint,2);
	hsize=*bufint;
	*bufint=0;

	lseek(fd,-(hsize),SEEK_END);
	read(fd, bufint, 2);

	int version=*bufint;
	*bufint=0;

	if(version<77||version>191){
		printf("ERROR\nwrong version\n");
		free(bufint);
		return;
	}

	int nr_section;
	read(fd,bufint,1);
	nr_section=*bufint;

	if(nr_section<8||nr_section>10){
		printf("ERROR\nwrong sect_nr\n");
		free(bufint);
		return;
	}

	section s[nr_section+1];

	for(int i=1;i<=nr_section;i++){
		read(fd,buf,14);
		strcpy(s[i].section_name,buf);
		strcpy(buf,"");
		read(fd,bufint,2);
		s[i].section_type=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_offset=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_size=*bufint;
		*bufint=0;
	}

	int types[]={50,55,28,71,10,99};
	
	for(int i=1;i<=nr_section;i++)
		if(isin(types,s[i].section_type,6)==0){
			printf("ERROR\nwrong sect_types\n");
			free(bufint);
			return;
		}


	printf("SUCCESS\n");
	printf("version=%d\n",version);
	printf("nr_sections=%d\n",nr_section);

	for(int i=1;i<=nr_section;i++){
		printf("section%d: %s %d %d\n",i,s[i].section_name,s[i].section_type,s[i].section_size);
	}

	free(bufint);
}


void findall(char* dpath){
	DIR* dir;
	struct dirent* f;
	struct stat inode;
	char pathf[1024];
	dir=opendir(dpath);

	if(dir==0){
		return;
	}

	while((f=readdir(dir))!=0){
		
		if(strcmp(f->d_name, ".") !=0 && strcmp(f->d_name, "..") != 0){
			strcpy(pathf,dpath);
			strcat(pathf,"/");
			strcat(pathf,f->d_name);
			lstat (pathf, &inode);

			if((S_ISDIR(inode.st_mode))){
				findall(pathf);
			}
			if((S_ISREG(inode.st_mode))){
				if(issf(pathf)==1)
					printf("%s\n",pathf);
			}
		}
	
	}
closedir(dir);

}


void extract(char *dpath, int snr, int lnr){
	int fd;
	int hsize;
	int* bufint;
	bufint=(int*)malloc(sizeof(int)*MAX_BUF);
	

	fd=open(dpath,O_RDONLY);
	if(fd<0){
		printf("ERROR\ninvalid file\n");
		free(bufint);
		return;
	}

	char buf[MAX_BUF];
	strcpy(buf,"");

	lseek(fd,-1,SEEK_END);
	read(fd,buf,1);

	if(buf[0]!='m'){
		printf("ERROR\ninvalid file\n");
		free(bufint);
		return;
	}
	*bufint=0;
	lseek(fd, -3,SEEK_END);
	read(fd, bufint,2);
	hsize=*bufint;
	*bufint=0;

	lseek(fd,-(hsize),SEEK_END);
	read(fd, bufint, 2);

	int version=*bufint;
	*bufint=0;

	if(version<77||version>191){
		printf("ERROR\ninvalid file\n");
		free(bufint);
		return;
	}

	int nr_section;
	read(fd,bufint,1);
	nr_section=*bufint;

	if(nr_section<8||nr_section>10){
		printf("ERROR\ninvalid file\n");
		free(bufint);
		return;
	}

	section s[nr_section+1];

	for(int i=1;i<=nr_section;i++){
		read(fd,buf,14);
		strcpy(s[i].section_name,buf);
		strcpy(buf,"");
		read(fd,bufint,2);
		s[i].section_type=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_offset=*bufint;
		*bufint=0;
		read(fd,bufint,4);
		s[i].section_size=*bufint;
		*bufint=0;
	}

	int types[]={50,55,28,71,10,99};
	
	for(int i=1;i<=nr_section;i++)
		if(isin(types,s[i].section_type,6)==0){
			printf("ERROR\nwrong sect_types\n");
			free(bufint);
			return;
		}

	if(snr>nr_section||snr<1){
		printf("ERROR\ninvalid section\n");
		free(bufint);
		return;
	}

	int nrline=0;
	char* buff=(char*)malloc(sizeof(char)*TEXT_BUF);

	lseek(fd,s[snr].section_offset,SEEK_SET);
	read(fd,buff,s[snr].section_size);
	char* token;
	token=strtok(buff,"\x0A");
	char sirulet[TEXT_BUF];
	while(token!=NULL&&lnr!=nrline){
		nrline++;
		strcpy(sirulet,"");
		strcpy(sirulet,token);
		token=strtok(NULL,"\x0A");
	}
	printf("SUCCESS\n");
	for(long long i=strlen(sirulet)-1;i>=0;i--){
		printf("%c",sirulet[i]);
	}
	printf("\n");

	free(buff);
	free(bufint);
}


int main(int argc, char **argv){
    	
    char mode;

    char* dpath;
   	dpath= (char*)malloc(sizeof(char)*1024);
   	
   	DIR* dir;

    if(argc >= 2){
        if(strstr(argv[1], "variant")){
            printf("12906\n");
        }
        for(int i=1;i<=argc-1;i++){
			if(strstr(argv[i],"list")){
				mode='l';
        	}
        	if(strstr(argv[i],"parse")){
        		mode='p';
        	}
        	if(strstr(argv[i],"extract")){
        		mode='e';
        	}
        	if(strstr(argv[i],"findall")){
        		mode='f';
        	}
        }
    }

    char lmode='l';
    char ss='f';
    char nend='f';
    int value=-1;
    char aux[100];

    char* endsw;
    endsw=(char*)malloc(sizeof(char)*150);

    int lnr, snr;

    if(mode=='l'){
    	for(int i=1;i<argc;i++){
    		if(strstr(argv[i],"path")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				strcpy(dpath,token);
    				token=strtok(NULL,"=");
    			} 
    			free(token);   		
    		}
    		if(strstr(argv[i],"recursive"))
    			lmode='r';
    		if(strstr(argv[i],"size_smaller")){
    			ss='t';
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				strcpy(aux,token);
    				value=atoi(aux);
    				token=strtok(NULL,"=");
    			}
    		}

    		if(strstr(argv[i],"name_ends_with")){
    			nend='t';
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				strcpy(endsw,token);
    				token=strtok(NULL,"=");
    			}
    		}
    	}
    	
    	if(ss=='t'){
    		if(lmode=='l')
    			listdirs(dpath,value, "");
    		else
    			reclist(dpath,value, "");
    	}
    	if(nend=='t'){
    		if(lmode=='l')
    			listdirs(dpath,-1,endsw);
    		else
    			reclist(dpath,-1,endsw);
    	}
    	if(lmode=='l'&& ss=='f' && nend=='f')
    		listdirs(dpath,-1,"");
    	if(lmode=='r'&& ss=='f'&& nend=='f')
    		reclist(dpath,-1, "");
	}

	if(mode=='p'){
		for(int i=1;i<argc;i++){
    		if(strstr(argv[i],"path")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				strcpy(dpath,token);
    				token=strtok(NULL,"=");
    			}    		
    		}
    	}
    	parse(dpath);
	}

	if(mode=='f'){
		for(int i=1;i<argc;i++){
    		if(strstr(argv[i],"path")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				strcpy(dpath,token);
    				token=strtok(NULL,"=");
    			}    		
    		}
    	}
    	if((dir=opendir(dpath))){
    		printf("SUCCESS\n");
    		closedir(dir);
    		findall(dpath);
    	}
    	else{
    		printf("ERROR\ninvalid directory path\n");
    		closedir(dir);
    	}
	}

	if(mode=='e'){
		for(int i=1;i<argc;i++){
    		if(strstr(argv[i],"path")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				strcpy(dpath,token);
    				token=strtok(NULL,"=");
    			}    		
    		}
    		if(strstr(argv[i],"section")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				snr=atoi(token);
    				token=strtok(NULL,"=");
    			}    		
    		}
    		if(strstr(argv[i],"line")){
    			char *token;
    			token=strtok(argv[i],"=");
    			while(token!=NULL){
    				//printf("%s\n",token);
    				lnr=atoi(token);
    				token=strtok(NULL,"=");
    			}    		
    		}
    	}
    	extract(dpath, snr, lnr);
	}

	free(endsw);
    free(dpath);
    return 0;
}
