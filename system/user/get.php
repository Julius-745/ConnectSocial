<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

echo json_encode($db->row("SELECT * FROM users WHERE id = :id", array(
    ":id" => $_SESSION["user_id"]
)));