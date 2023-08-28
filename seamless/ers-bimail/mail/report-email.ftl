<!DOCTYPE html>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
      <p>Hi ${name},</p>
      <p>Please find attached requested report <u><#if (reportTitle)??
      > ${reportTitle} <#else> ${reportName} </#if></u></p>
      <p>Details: <br/>
          Message: ${message}<br/>
          From Date: ${reportConfiguration.fromDate}<br/>
          To Date: ${reportConfiguration.toDate}<br/>
      </p>
      <p>Regards,</p>
      <p>
        <em>Seamless</em> <br />
      </p>
    </body>
</html>


