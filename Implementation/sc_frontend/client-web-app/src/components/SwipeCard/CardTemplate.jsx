import React from "react";
import "./CardTemplate.css";

function MatchCard(props) {
  return (
    <div className="card">
      {createCardHeader({name: props.companyName, position: props.position })}
      <hr className="divider" />
      <p className="description"> 
        {props.description}</p>
    </div>
  );
}


function createCardHeader({ name, position }) {
  return (
    <div className="card-header">
      <div className="logo" style={{ backgroundColor: logoBackgroundColor(name[0]) }}>
        {name[0]}
      </div>
      <div className="content">
        <b><p className="title">{name}</p></b>
        <p className="position">{position}</p>
      </div>
    </div>
  );
}

// Set the background color of the logo based on the first letter of the company name
function logoBackgroundColor(initial) {
  const lowerInitial = initial.toLowerCase();
  switch (true) {
    case lowerInitial <= "g":
      return "rgb(0, 204, 102)";
    case lowerInitial <= "n":
      return "rgb(255, 0, 247)";
    case lowerInitial <= "u":
      return "rgb(0, 0, 153)";
    default:
      return "rgb(204, 0, 0)";
  }
}
export default MatchCard;
