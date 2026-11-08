# Visitor pattern file system

This Java 17 project models a small file system in memory. It uses the Visitor pattern to calculate the total file size, search files by name and minimum size, and collect tree statistics.

## Run the tests

From the project root, run:

```text
mvn test
```

## Run the demonstration

Build the project and run the client:

```text
mvn package
java -cp target/classes visitor.Client
```

The client builds the same sample tree on every run and prints the total size, matching Java file paths, file and directory counts, and maximum depth.
