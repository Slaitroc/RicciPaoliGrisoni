import React from "react";
import { styled } from "@mui/material/styles";

const StyledMain = styled("main", { shouldForwardProp: (prop)=> prop!=="open" && prop!=="drawerWidht"})(
    ({theme, open, drawerWidth}) =>({
        flexGrow: 1,
        padding: theme.spacing(3),
        transition: theme.transitions.create("margin", {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: `-${drawerWidth}px`,
        ...(open && {
            transition: theme.transitions.create("margin", {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen
            }),
            marginLeft: 0,
        }),
    })
)

const SCMain = ({open, drawerWidth, children})=> {
    return <StyledMain open={open} drawerWidth={drawerWidth}>{children}</StyledMain>
}

export default SCMain;