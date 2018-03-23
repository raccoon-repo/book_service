mvn clean package
cd target
sudo mv book-service-1.0-SNAPSHOT.war book-service.war
sudo cp book-service.war /opt/tomcat/webapps
sudo systemctl restart tomcat
cd ..