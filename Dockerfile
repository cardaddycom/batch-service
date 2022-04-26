FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
# Add Maintainer Info
LABEL maintainer="george.christman@healthresearch.org"

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
# Add the application's jar to the container
ADD target/batch-service-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
