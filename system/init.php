<?php

$db = new PDO('mysql:host=localhost;dbname=connectsocial', 'root', '');
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

session_start();