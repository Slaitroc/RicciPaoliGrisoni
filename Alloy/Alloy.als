    /*
    Students&Companies (S&C) is a platform that helps match university students looking for internships and companies offering them. The platform should ease the matching between students and companies based on:

    - the experiences, skills and attitudes of students, as listed in their CVs;
    - the projects (application domain, tasks to be performed, relevant adopted technologies-if any-etc.) and terms offered by companies (for example, some company might offer paid internships and/or provide both tangible and intangible benefits, such as training, mentorship, etc.).

    The platform is used by companies to advertise the internships that they offer, and by students to look for internships. Students can be proactive when they look for internships (i.e., they initiate the process, go through the available internships, etc.). Moreover, the system also has mechanisms to inform students when an internship that might interest them becomes available and can inform companies about the availability of student CVs corresponding to their needs. We refer to this process as “recommendation”.

    Recommendation in S&C can employ mechanisms of various levels of sophistication to match students with internships, from simple keyword searching, to statistical analyses based on the characteristics of students and internships.

    When suitable recommendations are identified and accepted by the two parties, a contact is established. After a contact is established, a selection process starts. During this process, companies interview students (and collect answers from them, possibly through structured questionnaires) to gauge their fit with the company and the internship. S&C supports this selection process by helping manage (set-up, conduct, etc.) interviews and also finalize the selections.

    To feed statistical analysis applied during recommendation, S&C collects various kinds of information regarding the internships, for example, by asking students and companies to provide feedback and suggestions.
    Moreover, S&C should be able to provide suggestions both to companies and to students regarding how to make their submissions (project descriptions for companies and CVs for students) more appealing for their counterparts.

    In general, S&C provides interested parties with mechanisms to keep track and monitor the execution and the outcomes of the matchmaking process and of the subsequent internships from the point of view of all interested parties. For example, it provides spaces where interested parties can complain, communicate problems, and provide information about the current status of the ongoing internship. The platform is used by students at different universities. Universities also need to monitor the situation of internships; in particular, they are responsible for handling complaints, especially ones that might require the interruption of the internship.

    */

    sig Email, VatNumber, CV{}{
        //No Email, VatNumber or CV can be created without an associated user
        Email = User.userEmail
        VatNumber = (Company.companyVatNumber + University.universityVatNumber)
        CV = Student.cv
    }

    abstract sig User {
        userEmail: one Email,
    }

    sig Student extends User {
        enrolledIn: one University,
        cv: lone CV,
        recommendations: set Recommendation,
        spontaneousApplications: set SpontaneousApplication
    }

    sig University extends User {
        universityVatNumber: one VatNumber,
    }

    sig Company extends User {
        companyVatNumber: one VatNumber,
        offeredInternshipPosition: set InternshipsOffer,
    }

    sig InternshipsOffer{
        recommendations: set Recommendation,
        spontaneousApplications: set SpontaneousApplication
    }{
        //A InternshipOffer exists only if a company has offered it
        InternshipsOffer = Company.offeredInternshipPosition
    }

    /*
    Define the possible status of a Recommendation.
    - toBeAccepted represents a match by the Platform 
    - acceptedByStudent and acceptedByCompany are refer in the document as "PendingMatch"
    - acceptedMatch and rejectedMatch have the same definition as in the document
    */
    enum recommendationStatus{toBeAccepted, acceptedByStudent, acceptedByCompany, acceptedMatch, rejectedMatch}

    /*
    Define the possible status of a SpontaneousApplication.
    - toBeEvaluated represents the sending of a spontaneous application that has not been evaluated by the Company yet
    - acceptedApplication and rejectedApplication are the possible outcomes of the evaluation of a spontaneous application
    */
    enum spontaneousApplicantStatus{toBeEvaluated, acceptedApplication, rejectedApplication}

    sig Recommendation{
        matchedStudent: one Student,
        matchedInternship: one InternshipsOffer,
        var status: one recommendationStatus
    }{
        //A recommendation exists only if a student and an internship have been matched
        (InternshipsOffer.recommendations & Student.recommendations) = Recommendation
    }

    sig SpontaneousApplication{
        spontaneousApplicant : one Student,
        interestedInternshipOffer: one InternshipsOffer,
        var status: one spontaneousApplicantStatus
    }{
        //A spontaneous application exists only if a student has sent it
        (SpontaneousApplication & Student.spontaneousApplications) = SpontaneousApplication
    }

    var sig Interview{
        var recommendation: lone Recommendation,
        var spontaneousApplication: lone SpontaneousApplication,
        var status: one interviewStatus
    }{
        //An interview can only be assign to a recommendation or a spontaneous application
        recommendation.status = acceptedMatch or spontaneousApplication.status = acceptedApplication
        one recommendation => no spontaneousApplication
        one spontaneousApplication => no recommendation
    }

    fact interviewUniqueness{
        always all i1, i2: Interview | i1 != i2 => ((i1.recommendation & i2.recommendation) = none) and ((i1.spontaneousApplication & i2.spontaneousApplication) = none)
    } 

    /*TO FIX: 
    -If interview has a status, then only one can be spawned
    -Interview status evolution crash the program if written like RecommendationEvolutionRules
    */
    enum interviewStatus{toBeSubmitted, submitted, passed, failed}

    //Ensure that VatNumbers and unique for Company and University
    fact UniqueVatNumber{
        //The set of VatNumbers for companies and universities should be different from each other
        Company.companyVatNumber & University.universityVatNumber = none
        //Different companies and universities should have different vat numbers
        all c1, c2: Company | c1 != c2 => c1.companyVatNumber != c2.companyVatNumber
        all u1, u2: University | u1 != u2 => u1.universityVatNumber != u2.universityVatNumber
    }

    //Different Users shall have different emails
    fact UniqueEmailEndEnrollment{
        all u1, u2: User | u1 != u2 => u1.userEmail != u2.userEmail
    }

    //All students shall be enrolled in a university
    fact StudentEnrolledInUniversity{
        all s: Student | s.enrolledIn != none
    }

    //Different students shall have different CVs
    fact CurriculumUniqueness{
        all s1, s2: Student | (s1 != s2 and s1.cv != none and s2.cv != none) => s1.cv != s2.cv
    }

    //Different companies shall have different offeredInternshipPositions
    fact UniqueInternshipOffer{
        all c1, c2: Company | (c1 != c2 and c1.offeredInternshipPosition != none and c2.offeredInternshipPosition != none) => c1.offeredInternshipPosition != c2.offeredInternshipPosition
    }

    //Only a student with a Cv and a Company with an OfferedInternshipPosition can be matched.
    //Only a student with a Cv can send a spontaneous application
    fact StudentWithCVInteraction{
        all r: Recommendation | r.matchedStudent.cv != none && r.matchedInternship != none
        all s: SpontaneousApplication | s.spontaneousApplicant.cv != none && s.interestedInternshipOffer != none
    }

    //Define how one Recommendation differs from another Recommendation and similarly for SpontaneousApplications
    fact SingleApplicationSource{
        all r1, r2: Recommendation | r1 != r2 => r1.matchedStudent != r2.matchedStudent or r1.matchedInternship != r2.matchedInternship 
        all sa1, sa2: SpontaneousApplication | sa1 != sa2 => sa1.spontaneousApplicant != sa2.spontaneousApplicant or sa1.interestedInternshipOffer != sa2.interestedInternshipOffer
    }

    //Define the reflexive property Recommendation and SpontaneousApplication
    fact ApplicationReflexivity{
        all r: Recommendation, i: InternshipsOffer | r in i.recommendations iff r.matchedInternship = i
        all r: Recommendation, s: Student | r in s.recommendations => r.matchedStudent = s
        all sa: SpontaneousApplication, i: InternshipsOffer | sa in i.spontaneousApplications iff sa.interestedInternshipOffer = i
        all sa: SpontaneousApplication, s: Student | sa in s.spontaneousApplications => sa.spontaneousApplicant = s
    }

    //An Application is unique and cannot be shared between two different Students or InternshipOffers
    fact ApplicationUniqueness{
        all i1, i2: InternshipsOffer | i1 != i2 =>  ((i1.recommendations & i2.recommendations) = none)
        all sa1, sa2: SpontaneousApplication | sa1 != sa2 =>  ((sa1.interestedInternshipOffer & sa2.interestedInternshipOffer) = none)
    }

    //Define the initial status of a Recommendation and a SpontaneousApplication
    fact initAcceptance {
        Recommendation.status = toBeAccepted
        SpontaneousApplication.status = toBeEvaluated
    }

    //Constraints that define the evolution of the status of a Recommendation
    fact RecommendationEvolutionRules{
        //A Match need to be accepted by both parties before it can be considered accepted. It can't become accepted in one-step
        all r: Recommendation | always ((r.status = toBeAccepted) => (r.status' != acceptedMatch))
        //A party cannot retract its acceptance of a match. Once accepted, it remains accepted.
        all r: Recommendation | always ((r.status = acceptedByStudent) => (r.status' != acceptedByCompany and r.status' != toBeAccepted))
        all r: Recommendation | always ((r.status = acceptedByCompany) => (r.status' != acceptedByStudent and r.status' != toBeAccepted))
        //Rejected and accepted matches remain rejected and accepted forever
        all r: Recommendation | always ((r.status = rejectedMatch) => always (r.status = rejectedMatch))
        all r: Recommendation | always ((r.status = acceptedMatch) => always (r.status = acceptedMatch))
    }

    fact SpontaneousApplicationEvolutionRules{
        always all sa: SpontaneousApplication | (sa.status = toBeEvaluated) => ((sa.status' = acceptedApplication) or (sa.status' = rejectedApplication) or (sa.status' = toBeEvaluated))
        //Once a spontaneous application has been accepted or rejected, it cannot change its status
        all sa: SpontaneousApplication | always ((sa.status = acceptedApplication) => always (sa.status = acceptedApplication))
        all sa: SpontaneousApplication | always ((sa.status = rejectedApplication) => always (sa.status = rejectedApplication))
        
    }

    //Here the Interviews are created and for now the starting status is toBeSubmitted
    fact InterviewIFRecommendationAccepted{
        always all r: Recommendation | ((r.status = acceptedMatch) => (one i: Interview |  i.recommendation = r ))
        always all sa: SpontaneousApplication | ((sa.status = acceptedApplication) => (one i: Interview | i.spontaneousApplication = sa))
        always all i: Interview, r:Recommendation | ((i.recommendation = r) => always (i.recommendation = r))
        always all i: Interview, r:SpontaneousApplication | ((i.spontaneousApplication = r) => always (i.spontaneousApplication = r))
        always (all i: Interview | once (i.status = toBeSubmitted))
    }

    fact InterviewStatusEvolution{
        // If interview is submitted, then sometime in the past it had to be toBeSubmitted
        always all i: Interview | always ((i.status = submitted) => once (i.status = toBeSubmitted and i.status' = submitted)) 
        // If interview is failed, then sometime in the past it had to be submitted
        always all i: Interview | always ((i.status = failed) => once (i.status = submitted and i.status' = failed))
        // If interview is passed, then sometime in the past it had to be submitted
        always all i: Interview | always ((i.status = passed) => once (i.status = submitted and i.status' = passed)) 
        always all i: Interview | always ((i.status = submitted) => after always (i.status != toBeSubmitted))
        always all i: Interview | always ((i.status = passed) => after always (i.status != submitted))
        always all i: Interview | always ((i.status = failed) => after always (i.status != submitted))
        always all i: Interview | always ((i.status' != toBeSubmitted) => once (i.status = toBeSubmitted))
    }

    //A function that returns the company that has offered a specific InternshipsOffer
    fun FindInternshipPositionCompany[i: InternshipsOffer]: lone Company {
        { c: Company | i in c.offeredInternshipPosition }
    }

    //If a student has no CV, then it cannot be matched with a recommendation or send a spontaneous application
    //If a company has no offeredInternshipPosition, then it cannot be matched with a recommendation
    assert NoInfoProvided{
        all s: Student, r: Recommendation | (s.cv = none) => (r.matchedStudent != s)
        all s: Student, sa: SpontaneousApplication | (s.cv = none) => (sa.spontaneousApplicant != s)
        all c: Company, r: Recommendation | (c.offeredInternshipPosition = none) => (FindInternshipPositionCompany[r.matchedInternship] != c)
    }

    //If a student did not accept a recommendation, then the recommendation cannot be accepted
    assert BothPartyNeedToAct{
        always all r: Recommendation | (r.status' = acceptedMatch) => (r.status = acceptedByStudent or r.status = acceptedByCompany or r.status = acceptedMatch)
        always all sa: SpontaneousApplication | (sa.status' = acceptedApplication) => (sa.status = toBeEvaluated or sa.status = acceptedApplication)
    }

    //If a student has multiple recommendations, then the recommendations are different
    //If a InternshipOffer has multiple recommendations, then the students recommended are different
    assert UniqueRecommendation{
        all s: Student, r1, r2: Recommendation | (r1 != r2 and r1.matchedStudent = s and r2.matchedStudent = s) => r1.matchedInternship != r2.matchedInternship
        all i: InternshipsOffer, r1, r2: Recommendation | (r1 != r2 and r1.matchedInternship = i and r2.matchedInternship = i) => r1.matchedStudent != r2.matchedStudent
    }

    //Two companies cannot offer the same InternshipOffer
    assert internshipsOfferUniqueness{
        all c1, c2: Company | (c1 != c2 and c1.offeredInternshipPosition != none and c2.offeredInternshipPosition != none) => c1.offeredInternshipPosition != c2.offeredInternshipPosition
    }

    //Two students cannot have the same CV
    assert CVUniqueness{
        all s1, s2: Student | (s1 != s2 and s1.cv != none and s2.cv != none) => s1.cv != s2.cv
    }

    //An interview can be assigned to a recommendation or a spontaneous application only if they have been accepted
    assert InterviewAssignment{
        always all i: Interview | (i.recommendation.status = acceptedMatch or i.spontaneousApplication.status = acceptedApplication)
    }

    //An interview can be assigned only to a student with a CV
    assert StudentWithInterviewHasCV{
        //(A <=> !B) equivalent to (A = !B and B = !A) equivalent to (A XOR B)
        always all i: Interview | (i.recommendation.matchedStudent.cv != none) <=> !(i.spontaneousApplication.spontaneousApplicant.cv != none)
    }

    run {} for 5 but exactly 3 Recommendation, exactly 1 SpontaneousApplication, exactly 3 InternshipsOffer, exactly 10 steps
