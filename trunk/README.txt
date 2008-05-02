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
  ant deploy


5. Open http://YOUR_TOMCAT_SERVER/blms-web

To undeploy BLMS-web, just run 

  ant undeploy

