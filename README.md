This is a project I was asked to do after the job interview
in summer 2019 but after several system reinstallings it was successfully lost :<

So now I decided to restart it over (and actually push the damn thing to git this time)
just for practise, after all I can't say I worked in Java EE a lot (without 
Spring Boot, JPA, MVC and all this pretty stuff).

Note: the data/* database files contain some test random data you can play with
if you want to try.

**The task is as follows:**

There are employees and departments.
A department may have lots of employees. Or it may not.

There is a list of departments and there are buttons "Add/Edit/Remove/List"
 
By pressing "List" user gets the list of this departments' employees with
the same buttons.

List is a table, pages of adding/editing - a set of text fields.

*Technologies*

1. Database - jdbc(any)
2. Controller - servlets
3. View - jsp + el + jstl
4. JDK - 1.8

*Requirements*

1. Data validation
2. Each department has a unique name, employee - email
3. Employee must have a numerical and a date fields
4. Data must not disappear after validation even when they were invalid


**Installing**
You gotta have Tomcat server running with settings, specified in pom.xml ([here's a good article on it](https://www.theserverside.com/video/Step-by-step-Maven-Tomcat-WAR-file-deploy-example)), but if shortly, your .../apache-tomcat-<version>/conf/tomcat-users.xml file should have the following line:
 
 ```xml
<!-- Roles are mostly optional, you only need manager-script to run from Maven. -->
 <user username="admin" password="admin" roles="manager-gui, manager-script, manager-jmx" />
 ```
You also have to specify the user in settings.xml in your Maven directory - you can find it by running `echo $M2_HOME` command - by adding the following:
```xml
<server>
  <id>TomcatServer</id>
  <username>admin</username>
  <password>admin</password>
</server>
```

Then go to the project directory (so that if you do `ls`- or `dir` if you're on Windows - you can see pom.xml) and run `mvn tomcat7:deploy`. The app will be running on localhost:8080/dae path.
