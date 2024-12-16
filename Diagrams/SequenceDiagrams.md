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
            Student&Company -->> Student: showPage(HomePage)
            deactivate Student&Company
    else Unsuccessful Authentication
        activate Student&Company
        Student&Company -->> Student: showError(InvalidCredentials)
        deactivate Student&Company
        end
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
            Student&Company -->> Company: showPage(HomePage)
            deactivate Student&Company
        else Unsuccessful Authentication
            activate Student&Company
            Student&Company -->> Company: showError(InvalidCredentials)
            deactivate Student&Company
        end
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
            Student&Company -->> University: showPage(HomePage)
            deactivate Student&Company
        else Unsuccessful Authentication
            activate Student&Company
            Student&Company -->> University: showError(InvalidCredentials)
            deactivate Student&Company
        end
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
        Student&Company -->> User: showPage(HomePage)
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
    activate Student
    activate Student&Company
    Student ->> Student&Company: press(LoadCurriculumMenu)
    Student&Company -->> Student: showPage(CurriculumPage)
    Student ->> Student&Company: submit(Curriculum)
    Student&Company ->> Student&Company: publish(Curriculum)
    Student&Company ->> Student&Company: generate(Matches)
    Student&Company -->> Student: showPage(AccountPage)
    deactivate Student&Company
    deactivate Student
```

# AdvertiseInternship
```mermaid
sequenceDiagram
    actor Company
    participant Student&Company
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
        Student&Company ->> Company: showMessage(Suggestions)
        Student&Company -->> Company: showPage(homePage)
        deactivate Student&Company
        deactivate Company
    end
```

# SpontaneousApplication
```mermaid
sequenceDiagram
    actor Student
    participant Student&Company
    actor Company
    activate Student
    activate Student&Company
    Student ->> Student&Company: press(BrowseInternships)
    Student&Company -->> Student: showPage(InternshipsPage)
    Student ->> Student&Company: press(Apply)
    Student&Company ->> Student&Company: update(CompanyApplications)
    Student&Company ->> Student&Company: update(StudentApplications)
    Student&Company ->> Company: notify(Application)
    Student&Company -->> Student: showMessage(Confirmation)
    deactivate Student
    deactivate Student&Company
```
%% Should Company be active or not?
%% Do the update functions make sense?

# AcceptMatch
```mermaid
sequenceDiagram
    actor Student/Company
    participant Student&Company
    actor Company/Student
    activate Student/Company
    activate Student&Company
    
    Student/Company ->> Student&Company: press(MyRecommendations)
    Student&Company -->> Student/Company: showPage(RecommendationsPage)
    Student/Company ->> Student&Company: press(Accept)
    Student&Company ->> Student&Company: update(Matches)
    alt Other party has already accepted
        Student&Company ->> Student&Company: update(Interview)
        Student&Company ->> Company/Student: notify(Interview)
        Student&Company ->> Student/Company: notify(Interview)
    end
    deactivate Student&Company
    deactivate Student/Company
```

# FeedbackMechanism
```mermaid
sequenceDiagram
    actor Student/Company
    participant Student&Company
    activate Student/Company
    activate Student&Company
    Student/Company ->> Student&Company: press(MyRecommendations)
    Student&Company -->> Student/Company: showPage(RecommendationsPage)
    Student/Company ->> Student&Company: press(Accept)/press(Reject)
    Student&Company -->> Student/Company: showMessage(AskFeedback)
    Student/Company ->> Student&Company: submit(Feedback)
    Student&Company ->> Student&Company: update(Feedback)
    deactivate Student&Company
    deactivate Student/Company
```

# AssignInterview
```mermaid
sequenceDiagram
    actor Company
    participant Student&Company
    actor Student
    activate Company
    activate Student&Company
    Company ->> Student&Company: press(MyInterviews)
    Student&Company -->> Company: showPage(MyInterviewsPage)
    Company ->> Student&Company: press(Student)
    Student&Company -->> Company: showPage(InterviewCreationPage)
    Company ->> Student&Company: submit(ToCompileInterview)
    Student&Company ->> Student&Company: check(Interview)
    alt Empty Interview
        Student&Company -->> Company: showError(EmptyInterview)
        deactivate Student&Company
    else Successful Check
        activate Student&Company
        Student&Company ->> Student: submit(Interview)
        Student&Company -->> Company: showPage(InterviewsPage)
        deactivate Student&Company
    end
    deactivate Company
```

# PublishComplaint
```mermaid
sequenceDiagram
    actor Company
    participant Student&Company
    actor Student
    actor University
    activate Company
    activate Student&Company
    Company ->> Student&Company: press(Complaints)
    Student&Company -->> Company: showPage(ComplaintsPage)
    Company ->> Student&Company: press(CreateComplaint)
    Student&Company -->> Company: showPage(ComplaintCreationPage)
    Company ->> Student&Company: submit(Complaint)
    Student&Company ->> Student&Company: update(Complaints)
    Student&Company -->> Company: showPage(homepage)
    Student&Company ->> Student: notify(Complaint)
    Student&Company ->> University: notify(Complaint)
    deactivate Student&Company
    deactivate Company
```

# RespondToComplaint
```mermaid
sequenceDiagram
    actor User
    participant Student&Company
    actor OtherUser
    activate User
    activate Student&Company
    User ->> Student&Company: press(Complaints)
    Student&Company -->> User: showPage(ComplaintsPage)
    User ->> Student&Company: press(TargetComplaint)
    Student&Company -->> User: showPage(ComplaintPage)
    User ->> Student&Company: submit(Message)
    Student&Company -->> OtherUser: notify(Message)
    deactivate Student&Company
    deactivate User
```

# TerminateInternship
```mermaid
sequenceDiagram
    actor University
    participant Student&Company
    actor Company/Student
    activate University
    activate Student&Company
    University ->> Student&Company: press(Complaints)
    Student&Company -->> University: showPage(ComplaintsPage)
    University ->> Student&Company: press(TerminateInternship)
    Student&Company ->> Company/Student: notify(InternshipTermination)
    deactivate Student&Company
    deactivate University
```