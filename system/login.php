<?php

require "init.php";

if (!isset($_POST['user'], $_POST['pass'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$stmt = $db->prepare("SELECT * FROM users WHERE username = :username AND password = :password");

$stmt->execute(array(
    ":username" => $_POST["user"],
    ":password" => $_POST["pass"]
));

$stmt->setFetchMode(PDO::FETCH_ASSOC);

$row = $stmt->fetch();

if ($row) {
    session_regenerate_id(true);
    $_SESSION["user"] = $row["username"];
} else {
    header("HTTP/1.1 403 Forbidden");
    exit;
}