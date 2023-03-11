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
            console.log(journal);
            console.log(new JournalCard(journal))
            journalsContainer.append(new JournalCard(journal));
        });
    });
}
