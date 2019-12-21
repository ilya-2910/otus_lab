-XX:+UseG1GC -Xms10m -Xmx10m

-XX:+UseParallelGC -Xms10m -Xmx10m

execution time sec=107

|                               | -XX:+UseG1GC  |  -XX:+UseParallelGC | 
| -------------                 | ------------- |----- |
| young
| all gc pause (throughput)     | 356                             | 82  |
| count gc pause ms             | 368                             | 166 |
| maxPause ms (latency)         | 7                               | 6   |
| old
| all gc pause (throughput)     | 1741                            | 614  |
| count gc pause                | 384                             | 88   |
| maxPause ms (latency)         | 11                              | 21   |
| STOP THE WORLD ms             | 356 (only minor young)          | 696 (Both Young and Old collections trigger stop-the-world events) |


Выводы
1. G1 лучше по пропускной способности(throughput) (основываясь на времени STOP THE WORLD)
2. G1 лучше по задержкам(latency) (основываясь на времени maxPause)
