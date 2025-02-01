import React, { useEffect, useState } from "react";
import { Avatar, Box, Button } from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";
import { useGlobalContext } from "../../global/GlobalContext";
import { useNavigate, useParams } from "react-router-dom";
import { useInterviewsContext } from "./InterviewsContext";
import * as logger from "../../logger/logger";

export default function SCInterview({ offerData }) {
  const navigate = useNavigate();
  const { id } = useParams();
  const { offerDataSnapshot } = useInterviewsContext();

  const { previewUrl, profile } = useGlobalContext();

  const onEditClick = () => {
    navigate(`/dashboard/internship-offers/edit/${id}`);
  };

  useEffect(() => {}, []);

  return (
    <>
      <Box display="flex" flexDirection="column" height="100%" gap={2}>
        <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Box
            display="flex"
            flexDirection="row"
            justifyContent="space-between"
          >
            <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
              <Avatar src={previewUrl} alt="Preview" />
              <Typography variant="h5" gutterBottom>
                Internship Offer Summary
              </Typography>
            </Box>
            {offerDataSnapshot && (
              <Box display="flex" flexDirection="column" alignItems="end">
                <Typography variant="h6 " gutterBottom>
                  Last Update:{" "}
                  {new Date(offerDataSnapshot.updateTime.value).toLocaleString(
                    "it-IT",
                    {
                      year: "numeric",
                      month: "2-digit",
                      day: "2-digit",
                      hour: "2-digit",
                      minute: "2-digit",
                    }
                  )}
                </Typography>
                <Button
                  variant="outlined"
                  onClick={onEditClick}
                  align="left"
                  sx={{
                    whiteSpace: "nowrap",
                    width: "50%",
                    paddingX: 6,
                  }}
                >
                  Edit Offer
                </Button>
              </Box>
            )}
          </Box>
          <Box key={uuidv4()} sx={{ mb: 2 }}>
            <Box display="flex" flexDirection="column" gap={1}>
              <Typography variant="h6">Company Name:</Typography>
              <Typography
                variant="body1"
                whiteSpace="pre-line"
                color="text.secondary"
              >
                {profile.name}
              </Typography>
            </Box>
          </Box>
          {offerDataSnapshot &&
            Object.entries(offerDataSnapshot).map((field) => {
              if (
                field[0] === "companyID" ||
                field[0] === "id" ||
                field[0] === "updateTime" ||
                field[0] === "companyName"
              )
                return null;
              return (
                <Box key={uuidv4()} id={field.id} sx={{ mb: 2 }}>
                  <Box display="flex" flexDirection="column" gap={1}>
                    <Typography variant="h6">{field[1].label}:</Typography>
                    <Typography
                      variant="body1"
                      whiteSpace="pre-line"
                      color="text.secondary"
                    >
                      {((value) => {
                        if (value && value !== 0) {
                          return value;
                        } else {
                          return "No content provided.";
                        }
                      })(field[1].value) && field[1].value}
                    </Typography>
                  </Box>
                </Box>
              );
            })}
        </Box>
      </Box>
    </>
  );
}
