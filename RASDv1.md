# Requirements and Analysis Specification Document

## Table of Contents

1. [Introduction](#introduction)
    - [Purpose](#purpose)
    - [Goals](#goals)
    - [Scope](#scope)
    - [World Phenomena](#world-phenomena)
    - [Shared Phenomena](#shared-phenomena)
    - [Definitions, Acronyms, Abbreviations](#definitions-acronyms-abbreviations)
      - [Definition](#definition)
      - [Acronyms](#acronyms)
      - [Abbreviations](#abbreviations)
    - [Revision History](#revision-history)
    - [Reference Documents](#reference-documents)
    - [Document Structure](#document-structure)

2. [Overall Description](#overall-description)
    - [Product Prospective](#product-prospective)
        - [User Scenarios](#user-scenarios)
        - [Class Diagrams](#class-diagrams)
        - [State Charts](#state-charts)
    - [Product Functions](#product-functions)
        - [Requirements](#requirements)   
    - [User Characteristics](#user-characteristics)
    - [Assumptions, dependencies and constraints](#assumptions,-dependencies-and-constraints)
        - [Domain Assumptions](#domain-assumptions)
        - [Dependencies](#dependencies)



3. [Specific Requirements](#specific-requirements)
    - [External Interface Requirements](#external-interface-requirements)
      - [User Interfaces](#user-interfaces)
      - [Hardware Interfaces](#hardware-interfaces)
      - [Software Interfaces](#software-interfaces)
      - [Communication Interfaces](#communication-interfaces)
    - [Functional Requirements](#functional-requirements)
      - [Use Case Diagrams](#use-case-diagrams)
      - [Use Cases](#use-cases)
      - [Sequence Diagrams](#sequence-diagrams)
      - [Requirements Mapping](#requirements-mapping)
    - [Performance Requirements](#performance-requirements)
    - [Design Constraints](#design-constraints)
      - [Standards Compliance](#standards-compliance)
      - [Hardware Limitations](#hardware-limitations)
    - [Software System Attributes](#software-system-attributes)
      - [Reliability & Availability](#reliability--availability)
      - [Security](#security)
      - [Maintainability](#maintainability)
      - [Portability](#portability)

4. [Formal Analysis Using Alloy](#formal-analysis-using-alloy)
    - [Signatures](#signatures)
    - [Facts](#facts)
    - [Assertions](#assertions)
    - [Model Visualization](#model-visualization)

5. [Effort Spent](#effort-spent)

6. [References](#references)

7. [Assignment Description](#assignment-description)

## Introduction

### Purpose

The purpose of the Student&Company (S&C) Platform is to create a system that allows Students to find
Internships to enhance their education and improve their curriculum, while allowing Companies to find
suitable candidates for their internship programs. All of this is done in a simple and efficient way by
providing a series of tools to help both parties in the process.
S&C will support the entire lifecycle of the Internship process for both Students and Companies. The
matchmaking can be done both automatically by the system through a proprietary Recommendation Pro-
cess or by a Student with a Spontaneous Application to a specific internship offer. The final selection
process is done through interviews created and submitted by Companies directly on the platform.
In the meantime, Student&Company will also provide Students and Companies with a series of sugges-
tions to improve their respectively published CVs and internship offers. The platform will also allow
Universities whose Students are actually doing an internship to monitor the progress of such activities
and handle any Complaints that may arise, even by terminating the internship if no other solution to the
problem can be found.

### Goals

| **Goal ID** | **Goal Description** |
|-------------|-----------------------|
| **G1**      | Companies would like to advertise the internships they offer. |
| **G2**      | Students would like to autonomously candidate for available internships. |
| **G3**      | Students would like to be matched with internships they might be interested in. |
| **G4**      | Companies would like to perform interviews with suitable students. |
| **G5**      | Students and companies would like to complain, communicate problems, and provide information about an ongoing internship. |
| **G6**      | Students and companies would like to be provided with suggestions about how to improve their submission. |
| **G7**      | Universities would like to handle complaints about ongoing internships. |
| **G8**      | Students would like to choose which internship to attend from among those for which they passed the interview. |
| **G9**      | Companies would like to select students for the internship position among those who passed the interview. |

### Scope

### World Phenomena

| **Phenomenon ID** | **Phenomenon Description** |
|--------------------|-----------------------------|
| **WP1**           | A company wants to advertise its internship. |
| **WP2**           | A student wants to apply for an internship. |
| **WP3**           | A company wants to accept a suitable recommendation. |
| **WP4**           | A company wants to accept a student’s spontaneous application. |
| **WP5**           | A student wants to accept a suitable recommendation. |
| **WP6**           | A company wants to interview a student. |
| **WP7**           | A company wants to manage (create, visualize, send, evaluate) its interviews. |
| **WP8**           | A student wants to answer questions concerning an interview. |
| **WP9**           | A company wants to complain, communicate problems, provide information about an ongoing internship. |
| **WP10**          | A student wants to complain, communicate problems, provide information about an ongoing internship. |
| **WP11**          | A student wants to receive suggestions on how to improve their CV. |
| **WP12**          | A company wants to receive suggestions on how to improve their internship offers. |
| **WP13**          | A university wants to monitor an ongoing internship that involves one of their students. |
| **WP14**          | A university wants to handle complaints about an ongoing internship that involves one of their students. |
| **WP15**          | A university wants to interrupt an ongoing internship that involves one of their students. |
| **WP16**          | A company wants to choose from the students they are interested in the ones to whom they will offer the internship position. |
| **WP17**          | A student wants to choose an internship position offer. |

### Shared Phenomena

#### Shared Phenomena by World

| **Phenomenon ID** | **Phenomenon Description** |
|--------------------|-----------------------------|
| **SPW1**          | A company publishes an internship. |
| **SPW2**          | A student inserts their CV. |
| **SPW3**          | A company accepts a spontaneous application. |
| **SPW4**          | A student or company monitors recommendations. |
| **SPW5**          | A student or a company accepts a recommendation. |
| **SPW6**          | A company creates interviews for a specific internship. |
| **SPW7**          | A company sends a previously created interview to a student. |
| **SPW8**          | A company evaluates a previously sent interview that has been answered by the student. |
| **SPW9**          | A student answers questions related to an interview. |
| **SPW10**         | A company sends to a student who passed the interview an internship position offer. |
| **SPW11**         | A student accepts an internship position offer. |
| **SPW12**         | A student or company complains, communicates problems, provides information about an ongoing internship. |
| **SPW13**         | A university responds to complaints about ongoing internships that involve one of their students. |
| **SPW14**         | A university interrupts an internship that involves one of their students. |

#### Shared Phenomena by Machine

| **Phenomenon ID** | **Phenomenon Description** |
|--------------------|-----------------------------|
| **SPM1**          | The platform shows to students available internships. |
| **SPM2**          | The platform shows to companies available candidates for their internships. |
| **SPM3**          | The platform shows to students and companies information about their recommendations and spontaneous applications. |
| **SPM4**          | The platform asks for feedback to improve the recommendation research mechanism. |
| **SPM5**          | The platform notifies students and companies when a suitable recommendation involving them is found. |
| **SPM6**          | The platform shows to companies information about their interviews. |
| **SPM7**          | The platform provides students and companies suggestions about how to formulate better their CVs and internship offers. |
| **SPM8**          | The platform shows to universities information about an ongoing internship. |
| **SPM9**          | The platform notifies companies when there is a new spontaneous application regarding one of their internships. |
| **SPM10**         | The platform notifies a student when a company accepts their spontaneous application. |
| **SPM11**         | The platform notifies a student when a company sends them a new interview. |
| **SPM12**         | The platform notifies a student when a company evaluates an interview they previously sent. |
| **SPM13**         | The platform notifies a student when a company sends them an internship offer. |
| **SPM14**         | The platform notifies a company when a student accepts their internship position offer. |
| **SPM15**         | The platform notifies the involved student and the company when a university terminates their internship. |
| **SPM16**         | The platform notifies the university when there is a new complaint or problem about an ongoing internship regarding one of their students. |


### Definitions, Acronyms, Abbreviations

#### Definition

#### Acronyms

#### Abbreviations

### Revision History

### Reference Documents

### Document Structure

## Overall Description

### Product Prospective

#### User Scenarios

#### Class Diagrams

#### State Charts

### Product Functions

#### Requirements

| **Requirement ID** | **Requirement Description** |
|---------------------|-----------------------------|
| **R1**             | The platform shall allow any unregistered students to register by providing personal information and selecting their University. |
| **R2**             | The platform shall allow any companies to register by providing company information. |
| **R3**             | The platform shall allow any universities to register by providing university information. |
| **R4**             | The platform shall allow Users to log in using their email and password. |
| **R5**             | The platform shall send notifications to Users when relevant events occur. |
| **R6**             | The platform shall allow Companies to create and publish Internship offers specifying details. |
| **R7**             | The platform shall allow Companies to terminate their Internship offers at their own discretion. |
| **R8**             | The platform shall provide Students with Matches automatically obtained by the Recommendation Process. |
| **R9**             | The platform shall allow Students to view and navigate all available Internships. |
| **R10**            | The platform shall enable Students to submit Spontaneous Applications to Internships they choose. |
| **R11**            | The platform shall allow Students to submit their CV. |
| **R12**            | The platform shall allow Students to modify their CV. |
| **R13**            | The platform shall allow Students to monitor the status of their Spontaneous Applications. |
| **R14**            | The platform shall allow Students to monitor the status of their Recommendation. |
| **R15**            | The platform shall display to Students all the Internships found by the Recommendation Process. |
| **R16**            | The platform shall display to Companies all the CVs of Matched Students obtained by the Recommendation Process. |
| **R17**            | The platform shall allow Students and Companies to accept a Recommendation. |
| **R18**            | The platform shall allow Companies to accept a Spontaneous Application. |
| **R19**            | The platform shall start a Selection Process only if both the Company and the Student have accepted the Recommendation. |
| **R20**            | The platform shall start a Selection Process only if the Company has accepted the Spontaneous Application. |
| **R21**            | The platform shall allow Companies to create Interviews. |
| **R22**            | The platform shall allow Companies to submit Interviews to Students they have initiated a Selection Process with. |
| **R23**            | The platform shall allow Students to answer Interview questions and submit them. |
| **R24**            | The platform shall allow Companies to manually evaluate Interview submissions. |
| **R25**            | The platform shall allow Students and Companies to monitor the status of their Interviews. |
| **R26**            | The platform shall enable Companies to complete the Interview process by submitting the final outcome to each candidate. |
| **R27**            | The platform shall enable Companies to send an Internship Position Offer to a Student only if he previously passed the relative Interview. |
| **R28**            | The platform shall enable Students to accept or reject an Internship Position Offer sent by a Company only if he previously passed the relative Interview. |
| **R29**            | The platform shall collect Feedback from both Students and Companies regarding the Recommendation Process. |
| **R30**            | The platform shall provide Suggestions to Students on improving their CVs. |
| **R31**            | The platform shall provide Suggestions to Companies on improving Internship descriptions. |
| **R32**            | The platform shall allow registered Universities to access and monitor Internship Communications related to their Students. |
| **R33**            | The platform shall provide a dedicated space for Students and Companies to exchange Communications about the current status of an Ongoing Internship. |
| **R34**            | The platform shall allow registered Universities to handle Complaints and to interrupt an Internship at their own discretion. |


### User Characteristics

### Assumptions, dependencies and constraints

#### Domain Assumptions

| **Assumption ID** | **Assumption Description** |
|--------------------|----------------------------|
| **D1**            | Students and Companies provide the Platform with correct and truthful information. |
| **D2**            | Companies remove published Internships if they are no longer available. |
| **D3**            | The Email Provider and Notification Manager services are reliable, and the Users visualize every notification. |
| **D4**            | Students, Companies, and Universities have a working internet connection. |
| **D5**            | Universities interrupt an Ongoing Internship only if no solution is found to the Complaints. |

#### Dependencies

###

## Specific Requirements

### External Interface Requirements

#### User Interfaces

#### Hardware Interfaces

#### Software Interfaces

#### Communication Interfaces

### Functional Requirements

#### Use Case Diagrams

#### Use Cases

#### Sequence Diagrams

#### Requirements Mapping

### Performance Requirements

### Design Constraints

#### Standards Compliance

#### Hardware Limitations

### Software System Attributes

#### Reliability & Availability

#### Security

#### Maintainability

#### Portability

## Formal Analysis Using Alloy

### Signatures

### Facts

### Assertions

### Model Visualization

## Effort Spent

## References

## Assignment Description

Students&Companies (S&C) is a platform that helps match university students looking for internships and companies offering them. The platform should ease the matching between students and companies based on:

- the experiences, skills and attitudes of students, as listed in their CVs;
- the projects (application domain, tasks to be performed, relevant adopted technologies-if any-etc.) and terms offered by companies (for example, some company might offer paid internships and/or provide both tangible and intangible benefits, such as training, mentorship, etc.).

The platform is used by companies to advertise the internships that they offer, and by students to look for internships. Students can be proactive when they look for internships (i.e., they initiate the process, go through the available internships, etc.). Moreover, the system also has mechanisms to inform students when an internship that might interest them becomes available and can inform companies about the availability of student CVs corresponding to their needs. We refer to this process as “recommendation”.

Recommendation in S&C can employ mechanisms of various levels of sophistication to match students with internships, from simple keyword searching, to statistical analyses based on the characteristics of students and internships.

When suitable recommendations are identified and accepted by the two parties, a contact is established. After a contact is established, a selection process starts. During this process, companies interview students (and collect answers from them, possibly through structured questionnaires) to gauge their fit with the company and the internship. S&C supports this selection process by helping manage (set-up, conduct, etc.) interviews and also finalize the selections.

To feed statistical analysis applied during recommendation, S&C collects various kinds of information regarding the internships, for example, by asking students and companies to provide feedback and suggestions.

Moreover, S&C should be able to provide suggestions both to companies and to students regarding how to make their submissions (project descriptions for companies and CVs for students) more appealing for their counterparts.

In general, S&C provides interested parties with mechanisms to keep track and monitor the execution and the outcomes of the matchmaking process and of the subsequent internships from the point of view of all interested parties. For example, it provides spaces where interested parties can complain, communicate problems, and provide information about the current status of the ongoing internship. The platform is used by students at different universities. Universities also need to monitor the situation of internships; in particular, they are responsible for handling complaints, especially ones that might require the interruption of the internship.

