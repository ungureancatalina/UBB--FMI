let emptyCellIndex;
const size = 4;

function initPuzzle() {
    let puzzleBoard = document.getElementById("puzzleBoard");
    puzzleBoard.innerHTML = "";
    let numbers = [...Array(15).keys()].map(n => n + 1).concat("").sort(() => Math.random() - 0.5);

    numbers.forEach((num, index) => {
        let tile = document.createElement("div");
        tile.textContent = num.toString();
        tile.className = "tile";
        if (num === "") {
            tile.classList.add("empty");
            emptyCellIndex = index;
        }
        puzzleBoard.appendChild(tile);
    });
}

document.addEventListener("keydown", (e) => {
    let tiles = document.querySelectorAll(".tile");
    let row = Math.floor(emptyCellIndex / size);
    let col = emptyCellIndex % size;
    let targetIndex;

    if (e.key === "ArrowUp" && row < size - 1) targetIndex = emptyCellIndex + size;
    if (e.key === "ArrowDown" && row > 0) targetIndex = emptyCellIndex - size;
    if (e.key === "ArrowLeft" && col < size - 1) targetIndex = emptyCellIndex + 1;
    if (e.key === "ArrowRight" && col > 0) targetIndex = emptyCellIndex - 1;

    if (targetIndex !== undefined) {
        [tiles[emptyCellIndex].textContent, tiles[targetIndex].textContent] =
            [tiles[targetIndex].textContent, tiles[emptyCellIndex].textContent];

        tiles[emptyCellIndex].classList.toggle("empty");
        tiles[targetIndex].classList.toggle("empty");

        emptyCellIndex = targetIndex;
    }
});

initPuzzle();