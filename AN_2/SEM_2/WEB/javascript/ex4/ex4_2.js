let sortDirections = [];

function sortTable(rowIndex) {
    const table = document.getElementById("sortableTable");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.rows);

    if (sortDirections[rowIndex] === undefined) {
        sortDirections[rowIndex] = true;
    }

    const rowToSort = rows[rowIndex];
    const cells = Array.from(rowToSort.cells);

    const indices = cells.map((_, i) => i);
    const ascending = sortDirections[rowIndex];

    indices.sort((a, b) => {
        let valA = cells[a].textContent.trim();
        let valB = cells[b].textContent.trim();

        valA = isNaN(valA) ? valA : parseFloat(valA);
        valB = isNaN(valB) ? valB : parseFloat(valB);

        if (valA < valB) return ascending ? -1 : 1;
        if (valA > valB) return ascending ? 1 : -1;
        return 0;
    });

    rows.forEach((row) => {
        const dataCells = Array.from(row.cells).slice(1);
        const reordered = indices.map(i => dataCells[i].cloneNode(true));

        while (row.cells.length > 1) {
            row.deleteCell(1);
        }

        reordered.forEach(cell => row.appendChild(cell));
    });

    sortDirections[rowIndex] = !ascending;
}
