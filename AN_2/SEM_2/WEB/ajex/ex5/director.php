<?php
header('Content-Type: application/json');

define('ROOT_PATH', realpath(__DIR__ . '/testroot'));

function isSafePath($target) {
    return str_starts_with(realpath($target), ROOT_PATH);
}

if (isset($_GET['file'])) {
    $file = realpath($_GET['file']);
    if ($file && is_file($file) && isSafePath($file)) {
        echo file_get_contents($file);
    } else {
        http_response_code(403);
        echo "Access denied";
    }
    exit;
}

$path = $_GET['path'] ?? '.';
$target = realpath(ROOT_PATH . '/' . ltrim($path, '/'));

if (!$target || !isSafePath($target) || !is_dir($target)) {
    http_response_code(403);
    echo json_encode(['error' => 'Invalid path']);
    exit;
}

$result = [];
foreach (scandir($target) as $entry) {
    if ($entry === '.' || $entry === '..') continue;
    $fullPath = $target . '/' . $entry;
    $result[] = [
        'name' => $entry,
        'path' => $path . '/' . $entry,
        'type' => is_dir($fullPath) ? 'dir' : 'file'
    ];
}

echo json_encode($result);
