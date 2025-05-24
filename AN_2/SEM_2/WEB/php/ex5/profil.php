<?php
session_start();
if (!isset($_SESSION['user_id'])) {
  header("Location: index.html");
  exit;
}
$pdo = new PDO("mysql:host=localhost;dbname=galerie", "root", "");

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['imagine'])) {
    $file = $_FILES['imagine'];
    if ($file['error'] === 0 && $file['size'] <= 2 * 1024 * 1024) {
        $ext = strtolower(pathinfo($file['name'], PATHINFO_EXTENSION));
        if (in_array($ext, ['jpg', 'jpeg', 'png'])) {
            $nume_fisier = uniqid() . '.' . $ext;
            move_uploaded_file($file['tmp_name'], 'uploads/' . $nume_fisier);
            $stmt = $pdo->prepare("INSERT INTO poze (id_utilizator, fisier) VALUES (?, ?)");
            $stmt->execute([$_SESSION['user_id'], $nume_fisier]);
            header("Location: profil.php");
            exit;
        }
    }
}

$pozele_mele = $pdo->prepare("SELECT * FROM poze WHERE id_utilizator = ?");
$pozele_mele->execute([$_SESSION['user_id']]);
$toti_utilizatorii = $pdo->prepare("SELECT * FROM utilizatori WHERE id != ?");
$toti_utilizatorii->execute([$_SESSION['user_id']]);
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Profil <?= htmlspecialchars($_SESSION['username']) ?></title>
    <link rel="stylesheet" href="ex5.css">
</head>
<body>
<header><h1>Profilul lui <?= htmlspecialchars($_SESSION['username']) ?></h1></header>

<form method="post" enctype="multipart/form-data">
    <label>Selectează imagine:
        <input type="file" name="imagine" accept=".jpg,.jpeg,.png" required>
    </label>
    <button type="submit">Uploadează</button>
</form>

<h2>Pozele tale</h2>
<div class="galerie">
    <?php foreach ($pozele_mele as $p): ?>
        <div>
            <img src="uploads/<?= $p['fisier'] ?>" alt="">
            <form method="post" action="sterge.php">
                <input type="hidden" name="id" value="<?= $p['id'] ?>">
                <button style="background:#dc3545;">Șterge</button>
            </form>
        </div>
    <?php endforeach; ?>
</div>

<h2>Profilurile celorlalți utilizatori</h2>
<?php foreach ($toti_utilizatorii as $u): ?>
    <h3><?= htmlspecialchars($u['username']) ?></h3>
    <div class="galerie">
        <?php
        $poze = $pdo->prepare("SELECT * FROM poze WHERE id_utilizator = ?");
        $poze->execute([$u['id']]);
        foreach ($poze as $img):
        ?>
            <img src="uploads/<?= $img['fisier'] ?>" alt="">
        <?php endforeach; ?>
    </div>
<?php endforeach; ?>

<p><a href="logout.php">Logout</a></p>
</body>
</html>