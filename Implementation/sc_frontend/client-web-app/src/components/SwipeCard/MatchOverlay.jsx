import './MatchOverlay.css'

function MatchOverlay({zIndex, name, setMatch}) {
   return (
      <div className="matchOV-container" style={{zIndex: zIndex}}>
        <h1 className="matchOV-text">It&#39;s a Match!</h1>
        <hr className="matchOV-divider" />  
        <p>You and {name} are a Confirmed Match!</p>
        <button className="keepSwipeBtn" onClick={() => {setMatch(false)}}>Keep Swiping</button> 
        <button className='goToMatch'>Go to your Match</button>
      </div>
    );
}

export default MatchOverlay