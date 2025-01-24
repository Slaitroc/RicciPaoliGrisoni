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
import { useGlobalContext } from "../../global/GlobalContext";

const key = uuidv4();

export default function SCIntOffers({ offerData }) {
  const [temporalData, setTemporalData] = useState(
    offerData.map((item) => ({
      id: item.id,
      content: item.content.map((subitem) => ({
        id: subitem.id,
        content: subitem.content,
      })),
    }))
  );

  const [showEdit, setShowEdit] = useState(false);

  const onEditClick = (bool) => {
    return () => setShowEdit(bool);
  };

  const updateTemporalData = (itemID, subItemID, event) => {
    const newContent = event.target.value;
    const updatedOfferData = temporalData.map((item) =>
      item.id === itemID
        ? {
            ...item,
            content: item.content.map((subitem) =>
              subitem.id === subItemID
                ? { ...subitem, content: newContent }
                : subitem
            ),
          }
        : item
    );
    setTemporalData(updatedOfferData);
  };

  const updateOfferData = (itemID, subItemID) => {
    const updatedOfferData = offerData.map((item) =>
      item.id === itemID
        ? {
            ...item,
            content: item.content.map((subitem) =>
              subitem.id === subItemID
                ? {
                    ...subitem,
                    content: temporalData
                      .find((tempItem) => tempItem.id === itemID)
                      .content.find(
                        (tempSubItem) => tempSubItem.id === subItemID
                      ).content,
                  }
                : subitem
            ),
          }
        : item
    );
    setOfferData(updatedOfferData);
  };

  return (
    <>
      <Box display="flex" flexDirection="column" height="100%" gap={2}>
        <Box sx={{ mt: 4, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
            <Avatar src={""} alt="Preview" />
            <Typography variant="h3" gutterBottom>
              Internship Offer Summary
            </Typography>
          </Box>
          {offerData.map((item) => (
            <Box>
              <Typography variant="h4">{item.title}</Typography>
              {item.content.map((subitem) => (
                <Box key={uuidv4()} id={subitem.id} sx={{ mb: 0 }} padding={1}>
                  <Typography variant="h6">{subitem.title}</Typography>
                  <Typography variant="body1" whiteSpace="pre-line">
                    {subitem.content || "No content provided."}
                  </Typography>
                </Box>
              ))}
            </Box>
          ))}
        </Box>
        <Box display="flex" justifyContent="center">
          <Button
            variant="contained"
            onClick={onEditClick(!showEdit)}
            sx={{
              width: "20%",
            }}
          >
            Edit Internship Offer
          </Button>
        </Box>

        {showEdit && (
          <>
            {offerData.map((item) => {
              return (
                <React.Fragment key={key}>
                  <Typography variant="h4">{item.title}</Typography>
                  <Box display="flex" flexDirection="column" gap={6}>
                    {item.content.map((subitem) => {
                      return (
                        <Box display="flex" flexDirection="row" gap={6}>
                          <FormControl sx={{ width: "100%" }}>
                            <FormLabel>{subitem.title}</FormLabel>
                            <TextField
                              multiline
                              variant="outlined"
                              placeholder={subitem.title}
                              onChange={(e) =>
                                updateTemporalData(item.id, subitem.id, e)
                              }
                              sx={{
                                "& .MuiOutlinedInput-root": {
                                  minHeight: "auto", // Altezza minima dinamica
                                  height: "auto", // Altezza complessiva non fissa
                                },
                              }}
                            />
                            <Box
                              display="flex"
                              justifyContent="left"
                              padding={2}
                            >
                              <Button
                                onClick={() =>
                                  updateOfferData(item.id, subitem.id)
                                }
                                variant="outlined"
                                sx={{
                                  width: "20%",
                                  height: "60%",
                                  // "&.MuiButton-root": {
                                  //   border: "1px solid grey", // Modifica solo questo bottone
                                  // },
                                }}
                              >
                                Update
                              </Button>
                            </Box>
                          </FormControl>
                          <FormControl sx={{ width: "100%" }}>
                            <FormLabel>{subitem.title}</FormLabel>
                            <Card
                              sx={{ width: "100%", whiteSpace: "pre-line" }}
                            >
                              {" "}
                              {subitem.content}
                            </Card>
                          </FormControl>
                        </Box>
                      );
                    })}
                  </Box>
                </React.Fragment>
              );
            })}
          </>
        )}
      </Box>
    </>
  );
}
