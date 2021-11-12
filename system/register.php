<?php

require "init.php";

// Converts it into a PHP object
$data = $_POST;

if (!isset($data['username'], $data['password'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$db->prepare("INSERT INTO users (username, password) VALUES (:username, :password)")->execute(array(
    ':username' => $data['username'],
    ':password' => $data['password']
));

echo 'OK';
