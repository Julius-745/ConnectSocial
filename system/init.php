<?php
require "vendor/autoload.php";
$db = \ParagonIE\EasyDB\Factory::fromArray([
    'mysql:host=localhost;dbname=connectsocial',
    'root',
    ''
]);
date_default_timezone_set('Asia/Jakarta');
session_start();
header("Content-Type", "application/json");