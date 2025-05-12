$(document).ready(function () {
    const table = $('.sortable-vertical');
    const thead = table.find('thead tr');
    const tbody = table.find('tbody');
    const sortStates = {};

    thead.find('th').each(function (index) {
        sortStates['head_' + index] = 1;

        if (index === 0) return;

        $(this).on('click', function () {
            const direction = sortStates['head_' + index];
            sortStates['head_' + index] *= -1;

            const headers = thead.find('th').slice(1).map((_, th) => $(th).text()).get();
            const row1 = tbody.find('tr').eq(0).find('td');
            const row2 = tbody.find('tr').eq(1).find('td');

            const cols = headers.map((header, i) => ({
                index: i + 1,
                header: header,
                values: [row1.eq(i).text(), row2.eq(i).text()]
            }));

            cols.sort((a, b) => {
                const valA = a.values[index - 1];
                const valB = b.values[index - 1];
                const parsedA = parseFloat(valA) || valA;
                const parsedB = parseFloat(valB) || valB;

                if (parsedA < parsedB) return -1 * direction;
                if (parsedA > parsedB) return 1 * direction;
                return 0;
            });

            cols.forEach((col, i) => {
                thead.find('th').eq(i + 1).text(col.header);
                tbody.find('tr').eq(0).find('td').eq(i).text(col.values[0]);
                tbody.find('tr').eq(1).find('td').eq(i).text(col.values[1]);
            });
        });
    });

    tbody.find('th').each(function (rowIndex) {
        sortStates['side_' + rowIndex] = 1;

        $(this).on('click', function () {
            const direction = sortStates['side_' + rowIndex];
            sortStates['side_' + rowIndex] *= -1;

            const headers = thead.find('th').slice(1).map((_, th) => $(th).text()).get();
            const row1 = tbody.find('tr').eq(0).find('td');
            const row2 = tbody.find('tr').eq(1).find('td');

            const cols = headers.map((header, i) => ({
                index: i + 1,
                header: header,
                values: [row1.eq(i).text(), row2.eq(i).text()]
            }));

            cols.sort((a, b) => {
                const valA = a.values[rowIndex];
                const valB = b.values[rowIndex];
                const parsedA = parseFloat(valA) || valA;
                const parsedB = parseFloat(valB) || valB;

                if (parsedA < parsedB) return -1 * direction;
                if (parsedA > parsedB) return 1 * direction;
                return 0;
            });

            cols.forEach((col, i) => {
                thead.find('th').eq(i + 1).text(col.header);
                tbody.find('tr').eq(0).find('td').eq(i).text(col.values[0]);
                tbody.find('tr').eq(1).find('td').eq(i).text(col.values[1]);
            });
        });
    });
});
