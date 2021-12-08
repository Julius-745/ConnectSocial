<?php

require "init.php";

// Converts it into a PHP object
$data = $_POST;

if (!isset($data['email'], $data['password'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}

try {
    $db->insert("users", [
        "name" => $data["name"],
        'email' => $data['email'],
        'password' => $data['password']
    ]);
    echo json_encode(array("status" => 1, "message" => "OK"));
} catch (\Throwable $th) {
    echo json_encode(array("status" => 0, "message" => "Email atau password sudah ada"));
    throw $th;
}
