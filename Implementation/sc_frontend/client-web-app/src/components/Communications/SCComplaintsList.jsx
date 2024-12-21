import * as React from "react";
import { SCCommunicationItem } from "./SCMessageItem";
import { List } from "@mui/material";
const message0 = {
  type: "Communication",
  sender: "Student2",
  title: "Request for Additional Information",
  body: "Could you please provide more details about the upcoming project tasks?",
  timestamp: "5 minutes ago",
};

const message1 = {
  type: "Communication",
  sender: "UniversityAdmin",
  title: "Internship Progress Update",
  body: "Please submit your weekly internship progress report by Friday.",
  timestamp: "2 hours ago",
};

const message2 = {
  type: "Complaint",
  sender: "CompanyRepresentative",
  title: "Issue with Submitted Documentation",
  body: "The document submitted by the student is incomplete. Please review and resubmit.",
  timestamp: "1 day ago",
};

const message3 = {
  type: "Communication",
  sender: "Student3",
  title: "Request for Remote Work Options",
  body: "Would it be possible to work remotely for the next week?",
  timestamp: "10 minutes ago",
};

const message4 = {
  type: "Communication",
  sender: "CompanyHR",
  title: "New Internship Opportunity",
  body: "A new internship position in the Marketing department is now available.",
  timestamp: "1 hour ago",
};

const message5 = {
  type: "Complaint",
  sender: "Student4",
  title: "Unclear Task Description",
  body: "The description for Task 5 is unclear. Could you clarify the expected outcome?",
  timestamp: "3 hours ago",
};

const message6 = {
  type: "Communication",
  sender: "CompanyMentor",
  title: "Feedback on Your Recent Submission",
  body: "Great work on the recent report! Keep it up.",
  timestamp: "4 hours ago",
};

const message7 = {
  type: "Communication",
  sender: "UniversityAdmin",
  title: "Mandatory Internship Meeting",
  body: "All students must attend the internship review meeting on Friday.",
  timestamp: "6 hours ago",
};

const message8 = {
  type: "Complaint",
  sender: "CompanyRepresentative",
  title: "Delayed Submission",
  body: "The project deliverable was not submitted on time. Please provide an explanation.",
  timestamp: "8 hours ago",
};

const message9 = {
  type: "Communication",
  sender: "Student5",
  title: "Request for Additional Resources",
  body: "Could you provide access to the company's knowledge base for the project?",
  timestamp: "1 day ago",
};

const message10 = {
  type: "Communication",
  sender: "CompanyHR",
  title: "Holiday Schedule Update",
  body: "The office will remain closed on Monday due to a public holiday.",
  timestamp: "2 days ago",
};

const message11 = {
  type: "Complaint",
  sender: "Student6",
  title: "Unresponsive Supervisor",
  body: "I have not received any feedback from my supervisor in the last two weeks.",
  timestamp: "3 days ago",
};

const message12 = {
  type: "Communication",
  sender: "Student7",
  title: "Request for Deadline Extension",
  body: "Could you extend the deadline for Task 3 by two days?",
  timestamp: "4 days ago",
};

export default function SCComplaintsList() {
  return (
    <List
      sx={{
        width: "100%",
        bgcolor: "background.paper",
        position: "relative",
        overflow: "auto",
        maxHeight: 550,
      }}
    >
      <SCCommunicationItem message={message0} />
      <SCCommunicationItem message={message1} />
      <SCCommunicationItem message={message2} />
      <SCCommunicationItem message={message3} />
      <SCCommunicationItem message={message4} />
      <SCCommunicationItem message={message5} />
      <SCCommunicationItem message={message6} />
      <SCCommunicationItem message={message7} />
      <SCCommunicationItem message={message8} />
    </List>
  );
}
