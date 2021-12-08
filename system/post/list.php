<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

if (!isset($_GET['offset'])) {
    $offset = 0;
} else {
    $offset = $_GET['offset'];
}

$stmt = $db->run("SELECT * FROM posts LIMIT 30 OFFSET ? ORDER BY id DESC", $offset);

echo json_encode($stmt);
