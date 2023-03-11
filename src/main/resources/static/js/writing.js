document.addEventListener("DOMContentLoaded", init);

function init() {
    const journal = document.getElementById('journal').value;
    document.getElementById('save').addEventListener('click', ()=>{
        $.post("/saveJournal", {journal: journal}, function(data) {
            window.location.href = "/home";
        });
    });
}