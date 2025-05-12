$(document).ready(function() {
    let selected = [];
    let matchedPairs = 0;
    let isImageMode = false;
    let isBusy = false;

    const numbers = [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8];
    const images = [
        'f1.jpg', 'f1.jpg', 'f2.jpg', 'f2.jpg', 'f3.jpg', 'f3.jpg',
        'f4.jpg', 'f4.jpg', 'f5.jpg', 'f5.jpg', 'f6.jpg', 'f6.jpg',
        'f7.jpg', 'f7.jpg', 'f8.jpg', 'f8.jpg'
    ];

    function createBoard() {
        $('#gameBoard').empty();
        $('#winMessage').text('');
        const data = isImageMode ? [...images] : [...numbers];
        data.sort(() => Math.random() - 0.5);

        data.forEach((item, index) => {
            const $cell = $('<div class="cell"></div>').attr('data-index', index);

            if (isImageMode) {
                const $img = $('<img>').attr('src', item);
                $cell.append($img);
            }

            $cell.on('click', function () {
                selectCard($(this), item);
            });

            $('#gameBoard').append($cell);
        });
    }

    function selectCard($cell, item) {
        if (isBusy || selected.length >= 2 || $cell.hasClass('revealed')) return;

        if (isImageMode) {
            $cell.find('img').show();
        } else {
            $cell.text(item);
        }

        $cell.addClass('revealed');
        selected.push({ $cell, item });

        if (selected.length === 2) {
            isBusy = true;
            setTimeout(checkMatch, 300);
        }
    }

    function checkMatch() {
        const [first, second] = selected;

        if (first.item === second.item) {
            matchedPairs++;
            if (matchedPairs === (isImageMode ? images.length : numbers.length) / 2) {
                setTimeout(() => {
                    $('#winMessage').text('Ai câștigat!');
                }, 300);
            }
        } else {
            setTimeout(() => {
                if (isImageMode) {
                    first.$cell.find('img').hide();
                    second.$cell.find('img').hide();
                } else {
                    first.$cell.text('');
                    second.$cell.text('');
                }
                first.$cell.removeClass('revealed');
                second.$cell.removeClass('revealed');
            }, 1000);
        }

        setTimeout(() => {
            selected = [];
            isBusy = false;
        }, 2000);
    }

    function restartGame() {
        matchedPairs = 0;
        selected = [];
        isBusy = false;
        isImageMode = !isImageMode;

        $('#modeLabel').text(`Mod: ${isImageMode ? 'Imagini' : 'Numere'}`);
        createBoard();
    }

    $('#restartButton').on('click', restartGame);

    createBoard();
});
