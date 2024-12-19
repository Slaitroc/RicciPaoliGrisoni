<!--Warning Mermeid is not case-sensitive, so it will give 
problems when trying to name an actor Participant. to fix, copy&paste an 
invisible character before and after the name:  THIS->(ㅤ)<-THIS    -->

# StudentSignup
```mermaid
sequenceDiagram
    actor  Student
    participant Student&Company
    participant  Email Provider
    activate Student
    Student ->> Student&Company: openApplication()
    activate Student&Company
    Student&Company -->> Student: showPage(HomePage)
    Student ->> Student&Company: press(SignUpMenu)
    Student&Company -->> Student: showPage(SignUpPage)
    Student ->> Student&Company: submit(StudentInfo)
    Student&Company ->> Student&Company: Check(email)
    alt Successful Authentication
        Student&Company ->> Student&Company: Register(Student)
        Student&Company ->> Email Provider: send(ConfirmationEmail)
        activate Email Provider
        Email Provider -) Student: send(ConfirmationEmail)
        Email Provider -->> Student&Company: confirmationEmailSent()
        Student&Company -->> Student: showMessage(ConfirmationEmailSent)
        deactivate Student&Company
        alt TimeOut Expired
            Email Provider -) Student&Company: emailExpired()
            activate Student&Company
            deactivate Email Provider
            Student&Company -->> Student: showError(CredentialsExpired)
            deactivate Student&Company
        else Email Confirmed
            activate Email Provider
            Student ->> Email Provider: confirmEmail()
            Email Provider -->> Student&Company: emailConfirmed()
            deactivate Email Provider
            activate Student&Company
            Student&Company ->> Student&Company: Activate(Student)
            Student&Company -->> Student: showPage(DashBoard)
            deactivate Student&Company
        end
    else Unsuccessful Authentication
        activate Student&Company
        Student&Company -->> Student: showError(InvalidCredentials)
        deactivate Student&Company
    end
    deactivate Student
```

# CompanySignUp
```mermaid
sequenceDiagram
    actor  Company
    participant Student&Company
    participant  Email Provider
    activate Company
    Company ->> Student&Company: openApplication()
    activate Student&Company
    Student&Company -->> Company: showPage(HomePage)
    Company ->> Student&Company: press(SignUpMenu)
    Student&Company -->> Company: showPage(SignUpPage)
    Company ->> Student&Company: submit(CompanyInfo)
    Student&Company ->> Student&Company: Check(VAC)
    alt Successful Authentication
        Student&Company ->> Student&Company: Register(Company)
        Student&Company ->> Email Provider: send(ConfirmationEmail)
        activate Email Provider
        Email Provider -) Company: send(ConfirmationEmail)
        Email Provider -->> Student&Company: confirmationEmailSent()
        Student&Company -->> Company: showMessage(ConfirmationEmailSent)
        deactivate Student&Company
        alt TimeOut Expired
            Email Provider -) Student&Company: emailExpired()
            activate Student&Company
            deactivate Email Provider
            Student&Company -->> Company: showError(CredentialsExpired)
            deactivate Student&Company
        else Email Confirmed
            activate Email Provider
            Company ->> Email Provider: confirmEmail()
            Email Provider -->> Student&Company: emailConfirmed()
            deactivate Email Provider
            activate Student&Company
            Student&Company ->> Student&Company: Activate(Company)
            Student&Company -->> Company: showPage(DashBoard)
            deactivate Student&Company
        end
        else Unsuccessful Authentication
            activate Student&Company
            Student&Company -->> Company: showError(InvalidCredentials)
            deactivate Student&Company
    end
    deactivate Company
```

