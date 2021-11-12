<?php

require "init.php";

// Takes raw data from the request
$json = file_get_contents('php://input');

// Converts it into a PHP object
$data = json_decode($json);

if (!isset($data['username'], $data['password'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

$db->prepare("INSERT INTO users (username, password) VALUES (:username, :password)")->execute(array(
    ':username' => $data['username'],
    ':password' => $data['password']
));

echo 'OK';
