#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>



int  MIN_PID =100;
int  MAX_PID =1000

int size_of_thread;
int thread_number = 0;
pthread_mutex_t mutex;

int allocate_map(void)                                  
{
    int i,j;
    for(i = MIN_PID, j =0; i <= MAX_PID; i++, j++)
    {
        pidArr[j].pid = i;
        pidArr[j].bitmap = 0;
    }

    if(i == MAX_PID && j == MAX_PID)
    {
    return 1;
	}
    else
    {
	return -1;
	}
}

int allocate_pid(void)                                
{
	int i,j;
    for(i = MIN_PID, j =0; i <= MAX_PID; i++, j++)
    {
        if(pidArr[j].bitmap == 0)
        {
            pidArr[j].pid = i;
            pidArr[j].bitmap = 1;
            return i;
            break;
        }
    }
	
}



void * Multi_Thread(void * v)                         
{
    int rpid = allocate_pid();      
	while (thread_number < size_of_thread)
    {
        pthread_mutex_lock(&mutex);     //mutex lock occurs
        if (thread_number >= size_of_thread)
        {
            pthread_mutex_unlock(&mutex);
            break;
        }
        thread_number++;                    
     //   sleep(10);
        
        printf("\n\t##   Thread %d       Succesfully Created   ",thread_number);
        pthread_mutex_unlock(&mutex);      
    }
    //sleep(5);
    release_pid(rpid);         
    
}

int main()
{
    int i =0;
    printf("\n\tEnter How Many Number of Threads You Want : ");
    scanf("%d",&size_of_thread);
    pthread_t thread[size_of_thread];


    for(i = 0; i < size_of_thread; i++)
    {
        pthread_mutex_init(&mutex, NULL);
        pthread_create(&thread[i], NULL, Multi_Thread, NULL);
        Multi_Thread(NULL);
    }

    for(i = 0; i < size_of_thread; i++)
    {
        pthread_join(thread[i], NULL);
        pthread_mutex_destroy(&mutex);
    }
	
    return 0;
}