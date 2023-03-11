const style = `
<style>
    .journal {
        display: grid;
        grid-template-rows: 30px 1fr 25px;

        background: #232223;
        padding: 20px;
        border-radius: 10px;
        aspect-ratio: 1 / 1;

        text-align: center;
    }

    .journal .text {
        padding-top: 10px;
        max-width: 230px;
        background: linear-gradient(180deg, #EBECF1 0%, rgba(235, 236, 241, 0.3) 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .journal-header {
        font-weight: 700;
        font-size: 20px;
        line-height: 30px;
    }

    .color-grey {
        color: #EBECF1;
        opacity: 0.4;
    }
</style>
`;

export default class JournalCard extends HTMLElement {
    constructor(object) {
        super();
        this._object = object ? object : null;
        this.attachShadow({mode: 'open'});
    }

    connectedCallback() {
        if (!this._object) {
            this._object = {id: '1', title: 'Title', body: 'Body of journal', postedDate: 'Date'};
        }

        const tmpl = document.createElement('template');
        const container = document.createElement('div');
        container.id = this._object.id;
        container.classList.add('journal');

        container.innerHTML = `
            <div class="journal-header">${this._object.title}</div>
            <div class="text">${this._object.body}</div>
            <div class="color-grey">${this._object.postedDate}</div>
        `;

        tmpl.innerHTML = style;
        tmpl.content.append(container);

        const shadow = this.shadowRoot;
        shadow.append(tmpl.content.cloneNode(true));
    }
}
