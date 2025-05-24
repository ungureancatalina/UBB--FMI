$(document).ready(function () {
    function incarcaRezultate() {
        $.ajax({
            url: 'filtru.php',
            method: 'POST',
            data: $('#filterForm').serialize(),
            success: function (raspuns) {
                $('#rezultate').html(raspuns);
            }
        });
    }

    $('#filterForm select').on('change', incarcaRezultate);

    incarcaRezultate();
});
