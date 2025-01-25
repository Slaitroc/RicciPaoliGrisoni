## 1 Introduction
### 1.1 Purpose
The purpose of the Student&Company (S&C) platform is to enable students to enroll into internships
that will enhance their education and strengthen their CVs, while letting companies publish internship
offers and select the best candidates through interviews. More over, S&C allow students’ universities
to monitor each of their students’ progress and intervene if needed. The platform support and aid the
users throughout the entire process by provide suggestion to the uploaded CVs and internship offers,
automatically matches students and companies thanks to a proprietary algorithm, manage the distribution
and collection of interviews and provides a space for filing and resolving complaints. The reader can find
more information about the platform in the RASD document. In the remaining part of this chapter we
will present a summary of the technical choices made for the creation of the platform and different bullet
point lists and table including the Goals that we are trying to accomplish with this software and the
Definition, Acronyms, Abbreviations used in this document.

### 1.2 Scope
This document, Implementation and Test Document (ITD), provides a comprehensive description of the
implementation and testing phases of the S&C platform. Specifically, it focuses on the functionalities
developed, the adopted frameworks, and the structure of the source code. Additionally, it includes a
detailed testing strategy, covering the procedures, tools, and methodologies used during the development
process. This document also serves as a guide for installing and running the platform, offering installation
instructions and addressing any prerequisites or potential issues. The effort spent by the team members
is also summarized to provide insight into the workload distribution.

### 1.3 Definitions, Acronyms, Abbreviations
This section provides definitions and explanations of the terms, acronyms, and abbreviations used through-
out the document, making it easier for readers to understand and reference them.
#### 1.3.1 Definitions
- **University**: A university that is registered on the S&C platform.
- **Company**: A company that is registered on the S&C platform.
- **Student**: A person who is currently enrolled in a University and is registered on the S&C platform.
- **User**: Any registered entity on the S&C platform.
- **Internship Offer**: The offer of an opportunity to enroll in an internship provided by a Company. The offer remains active on the platform indefinitely until the publishing Company removes it.
- **Participant**: An entity that interacts with the platform for the purpose of finding or offering an Internship Position Offer, like Students and Companies.
- **Recommendation Process**: The process of matching a Student with an Internship offered by a Company based on the Student’s CV and the Internship’s requirements, made by the S&C platform.
- **Recommendation/Match**: The result of the Recommendation Process. It is a match between a Student and an Internship.
- **Spontaneous Application**: The process of a Student spontaneously applying for an Internship that was not matched through the Recommendation Process.
- **Interview**: The process of evaluating a Student’s application for an Internship done by a Company through the S&C platform.
- **Feedback**: Information provided by a Participant to the S&C platform to improve the Recommendation Process.
- **Internship Position Offer**: The formal offer of an internship position presented to a Student who has successfully passed the Interview, who can decide to accept or reject it.
- **Suggestion**: Information provided by the S&C platform to a Participant to improve their CVs and Internship descriptions.
- **Confirmed Internship**: An Internship that has been accepted by the Student and the offering Company.
- **Ongoing Internship**: An Internship that is currently in progress. All Ongoing Internships are Confirmed Internships, but the vice versa is not always true.
- **Complaint**: A report of a problem or issue that a Student or Company has with an Ongoing Internship. It can be published on the platform and handled by the University.
- **Confirmed Match**: A match that has been accepted by both a Student and a Company.
- **Rejected Match**: A match that has been refused by either a Student or a Company.
- **Pending Match**: A match that has been accepted only by one party, waiting for a response from the other party.
- **Unaccepted Match**: A match that has been refused by either a Student or a Company.
#### 1.3.2 Acronyms
The following acronyms are used throughout the document:
| **Acronym** | **Definition** |
|------------|---------------|
| ITD        | Implementation and Test Document |
| CV         | Curriculum Vitae |
| UI         | User Interface |
| UX         | User Experience |
| DB         | Database |
| API        | Application Programming Interface |
| ORM        | Object-Relational Mapping |
| DBMS       | Database Management System |
| SPA        | Single Page Application |
| DMZ        | Demilitarized Zone |
| JPA        | Java Persistence API |
| JS         | JavaScript |
| HTML       | HyperText Markup Language |
| JWT        | JSON Web Token |
| HTTP       | HyperText Transfer Protocol |
| HTTPS      | HyperText Transfer Protocol Secure |
| SQL        | Structured Query Language |
| CRUD       | Create, Read, Update, Delete |
| API        | Application Programming Interface |
| ORM        | Object-Relational Mapping |
| DBMS       | Database Management System |

