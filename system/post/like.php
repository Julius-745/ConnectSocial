<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

if (!isset($_POST['liked'], $_GET['id'])) {
    header("HTTP/1.1 403 Forbidden");
    echo json_encode(array("message" => "id, liked required"));
    exit;
}

$row = $db->row("SELECT * FROM posts WHERE id = ?", $_GET['id']);


if (!$row) {
    header("HTTP/1.1 404 Not Found");
} else {
    if ($_POST['liked'] == "true") {
        $db->insert("likes", ["user_id" => $_SESSION['id'], "post_id" => $_GET['id']]);
    } else {
        $db->delete("likes", ["user_id" => $_SESSION['id'], "post_id" => $_GET['id']]);
    }

    echo json_encode([
        "status" => 1,
        "liked" => $_POST['liked'] == "true",
    ]);
}
