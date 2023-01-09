# Ex2

<h1> EX2_1 <h1>
  
  
   <h2> explaining the part 5 in the first part of the assignment
   which is about threads and why it might be faster or slower 
   based on the mission it has to complete <h2> 
   
  
   <h5> first lets talk about why threadpool is faster and more efficient than Multithreading :
   A thread pool is a design pattern in software engineering, where a collection of threads wait for tasks to be assigned to them.
   The traditional way of thread utilization is to create a new thread for each task and then destroy that thread once the task is finished. However, this 
   approach is not scalable, because when your program starts having several tasks, the overhead of creating and destroying threads slows down your program  
   considerably.

  By using thread pools, multiple long-lasting threads are initialized to begin with, and they wait to be assigned tasks. After these threads complete 
  their tasks, they are not destroyed, but they can actually be reused to complete more tasks. This is a far more efficient use of threads, as threads can
  be used over and over again instead of wasting time destroying and creating new threads to perform another incoming task.
  
  
  now lets talk about why threading might be a problem in some tasks and not using it might be way more efficient that using it : 
  
  one way of this might happen is when we wanna read from a small sized file so reading it directly is way faster that creating thread and reading it
  
  another one is the time that thread interchange will be big and doing the task directly like reading files more faster
  
  and another example might be is when threads wait for eachother may take a long time and be less efficient 
  
  and the last example i may think of for now is when we make alot of threads that handicap the cpu and make his job harder interchange between all the thread <h5>
  
  <h2> now lets see our program and decide which is faster <h2>
    
  the program we have written is reading from alot of files by regular method without using thread
  and one with using thread 
  and one with using thread pool
  
      
    
  
  
