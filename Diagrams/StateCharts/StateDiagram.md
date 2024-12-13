```mermaid
%% Raccomandation Process Evolution
stateDiagram-v2
    [*] --> WaitForConfirmation: Raccomandation Found
    WaitForConfirmation --> Ended: One between Company<br> and Student reject
    WaitForConfirmation --> Ended: Both Company <br>and Student accept
    
    Ended --> [*]
```
```mermaid
%% Internship Evolution
stateDiagram-v2
    state if <<choice>>
    
    [*] --> InterviewCreation: Raccomandation has been Accepted
    InterviewCreation --> InterviewSubmitted: Company create and submits<br> Interview to student
    InterviewSubmitted --> if: Student compiles<br> and sends Interview
    InterviewSubmitted --> Ended: Deadline<br> expired
    if --> ManualReviewNeeded: Manual Review<br> needed
    ManualReviewNeeded --> Consolidation: Company manually<br> review the Interview
    if --> Consolidation: Complete automatic<br> review
    Consolidation --> Ended: Deadline<br> expired
    Consolidation --> Ended: Student accept<br> Internship
    Consolidation --> Ended: Student reject<br> Internship
    Ended --> [*] 

```

```mermaid
%% Interation between Raccomandation and Internship
stateDiagram-v2
    state if <<choice>>
    [*] --> WaitForResult: Raccomandation Found
    WaitForResult --> Rejected: One between Company<br> and Student reject
    WaitForResult --> Accepted: Both Company <br>and Student accept
    Rejected --> Ended: Close Raccomandation
    Accepted --> InterviewProcess: Start Internship<br>Interview
    state InterviewProcess{
        [*] --> InterviewCreation
        InterviewCreation --> InterviewSubmitted: Company subbmits<br> Interview to student
        InterviewSubmitted --> if: Student compiles<br> and sends Interview
        InterviewSubmitted --> Consolidation: Deadline expired
        if --> ManualReview: Manual Review<br> needed
        ManualReview --> Consolidation: Manual Review<br> completed
        if --> Consolidation: Complete automatic<br> review
        Consolidation --> [*]
    }
    InterviewProcess --> Ended: Timeout
    InterviewProcess --> Ended: Hired
    Ended --> [*]
```
