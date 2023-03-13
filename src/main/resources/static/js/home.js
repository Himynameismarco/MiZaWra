import JournalCard from "/js/JournalCard.js";

window.customElements.define('journal-card', JournalCard);

document.addEventListener("DOMContentLoaded", init);

function init() {
    fetchEvents();
}

function fetchEvents(page) {
    $.get('/journal/get', function(data) {
        const journalsContainer = document.querySelector('.journal-container');
        data.forEach(journal => {
            journalsContainer.append(new JournalCard(journal));
        });
    });
}
