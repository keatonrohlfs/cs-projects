//Name: Keaton Rohlfs
//CWID: 11893990
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 80 /* The maximum length command */

char* history[10][MAX_LINE/2 + 1];
int history_wait[10];
int bufferHead = 0;
char** historyCalculate(char **args, int *shouldWait) {
    int i;
    if(args[1] == NULL && strcmp(args[0],"!!")==0) {
        if(bufferHead>0){
            strcpy(args[0],history[(bufferHead - 1) % 10][0]);
            for(i = 1; history[(bufferHead - 1) % 10][i] != NULL; i++) {
                args[i]=(char*)malloc((MAX_LINE + 1) * sizeof(char));
                strcpy(args[i],history[(bufferHead - 1) % 10][i]);
            }
            args[i]=NULL;
            *shouldWait = history_wait[(bufferHead - 1) % 10];
        } else {
            printf("No commands in history.\n");
            return args;
        }
    } else if (args[1] == NULL && args[0][0] == '!') {
        int index;
        char *string_ptr= &(args[0][1]);
        if(sscanf(string_ptr,"%d",&index) == 1) {
            if(index > 0 && bufferHead > index - 1 && index > bufferHead - 9) {
                strcpy(args[0],history[(index - 1) % 10][0]);
                for(i = 1; history[(index - 1) % 10][i] != NULL; i++) {
                    args[i]=(char*)malloc((MAX_LINE + 1) * sizeof(char));
                    strcpy(args[i],history[(index - 1) % 10][i]);
                }
                args[i]=NULL;
                *shouldWait = history_wait[(index - 1) % 10];
            } else {
                printf("No such command in history.\n");
                return args;
            }
        } else {
            printf("No such command in history.\n");
            return args;
        }
    }

    for(i = 0; i < (MAX_LINE / 2 + 1) && history[bufferHead % 10][i] != NULL; i++){
        free(history[bufferHead%10][i]);    
    }
    for(i = 0;args[i] != NULL; i++) {
        history[bufferHead % 10][i] = args[i];
    }
    history[bufferHead % 10][i] = args[i];
    history_wait[bufferHead % 10] = *shouldWait;
    return history[(bufferHead++) % 10];
}
void historyStart(void) {
    for(int i = 0; i < 10; i++) {
        for(int j = 0; j < (MAX_LINE / 2 + 1); j++) {
            history[i][j] = NULL;
        }
        history_wait[i] = 0;
    }
}
void historyDealloc(void) {
    for(int i = 0; i < 10 && i < bufferHead; i++) {
        for(int j = 0; history[i][j] != NULL; j++) {
            if(history[i][j]) {
                free(history[i][j]);    
            }
        }
    }
}
void historyOutput(void) {
    int index;
    for(int i = 0; i < 10 && i < bufferHead; i++) {
        if(bufferHead > 10){
            index = bufferHead - 9 + i;            
        }
        else {
            index = i + 1;            
        }
        printf("[%d] ", index);
        for(int j = 0; history[(index - 1) % 10][j] != NULL; j++) {
            printf("%s ",history[(index - 1) % 10][j]);
        }
        if(history_wait[(index - 1) % 10] == 0) {
            printf("&");
        }
        printf("\n");
    }
}

int main(void) {
	char *args[MAX_LINE/2 + 1];
    int should_run = 1;
    historyStart();
    while (should_run){   
        printf("Rohlfs %d >", (int) getpid());
        fflush(stdout);

        pid_t pid;
        char cmdLine[MAX_LINE+1];
        char *string_ptr = cmdLine;
        int argvalue = 0;
        if(scanf("%[^\n]%*1[\n]",cmdLine)<1) {
            if(scanf("%1[\n]",cmdLine)<1) {
                printf("Stdin failed.\n");
                return 1;
            }
            break;
        }
        while(*string_ptr == ' ' || *string_ptr == '\t')
            string_ptr++;
        while(*string_ptr!='\0'){
            char *buffer=(char*)malloc((MAX_LINE+1)*sizeof(char));
            args[argvalue]=(char*)malloc((MAX_LINE+1)*sizeof(char));
            int val = sscanf(string_ptr,"%[^ \t]",args[argvalue]);
            string_ptr += strlen(args[argvalue]);
            if(val < 1){
                printf("Invalid command.\n");
                return 1;
            }
            val = sscanf(string_ptr,"%[ \t]",buffer);
            if(val>0)
                string_ptr += strlen(buffer);
            argvalue++;
            free(buffer);
        }
        int should_wait = 1;
        if(strlen(args[argvalue-1])==1 && args[argvalue-1][0]=='&') {
            should_wait = 0;
            free(args[argvalue-1]);
            args[argvalue-1]=NULL;
        } else {
            args[argvalue]=NULL;
        }
        if(strcmp(args[0],"exit")==0){
            historyDealloc();
            return 0;
        }
        //History Computation
        if(args[1]==NULL && strcmp(args[0],"history")==0) {
            historyOutput();
            break;
        }
char **argptr = historyCalculate(args, &should_wait);
        pid = fork();
        if(pid < 0) {
            printf("Fork failed.\n");
            return 1;
        } else if(pid == 0) {
            if(execvp(argptr[0],argptr)) {
                printf("Invalid command.\n");
                return 1;
            }
        } else {
            if(should_wait) {
                while(wait(NULL) != pid);
            }
            else {
                printf("%d\n",pid);
            }
        }
    }
    
	return 0;
}