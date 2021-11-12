<?php

require "init.php";

if (!isset($_POST['user'], $_POST['pass'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$db->prepare("INSERT INTO users (username, password) VALUES (:username, :password)")->execute(array(
    ':username' => $_POST['user'],
    ':password' => $_POST['pass']
));

echo 'OK';
