$(document).ready(function() {
    $("#lista1, #lista2").on("dblclick", function() {
        const $optiuneSelectata = $(this).find("option:selected");
        const $destinatie = this.id === "lista1" ? $("#lista2") : $("#lista1");
        $destinatie.append($optiuneSelectata);
    });
});
