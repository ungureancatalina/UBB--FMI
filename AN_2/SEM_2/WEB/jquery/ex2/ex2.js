$(document).ready(function() {
    $("#formular").on("submit", function(e) {
        e.preventDefault();

        const nume = $("#nume");
        const dataNasterii = $("#data_nasterii");
        const varsta = $("#varsta");
        const email = $("#email");
        const mesaj = $("#mesaj");

        $(".error").removeClass("error");
        mesaj.removeClass("error").text("");

        let erori = [];

        if (!/^[A-Za-z\s]+$/.test(nume.val().trim())) {
            erori.push("Nume");
            nume.addClass("error");
        }
        if (!dataNasterii.val().trim()) {
            erori.push("Data Nașterii");
            dataNasterii.addClass("error");
        }
        if (isNaN(varsta.val()) || varsta.val().trim() === "" || varsta.val() < 10 || varsta.val() > 90) {
            erori.push("Varsta");
            varsta.addClass("error");
        }
        if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(email.val().trim())) {
            erori.push("Email");
            email.addClass("error");
        }

        if (erori.length > 0) {
            mesaj.text("Campurile " + erori.join(" și ") + " nu sunt completate corect").addClass("error");
            setTimeout(() => mesaj.text(""), 10000);
            return;
        }

        mesaj.text("Datele sunt completate corect").removeClass("error");
        setTimeout(() => mesaj.text(""), 10000);
        setTimeout(() => $("#formular")[0].reset(), 5000);
    });
});
