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
    Email = User.userEmail
    VatNumber = (Company.companyVatNumber + University.universityVatNumber)
    CV = Student.cv
}

sig InternshipsOffer{
    recommendations: set Recommendation,
    spontaneousApplications: set SpontaneousApplication
}{
    InternshipsOffer = Company.offeredInternshipPosition
}


sig Recommendation{
    matchedStudent: one Student,
    matchedInternship: one InternshipsOffer,
    var StudentAcceptance: lone Student,
    var CompanyAcceptance: lone Company
}{
    (InternshipsOffer.recommendations & Student.recommendations) = Recommendation
}

sig SpontaneousApplication{
    spontaneousApplicant : one Student,
    interestedInternshipOffer: one InternshipsOffer,
}{
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

fact UniqueVatNumber{
    //Vat number for companies and universities should be different from each other
    Company.companyVatNumber != University.universityVatNumber
    //Different companies and universities should have different vat numbers
    all c1, c2: Company | c1 != c2 => c1.companyVatNumber != c2.companyVatNumber
    all u1, u2: University | u1 != u2 => u1.universityVatNumber != u2.universityVatNumber
}

//Different users should have different emails
fact UniqueEmailEndEnrollment{
    all u1, u2: User | u1 != u2 => u1.userEmail != u2.userEmail
}

//All students should be enrolled in a university
fact StudentEnrolledInUniversity{
    all s: Student | s.enrolledIn != none
}

fact CurriculumUniqueness{
    all s1, s2: Student | (s1 != s2 and s1.cv != none and s2.cv != none) => s1.cv != s2.cv
}

//Only a student with a Cv and a Company with an OfferedInternshipPosition can be matched.
//Only a student with a Cv can send a spontaneous application
fact StudentWithCVInteraction{
    all r: Recommendation | r.matchedStudent.cv != none && r.matchedInternship != none
    all s: SpontaneousApplication | s.spontaneousApplicant.cv != none && s.interestedInternshipOffer != none
}


fact SingleApplicationSource{
    all r1, r2: Recommendation | r1 != r2 => r1.matchedStudent != r2.matchedStudent or r1.matchedInternship != r2.matchedInternship 
    all sa1, sa2: SpontaneousApplication | sa1 != sa2 => sa1.spontaneousApplicant != sa2.spontaneousApplicant or sa1.interestedInternshipOffer != sa2.interestedInternshipOffer
}

fact ApplicationReflexivity{
    all r: Recommendation, i: InternshipsOffer | r in i.recommendations iff r.matchedInternship = i
    all r: Recommendation, s: Student | r in s.recommendations => r.matchedStudent = s
    all sa: SpontaneousApplication, i: InternshipsOffer | sa in i.spontaneousApplications iff sa.interestedInternshipOffer = i
    all sa: SpontaneousApplication, s: Student | sa in s.spontaneousApplications => sa.spontaneousApplicant = s
}

fact ApplicationUniqueness{
    all i1, i2: InternshipsOffer | i1 != i2 =>  ((i1.recommendations & i2.recommendations) = none)
    all sa1, sa2: SpontaneousApplication | sa1 != sa2 =>  ((sa1.interestedInternshipOffer & sa2.interestedInternshipOffer) = none)
}

fun FindInternshipPositionCompany[i: InternshipsOffer]: lone Company {
    { c: Company | i in c.offeredInternshipPosition }
}

//At step 0 the acceptance of the recommendation is none by both parties
fact InitializeAcceptance{
    Recommendation.StudentAcceptance = none
    Recommendation.CompanyAcceptance = none
}

fact StudentAcceptance{

}

fact RecommendationAcceptance{
    
}

//Some recommendations are accepted by both parties
fact Acceptance{
    some r: Recommendation | (r.CompanyAcceptance = none) and always (after r.CompanyAcceptance = FindInternshipPositionCompany[r.matchedInternship] and )


    some r: Recommendation | (r.StudentAcceptance = none) and always (after r.StudentAcceptance = r.matchedStudent)
}


run {} for 3 but exactly 1 Student, exactly 1 University, exactly 1 Company, exactly 1 SpontaneousApplication, exactly 2 Recommendation, exactly 2 InternshipsOffer,  10 steps
