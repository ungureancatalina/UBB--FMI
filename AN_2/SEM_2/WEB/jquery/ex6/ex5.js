$(document).ready(function () {
    const size = 4;
    let emptyCellIndex;

    function initPuzzle() {
        const numbers = [...Array(15).keys()].map(n => n + 1).concat("").sort(() => Math.random() - 0.5);
        $('#puzzleBoard').empty();

        numbers.forEach((num, index) => {
            const $tile = $('<div></div>').addClass('tile').text(num);
            if (num === "") {
                $tile.addClass('empty');
                emptyCellIndex = index;
            }
            $('#puzzleBoard').append($tile);
        });
    }

    function checkWin() {
        const $tiles = $('.tile');
        for (let i = 0; i < 15; i++) {
            if ($tiles.eq(i).text().trim() !== (i + 1).toString()) {
                return false;
            }
        }
        return $tiles.eq(15).text().trim() === "";
    }

    function showWinMessage() {
        if (!$('#winMessage').length) {
            $('<p id="winMessage"></p>').insertAfter('#puzzleBoard');
        }
        $('#winMessage').text("Felicitări! Ai rezolvat puzzle-ul!").css({
            color: "green",
            fontWeight: "bold",
            fontSize: "18px"
        });
    }

    $(document).on("keydown", function (e) {
        const $tiles = $(".tile");
        let row = Math.floor(emptyCellIndex / size);
        let col = emptyCellIndex % size;
        let targetIndex;

        if (e.key === "ArrowUp" && row < size - 1) targetIndex = emptyCellIndex + size;
        if (e.key === "ArrowDown" && row > 0) targetIndex = emptyCellIndex - size;
        if (e.key === "ArrowLeft" && col < size - 1) targetIndex = emptyCellIndex + 1;
        if (e.key === "ArrowRight" && col > 0) targetIndex = emptyCellIndex - 1;

        if (targetIndex !== undefined) {
            const $empty = $tiles.eq(emptyCellIndex);
            const $target = $tiles.eq(targetIndex);

            const tempText = $empty.text();
            $empty.text($target.text());
            $target.text(tempText);

            $empty.toggleClass("empty");
            $target.toggleClass("empty");

            emptyCellIndex = targetIndex;

            if (checkWin()) {
                showWinMessage();
            }
        }
    });

    initPuzzle();
});