### 1.3.3 Abbreviations
The following abbreviations are used throughout the document:
| **Abbreviation** | **Definition** |
|------------------|----------------|
| S&C              | Students&Companies |

## 2 Implemented Functionalities and Requirements 

### 2.1 Implemented Functionalities
- **User Management**: The platform allows Students, Companies, and Universities to register and login. It also provides Students with the ability to upload and modify their CVs, and Companies with the ability to view and manage their Internship Offers.
- **Internship Creation and Management**: Companies can create, publish, and manage Internship Offers on the platform. They define details such as job description, requirements, location, and benefits. 
- **Student Application Process**: Students can browse available Internships and apply to Internships either through automatic matching or by submitting Spontaneous Applications. They can also track the status of their Applications throughout the process.
- **Automated Recommendations**: The platform matches Students with suitable Internships based on their CVs and the specific requirements set by Companies. Once a match is found, both Students and Companies are notified, and they can accept or reject the Recommendation.
- **Interview Management**: Companies can create and assign Interviews to Students, which include closed and open questions to assess their suitability for an Internship. Both Students and Companies can track the Interview progress, and Companies can evaluate Student responses. Companies can also select among students who have passed the interview those to whom they will propose an Internship Position Offer.
- **Interview Reassignment**: Companies can reassign Interviews question to other Students, allowing them to shorten the time needed to find the right candidate.
- **Feedback for Improvement**: The platform collects Feedback from Students and Companies to improve the Recommendation Process by dynamically changing the Recommendation Process.
- **Complaint Management**: Students and Companies can publish Complaints about Ongoing Internships, notifying Universities.
- **Notification System**: Notifications are sent to Students, Companies, and Universities when relevant events occur, such as new Internships, founded matches, Interview assignments, Internship Position Offers, Sign-up confirmation or Communications.

### 2.2 Requirements
The following requirements have been implemented in the S&C platform:
- **[R1]** The platform shall allow any unregistered students to register by providing personal information and selecting their University.
- **[R2]** The platform shall allow any companies to register by providing company information.
- **[R3]** The platform shall allow any universities to register by providing university information.
- **[R4]** The platform shall allow Users to log in using their email and password.
- **[R5]** The platform shall send notifications to Users when relevant events occur.

- **[R6]** The platform shall allow Companies to create and publish Internship offers specifying details.
- **[R7]** The platform shall allow Companies to terminate their Internship offers at their own discretion.
- **[R8]** The platform shall provide Students with Matches automatically obtained by the Recommendation Process.
- **[R9]** The platform shall allow Students to view and navigate all available Internships.
- **[R10]** The platform shall enable Students to submit Spontaneous Applications to Internships they choose.
- **[R11]** The platform shall allow Students to submit their CV.
- **[R12]** The platform shall allow Students to modify their CV.
- **[R13]** The platform shall allow Students to monitor the status of their Spontaneous Applications.
- **[R14]** The platform shall allow Students to monitor the status of their Recommendation.

- **[R15]** The platform shall display to Students all the Internships found by the Recommendation Process.
- **[R16]** The platform shall display to Companies all the CVs of Matched Students obtained by the Recommendation Process.
- **[R17]** The platform shall allow Students and Companies to accept a Recommendation.
- **[R18]** The platform shall allow Companies to accept a Spontaneous Application.
- **[R19]** The platform shall start a Selection Process only if both the Company and the Student have accepted the Recommendation.
- **[R20]** The platform shall start a Selection Process only if the Company has accepted the Spontaneous Application.

