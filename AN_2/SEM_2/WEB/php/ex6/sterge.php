<?php
session_start();
if (!isset($_SESSION["admin"])) exit;
$conn = new mysqli("localhost", "root", "", "retete");
$id = (int)$_POST["id"];
$conn->query("DELETE FROM comentarii WHERE id=$id");
header("Location: modereaza.php");
