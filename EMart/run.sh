aws s3 cp $CONFIG_URL ~/application.properties
java -jar target/mgor.webservices-1.0-SNAPSHOT.jar --spring.config.location=~/,/root/