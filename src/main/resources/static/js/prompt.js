document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelectorAll('.prompt').forEach(prompt=>{
        prompt.addEventListener('click', evt => {
            window.location.href = document.location.origin + '/write?mode=' + evt.target.closest('.prompt').id.toUpperCase();
        });
    });
}