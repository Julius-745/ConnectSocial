<?php

require "init.php";

if (!isset($_POST['email'], $_POST['pass'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$row = $db->row("SELECT * FROM users WHERE email = ? AND password = ?", $_POST['email'], $_POST['pass']);

if ($row) {
    session_regenerate_id(true);
    $_SESSION["id"] = $row["id"];
    echo json_encode(array("message" => "OK"));
} else {
    header("HTTP/1.1 403 Forbidden");
    echo json_encode(array("message" => "Salah email atau password"));
}
