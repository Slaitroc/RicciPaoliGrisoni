import React, { useEffect, useState } from "react";
import SCIntOffers from "../components/InternshipOffers/SCIntOffer";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import { useGlobalContext } from "../global/GlobalContext";
import { Alert } from "@mui/material";
import Card from "@mui/material/Card";
import { getFormattedCompanyInternships } from "../api-calls/api-wrappers/submission-wrapper/internshipOffer";
const InternshipOffers = () => {
  const [offerDataOld, setOfferDataOld] = useState([
    {
      id: 0,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Marketing Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Support the marketing team in creating campaigns and analyzing results.",
            },
            { id: 2, title: "Duration", content: "3 months" },
            { id: 3, title: "Work Mode", content: "Hybrid" },
            { id: 4, title: "Preferred Start Date", content: "2024-01-15" },
            { id: 5, title: "Workplace", content: "Milan, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Social Media Management, Analytics, Communication",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Marketing or Business Administration",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (B2+), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Basic knowledge of SEO and Google Analytics",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-01-15" },
            { id: 5, title: "Workplace", content: "Milan, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€600/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Networking events, flexible hours",
            },
            { id: 2, title: "Working Hours", content: "25 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Campaign templates attached",
            },
            {
              id: 1,
              title: "Notes",
              content: "Submit a portfolio of previous work if available.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content:
                "The candidate must adhere to company branding policies.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 1,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Human Resources Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Assist in recruitment processes and employee engagement initiatives.",
            },
            { id: 2, title: "Duration", content: "6 months" },
            { id: 3, title: "Work Mode", content: "On-site" },
            { id: 4, title: "Preferred Start Date", content: "2024-02-01" },
            { id: 5, title: "Workplace", content: "Rome, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Interpersonal Communication, Data Entry, Organization",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Human Resources or Psychology",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (C1), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience with HR tools is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-02-01" },
            { id: 5, title: "Workplace", content: "Rome, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€500/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Employee discounts, mentorship opportunities",
            },
            { id: 2, title: "Working Hours", content: "20 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Onboarding checklist attached",
            },
            {
              id: 1,
              title: "Notes",
              content: "Candidates must complete an HR simulation task.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content:
                "The candidate agrees to uphold confidentiality in all tasks.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 2,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Event Planning Intern" },
            {
              id: 1,
              title: "Description",
              content: "Assist in planning and coordinating corporate events.",
            },
            { id: 2, title: "Duration", content: "4 months" },
            { id: 3, title: "Work Mode", content: "Hybrid" },
            { id: 4, title: "Preferred Start Date", content: "2024-03-01" },
            { id: 5, title: "Workplace", content: "Turin, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Organization, Communication, Event Management Software",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Event Management or Hospitality",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (B2+), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience with event logistics is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-03-01" },
            { id: 5, title: "Workplace", content: "Turin, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€700/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Networking opportunities, flexible hours",
            },
            { id: 2, title: "Working Hours", content: "20 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Sample event checklist attached",
            },
            {
              id: 1,
              title: "Notes",
              content: "Submit a proposal for a mock event.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content: "Candidates agree to provide regular progress updates.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 3,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Environmental Studies Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Support research on sustainable development initiatives.",
            },
            { id: 2, title: "Duration", content: "5 months" },
            { id: 3, title: "Work Mode", content: "Remote" },
            { id: 4, title: "Preferred Start Date", content: "2024-04-01" },
            { id: 5, title: "Workplace", content: "Florence, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content:
                "Research, Data Analysis, Environmental Policy Knowledge",
            },
            {
              id: 1,
              title: "Education Level",
              content:
                "Undergraduate in Environmental Science or Sustainability",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (C1), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience with data collection is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-04-01" },
            { id: 5, title: "Workplace", content: "Remote" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€900/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Access to research tools, training sessions",
            },
            { id: 2, title: "Working Hours", content: "30 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Research guidelines document attached",
            },
            {
              id: 1,
              title: "Notes",
              content: "Submit a research proposal as part of the application.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content:
                "Candidate consents to using findings in public reports.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 4,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Graphic Design Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Collaborate with the creative team to design visual assets for marketing campaigns.",
            },
            { id: 2, title: "Duration", content: "4 months" },
            { id: 3, title: "Work Mode", content: "On-site" },
            { id: 4, title: "Preferred Start Date", content: "2024-05-01" },
            { id: 5, title: "Workplace", content: "Naples, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Adobe Photoshop, Illustrator, Creativity",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Graphic Design or similar",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (B2+), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience with branding projects is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-05-01" },
            { id: 5, title: "Workplace", content: "Naples, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€750/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Access to creative tools, team-building activities",
            },
            { id: 2, title: "Working Hours", content: "25 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Portfolio examples attached",
            },
            {
              id: 1,
              title: "Notes",
              content: "Submit a portfolio with your application.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content:
                "Candidates agree to the company's confidentiality policies.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 5,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Customer Support Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Assist the support team in resolving customer inquiries and improving service processes.",
            },
            { id: 2, title: "Duration", content: "3 months" },
            { id: 3, title: "Work Mode", content: "Hybrid" },
            { id: 4, title: "Preferred Start Date", content: "2024-06-01" },
            { id: 5, title: "Workplace", content: "Venice, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Communication, Problem-Solving, CRM Tools",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Business Administration or similar",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (C1), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience in customer service is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-06-01" },
            { id: 5, title: "Workplace", content: "Venice, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€600/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Flexible working hours, mentorship opportunities",
            },
            { id: 2, title: "Working Hours", content: "30 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Customer service guidelines attached",
            },
            {
              id: 1,
              title: "Notes",
              content:
                "Strong interpersonal skills are crucial for this position.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content: "Candidates must adhere to GDPR guidelines.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 6,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Data Analyst Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Analyze data trends and provide insights to support business decisions.",
            },
            { id: 2, title: "Duration", content: "5 months" },
            { id: 3, title: "Work Mode", content: "Remote" },
            { id: 4, title: "Preferred Start Date", content: "2024-07-01" },
            { id: 5, title: "Workplace", content: "Remote" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "SQL, Python, Data Visualization Tools (e.g., Power BI)",
            },
            {
              id: 1,
              title: "Education Level",
              content:
                "Undergraduate in Statistics, Mathematics, or Data Science",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (C1), Italian (Optional)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience in data analysis is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-07-01" },
            { id: 5, title: "Workplace", content: "Remote" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€800/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Access to company datasets, training sessions",
            },
            { id: 2, title: "Working Hours", content: "20 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Data analysis sample project attached",
            },
            {
              id: 1,
              title: "Notes",
              content:
                "Submit a data analysis portfolio with your application.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content:
                "Candidates must comply with confidentiality agreements.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
    {
      id: 7,
      content: [
        {
          id: 0,
          title: "Internship Details",
          content: [
            { id: 0, title: "Title", content: "Legal Intern" },
            {
              id: 1,
              title: "Description",
              content:
                "Assist the legal team with contract reviews, research, and administrative tasks.",
            },
            { id: 2, title: "Duration", content: "6 months" },
            { id: 3, title: "Work Mode", content: "On-site" },
            { id: 4, title: "Preferred Start Date", content: "2024-08-01" },
            { id: 5, title: "Workplace", content: "Bologna, Italy" },
          ],
        },
        {
          id: 1,
          title: "Student Requirements",
          content: [
            {
              id: 0,
              title: "Required Skills",
              content: "Legal Research, Writing, Attention to Detail",
            },
            {
              id: 1,
              title: "Education Level",
              content: "Undergraduate in Law",
            },
            {
              id: 2,
              title: "Languages",
              content: "English (C1), Italian (Mandatory)",
            },
            {
              id: 3,
              title: "Previous Experience",
              content: "Experience in legal writing is a plus",
            },
            { id: 4, title: "Preferred Start Date", content: "2024-08-01" },
            { id: 5, title: "Workplace", content: "Bologna, Italy" },
          ],
        },
        {
          id: 2,
          title: "Benefit and Conditions",
          content: [
            { id: 0, title: "Compensation", content: "€900/month" },
            {
              id: 1,
              title: "Additional Benefits",
              content: "Mentorship, networking opportunities",
            },
            { id: 2, title: "Working Hours", content: "30 hours/week" },
          ],
        },
        {
          id: 3,
          title: "Additional Information",
          content: [
            {
              id: 0,
              title: "Attachments",
              content: "Sample contract documents attached",
            },
            {
              id: 1,
              title: "Notes",
              content:
                "Candidates must have a basic understanding of Italian law.",
            },
          ],
        },
        {
          id: 4,
          title: "Review and Publication",
          content: [
            {
              id: 0,
              title: "Terms and Conditions",
              content: "Candidates must maintain strict confidentiality.",
            },
            { id: 1, title: "Offer Status", content: "Open" },
          ],
        },
      ],
    },
  ]);

  const { profile } = useGlobalContext();
  const [offerData, setOfferData] = useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  //When the component mounts, fetch the internship offers of this company
  useEffect(() => {
    console.log("Profile:", profile);
    if(profile.userType != "COMPANY"){
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a company");
      console.log("User is not a company");
    }
    getFormattedCompanyInternships(profile.userID).then((response) => {
      console.log(response.message);
      if(response.status === 404){
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage(response.message);
      }else if(response.status === 204){
        setOpenAlert(true);
        setAlertSeverity("info");
        setAlertMessage(response.message);
      }else{
        setOfferData(response.data);
        console.log("Offer data:", response.data);
      }
    });
  }, []);

  return (
    <>
      <Card variant="outlined">
        {openAlert && (
          <>
            <Alert severity={alertSeverity}>{alertMessage}</Alert>
            <div style={{ margin: '20px 0' }}></div>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
            <SCIntOffersPreview offerData={offerData} />
        </div>
          </>
        )}
        {!openAlert && <SCIntOffersPreview offerData={offerData} />}      
      </Card>
    </>
  );

  //return (
  //  <Card variant="outlined">
  //    {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}      
  //  </Card>
  //);
};

export default InternshipOffers;