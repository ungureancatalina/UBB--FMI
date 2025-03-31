let sortDirections = [true, true, true];

function sortTable(columnIndex) {
    let table = document.getElementById("sortableTable");
    let tbody = table.querySelector("tbody");
    let rows = Array.from(tbody.rows);

    let isNumeric = !isNaN(rows[0].cells[columnIndex].innerText);
    let isAscending = sortDirections[columnIndex];

    rows.sort((rowA, rowB) => {
        let valA = rowA.cells[columnIndex].innerText;
        let valB = rowB.cells[columnIndex].innerText;

        if (isNumeric) {
            valA = parseFloat(valA);
            valB = parseFloat(valB);
        }

        return isAscending ? valA > valB ? 1 : -1 : valA < valB ? 1 : -1;
    });

    rows.forEach(row => tbody.appendChild(row));

    sortDirections[columnIndex] = !isAscending;
}
