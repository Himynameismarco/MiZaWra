document.addEventListener("DOMContentLoaded", init);

let isPaused = false;

function init() {
    var audio = new Audio('/sound.mp3');

    if(document.querySelector('.time-container h1')) {
        const duration = 60*15;
        const timeContainer = document.querySelector('.time-container');

        timeContainer.addEventListener('click', ()=> {
            isPaused = !isPaused;
            audio.pause();
        });

        startTimer(duration, timeContainer.querySelector('h1'), audio);
    }

    document.getElementById('save').addEventListener('click', ()=>{
        const id = document.querySelector('.container').id;
        const mode = document.getElementById('prompt')?.getAttribute('mode');
        const title = document.getElementById('title').value;
        const journal = document.getElementById('journal').value;

        const dto = {id: id, mode: mode, title: title, body: journal};

        $.post("/journal/save", dto, function(data) {
            window.location.href = "/home";
        });
    });
}

function startTimer(duration, display, audio) {
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
                }, 10000);
            }
        }
    }, 1000);
}