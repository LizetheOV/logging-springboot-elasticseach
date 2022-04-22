# logging-springboot-elasticseach
Simple example of sending logs directly to elasticsearch using <a href="https://github.com/internetitem/logback-elasticsearch-appender" target="_blank">Logback Elasticsearch Appender</a> 

Send log events directly from Logback to Elasticsearch. Logs are delivered asynchronously (i.e. not on the main thread) so will not block execution of the program. Note that the queue backlog can be bounded and messages can be lost if Elasticsearch is down and either the backlog queue is full or the producer program is trying to exit (it will retry up to a configured number of attempts, but will not block shutdown of the program beyond that). For long-lived programs, this should not be a problem, as messages should be delivered eventually.

This software is dual-licensed under the EPL 1.0 and LGPL 2.1, which is identical to the Logback License itself.

   ![springboot-elasticsearch](https://user-images.githubusercontent.com/103335597/164795336-16e4a6a4-7f15-4c06-a8d4-daaa653141d7.png)

<h3>Usage</h3>
<hr>
Include slf4j and logback as usual (depending on this library will not automatically pull them in).

In your pom.xml (or equivalent), add:

    <dependency>
       <groupId>com.internetitem</groupId>
       <artifactId>logback-elasticsearch-appender</artifactId>
       <version>1.6</version>
    </dependency>
 
In your logback-spring.xml:

      <?xml version="1.0" encoding="UTF-8"?>
      <configuration>
          <springProperty scope="context" name="app_name" source="app.name"/>
          <property name="LOGS" value="./logs"/>
          <appender name="Console"
                    class="ch.qos.logback.core.ConsoleAppender">
              <layout class="ch.qos.logback.classic.PatternLayout">
                  <Pattern>
                      %black(%d{ISO8601}) %highlight(%-5level) [%blue(%t)] %yellow(%C{1.}): %msg%n%throwable
                  </Pattern>
              </layout>
          </appender>
          <appender name="RollingFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
              <file>${LOGS}/spring-boot-logger.log</file>
              <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
                  <Pattern>%d %p %C{1.} [%t] %m%n</Pattern>
              </encoder>
              <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                  <fileNamePattern>${LOGS}/archived/spring-boot-logger-%d{yyyy-MM-dd}.%i.log
                  </fileNamePattern>
                  <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                      <maxFileSize>10MB</maxFileSize>
                  </timeBasedFileNamingAndTriggeringPolicy>
              </rollingPolicy>
          </appender>
          <appender name="ELASTIC" class="com.internetitem.logback.elasticsearch.ElasticsearchAppender">
              <url>http://yourserver/_bulk</url>
              <index>springboot-logs-%date{yyyy-MM-dd}</index>
              <loggerName>springboot-logger</loggerName> <!-- optional -->
              <errorLoggerName>springboot-error-logger</errorLoggerName> <!-- optional -->
              <connectTimeout>30000</connectTimeout> <!-- optional (in ms, default 30000) -->
              <errorsToStderr>false</errorsToStderr> <!-- optional (default false) -->
              <includeCallerData>false</includeCallerData> <!-- optional (default false) -->
              <logsToStderr>false</logsToStderr> <!-- optional (default false) -->
              <maxQueueSize>104857600</maxQueueSize> <!-- optional (default 104857600) -->
              <maxRetries>3</maxRetries> <!-- optional (default 3) -->
              <readTimeout>30000</readTimeout> <!-- optional (in ms, default 30000) -->
              <sleepTime>250</sleepTime> <!-- optional (in ms, default 250) -->
              <rawJsonMessage>false</rawJsonMessage> <!-- optional (default false) -->
              <includeMdc>true</includeMdc> <!-- optional (default false) -->
              <maxMessageSize>10000</maxMessageSize> <!-- optional (default -1 -->
              <properties>
                  <property>
                      <name>appName</name>
                      <value>${app_name}</value>
                      <allowEmpty>false</allowEmpty>
                  </property>
                  <property>
                      <name>appVersion</name>
                      <value>${app_version}</value>
                      <allowEmpty>false</allowEmpty>
                  </property>
                  <property>
                      <name>host</name>
                      <value>${HOSTNAME}</value>
                      <allowEmpty>false</allowEmpty>
                  </property>
                  <property>
                      <name>severity</name>
                      <value>%level</value>
                  </property>
                  <property>
                      <name>thread</name>
                      <value>%thread</value>
                  </property>
                  <property>
                      <name>exception</name>
                      <value>%ex</value>
                  </property>
                  <property>
                      <name>logger</name>
                      <value>%logger</value>
                  </property>
              </properties>
              <headers>
                  <header>
                      <name>Content-Type</name>
                      <value>application/json</value>
                  </header>
              </headers>
          </appender>
          <root level="info">
              <appender-ref ref="RollingFile"/>
              <appender-ref ref="Console"/>
              <appender-ref ref="ELASTIC"/>
          </root>
          <logger name="com.baeldung" level="trace" additivity="false">
              <appender-ref ref="RollingFile"/>
              <appender-ref ref="ELASTIC"/>
          </logger>
          <logger name="es-logger" level="INFO" additivity="false">
              <appender-ref ref="ELASTIC"/>
          </logger>

          <logger name="com.logging" level="trace" additivity="false">
              <appender-ref ref="RollingFile"/>
              <appender-ref ref="ELASTIC"/>
          </logger>
      </configuration>

<h3>Configuration Reference</h3>
<hr>
   <strong>url (required):</strong> The URL to your Elasticsearch bulk API endpoint
<strong>Note:</strong> For more detail about configuration review this <a href="https://github.com/internetitem/logback-elasticsearch-appender" target="_blank">Logback Elasticsearch Appender</a>
