<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!-->
<html class="no-js" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"
      xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->
<head>
    <title>Sandboxer Html Page</title>
    <meta charset="ISO-8859-1">
    <meta name="description" content="">
    <meta name="HandheldFriendly" content="True">
    <meta name="MobileOptimized" content="320">
    <meta http-equiv="cleartype" content="on">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="viewport" content="initial-scale=1.0, width=100%, maximum-scale=1, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="../assets/styles.css">
</head>
<body>
    <div id="main-container">
        Welcome, to the Sandboxer Service! You are using this service in version ${templateData.versionNumber}...
    </div>
    see the service ports afterwards:
    <div id="client-blocks">
        <#list templateData.clients as client>
            <div class="client-block">
                <dl>
                    <dt>Endpoint:</dt><dd>${client.endpoint}</dd>
                    <dt>Response:</dt><dd>${client.response}</dd>
                </dl>
            </div>
        </#list>
    </div>
</body>
</html>