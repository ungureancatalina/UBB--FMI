<?php
$conn = new mysqli("localhost", "root", "", "retete");
if ($conn->connect_error) die("Eroare conectare");

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $nume = $conn->real_escape_string($_POST["nume"]);
    $comentariu = $conn->real_escape_string($_POST["comentariu"]);
    $conn->query("INSERT INTO comentarii (nume, comentariu, aprobat) VALUES ('$nume', '$comentariu', 0)");
}
$comentarii = $conn->query("SELECT nume, comentariu FROM comentarii WHERE aprobat = 1");
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Articol Reteta</title>
    <link rel="stylesheet" href="ex6.css">
</head>
<body>
<header><h1>Salata de vinete cu maioneza</h1></header>

<article>
    <h2>Ingrediente</h2>
    <ul>
        <li>2 vinete mari coapte</li>
        <li>1 galbenus de ou</li>
        <li>150ml ulei</li>
        <li>sare dupa gust</li>
    </ul>
    <h2>Mod de preparare</h2>
    <p>Vinetele se coc, se curata si se toaca. Se adauga galbenusul si se incorporeaza uleiul treptat. Se sareaza dupa gust.</p>
</article>

<section>
    <h2>Adauga comentariu</h2>
    <form method="post">
        <label>Nume:
            <input type="text" name="nume" required>
        </label>
        <label>Comentariu:
            <textarea name="comentariu" required></textarea>
        </label>
        <button type="submit">Trimite comentariu</button>
    </form>
</section>

<section>
    <h2>Comentarii aprobate</h2>
    <?php while ($row = $comentarii->fetch_assoc()): ?>
        <div class="comment">
            <strong><?= htmlspecialchars($row["nume"]) ?></strong><br>
            <?= nl2br(htmlspecialchars($row["comentariu"])) ?>
        </div>
    <?php endwhile; ?>
</section>
</body>
</html>
