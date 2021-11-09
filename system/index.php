<?php

if (!isset($_POST['user'], $_POST['pass'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$db = new PDO('mysql:host=localhost;dbname=connectsocial', 'root', '');
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
$db->prepare("INSERT INTO users (username, password) VALUES (:username, :password)")->execute(array(
    ':username' => $_POST['user'],
    ':password' => $_POST['pass']
));

echo 'OK';
