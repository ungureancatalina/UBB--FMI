<?php
$pdo = new PDO("mysql:host=localhost;dbname=user_directory", "root", "");
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$page = isset($_GET['page']) ? (int) $_GET['page'] : 1;
$limit = 3;
$offset = ($page - 1) * $limit;

$total = $pdo->query("SELECT COUNT(*) FROM users")->fetchColumn();

$stmt = $pdo->prepare("SELECT nume, prenume, telefon, email FROM users LIMIT :limit OFFSET :offset");
$stmt->bindValue(':limit', $limit, PDO::PARAM_INT);
$stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
$stmt->execute();

$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
header('Content-Type: application/json');
echo json_encode([
    'data' => $data,
    'total' => $total
]);
?>
