<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

echo json_encode($db->update("users", array_filter(array_intersect_key(
    $_POST,
    array_flip(["name", "email", "password", "lang"])
)), array(
    "id" => $_SESSION["id"]
)));
