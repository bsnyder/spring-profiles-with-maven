Spring Profiles With Maven
==========================

This project provides an example of using the new Spring profiles from the
Spring 3.1 M1 release. I put this together quickly in an attempt to figure out
why the Spring profiles are not being enabled by system properties either on
the command line or in a Maven POM file. 

Steps to Recreate Problem 
-------------------------

    $ git clone git@github.com:bsnyder/spring-profiles-with-maven.git
    $ mvn clean install 
    $  cat ./test1/test2/target/surefire-reports/com.mycompany.app.AppTest.txt 
	-------------------------------------------------------------------------------
	Test set: com.mycompany.app.AppTest
	-------------------------------------------------------------------------------
	Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 2.304 sec <<< FAILURE!
	test(com.mycompany.app.AppTest)  Time elapsed: 0.354 sec  <<< ERROR!
	java.lang.AssertionError: The PostgreSQL driver is not being loaded (${jdbc.driver.class})
		at org.junit.Assert.fail(Assert.java:91)
		at org.junit.Assert.assertTrue(Assert.java:43)
		at com.mycompany.app.AppTest.test(AppTest.java:22)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
		at java.lang.reflect.Method.invoke(Method.java:597)
		at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
		at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
		at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
		at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
		at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
		at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
		at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
		at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
		at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
		at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
		at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
		at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
		at org.apache.maven.surefire.junit4.JUnit4TestSet.execute(JUnit4TestSet.java:62)
		at org.apache.maven.surefire.suite.AbstractDirectoryTestSuite.executeTestSet(AbstractDirectoryTestSuite.java:140)
		at org.apache.maven.surefire.suite.AbstractDirectoryTestSuite.execute(AbstractDirectoryTestSuite.java:127)
		at org.apache.maven.surefire.Surefire.run(Surefire.java:177)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
		at java.lang.reflect.Method.invoke(Method.java:597)
		at org.apache.maven.surefire.booter.SurefireBooter.runSuitesInProcess(SurefireBooter.java:345)
		at org.apache.maven.surefire.booter.SurefireBooter.main(SurefireBooter.java:1009)

Notice the exception. Even though the postgresql profile is enabled via a system
property that is specified in the Maven POM, the profile did not get enabled. 

The same result occurs by using a system property on the command line as well.
Try running the tests and add the following system property: 

    $ mvn clean install -Dspring.profiles.active=postgresql

The result will be the same error indicating that the profile did not load. 

The reason that I did not use the Spring @ContextConfiguration annotation to
load the app context XML file is because I'm trying to simulate exactly what
I'm tyring to do in my real project. 

So, I have recreated the problem and you have seen the error. The only way
that I have found to work around this issue is to uncomment line 16 in the
[AppTest.java](https://github.com/bsnyder/spring-profiles-with-maven/blob/master/test1/test2/src/test/java/com/mycompany/app/AppTest.java).
Here is a snippet of that line: 

    ctx.getEnvironment().setActiveProfiles("postgresql");

By uncommenting this line, the Spring API is used directly instead of trying
to rely upon a system property. Once this line is uncommented, the Spring
profile loads correctly. Unfortunately this solution won't work for my 
situation.
