import * as React from "react";
import { useState } from "react";
import {
  Avatar,
  Box,
  Button,
  Card,
  FormControl,
  FormLabel,
  TextField,
} from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";
import SCUploadImage from "../Shared/SCUploadImage";
import { useGlobalContext } from "../../global/GlobalContext";
import { useCVContext } from "./CVContext";
import { useNavigate } from "react-router-dom";

const key = uuidv4();

export default function SCCv() {
  const navigate = useNavigate();
  const { previewUrl } = useGlobalContext();
  const { cvData, setCvData } = useCVContext();
  // const [cvData, setCvData] = useState([
  //   {
  //     id: 0,
  //     title: "Personal Information",
  //     content:
  //       "Name: Mario Rossi\nDate of Birth: 12/05/1990\nAddress: Via Roma, 10, Milano, Italy\nPhone: +39 123 456 7890\nEmail: mario.rossi@example.com",
  //   },
  //   {
  //     id: 1,
  //     title: "Contacts",
  //     content:
  //       "LinkedIn: linkedin.com/in/mariorossi\nGitHub: github.com/mariorossi\nTwitter: @MarioRossi",
  //   },
  //   {
  //     id: 2,
  //     title: "Education",
  //     content:
  //       "BSc in Computer Science - Università degli Studi di Milano (2010-2013)\nMSc in Software Engineering - Politecnico di Milano (2014-2016)",
  //   },
  //   {
  //     id: 3,
  //     title: "Work Experience",
  //     content:
  //       "Software Developer - TechCorp (2016-2019)\nSenior Software Engineer - DevSolutions (2019-2022)\nTeam Lead - Innovatech (2022-Present)",
  //   },
  //   {
  //     id: 4,
  //     title: "Courses",
  //     content:
  //       "JavaScript Advanced Course - Codecademy\nFull-Stack Web Development - Udemy\nAI and Machine Learning - Coursera",
  //   },
  //   {
  //     id: 5,
  //     title: "Projects",
  //     content:
  //       "Project Management Tool (PMT): Developed a web-based platform for task management using React and Node.js.\nE-commerce Platform: Built a scalable e-commerce platform for a local business using Django and PostgreSQL.",
  //   },
  //   {
  //     id: 6,
  //     title: "Languages",
  //     content: "Italian (Native)\nEnglish (Fluent)\nSpanish (Intermediate)",
  //   },
  //   {
  //     id: 7,
  //     title: "Digital & IT Skills",
  //     content:
  //       "Programming: JavaScript, Python, Java\nFrameworks: React, Angular, Django\nTools: Git, Docker, Jenkins\nOther: Cloud Computing (AWS, Azure), SQL, NoSQL",
  //   },
  //   {
  //     id: 8,
  //     title: "Interests",
  //     content: "Tech Blogging, Hiking, Photography, Cooking",
  //   },
  //   {
  //     id: 9,
  //     title: "Publications",
  //     content:
  //       "Rossi, M. 'Building Scalable Web Apps', Tech Journal, 2021\nRossi, M. 'AI in Everyday Life', International Conference on AI, 2022",
  //   },
  //   {
  //     id: 10,
  //     title: "Authorization for Personal Data Processing",
  //     content:
  //       "I authorize the processing of personal data contained in my CV in accordance with GDPR regulations.",
  //   },
  // ]);

  const [temporalData, setTemporalData] = useState(
    cvData.map((item) => ({ id: item.id, content: item.content }))
  );

  const [showEdit, setShowEdit] = useState(false);

  const onEditClick = () => {
    //NAV
    navigate("/dashboard/cv/edit");
    // return () => setShowEdit(bool);
  };

  const updateTemporalData = (id, event) => {
    const newContent = event.target.value;
    const updatedCvData = temporalData.map((item) =>
      item.id === id ? { ...item, content: newContent } : item
    );
    setTemporalData(updatedCvData);
  };

  const updateCvData = (id) => {
    const updatedCvData = cvData.map((item) =>
      item.id === id
        ? {
            ...item,
            content: temporalData.find((tempItem) => tempItem.id === id)
              .content,
          }
        : item
    );
    setCvData(updatedCvData);
  };

  return (
    <>
      <Box display="flex" flexDirection="column" height="100%" gap={2}>
        
        <Box sx={{ mt: 4, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Box display="flex" justifyContent="center">
            <Button
              variant="contained"
              onClick={onEditClick}
              sx={{
                width: "20%",
              }}
            >
              Edit CV
            </Button>
          </Box>
          <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
            <Avatar src={previewUrl} alt="Preview" />
            <Typography variant="h5" gutterBottom>
              CV Summary
            </Typography>
          </Box>
          {cvData.map((cv) => (
            <Box key={uuidv4()} id={cv.id} sx={{ mb: 2 }}>
              <Typography variant="h6">{cv.title}</Typography>
              <Typography variant="body1" whiteSpace="pre-line">
                {cv.content || "No content provided."}
              </Typography>
            </Box>
          ))}
        </Box>
      </Box>
    </>
  );
}
