Billiards League Management System
Web Interface
(BLMS-web)

== Dependencies ==

You must have Apache Tomcat properly installed and running.
From now on we'll assume that Tomcat is accessible via http://localhost:8080/

You must have Billiards League Management System (the core application).

== File structure ==

If you have download BLMS and BLMS-WEB, your file structure should be
something like this:

  blms       # the core BLMS application
  blms-web   # BLMS-web
    /jars

== Installing ==

Go to the blms folder (from the core application). Run the command

  ant jar

This command will create a file name blms.jar. Copy it to blms-web/jars.

Run the command (adjust the parameters)

  ant deploy -lib /opt/apache-tomcat-6.0.16/lib -Dtomcat.home=/opt/apache-tomcat-6.0.16 -Dtomcat.url="http://localhost:8080/" -Dtomcat.username=tomcat -Dtomcat.password=s3cret

== Running ==

Open a web browse and enter the following address:

http://localhost:8080/blms-web/

