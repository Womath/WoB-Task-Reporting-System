Report Listing Application

This is a simple Java Spring application that reads data from an external source then creates an overall and
a list of monthly reports from it, then uploads it to an FTP server in a .json file. It also creates a .csv file
from data that couldn't be saved to the database.

Table of Contents
1. Prerequisites
2. Getting Started


Prerequisites
Before you begins, ensure that you have met the following requirements:

- Java Developement Kit (JDK) is installed on your computer
- Apache Maven is installed on your computer
- Git is installed on your computer
- PostgreSQL is installed on your computer
- You have access to a local or external FTP server

Getting Started
1. clone this repository to you local computer
git clone https://https://github.com/Womath/WoB-Task-Reporting-System.git

2. change directory to the project folder
cd WoB-Task-Reporting-System

3. build the project using Maven
mvn clean install

4. run the application
mvn spring-boot:run

5. create a report
type 'report' so the program starts fetching the data and creating a report from it