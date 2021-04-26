FROM registry.access.redhat.com/ubi8/openjdk-8

ADD ROOT.war ROOT.war

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ROOT.war"]
