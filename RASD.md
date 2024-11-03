# RASD

## Assignment

Students&Companies (S&C) is a platform that helps match university students looking for internships and companies offering them. The platform should ease the matching between students and companies based on:

- the experiences, skills and attitudes of students, as listed in their CVs;
- the projects (application domain, tasks to be performed, relevant adopted technologies-if any-etc.) and terms offered by companies (for example, some company might offer paid internships and/or provide both tangible and intangible benefits, such as training, mentorships, etc.).

The platform is used by companies to advertise the internships that they offer, and by students to look for internships. Students can be proactive when they look for internships (i.e., they initiate the process, go through the available internships, etc.). Moreover, the system also has mechanisms to inform students when an internship that might interest them becomes available and can inform companies about the availability of student CVs corresponding to their needs. We refer to this process as “recommendation”.

Recommendation in S&C can employ mechanisms of various level of sophistication to match students with internships, from simple keyword searching, to statistical analyses based on the characteristics of students and internships.

When suitable recommendations are identified and accepted by the two parties, a contact is established. After a contact is established, a selection process starts. During this process, companies interview students (and collect answers from them, possibly through structured questionnaires) to gauge their fit with the company and the internship. S&C supports this selection process by helping manage (set up, conduct, etc.) interviews and also finalize the selections.

To feed statistical analysis applied during recommendation, S&C collects various kinds of information regarding the internships, for example by asking students and companies to provide feedback and suggestions.

Moreover, S&C should be able to provide suggestions both to companies and to students regarding how to make their submissions (project descriptions for companies and CVs for students) more appealing for their counterparts.

In general, S&C provides interested parties with mechanisms to keep track and monitor the execution and the outcomes of the matchmaking process and of the subsequent internships from the point of view of all interested parties. For example, it provides spaces where interested parties can complain, communicate problems, and provide information about the current status of the ongoing internship. The platform is used by students at different universities. Universities also need to monitor the situation of internships; in particular, they are responsible for handling complaints, especially ones that might require the interruption of the internship.

## Fine Grained Goals

1. Companies can advertise the internship they offer
2. Companies can enter information about the internship they offer (application domain, tasks to be performed, relevant adopted technologies if-any, terms & benefits)
3. Students can enter their CV
4. Students can actively look for internships
5. Platform matches students with internships in a process called "recommendation" using different mechanism based on the information provided by both
6. Platform notifies Students and Companies when the recommendation process find a suitable match
7. Students and Companies can accept a suitable recommendation
8. Companies can interview students that has accepted a recommendation collecting answers from them (selection process)
9. Companies can manage (set-up, conduct, finalize) interviews
10. Platform collects information from Student and Companies to improve the recommendation process through feedbacks and suggestions about internships
11. Platform provides suggestions to both Student and Companies regarding how to make their submissions.
12. Students and Companies can monitor the execution and outcome of recommendation process
13. Students and Companies can complain, communicate problems, provide information about an ongoing internship
14. Universities can monitor ongoing internships
15. Universities can handle complains about ongoing internships
16. Universities can interrupt an internship due to complains

## Goals

## World Phenomena

1. A Company wants to advertise its internship
2. A Student wants to look for an internship
3. A Company wants to accept a suitable recommendation (??)
4. A Student wants to accept a suitable recommendation (??)
5. A Company wants to interview a Student during selection process
6. A Company wants to manage interviews (??)
7. A Students wants to answer questions concerning a selection process
8. A Company wants to complain, communicates problem, provide information about an ongoing internship (??)
9. A Students wants to complain, communicates problem, provide information about an ongoing internship (??)
10. A University wants to monitor an ongoing internship
11. A University wants to handle complains about an ongoing internship
12. A University wants to interrupt an ongoing internship (??)

## Shared Phenomena

1. A Company starts the process of internship submission
2. A Company inserts the information about an internship it's offering
3. A Company submits information about an internship
4. A Student inserts his CV
5. A Student starts proactive research of internships
6. An Actor initiates the process of recommendation monitoring
7. An Actor terminates the process of recommendation monitoring
8. An Actor accepts a suitable recommendation
9. A Company starts the process of setting up an interview for a suitable accepted recommendation
10. A Company starts the process of conducting a previously set up interview
11. A Company starts the process of finalizing a previously conducted interview
12. An Actor complains, communicates problem, provides information about an ongoing internship
13. A Student begins answering questions related to an interview
14. An Actor monitors his recommendation process
15. A University handles a complaint about an ongoing internship
16. A University interrupts an internship
17. A University monitors an ongoing internship

### Controlled By the Machine (Fine Grained)

1. The Platform presents the interface for Companies' internship data submission
2. The Platform presents the interface for Students' CV submission
3. The Platform presents the interface for actively searching available internships
4. The Platform presents the interface for monitoring recommendation
5. The Platform presents the interface for a Company to set up an interview
6. The Platform presents the interface for a Company to conduct previously set up interviews
7. The Platform presents the interface for a Company to finalize previously conducted interviews
8. The Platform displays the interface for a Student to answer interview questions
9. The Platform presents the interface for complaining, communicating problems, providing information about an ongoing internship
10. The Platform presents the interface for a University to handle complains
11. The Platform presents the interface for a University to Monitor an ongoing internship
12. The Platform presents the interface for a University to interrupt an ongoing internship
13. The Platform notifies Students and Companies when a suitable recommendation is found
14. The Platform provides Students and Companies suggestions about how to make their submissions
15. The Platform asks for feedback and suggestions to improve the recommendation process

## Domain Assumption

1. Students, Companies and Universities has a working and stable internet connection
2. Students and Companies provides the Platform with correct information
3. Universities interrupt an ongoing internship only if no solution to complains/problems are found
4. Universities, Students and Companies actively interact with the Platform

## Question about the RASD

These questions require an answer reached by mutual agreement

- La ricerca proattiva del tirocinio eseguita dallo studente consente allo stesso di proporsi o solo di scorrere le offerte disponibili?
