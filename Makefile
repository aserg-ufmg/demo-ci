build:
	mvn package

clean:
	mvn clean dependency:copy-dependencies package
