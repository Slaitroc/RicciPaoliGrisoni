import './MatchOverlay.css'

function MatchOverlay({zIndex, name, setMatch}) {
   return (
      <div className="matchOV-container" style={{zIndex: zIndex}}>
        <div className="matchOV-TitleContainer">
          <h1 className="matchOV-title">It&#39;s a Match!</h1>
          <hr className="matchOV-divider" /> 
        </div>
        <div className='matchOV-textContainer'>
          <p>You and {name} are a Confirmed Match!</p>
        </div>
        <div className = "matchOV-buttonContainer">
          <button className="matchOV-button" onClick={() => {setMatch(false)}}>Keep Swiping</button> 
          <button className="matchOV-button">Go to your match</button>
        </div>
      </div>
    );
}

export default MatchOverlay