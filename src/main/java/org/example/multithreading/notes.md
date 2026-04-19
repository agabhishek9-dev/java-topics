

                             <<interface>>
                                Executor
                        
                                   |
                                   | (extends)
                                   v
                             <<interface>>
                            ExecutorService
                        
                                   |
    -------------------------------------------------------
    |                |                                    |
    | (implements)   | (implements)                       | (extends)
    v                v                                    v
ThreadPool       ForkJoinPool                       <<interface>>
Executor                                      ScheduledExecutorService

                                                          |
                                                          | (implements)
                                                          v
                                               ScheduledThreadPoolExecutor
