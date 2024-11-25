import Button from "@mui/material/Button";

const ScButton = () => {
  return (
    <Button
      variant="outlined"
      onClick={() => {
        console.log("bottone cliccato");
      }}
    >
      Hello World{" "}
    </Button>
  );
};

export default ScButton;
