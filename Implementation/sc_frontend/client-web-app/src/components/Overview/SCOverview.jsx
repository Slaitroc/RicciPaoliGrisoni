import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";
import { motion } from "framer-motion";

export default function SCOverview() {
  const [isClicked, setIsClicked] = React.useState(false);

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      onClick={() => setIsClicked(!isClicked)}
      style={{
        display: "flex",
        flexDirection: "column",
        width: "100%",
        height: "100vh",
        justifyContent: "center",
        alignItems: "center",
        position: "fixed",
        top: 0,
        left: 0,
        backgroundColor: "rgba(14,14,14,255, 0.8)",
      }}
    >
      <motion.h1
        initial={{ scale: 1 }}
        animate={{
          rotate: isClicked ? 360 : 0,
        }}
        transition={{
          type: "spring",
          stiffness: 300,
          damping: 20,
        }}
        style={{
          fontSize: "clamp(2rem, 10vw, 12rem)",
          fontWeight: "bold",
          textAlign: "center",
          color: "#094b87",
          userSelect: "none",
          letterSpacing: "-0.05em",
          lineHeight: 1.1,
          padding: "0 5vw",
          maxWidth: "90vw",
          wordBreak: "break-word",
        }}
      >
        Student & Companies
      </motion.h1>
    </motion.div>
  );
}
