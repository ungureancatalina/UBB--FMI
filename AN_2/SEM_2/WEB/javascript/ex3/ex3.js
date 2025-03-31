let selected = [];
let matchedPairs = 0;
let gameBoard = document.getElementById('gameBoard');
let restartButton = document.getElementById('restartButton');
let winMessage = document.getElementById('winMessage');

const numbers = [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8];
const images = [
    'f1.jpg', 'f1.jpg', 'f2.jpg', 'f2.jpg', 'f3.jpg', 'f3.jpg',
    'f4.jpg', 'f4.jpg', 'f5.jpg', 'f5.jpg', 'f6.jpg', 'f6.jpg',
    'f7.jpg', 'f7.jpg', 'f8.jpg', 'f8.jpg'
];

let isImageMode = false;
let isBusy = false;

function createBoard() {
    gameBoard.innerHTML = '';
    winMessage.textContent = '';
    const data = isImageMode ? images : numbers;
    data.sort(() => Math.random() - 0.5);

    data.forEach((item, index) => {
        let cell = document.createElement('div');
        cell.classList.add('cell');
        cell.setAttribute('data-index', index.toString());

        if (isImageMode) {
            let img = document.createElement('img');
            img.setAttribute('src', item.toString());
            cell.appendChild(img);
        } else {
            cell.textContent = '';
        }

        cell.addEventListener('click', () => selectCard(cell, item));
        gameBoard.appendChild(cell);
    });
}

function selectCard(cell, item) {
    if (isBusy || selected.length >= 2 || cell.classList.contains('revealed')) return;

    if (isImageMode) {
        cell.querySelector('img').style.display = 'block';
    } else {
        cell.textContent = item;
    }

    cell.classList.add('revealed');
    selected.push({ cell, item });

    if (selected.length === 2) {
        isBusy = true;
        setTimeout(checkMatch, 500);
    }
}

function checkMatch() {
    const [first, second] = selected;

    if (first.item === second.item) {
        matchedPairs++;
        if (matchedPairs === (isImageMode ? images.length : numbers.length) / 2) {
            setTimeout(() => {
                winMessage.textContent = 'Ai câștigat!';
            }, 300);
        }
    } else {
        setTimeout(() => {
            if (isImageMode) {
                first.cell.querySelector('img').style.display = 'none';
                second.cell.querySelector('img').style.display = 'none';
            } else {
                first.cell.textContent = '';
                second.cell.textContent = '';
            }
            first.cell.classList.remove('revealed');
            second.cell.classList.remove('revealed');
        }, 2000);
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
    document.getElementById('modeLabel').textContent = `Mod: ${isImageMode ? 'Imagini' : 'Numere'}`;
    createBoard();
}

createBoard();
restartButton.addEventListener('click', restartGame);
