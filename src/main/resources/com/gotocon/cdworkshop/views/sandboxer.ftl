<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!-->
<html class="no-js" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"
      xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->
<head>
    <title>Sandboxer - GOTO Amsterdam 2015</title>
    <meta charset="ISO-8859-1">
    <meta name="description" content="">
    <meta name="HandheldFriendly" content="True">
    <meta name="MobileOptimized" content="320">
    <meta http-equiv="cleartype" content="on">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="viewport" content="initial-scale=1.0, width=100%, maximum-scale=1, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="../assets/css/styles.css">
</head>
<body>
    <div id="container">
        <h1>Sandboxer</h1>
        <h3>GOTO Amsterdam 2015 - CD Workshop</h3>
        <p class="intro">
           You're looking at the Sandboxer, an application to find them, integrate them all and in the html bind them. The Sandboxer currently runs in version ${templateData.versionNumber}.
        </p>
        <div id="client-blocks">
            <#list templateData.clients as client>
                <div class="client-block panel">
                    <div class="hover ${client.status}">
                        <dl>
                            <dt>Endpoint:</dt><dd>${client.endpoint}</dd>
                            <dt>Status (HttpStatusCode):</dt><dd>${client.status} (${client.statusCode})</dd>
                            <dt>Author:</dt><dd>${client.author}</dd>
                            <dt>Comment:</dt><dd>${client.comment}</dd>
                            <dt>Payload:</dt><dd>${client.payload}</dd>
                        </dl>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</body>
</html>