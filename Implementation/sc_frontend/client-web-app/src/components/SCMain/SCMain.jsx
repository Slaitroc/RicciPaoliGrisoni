import React from "react";
import { styled } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import SCDrawerHeader from "../SCDrawer/SCDrawerHeader/SCDrawerHeader";


const StyledMain = styled("main", { shouldForwardProp: (prop)=> prop!=="open" && prop!=="drawerWidht"})(
    ({theme, open, drawerWidth}) =>({
        flexGrow: 1,
        padding: theme.spacing(3),
        paddingTop: theme.spacing(9), //altrimenti l'appbar copre il contenuto della pagina
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
    return <StyledMain open={open} drawerWidth={drawerWidth}>
        {!open ? (
            <Typography sx={{ marginBottom: 2 }}>MenuClosed</Typography>
          ) : (
            <Typography sx={{ marginBottom: 2 }}>MenuOpen</Typography>
          )}
          {children}
        </StyledMain>
}

export default SCMain;