# UniversitySignUp
```mermaid
sequenceDiagram
    actor  University
    participant Student&Company
    participant  Email Provider
    activate University
    University ->> Student&Company: openApplication()
    activate Student&Company
    Student&Company -->> University: showPage(HomePage)
    University ->> Student&Company: press(SignUpMenu)
    Student&Company -->> University: showPage(SignUpPage)
    University ->> Student&Company: submit(UniversityInfo)
    Student&Company ->> Student&Company: Check(VAC)
    alt Successful Authentication
        Student&Company ->> Student&Company: Register(University)
        Student&Company ->> Email Provider: send(ConfirmationEmail)
        activate Email Provider
        Email Provider -) University: send(ConfirmationEmail)
        Email Provider -->> Student&Company: confirmationEmailSent()
        Student&Company -->> University: showMessage(ConfirmationEmailSent)
        deactivate Student&Company
        alt TimeOut Expired
            Email Provider -) Student&Company: emailExpired()
            activate Student&Company
            deactivate Email Provider
            Student&Company -->> University: showError(CredentialsExpired)
            deactivate Student&Company
        else Email Confirmed
            activate Email Provider
            University ->> Email Provider: confirmEmail()
            Email Provider -->> Student&Company: emailConfirmed()
            deactivate Email Provider
            activate Student&Company
            Student&Company ->> Student&Company: Activate(University)
            Student&Company -->> University: showPage(DashBoard)
            deactivate Student&Company
        end
        else Unsuccessful Authentication
            activate Student&Company
            Student&Company -->> University: showError(InvalidCredentials)
            deactivate Student&Company
    end
    deactivate University
```

# UserSignIn
```mermaid
sequenceDiagram
    actor User
    participant Student&Company
    activate User
    User ->> Student&Company: openApplication()
    activate Student&Company
    Student&Company -->> User: showPage(HomePage)
    User ->> Student&Company: press(SignInMenu)
    Student&Company -->> User: showPage(SignInPage)
    User ->> Student&Company: submit(Credentials)
    Student&Company ->> Student&Company: Authenticate(Credentials)
    alt Successful Authentication
        Student&Company ->> Student&Company: SignIn(User)
        Student&Company -->> User: showPage(DashBoard)
        deactivate Student&Company
    else Unsuccessful Authentication
        activate Student&Company
        Student&Company -->> User: showError(InvalidCredentials)
        deactivate Student&Company
    end
    deactivate User
```

# LoadCurriculum
```mermaid
sequenceDiagram
    actor Student
    participant Student&Company
    participant NotificationManager
    actor Company
    activate Student
    activate Student&Company
    Student ->> Student&Company: press(AccountMenu)
    Student ->> Student&Company: press(LoadCurriculum)
    Student&Company -->> Student: showPage(CurriculumPage)
    Student ->> Student&Company: submit(Curriculum)
    Student&Company ->> Student&Company: Check(Curriculum)
    alt Missing Informations
        Student&Company -->> Student: showError(EmptyCurriculum)
        deactivate Student&Company
    else Successful Check
        activate Student&Company
        Student&Company ->> Student&Company: publish(Curriculum)
        Student&Company ->> Student&Company: generate(Matches)
        Student&Company ->> Student: provideSuggestions()
        Student&Company -->> Student: showPage(AccountPage)
        loop For Each New Match
            Student&Company ->> NotificationManager: notify(Match, Company)
            activate NotificationManager
            NotificationManager -->> Company: SendNotification()
            NotificationManager -->> Student&Company: confirmation()
            deactivate NotificationManager 
        end
    end
        deactivate Student&Company
        deactivate Student
    
```

# AdvertiseInternship
```mermaid
sequenceDiagram
    actor Company
    participant Student&Company
    participant NotificationManager
    actor Student
    activate Company
    activate Student&Company
    Company ->> Student&Company: press(MyInternships)
    Student&Company -->> Company: showPage(MyInternshipPage)
    Company ->> Student&Company: press(CreateInternship)
    Student&Company -->> Company: showPage(InternshipCreationPage)
    Company ->> Student&Company: submit(Internship)
    Student&Company ->> Student&Company: check(Internship)
    alt Missing Informations
        Student&Company -->> Company: showError(MissingInfos)
        deactivate Student&Company
    else Successful Check
        activate Student&Company
        Student&Company ->> Student&Company: publish(Internship)
        Student&Company ->> Student&Company: generate(Matches)
        Student&Company ->> Company: provideSuggestions()
        Student&Company -->> Company: showPage(MyInternshipsPage)
        loop For Each New Match
        Student&Company ->> NotificationManager: notify(Match, Company)
        activate NotificationManager
        NotificationManager -->> Student: SendNotification()
        NotificationManager -->> Student&Company: confirmation()
        end
        deactivate NotificationManager
        deactivate Student&Company
        deactivate Company
    end
```

