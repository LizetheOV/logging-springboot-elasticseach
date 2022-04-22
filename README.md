# logging-springboot-elasticseach
Simple example of sending logs directly to elasticsearch using <a href="https://github.com/internetitem/logback-elasticsearch-appender" target="_blank">Logback Elasticsearch Appender</a> 

Send log events directly from Logback to Elasticsearch. Logs are delivered asynchronously (i.e. not on the main thread) so will not block execution of the program. Note that the queue backlog can be bounded and messages can be lost if Elasticsearch is down and either the backlog queue is full or the producer program is trying to exit (it will retry up to a configured number of attempts, but will not block shutdown of the program beyond that). For long-lived programs, this should not be a problem, as messages should be delivered eventually.

This software is dual-licensed under the EPL 1.0 and LGPL 2.1, which is identical to the Logback License itself.

   ![springboot-elasticsearch](https://user-images.githubusercontent.com/103335597/164795336-16e4a6a4-7f15-4c06-a8d4-daaa653141d7.png)
