document.addEventListener("DOMContentLoaded", init);

function init() {
const token = new URLSearchParams(window.location.search).get('token');
    console.log(token)
    document.querySelector('form').addEventListener('submit', (event)=>{
        event.preventDefault();
        const token = new URLSearchParams(window.location.search).get('token');
        console.log(token)
        _savePassword(event);
    });
}

function _savePassword(event) {
    const password = document.getElementById('password').value;
    const matchPassword = document.getElementById('matchPassword').value;
    const token = new URLSearchParams(window.location.search).get('token');
    console.log(token)
    if (password != matchPassword) {
        return;
    }
    console.log(token)
    $.post("/savePassword", {password : password, tokenId: token}, function(data) {
        window.location.href = "/login?message=" + data.message;
   })
}