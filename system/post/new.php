<?php

require "../init.php";

if (!$_SESSION) {
    header("HTTP/1.1 401 Authorized");
    exit;
}

if (!isset($_POST['content'])) {
    header("HTTP/1.1 400 Bad Request");
    exit;
}

$upload = new \Delight\FileUpload\FileUpload();
$upload->withTargetDirectory(realpath('../images/'));
$upload->from('image');

try {
    $uploadedFile = $upload->save();
    $image = '/images/' . $uploadedFile->getFilenameWithExtension();
    $db->insert('posts', [
        'user_id' => $_SESSION['id'],
        'title' => $_POST['title'],
        'content' => $_POST['content'],
        'image' => $image,
    ]);
    echo json_encode(array("status" => 1, "message" => "OK"));
} catch (\Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(array("status" => 0, "message" => "Error"));
}