- **[R21]** The platform shall allow Companies to create Interviews.
- **[R22]** The platform shall allow Companies to submit Interviews to Students they have initiated a Selection Process with.
- **[R23]** The platform shall allow Students to answer Interview questions and submit them.
- **[R24]** The platform shall allow Companies to manually evaluate Interview submissions.
- **[R25]** The platform shall allow Students and Companies to monitor the status of their Interviews.
- **[R26]** The platform shall enable Companies to complete the Interview process by submitting the final outcome to each candidate.
- **[R27]** The platform shall enable companies to assign a set of interview questions to students, allowing the same set of questions to be reassigned to multiple ones.

- **[R28]** The platform shall enable Companies to send an Internship Position Offer to a Student only if he previously passed the relative Interview.
- **[R29]** The platform shall enable Students to accept or reject an Internship Position Offer sent by a Company only if he previously passed the relative Interview.

- **[R30]** The platform shall collect Feedback from both Students and Companies regarding the Recommendation Process.

- **[R31]** The platform shall allow registered Universities to access and monitor Internship Communications related to their Students.
- **[R32]** The platform shall provide a dedicated space for Students and Companies to exchange Communications about the current status of an Ongoing Internship.

## 3 Adopted Development Frameworks
Different frameworks, technologies and languages have been used to implement the S&C platform. The following sections provide an overview of the main frameworks used for the backend, frontend, and database layers. Each section includes a brief description of the framework, its main features, and the reasons for choosing it.
### 3.1 Frameworks
### 3.1.1 Frontend
#### 3.1.1.1 React
React is a JavaScript library for building user interfaces. It is maintained by Facebook and a community of individual developers and companies. React can be used as a base in the development of single-page or mobile applications. It is a declarative, efficient, and flexible library that allows developers to compose complex UIs from small and isolated pieces of code called “components"\\
React was chosen for the frontend layer of the S&C platform because of its simplicity, flexibility, and performance. It allows for the creation of reusable UI components, which is essential for building a complex web application like ours. React also provides a virtual DOM, which improves performance by updating only the necessary parts of the UI when the application state changes. This feature is particularly useful for the S&C platform, where real-time updates are required to display different aspect of the application like newly founded matches or recently received notifications. React also has a large and active community, which ensures good support, documentation, and a wide range of third-party libraries and tools that can be used to enhance the development process. In particular library like Framer Motion was used to create smooth animations and transitions while the Material-UI library was used to create a consistent and responsive UI design.
### 3.1.2 Backend
### 3.1.2.1 Spring Boot
Spring is a popular open-source framework for building enterprise Java applications. It provides comprehensive infrastructure support for developing Java applications, including configuration management, dependency injection, and aspect-oriented programming. Spring was chosen mainly for its modularity that allow developers to use only the parts of the framework they need as well as for its easy of use and large community support. It also integrates well with other Java technologies, such as Hibernate and JPA.  
Spring Boot also simplifies the development process by providing a set of defaults and conventions, which reduces the amount of boilerplate code that developers need to write. This allows the team to focus on implementing the business logic of the application, rather than dealing with low-level configuration details. The Spring Data JPA module was used to interact with the database, providing a high-level abstraction over the underlying SQL queries. This allows developers to write database queries using Java objects and annotations, rather than raw SQL statements. Spring Boot web was used to create RESTful APIs that can be consumed by the frontend layer of the application. These APIs are used to perform CRUD operations on the database such as creating, reading, updating, and deleting data. 
#### 3.1.2.2 Lucene
Lucene is a high-performance, full-featured text search engine library written in Java. It is widely used in information retrieval and text mining applications, such as web search engines, document management systems, and e-commerce platforms. Lucene provides a rich set of features for indexing, searching, and analyzing text data, including support for full-text search, faceted search, and fuzzy matching. This capability are used to implement the matching algorithm that is used to match students with internships. The algorithm is based on the similarity between the student's CV and the internship description requirements. Thanks to the fuzzy matching feature of Lucene, as well as it's speed and accuracy, the algorithm is able to provide a list of the most suitable internships for each student even if the CV and the internship description are not an exact word-by-word match.
#### 3.1.2.. Firebase
Firebase is a platform developed by Google for creating mobile and web applications. It provides a variety of services, including a real-time database, authentication, cloud storage, and hosting.  It allows developers to build high-quality applications quickly and efficiently. Firebase was chosen for the backend layer of the S&C platform because of its authentication capability handling the sign-up, confirmation, and login process of each user and the ability to send notifications with the Firebase Cloud Messaging service when relevant events occur. 
### 3.1.3 Database
#### 3.1.3.1 JPA
Java Persistence API (JPA) is a Java specification for managing relational data in Java applications. It provides a set of standard interfaces and annotations for mapping Java objects to database tables and vice versa. JPA is part of the Java EE platform and is implemented by various ORM (Object-Relational Mapping) frameworks, such as Hibernate, EclipseLink, and OpenJPA. JPA was chosen for the database layer of the S&C platform because of its ease of use, flexibility, and compatibility with other Java technologies and framework, in particular Spring. 
Moreover Jpa  allows developers to write database queries using Java objects and annotations, rather than raw SQL statements. This makes the code more readable, maintainable, and less error-prone while providing a high-level abstraction over the underlying SQL queries, which simplifies the development process and reduces the amount of boilerplate code that developers need to write.
#### 3.1.3.2 Hibernate
Hibernate is a high-performance, object-relational mapping (ORM) framework for Java that handles the mapping between Java classes and database tables, as well as the generation of SQL queries and the management of database connections. Hibernate was chosen for the database layer of the S&C platform because of its ease of use, flexibility, and compatibility with other Java technologies as well as the rich set of features such as caching, transaction management, and query optimization, which improve the performance and scalability of the application. \\
Hibernate is widely used in enterprise Java applications and has a large and active community, which ensures good support, documentation, and a wide range of third-party libraries and tools that can be used to enhance the development process.
#### MariaDB
MariaDB is an open-source relational database management system (DBMS) that is compatible with MySQL. It is widely used in web applications, e-commerce platforms, and content management systems. MariaDB was mainly chosen for its compatibility with Hibernate and JPA, which simplifies the integration with the backend layer of the S&C platform as well as for its open-source nature, which allows developers to use it freely without any licensing costs. 

