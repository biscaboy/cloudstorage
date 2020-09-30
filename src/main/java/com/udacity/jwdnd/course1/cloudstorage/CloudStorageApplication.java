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
 * 		Custome Errors - https://www.baeldung.com/spring-boot-custom-error-page
 * 		Configure Tomcat - https://www.baeldung.com/spring-boot-configure-tomcat
 * Rajeev Singh - https://bit.ly/2ZWfDOJ - file upload example
 * How to Program Blog - https://howtoprogram.xyz/2017/01/17/read-file-and-resource-in-junit-test/ - Junit and files.
 * W3Schools.com - multiple topics including...
 * 		Java Read Files https://www.w3schools.com/java/java_files_read.asp
 * 		SQL Syntax - https://www.w3schools.com/sql/sql_insert.asp
 * 		Java Exceptions - https://www.w3schools.com/java/java_try_catch.asp
 * 		... and more html tags & attirbutes references, Java syntax and Bootstrap Modals.
 *
 * JavaDevJournal - Uploading File with Spring Boot - https://www.javadevjournal.com/spring/spring-file-upload/
 * Contributors of Answers at Stack Overflow:
 * 		Michael Pollmeier: Reading bytes from a file - https://stackoverflow.com/questions/858980/file-to-byte-in-java
 * 		Chirs Mowforth: Getting a file's mime type - https://stackoverflow.com/questions/51438/getting-a-files-mime-type-in-java
 *		bmargulies: writing files - https://stackoverflow.com/questions/4350084/byte-to-file-in-java
 *		lorinpa: Test Context Configuration  - 	https://stackoverflow.com/questions/21414750/spring-mvc-junit-test-applicationcontext-config#21414920
 *		dubbe:  Twitter Bootstrap Tabs - https://bit.ly/2FSND7O
 *		dubbe: Using AJAX in Javascript to load response into the browser : https://bit.ly/2FSND7O
 *		Maarten Bodewes - isBlank() vs isEmpty() - https://stackoverflow.com/questions/23419087/stringutils-isblank-vs-string-isempty
 *		Moritz Petersen - Conver byte array to download file - https://stackoverflow.com/questions/13989215/how-to-convert-a-byte-into-a-file-with-a-download-dialog-box
 *		Christopher Bales - inserting a string into an HTML Input element - https://stackoverflow.com/questions/7331146/how-to-get-text-of-single-input-tag
 *
 *	Junit 5 Doc - https://junit.org/junit5/
 *	Oracle Java SE 8 Javadoc - https://docs.oracle.com/javase/8/docs/api/overview-summary.html
 *  MyBatis-Spring - http://mybatis.org/spring/
 *  Spring Framework Guru - Using @RequestMapping - https://springframework.guru/spring-requestmapping-annotation/
 *  Guru99 - XPath in Selenium Web Driver - https://www.guru99.com/xpath-selenium.html
 * 	The Art of the Web - Html5 Validation Examples - https://www.the-art-of-web.com/html/html5-form-validation/
 *  Useful Angle - Validate URL - https://usefulangle.com/post/36/javascript-validate-url-form-input-field-without-regex
 *	Arul Rai - WebSparrow.org - uploading multi-part files - https://www.websparrow.org/spring/spring-boot-rest-api-file-upload-save-example
 *  W.S. Toh - Code-boxx.com - Using AJAX to fetch data from the server - https://code-boxx.com/submit-form-without-refreshing-page/
 *  Javascript.info - Using the HTMLHttpRequest object - https://javascript.info/xmlhttprequest
 *  HowToDoInJava.com - Java Escape HTML - https://howtodoinjava.com/java/string/escape-html-encode-string/
 *  Nam Ha Minh - Java Servlet File Download - https://www.codejava.net/java-ee/servlet/java-servlet-download-file-example
 *
 *  Udacity Knowledge Base Answers:
 *  	Eban D - Escaping quotes in Thymeleaf - https://knowledge.udacity.com/questions/275287
 * 		Utkarsh S - Generating a Secure Key - https://knowledge.udacity.com/questions/275248
 * 		Davide N - How to handle MaxUploadSizeExceededException - https://knowledge.udacity.com/questions/289021
 *
 * @TODO on home.html add message to click add button if there are no objects displayed.
 *
 */
@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
