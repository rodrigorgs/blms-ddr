Billiards League Management System (BLMS)
by Rodrigo Rocha
   Dalton Cézane
   Diego Santos
-----------------------------------------
How to Install and Run

(OBS.: A Tomcat installation is required to run the web interface)

1. Download dependencies

  Unzip http://gmf.ufcg.edu.br/~rodrigo/blms-jars.zip into blms
  Unzip http://gmf.ufcg.edu.br/~rodrigo/blms-jars.zip into blms-web
  Unzip http://gmf.ufcg.edu.br/~rodrigo/blms-web-jars.zip into blms-web

2. Run tests (optional)

  cd blms
  ant runTests

3. Copy blms.jar to BLMS-web

  ant copyweb

4. Deploy BLMS-web

  cd ..
  cd blms-web
  ant deploy -lib /opt/apache-tomcat-6.0.16/lib -Dtomcat.home=/opt/apache-tomcat-6.0.16 -Dtomcat.url="http://localhost:8080/" -Dtomcat.username=tomcat -Dtomcat.password=s3cret

The parameters may be different.

5. Open http://localhost:8080/blms-web
(Replace localhost:8080 by the URL of your Tomcat server)

