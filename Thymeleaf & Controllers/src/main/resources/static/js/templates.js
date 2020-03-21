function fillVirusTemplate(viruses) {
    if (viruses.length === 0) {
        return '';
    }
    const columns = Object.keys(viruses[0])
        .filter(column => column !== 'id');

    const tableHead = `<thead><tr>${columns.map(column => `<th scope="col">${column}</th>`)}</tr></thead>`;

    const tableBody = `<tbody>${viruses.map(virus => {
        return `<tr>${columns.map(column => {
                return getRowContent(virus[column])
            }
        )}
        ${getButtons(virus.id)}
        </tr>`
    })}</tbody>`;

    return tableHead + tableBody;
}

function getButtons(virusId) {
    return `<td><a href="/viruses/edit/${virusId}" class="btn btn-outline-dark" role="button" aria-pressed="true">Edit</a></td>
<td>
<form action="/viruses/delete/${virusId}" method="POST">
<button type="submit" class="btn btn-outline-dark">Delete</button>
</form>
</td>`
}

function getRowContent(column) {
    return `<td>${column}</td>`
}

function fillCapitalTemplate(capitals) {
    console.log(capitals);
    const columns = Object.keys(capitals[0]);

    const tableHead = `<thead><tr>${columns.map(column => `<th scope="col">${column}</th>`)}</tr></thead>`;
    const tableBody = `<tbody>${capitals.map(capital => {
        return `<tr>${columns.map(column => getRowContent(capital[column]))}</tr>`
    })}</tbody>`;

    return tableHead + tableBody;
}

export const templates = {
    fillVirusTemplate,
    fillCapitalTemplate
};