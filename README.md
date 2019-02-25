Payment System app
=====
Please write a program to purchase multiple items using different credit cards and thus their respective payment systems. 
Make suitable assumptions of interfaces

Description
-------
A simple online store simulator app where you can do payments using CreditCards for different products. 
It is a very simplified design

Future improvements suggestions
-------
Implement proper PaymentSystem and Online store
I did not implement any input reading and parsing system for the app, as I have implemented it using unit tests instead
I considered implementing a JSON, file based input reader or console.in reader is somehow not important for the case
of this app. This could be easily added in the next sprint. I started with the focus on the dummy Payment system

How to run it
-------
1) From IntelliJ
2) From terminal using sbt

Compile and run tests
```sbtshell
sbt clean compile test
```

Run
```sbtshell
sbt run
```