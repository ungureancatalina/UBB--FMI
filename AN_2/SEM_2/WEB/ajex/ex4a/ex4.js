let player;

window.addEventListener("DOMContentLoaded", function () {
    const chooseX = document.getElementById("chooseX");
    const choose0 = document.getElementById("choose0");
    const cells = document.querySelectorAll("#gameBoard td");

    chooseX.addEventListener("click", () => {
        player = "X";
        chooseX.style.display = "none";
        choose0.style.display = "none";
    });

    choose0.addEventListener("click", () => {
        player = "0";
        chooseX.style.display = "none";
        choose0.style.display = "none";
        makeMove();
    });

    cells.forEach((cell) => {
        cell.addEventListener("click", () => {
            if (cell.textContent === "" && player !== undefined) {
                cell.textContent = player;
                makeMove();
            }
        });
    });

    function makeMove() {
        const board = [];
        document.querySelectorAll("#gameBoard tr").forEach((row) => {
            const rowData = [];
            row.querySelectorAll("td").forEach((cell) => {
                rowData.push(cell.textContent);
            });
            board.push(rowData);
        });

        const data = new URLSearchParams();
        data.append("player", player);
        data.append("board", JSON.stringify(board));

        fetch("joc.php", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: data,
        })
            .then((res) => res.json())
            .then((response) => {
                const newBoard = response.board;
                document.querySelectorAll("#gameBoard tr").forEach((row, i) => {
                    row.querySelectorAll("td").forEach((cell, j) => {
                        cell.textContent = newBoard[i][j] || "";
                    });
                });

                if (response.gameOver) {
                    let message;
                    switch (response.gameOver) {
                        case "player":
                            message = "You won!";
                            break;
                        case "computer":
                            message = "Computer won!";
                            break;
                        case "draw":
                            message = "It's a draw!";
                            break;
                    }
                    alert(message);
                    cells.forEach((cell) =>
                        cell.replaceWith(cell.cloneNode(true))
                    );
                }
            });
    }
});
