$(document).ready(function() {
    let sortDirections = [];

    $('#sortableTable th').click(function() {
        const columnIndex = $(this).data('col');
        const $rows = $('#sortableTable tbody tr').get();

        const isNumeric = !isNaN($($rows[0]).children().eq(columnIndex).text().trim());
        sortDirections[columnIndex] = !sortDirections[columnIndex];
        const isAscending = sortDirections[columnIndex];

        $rows.sort(function(rowA, rowB) {
            let valA = $(rowA).children().eq(columnIndex).text().trim();
            let valB = $(rowB).children().eq(columnIndex).text().trim();

            if (isNumeric) {
                valA = parseFloat(valA);
                valB = parseFloat(valB);
            }

            if (valA === valB) return 0;
            return isAscending ? (valA > valB ? 1 : -1) : (valA < valB ? 1 : -1);
        });

        $.each($rows, function(index, row) {
            $('#sortableTable tbody').append(row);
        });
    });
});
