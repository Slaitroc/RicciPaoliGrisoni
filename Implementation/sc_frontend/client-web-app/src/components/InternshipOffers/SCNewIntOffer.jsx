import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  FormControl,
  FormLabel,
  TextField,
  Button,
  Box,
  Badge,
  IconButton,
  badgeClasses,
  Typography,
  Alert,
} from "@mui/material";
import * as logger from "../../logger/logger";
import Grid from "@mui/material/Grid2";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { useInternshipOffersContext } from "./InternshipOffersContext";
import NumberInput from "../Shared/InputHanlders/NumberInput";
import { DatePicker } from "@mui/x-date-pickers";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

export const SCNewIntOffer = () => {
  const navigate = useNavigate();
  const {
    offerDataSnapshot,
    setOfferDataSnapshot,
    newOfferRef,
    setForceRender,
    createInternshipOffer,
  } = useInternshipOffersContext();
  const [openTipAlert, setOpenTipAlert] = useState(true);

  useEffect(() => {
    const emptyObj = {
      id: {
        serverValue: "id",
        label: "Internship ID",
        value: undefined,
        type: "string",
      },
      companyID: {
        serverValue: "companyID",
        label: "Company ID",
        value: undefined,
        type: "string",
      },
      companyName: {
        serverValue: "companyName",
        label: "Company Name",
        value: undefined,
        type: "string",
      },
      title: {
        serverValue: "title",
        label: "Title",
        value: undefined,
        type: "string",
      },
      startDate: {
        serverValue: "startDate",
        label: "Start Date",
        value: undefined,
        type: "date",
      },
      endDate: {
        serverValue: "endDate",
        label: "End Date",
        value: undefined,
        type: "date",
      },
      duration: {
        serverValue: "duration",
        label: "Duration",
        value: 20,
        type: "int",
      },
      numberPositions: {
        serverValue: "numberPositions",
        label: "Number of Positions",
        value: 1,
        type: "int",
      },
      location: {
        serverValue: "location",
        label: "Location",
        value: undefined,
        type: "string",
      },
      description: {
        serverValue: "description",
        label: "Description",
        value: undefined,
        type: "string",
      },
      requiredSkills: {
        serverValue: "requiredSkills",
        label: "Required Skills",
        value: undefined,
        type: "string",
      },
      compensation: {
        serverValue: "compensation",
        label: "Compensation",
        value: 500,
        type: "int",
      },
      updateTime: {
        serverValue: "updateTime",
        label: "Last Update",
        value: undefined,
        type: "date-time",
      },
    };

    newOfferRef.current = emptyObj;
    setOfferDataSnapshot(emptyObj);
    // logger.focus("EMPTY VALUES OBJECT", newOfferRef.current);
  }, []);

  const handleFieldChange = (field, value) => {
    logger.focus(field, value);
    newOfferRef.current[field].value = value;
    setForceRender((prev) => prev + 1);
  };

  const clickBack = () => {
    // NAV to internship offers
    navigate(`/dashboard/internship-offers/`);
  };

  return (
    <Box display="flex" flexDirection="column" height="100%" gap={2}>
      <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
        <Box
          display="flex"
          paddingBottom={3}
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
        >
          <Badge
            color="error"
            variant="dot"
            invisible={true}
            sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
          >
            <IconButton size="small" onClick={clickBack}>
              <ArrowBackIcon />
            </IconButton>
          </Badge>
        </Box>
        {openTipAlert && (
          <Alert
            severity="info"
            sx={{ mb: 2 }}
            onClose={() => setOpenTipAlert(false)}
          >
            <Typography variant="body1">
              Create your Internship Offer and click Send Internship to send it
              to the world!
            </Typography>
            <Typography variant="h6" inline="true">
              Remember:
              <Typography inline="true" variant="body1">
                Using keywords instead of long phrases increases the chances of
                getting a match in the recommendation process.
              </Typography>
            </Typography>
          </Alert>
        )}
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {offerDataSnapshot &&
            Object.entries(offerDataSnapshot).map(([key, value]) => {
              if (
                key !== "id" &&
                key !== "companyID" &&
                key !== "companyName" &&
                key !== "updateTime"
              ) {
                return (
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Grid
                      item="true"
                      size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                      key={key}
                    >
                      {((item) => {
                        if (item.type === "string")
                          return (
                            <Box display="flex">
                              <FormControl sx={{ flexGrow: 1 }}>
                                <FormLabel>{item.label}</FormLabel>
                                <TextField
                                  multiline
                                  variant="outlined"
                                  placeholder={item.label}
                                  name={item.serverValue}
                                  onBlur={(e) =>
                                    handleFieldChange(
                                      item.serverValue,
                                      e.target.value
                                    )
                                  }
                                  sx={{
                                    flexGrow: 1,
                                    "& .MuiOutlinedInput-root": {
                                      minHeight: "auto",
                                      height: "auto",
                                    },
                                  }}
                                />
                              </FormControl>
                            </Box>
                          );
                        else if (item.type === "date")
                          return (
                            <Box display="flex" justifyContent="left">
                              <FormControl>
                                <FormLabel htmlFor="birthdate">
                                  {item.label} : {item.value}
                                </FormLabel>
                                <DatePicker
                                  onChange={(date) => {
                                    const data = new Date(date);
                                    data.setDate(data.getDate() + 1);
                                    const formattedDate = data
                                      .toISOString()
                                      .split("T")[0];
                                    handleFieldChange(
                                      item.serverValue,
                                      formattedDate
                                    );
                                  }}
                                  views={["year", "month", "day"]}
                                  sx={{
                                    "& .MuiIconButton-root": {
                                      width: "30px",
                                      height: "30px",
                                      padding: "4px",
                                      border: "none",
                                    },
                                  }}
                                />
                              </FormControl>
                            </Box>
                          );
                        else if (item.type === "int") {
                          if (item.serverValue === "compensation")
                            return (
                              <Box
                                display="flex"
                                flexDirection="column"
                                gap={0}
                              >
                                <NumberInput
                                  label={item.label}
                                  step={50}
                                  min={0}
                                  defaultValue={500}
                                  allowWheelScrub={true}
                                  onValueChange={(value, event) =>
                                    handleFieldChange(item.serverValue, value)
                                  }
                                />
                                <Typography
                                  variant="body2"
                                  color="text.secondary"
                                >
                                  (min: 0, step: 50)
                                </Typography>
                              </Box>
                            );
                          if (item.serverValue === "numberPositions")
                            return (
                              <Box
                                display="flex"
                                flexDirection="column"
                                gap={0}
                              >
                                <NumberInput
                                  label={item.label}
                                  step={1}
                                  min={1}
                                  defaultValue={1}
                                  allowWheelScrub={true}
                                  onValueChange={(value, event) =>
                                    handleFieldChange(item.serverValue, value)
                                  }
                                />
                                <Typography
                                  variant="body2"
                                  color="text.secondary"
                                >
                                  (min: 1, step: 1)
                                </Typography>
                              </Box>
                            );
                          if (item.serverValue === "duration")
                            return (
                              <Box
                                display="flex"
                                flexDirection="column"
                                gap={0}
                              >
                                <NumberInput
                                  label={item.label + ` in hours`}
                                  step={1}
                                  min={20}
                                  defaultValue={20}
                                  allowWheelScrub={true}
                                  onValueChange={(value, event) =>
                                    handleFieldChange(item.serverValue, value)
                                  }
                                />
                                <Typography
                                  variant="body2"
                                  color="text.secondary"
                                >
                                  (min: 20, step: 1)
                                </Typography>
                              </Box>
                            );
                        }
                      })(value)}
                    </Grid>
                  </LocalizationProvider>
                );
              } else return null;
            })}
        </Grid>

        {/* Pulsante per inviare tutti i dati */}
        <Box mt={3} display="flex" justifyContent="center">
          <Button
            variant="contained"
            color="primary"
            onClick={() => {
              createInternshipOffer(newOfferRef.current);
            }}
          >
            Create New Internship Offer
          </Button>
        </Box>
      </Box>
    </Box>
  );
};
