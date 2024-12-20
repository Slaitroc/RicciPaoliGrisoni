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

sig InternshipsOffer{
    recommendations: set Recommendation,
    spontaneousApplications: set SpontaneousApplication
}{
    //A InternshipOffer exists only if a company has offered it
    InternshipsOffer = Company.offeredInternshipPosition
}

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
    SpontaneousApplication = Student.spontaneousApplications
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

//A function that returns the company that has offered a specific InternshipsOffer
fun FindInternshipPositionCompany[i: InternshipsOffer]: lone Company {
    { c: Company | i in c.offeredInternshipPosition }
}

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
    //Once a spontaneous application has been accepted or rejected, it cannot change its status
    all sa: SpontaneousApplication | always ((sa.status = acceptedApplication) => always (sa.status = acceptedApplication))
    all sa: SpontaneousApplication | always ((sa.status = rejectedApplication) => always (sa.status = rejectedApplication))
}

run {} for 5 but exactly 3 Recommendation, exactly 1 SpontaneousApplication, exactly 3 steps
