document.addEventListener("DOMContentLoaded", init);

function init() {
    document.getElementById('confirm').addEventListener('click', ()=>{
        const email = document.getElementById('email').value;
        $.post("/forgetPassword", {email: email}, function(data) {
            window.location.href = "/login?message=" + data.message;
        }).fail(function() {
            window.location.href = "/fail";
        });
    });
}