# SpontaneousApplication
```mermaid
sequenceDiagram
    actor Student
    participant Student&Company
    participant NotificationManager
    actor Company
    activate Student
    activate Student&Company
    Student ->> Student&Company: press(BrowseInternships)
    Student&Company -->> Student: showPage(InternshipsPage)
    Student ->> Student&Company: press(Apply)
    alt Internship No Longer Available
        Student&Company -->> Student: showError(NoLongerAvailable)
        deactivate Student&Company
    else Successful Application
        activate Student&Company
    Student&Company ->> Student&Company: update(Applications)
    Student&Company ->> NotificationManager: notify(Application)
    activate NotificationManager
    NotificationManager -->> Company: SendNotification()
    NotificationManager -->> Student&Company: confirmation()
    deactivate NotificationManager
    Student&Company -->> Student: showMessage(Success)
    end
    deactivate Student
    deactivate Student&Company
```

# AcceptMatch
```mermaid
sequenceDiagram
    actor Participant1
    participant Student&Company
    participant NotificationManager
    actor Participant2
    activate Participant1
    activate Student&Company
    Participant1 ->> Student&Company: press(MyRecommendations)
    Student&Company -->> Participant1: showPage(RecommendationsPage)
    Participant1 ->> Student&Company: press(Accept)
    Student&Company ->> Student&Company: update(Matches)
    alt Other Party Has Already Accepted
        Student&Company ->> Student&Company: update(Interviews)
        Student&Company -->> Participant1: showMessage(Interview)
        Student&Company ->> NotificationManager: notify(Interview, Company/Student)
        activate NotificationManager
        NotificationManager ->> Participant2: SendNotification()
        NotificationManager -->> Student&Company: confirmation()
        deactivate NotificationManager
        deactivate Student&Company
    else Other Party Has Not Accepted
        activate Student&Company
        Student&Company -->> Participant1: showMessage(Wait)
    end
    deactivate Student&Company
    deactivate Participant1
```

# FeedbackMechanism
```mermaid
sequenceDiagram
    actor ㅤParticipantㅤ
    participant Student&Company
    activate ㅤParticipantㅤ
    activate Student&Company
    ㅤParticipantㅤ ->> Student&Company: press(MyRecommendations)
    Student&Company -->> ㅤParticipantㅤ: showPage(RecommendationsPage)
    ㅤParticipantㅤ ->> Student&Company: press(Accept)/press(Reject)
    Student&Company -->> ㅤParticipantㅤ: showMessage(AskFeedback)
    ㅤParticipantㅤ ->> Student&Company: submit(Feedback)
    Student&Company ->> Student&Company: update(Feedback)
    deactivate Student&Company
    deactivate ㅤParticipantㅤ
```

# AssignInterview
```mermaid
sequenceDiagram
    actor Company
    participant Student&Company
    participant NotificationManager
    actor Student
    activate Company
    activate Student&Company
    Company ->> Student&Company: press(MyInterviews)
    Student&Company -->> Company: showPage(MyInterviewsPage)
    Company ->> Student&Company: press(Student)
    Student&Company -->> Company: showPage(InterviewCreationPage)
    Company ->> Student&Company: submit(ToCompileInterview)
    Student&Company ->> Student&Company: check(Interview)
    alt Missing Informations
        Student&Company -->> Company: showError(MissingInformation)
        deactivate Student&Company
    else Successful Check
        activate Student&Company
        Student&Company ->> Student&Company: update(InterviewStatus)
        Student&Company ->> NotificationManager: notify(Interview, Student)
        activate NotificationManager
        NotificationManager -->> Student: SendNotification()
        NotificationManager -->> Student&Company: confirmation()
        deactivate NotificationManager
        Student&Company -->> Company: showPage(MyInterviewsPage)
        deactivate Student&Company
    end
    deactivate Company
```

