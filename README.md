
# Diploma project 

This is testing system for students and my diploma project.

### Installation dependencies

The following dependencies are necessary:

 - Java 8
 - Bower
 - Maven 3
 - MySQL

### Installing frontend dependencies

Run the following command on the root folder of the repository:

    bower install

After this please copy directory "bower_components" with replace to "../web/src/main/webapp/assets". Because there are some bugs with styles and side libraries. 

### Building and starting the server

Before running the app, create a MySQL database called `testing_system` with charset encoding `utf8_unicode_ci`. 
Then run the following command on the root folder of the repository:

    mvn clean install tomcat7:run-war

 Once hibernate creates tables  use `database_script.sql` to fill data.

After the server starts, the application is accessible at the following URL:

    http://localhost:8080/

And then you can login with roles:

Admin - login: admin; password: password(the same password for all users)
Lecturer - login: lecturer1; (to lecturer4)
Student - login: student1; (to student14)