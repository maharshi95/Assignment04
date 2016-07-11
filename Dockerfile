FROM maven:3-jdk-8
VOLUME /tmp
ADD . /
RUN apt-get -y update 
RUN apt-get install -y python-pip
RUN pip install awscli --ignore-installed six
RUN mvn clean package
CMD aws s3 cp $CONFIG_URL/application.properties application.properties && java -jar target/mgor.webservices-1.0-SNAPSHOT.jar