# PublishComplaint
```mermaid
sequenceDiagram
    actor Participant1
    participant Student&Company
    participant NotificationManager
    actor Participant2
    actor University
    activate Participant1
    activate Student&Company
    Participant1 ->> Student&Company: press(Complaints)
    Student&Company -->> Participant1: showPage(ComplaintsPage)
    Participant1 ->> Student&Company: press(CreateComplaint)
    Student&Company -->> Participant1: showPage(ComplaintCreationPage)
    Participant1 ->> Student&Company: submit(Complaint)
    Student&Company ->> Student&Company: update(Complaints)
    Student&Company ->> NotificationManager: notify(Complaint, Participant2, University)
    activate NotificationManager
    NotificationManager -->> Participant2: SendNotification()
    NotificationManager -->> University: SendNotification()
    NotificationManager -->> Student&Company: confirmation()
    deactivate NotificationManager
    Student&Company -->> Participant1: showMessage(Success)
    deactivate Student&Company
    deactivate Participant1
```

# RespondToComplaint
```mermaid
sequenceDiagram
    actor User1
    participant Student&Company
    participant NotificationManager 
    actor User2
    activate User1
    activate Student&Company
    User1 ->> Student&Company: press(Complaints)
    Student&Company -->> User1: showPage(ComplaintsPage)
    User1 ->> Student&Company: press(TargetComplaint)
    Student&Company -->> User1: showPage(ComplaintPage)
    User1 ->> Student&Company: submit(Message)
    Student&Company ->> NotificationManager: notify(Message, User2)
    activate NotificationManager
    NotificationManager -->> User2: SendNotification()
    NotificationManager -->> Student&Company: confirmation()
    deactivate NotificationManager
    Student&Company -->> User1: showMessage(Success)
    deactivate Student&Company
    deactivate User1
```
# HandleComplaint
```mermaid
sequenceDiagram
    actor University
    participant Student&Company
    participant NotificationManager
    actor Student
    actor Company
    activate University
    activate Student&Company
    University ->> Student&Company: press(Complaints)
    Student&Company -->> University: showPage(ComplaintsPage)
    University ->> Student&Company: press(TargetComplaint)
    Student&Company -->> University: showPage(ComplaintPage)
    University ->> Student&Company: submit(Message)
    Student&Company ->> Student&Company: update(Complaints)
    Student&Company ->> NotificationManager: notify(InternshipTermination, Student, University)
    activate NotificationManager
    NotificationManager -->> Student: SendNotification()
    NotificationManager -->> Company: SendNotification()
    NotificationManager -->> Student&Company: confirmation()
    deactivate NotificationManager
    Student&Company -->> University: showMessage(Success)
    deactivate Student&Company
    deactivate University
```

# TerminateInternship
```mermaid
sequenceDiagram
    actor University
    participant Student&Company
    participant NotificationManager
    actor Student
    actor Company
    activate University
    activate Student&Company
    University ->> Student&Company: press(Complaints)
    Student&Company -->> University: showPage(ComplaintsPage)
    University ->> Student&Company: press(TargetComplaint)
    Student&Company -->> University: showPage(ComplaintPage)
    University ->> Student&Company: press(TerminateInternship)
    Student&Company ->> Student&Company: update(Complaints)
    Student&Company ->> Student&Company: update(Internships)
    Student&Company ->> NotificationManager: notify(InternshipTermination, Student, University)
    activate NotificationManager
    NotificationManager -->> Student: SendNotification()
    NotificationManager -->> Company: SendNotification()
    NotificationManager -->> Student&Company: confirmation()
    deactivate NotificationManager
    Student&Company -->> University: showMessage(Success)
    deactivate Student&Company
    deactivate University
```