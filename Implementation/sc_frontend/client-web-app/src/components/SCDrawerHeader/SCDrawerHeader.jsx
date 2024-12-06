import { styled } from "@mui/material";
import React from "react";

const DrawerHeaderStyled = styled("div")(({theme})=>({
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: "flex-end",
}))

const SCDrawerHeader = ({children}) =>{
    return <DrawerHeaderStyled>{children}</DrawerHeaderStyled>
}

export default SCDrawerHeader;