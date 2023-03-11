document.addEventListener("DOMContentLoaded", init);

function init() {
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