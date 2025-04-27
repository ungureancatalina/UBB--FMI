function valideaza(ob) {
    this.event.preventDefault();
    let nume = document.getElementById("nume");
    let dataNasterii = document.getElementById("data_nasterii");
    let varsta = document.getElementById("varsta");
    let email = document.getElementById("email");
    let mesaj = document.getElementById("mesaj");

    [nume, dataNasterii, varsta, email].forEach(input => input.classList.remove("error"));

    let erori = [];

    if (!/^[A-Za-z\s]+$/.test(nume.value.trim())) {
        erori.push("Nume");
        nume.classList.add("error");
    }
    if (!dataNasterii.value.trim()) {
        erori.push("Data Nașterii");
        dataNasterii.classList.add("error");
    }
    if (isNaN(varsta.value) || varsta.value.trim() === "" || varsta.value < 10 || varsta.value > 90) {
        erori.push("Varsta");
        varsta.classList.add("error");
    }
    if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(email.value.trim())) {
        erori.push("Email");
        email.classList.add("error");
    }

    if (erori.length > 0) {
        mesaj.textContent = "Campurile " + erori.join(" și ") + " nu sunt completate corect";
        mesaj.classList.add("error");

        setTimeout(() => { mesaj.textContent = ""; }, 10000);
        return false;
    }

    mesaj.textContent = "Datele sunt completate corect";
    mesaj.classList.remove("error");

    setTimeout(() => { mesaj.textContent = ""; }, 10000);
    setTimeout(() => { document.getElementById("formular").reset(); }, 5000);

    return true;
}
