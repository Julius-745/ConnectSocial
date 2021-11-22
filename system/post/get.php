<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

if (!isset($_GET['id'])) {
    header("HTTP/1.1 403 Forbidden");
    exit;
}



$row = $stmt->row("SELECT * FROM posts WHERE id = ?",  $_GET['id']);

if (!$row) {
    header("HTTP/1.1 404 Not Found");
} else {

    $stmt = $stmt->row("SELECT * FROM posts_likes WHERE user_id = ? AND post_id = ?", $_SESSION['id'], $_GET['id']);

    $row['liked'] = boolval($stmt->fetch());

    echo json_encode($row);
}
