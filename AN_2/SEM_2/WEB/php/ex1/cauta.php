<?php
function h($x) {
  return htmlspecialchars($x, ENT_QUOTES, 'UTF-8');
}

$results = [];
$plecare = $_POST['plecare'] ?? '';
$sosire = $_POST['sosire'] ?? '';
$direct = isset($_POST['direct']);

try {
  $pdo = new PDO("mysql:host=localhost;dbname=trains;charset=utf8", "root", "");
  $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

  if ($plecare !== '' && $sosire !== '') {
    if ($direct) {
      $stmt = $pdo->prepare("SELECT * FROM trenuri WHERE plecare = ? AND sosire = ?");
      $stmt->execute([$plecare, $sosire]);
      $results = $stmt->fetchAll();
    } else {
      $stmt = $pdo->prepare("
        SELECT t1.nr_tren AS tren1, t1.plecare, t1.sosire AS legatura, t1.ora_plecare, t1.ora_sosire,
               t2.nr_tren AS tren2, t2.sosire, t2.ora_plecare AS leg_plecare, t2.ora_sosire AS final_sosire
        FROM trenuri t1
        JOIN trenuri t2 ON t1.sosire = t2.plecare
        WHERE t1.plecare = ? AND t2.sosire = ?
      ");
      $stmt->execute([$plecare, $sosire]);
      $results = $stmt->fetchAll();
    }
  }
} catch (Exception $e) {
  die("Eroare la conectarea bazei de date.");
}
?>

<!DOCTYPE html>
<html lang="ro">
<head>
  <meta charset="UTF-8">
  <title>Rezultate Trenuri</title>
  <link rel="stylesheet" href="ex1.css">
</head>
<body>
  <header>
    <h1>Rezultate căutare</h1>
  </header>

  <main>
    <?php if (empty($results)): ?>
      <p><em>Nu s-au găsit curse.</em></p>
    <?php else: ?>
      <table>
        <tr>
          <?php if (isset($results[0]['tren2'])): ?>
            <th>Tren 1</th><th>Plecare</th><th>Legătură</th><th>Tren 2</th><th>Destinație</th>
          <?php else: ?>
            <th>Nr Tren</th><th>Tip</th><th>Plecare</th><th>Sosire</th><th>Ora P</th><th>Ora S</th>
          <?php endif; ?>
        </tr>
        <?php foreach ($results as $r): ?>
          <tr>
            <?php if (isset($r['tren2'])): ?>
              <td><?= h($r['tren1']) ?></td>
              <td><?= h($r['plecare']) ?> (<?= h($r['ora_plecare']) ?>)</td>
              <td><?= h($r['legatura']) ?> (<?= h($r['ora_sosire']) ?>)</td>
              <td><?= h($r['tren2']) ?></td>
              <td><?= h($r['sosire']) ?> (<?= h($r['final_sosire']) ?>)</td>
            <?php else: ?>
              <td><?= h($r['nr_tren']) ?></td>
              <td><?= h($r['tip_tren']) ?></td>
              <td><?= h($r['plecare']) ?></td>
              <td><?= h($r['sosire']) ?></td>
              <td><?= h($r['ora_plecare']) ?></td>
              <td><?= h($r['ora_sosire']) ?></td>
            <?php endif; ?>
          </tr>
        <?php endforeach; ?>
      </table>
    <?php endif; ?>

    <br><a href="index.html">← Înapoi</a>
  </main>
</body>
</html>
