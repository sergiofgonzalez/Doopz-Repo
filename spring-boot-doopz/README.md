Spring Boot Doopz
=================

A command line application that looks for possible duplicate files from a given directory.

The application scans the files from a configured directory and displays a report of the files that:
- Have the same name
- Have the same length

so that you can manually check whether they are actually duplicates or not.

# Usage Notes
Modify the `application.yml` file to specify the directory from which you want to scan for duplicate files.

To run the application:
`mvn spring-boot:run`