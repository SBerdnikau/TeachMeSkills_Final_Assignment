#Here, the following tasks are implemented:

Create a program for processing payment documents and providing financial statements. Access to the program must be carried out by login and password. Implement two-factor authentication using OTP and QR code using Authenticator applications. The program must get the path to the folder with financial documents, read information from the documents and compile statistics. Documents can be of three types: invoices, orders, checks. All documents are in TXT format. Each type of document has its own structure and its own name template. Examples of documents will be provided. It is necessary to process files only for the current year. Make technical documentation of the program: solution diagram, class diagram, sequence diagram. Implement various checks. Implement saving logs in a separate file. It is advisable to separate the logs: for storing general information and for storing error information. Upon completion of the program, all invalid files must be moved to a separate folder. The final statistics must be downloaded to a separate file. The statistics file must be uploaded to Amazon S3 cloud storage. Settings for the program, such as keys for S3 and session lifetime, should be in the properties file.

Statistics:

total turnover for all invoices
total turnover for all orders
total turnover for all checks
Acceptance criteria

Working program.
Clean and understandable code.
Compliance with the naming convention for packages, classes, methods, variables.
Javadoc comments for services are required.
Comments in English.
Completed, short and clear ReadMe file. The file must be completed in English.
All working code must be in the master branch. The number of other branches is not limited.
The repository should not contain unnecessary files and folders (for example, out, target and others).
Test scenario

Launch the program
The program requests credentials -> enter login and password
The program generates a QR code -> scan the code and receive a temporary password to enter the program (OTP)
The program requests the path to the folder -> enter the path to the folder
The program is executed and the results of the program are saved in a separate folder, the report is uploaded to the cloud storage
Additional technical information on the project structure Services:

Authorization service.
File reading and processing service.
Packages:

classes for describing files
classes for writing logs
classes for parsing files
classes for describing sessions
classes with utility information
exceptions
