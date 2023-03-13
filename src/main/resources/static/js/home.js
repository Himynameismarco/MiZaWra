import JournalCard from "/js/JournalCard.js";

window.customElements.define('journal-card', JournalCard);

document.addEventListener("DOMContentLoaded", init);

let PAGES = 0;
let CURRENT_PAGE = 0;

function init() {
    fetchPages();
    fetchEvents(CURRENT_PAGE);
    window.addEventListener('scroll', ()=>{
        if (document.documentElement.scrollTop >= (document.body.scrollHeight - window.innerHeight - 80)
            && CURRENT_PAGE < PAGES) {
            ++CURRENT_PAGE;
            fetchEvents(CURRENT_PAGE);
        }
    });
}

function fetchEvents(page) {
    $.get('/journal/get?page=' + CURRENT_PAGE, function(data) {
        const journalsContainer = document.querySelector('.journal-container');
        data.forEach(journal => {
            journalsContainer.append(new JournalCard(journal));
        });
    });
}

function fetchPages() {
    $.get('/journal/pages', function(data) {
        PAGES = data;
    });
}