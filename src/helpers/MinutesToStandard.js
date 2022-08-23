function minutesToStandard(totalMinutes){
    let hours = Math.floor(totalMinutes / 60);
    let minutes = totalMinutes % 60;
    if(minutes < 10){
        minutes = "0" + minutes;
    }
    if(hours < 10){
        hours = "0" + hours;
    }
    return(hours + ":" + minutes)
}

export default minutesToStandard;