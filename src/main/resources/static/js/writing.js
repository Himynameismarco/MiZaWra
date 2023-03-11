document.addEventListener("DOMContentLoaded", init);

let isPaused = false;

function init() {
    if(document.querySelector('.time-container h1')) {
        const duration = 60*15;
        const timeContainer = document.querySelector('.time-container');

        timeContainer.addEventListener('click', ()=> {
            isPaused = !isPaused;
        });

        startTimer(duration, timeContainer.querySelector('h1'));
    }

    document.getElementById('save').addEventListener('click', ()=>{
        const mode = document.getElementById('prompt')?.getAttribute('mode');
        const title = document.getElementById('title').value;
        const journal = document.getElementById('journal').value;

        const dto = {mode: mode, title: title, body: journal};

        $.post("/journal/save", dto, function(data) {
            window.location.href = "/home";
        });
    });
}

function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        if (!isPaused) {
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = minutes + ":" + seconds;

            if (--timer < 0) {
                timer = duration;
            }
        }
    }, 1000);
}