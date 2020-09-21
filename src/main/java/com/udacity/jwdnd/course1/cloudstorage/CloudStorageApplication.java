package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Special thanks to these websites for their resources:
 * Spring Documentation: https://docs.spring.io - multiple topics including:
 * 		Resources, ResourceLoader and JUnit Testing.
 * Himanshu Dahiya - Medium.com - MyBatis Annotations and Result Mapping
 * 		- https://medium.com/@hsvdahiya/mybatis-annotations-result-mapping-spring-79944ff74b84
 * Baeldung: https://www.baeldung.com - multiple topics on Spring Boot
 * 		Using @Resource, @Autowired in Junit test - https://www.baeldung.com/spring-annotations-resource-inject-autowire
 * 		Guide to JUnit 5 Parameterized - https://www.baeldung.com/parameterized-tests-junit-5
 * 		Serve Static Resources with Spring - https://www.baeldung.com/spring-mvc-static-resources
 * 		Assert an Exception - https://www.baeldung.com/junit-assert-exception
 * 		Quick Guide to MyBatis - https://www.baeldung.com/mybatis
 * 		Testing in SpringBoot - https://www.baeldung.com/spring-boot-testing
 * Rajeev Singh - https://bit.ly/2ZWfDOJ - file upload example
 * How to Program Blog - https://howtoprogram.xyz/2017/01/17/read-file-and-resource-in-junit-test/ - Junit and files.
 * W3Schools.com - multiple topics
 * 		Java Read Files https://www.w3schools.com/java/java_files_read.asp
 * 		SQL Insert Syntax - https://www.w3schools.com/sql/sql_insert.asp
 * 		SQL Update Syntax - https://www.w3schools.com/SQL/sql_update.asp
 * 		Java Exceptions - https://www.w3schools.com/java/java_try_catch.asp
 * JavaDevJournal - Uploading File with Spring Boot - https://www.javadevjournal.com/spring/spring-file-upload/
 * Contributors at Stack Overflow:
 * 		Michael Pollmeier: Reading bytes from a file - https://stackoverflow.com/questions/858980/file-to-byte-in-java
 * 		Chirs Mowforth: Getting a file's mime type - https://stackoverflow.com/questions/51438/getting-a-files-mime-type-in-java
 *		bmargulies: writing files - https://stackoverflow.com/questions/4350084/byte-to-file-in-java
 *		lorinpa: Test Context Configuration  - 	https://stackoverflow.com/questions/21414750/spring-mvc-junit-test-applicationcontext-config#21414920
 *	Junit 5 Doc - https://junit.org/junit5/
 *	Oracle Java SE 8 Javadoc - https://docs.oracle.com/javase/8/docs/api/overview-summary.html
 *  MyBatis-Spring - http://mybatis.org/spring/
 */
@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
