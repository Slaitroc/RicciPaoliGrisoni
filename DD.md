# 1 Introduction
## 1.1 Purpose
The purpose of the Student&Company (S\&C) platform is to enable students to enroll into internships that will enhance their education and strengthen their CVs while letting companies publish internship offers and select the best candidates thought 
interviews. More over S\&C allow students' universities to monitor each of their students' progress and intervene if needed.\\
The platform support and aid the users throughout the entire process by provide suggestion to the uploaded CVs and internship offers, automatically matches students and companies thanks to a proprietary algorithm, manage the distribution and collection of interviews and provides a space for filing and resolving complaints. The reader can find more information about the platform in the RASD document.\\ <!-- non so se lasciare questa ultima frase, feedback? -->
In the remaining part of this chapter we will present a summary of the technical choice made for the creation of the platform, along with different tables including the Goals we are try to fullfil with this software and the Definition, Acronyms, Abbreviations used in this document.\\


## 1.2 Scope
This document, Design Document (DD), will provide a detailed description of the architecture of the S\&C platform from a more technical point of view. In particular it will provide a thorough description of the software with a special emphasis on its interfaces, system module, and architectural framework.
This document will also discuss the implementation, integration and testing plan describing the tools and methodologies that will be used during the development of the platform.\\
<!-- (Sam 1h)-->
## 1.2.1 Product domain

## 1.2.2 Main architectural choices
The architecture of the platform adopt a 3-tier architecture where the front-end is implemented using a web application that communicates with the back-end through a RESTful API.\\
The back-end, implemented using the Spring framework, will be responsible for the business logic of the platform, as well as the communication to the database, as it will be described in the following chapters while the front-end, following a lightweight architecture, is responsible only for the presentation of the data and the interaction with the user and will be implemented using the React framework.\\