### 3.2 Languages
The following languages have been used to implement the S&C platform:
- **Java**: Java is a general-purpose programming language that is widely used in enterprise applications, web development, and mobile applications. It is mainly know for its portability that allows developers to write code once and run it on any platform that supports Java and it's virtual machine. Java was chosen for the backend layer of the S&C platform because of its performance, scalability, and compatibility with other Java technologies and frameworks, such as Spring and Hibernate as well as the team members' familiarity with the language.
- **JavaScript**: JavaScript is a high-level, interpreted programming language that is widely used in web development. It is mainly known for its ability to create interactive and dynamic web pages. JavaScript was chosen for the frontend layer of the S&C platform because of its flexibility, performance, and compatibility with modern web browsers as well as the large and active community that provides good support, documentation, and a wide range of third-party frameworks and libraries like React.


### 5 Testing Strategy
The testing strategy for the S&C platform is based on a combination of manual and automated testing techniques. Manual testing is performed by the development team to verify the correctness of the application's functionality, user interface, and user experience. Automated testing is performed using testing frameworks and tools to verify the correctness of the application's business logic, data integrity, and performance. The following sections will provide an overview of the testing strategy using during the development and the integration phase.

### 5.1 Development Testing

### 5.2 Integration Testing
For the integration testing phase we mainly used Postman as a "Proxy Driver" to test the RESTful APIs created with Spring Boot following the same strategy describe in the DD document at the 3° testing step. The main goal of this phase is to verify that the different components of the S&C platform work together correctly and that the data flow between them is consistent and reliable. The integration testing phase is performed by the development team to identify and fix any issues related to the interaction between the call made by the frontend and the computation made by the backend and the database. We created more than 30 different API call that were later used in Postman unit suite, that allowed us to test all of the different functionalities of the platform, from the user registration to the internship creation, from the recommendation process to the interview management.\\
Postman allowed us to randomize request parameters and body content to test the system's robustness and evaluate various edge cases that could arise during normal platform usage. Examples include creating an internship with a missing field, accepting a recommendation that has already been approved, or attempting to fetch a non-existent internship and much more. The integration testing phase is essential to ensure that the S&C platform works as expected, all the functionalities are implemented correctly and consistently and the correct exception are thrown when the user tries to perform an invalid operation.
%insert here postman call screenshot