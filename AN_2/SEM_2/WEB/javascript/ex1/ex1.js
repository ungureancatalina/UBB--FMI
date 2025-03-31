function muta(sursa, destinatie) {
    if (sursa.selectedIndex !== -1) {
        destinatie.appendChild(sursa.options[sursa.selectedIndex]);
    }
}