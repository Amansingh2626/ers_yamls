<!DOCTYPE html>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
      <p>Hi ${name},</p>
      <p>Requested report: <u><#if (reportTitle)??> ${reportTitle} <#else> ${reportName} </#if></u> with error message: ${message} <br/>
          From Date: ${reportConfiguration.fromDate}<br/>
          To Date: ${reportConfiguration.toDate}<br/>
      Please contact Seamless Operations.</p>
      <p>Regards,</p>
      <p>
        <em>Seamless</em> <br />
      </p>
    </body>
</html>
