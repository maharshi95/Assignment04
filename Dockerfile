FROM maven:3-jdk-8
VOLUME /tmp
ADD . /
RUN apt-get -y update 
RUN apt-get install -y python-pip
RUN pip install awscli --ignore-installed six
RUN mvn clean package
CMD aws s3 cp s3://cnu-2016/mgor/application.properties target/application.properties && java -jar target/mgor.webservices-1.0-SNAPSHOT.jar --spring.config.location=./target/application.properties
