document.addEventListener("DOMContentLoaded", init);

let isPaused = false;
let journalLength = 0;

function init() {
    _addTimer();

    let saveInterval = setInterval(function(){
        const updatedJournal = document.getElementById('journal').value;
        if(updatedJournal.length !== journalLength) {
            _saveJournal();
            journalLength = updatedJournal.length;
        }
    }, 30000);

    document.getElementById('save').addEventListener('click', ()=>{
        _saveJournal();
        window.location.href = "/home";
    });
}

function _saveJournal() {
    const idContainer = document.querySelector('.container');
    const promptId = document.getElementById('prompt')?.getAttribute('prompt-id');
    const title = document.getElementById('title').value;
    const journal = document.getElementById('journal').value;

    const dto = {id: idContainer.id, promptId: promptId, title: title, body: journal};

    $.post("/journal/save", dto, function(data) {
        if (idContainer.id === '') {
            idContainer.id = data.id;
        }
    }).fail(function() {
        window.location.href = "/fail";
    });
}

function _addTimer() {
    if(document.querySelector('.time-container h1')) {
        var audio = new Audio('/sound.mp3');

        const duration = 60*15;
        const timeContainer = document.querySelector('.time-container');

        timeContainer.addEventListener('click', ()=> {
            isPaused = !isPaused;
            audio.pause();
        });

        _startTimer(duration, timeContainer.querySelector('h1'), audio);
    }
}

function _startTimer(duration, display, audio) {
    var timer = duration, minutes, seconds;
    var intervalId = setInterval(function () {
        if (!isPaused) {
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = minutes + ":" + seconds;

            if (--timer < 0) {
                clearInterval(intervalId);
                audio.play();
                setTimeout(() => {
                    audio.pause();
                }, 20000);
            }
        }
    }, 1000);
}